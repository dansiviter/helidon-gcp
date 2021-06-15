
# 1st stage, build the app
FROM adoptopenjdk/openjdk16:alpine as build
RUN apk add --no-cache bash maven

WORKDIR /helidon

# Create a first layer to cache the "Maven World" in the local repository.
# Incremental docker builds will always resume after that, unless you update
# the pom
ADD pom.xml .
RUN mvn package -Dmaven.test.skip -Declipselink.weave.skip

# Do the Maven build!
# Incremental docker builds will resume here when you change sources
ADD src src
RUN mvn package -DskipTests
RUN echo "done!"

# 2nd stage, build the runtime image
FROM adoptopenjdk/openjdk16:alpine-jre
WORKDIR /helidon

# Copy the binary built in the 1st stage
COPY --from=build /helidon/target/helidon-gcp.jar ./
COPY --from=build /helidon/target/libs ./libs

CMD ["java", "-jar", "helidon-gcp.jar"]

EXPOSE 8080

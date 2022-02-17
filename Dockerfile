# 1st stage, build the app
FROM eclipse-temurin:17-jdk-alpine as build
RUN apk add --no-cache bash maven

WORKDIR /helidon

ENV MAVEN_CLI_OPTS="-B -e -ntp"

# Create a first layer to cache the "Maven World" in the local repository.
# Incremental docker builds will always resume after that, unless you update
# the pom
ADD pom.xml .
RUN mvn $MAVEN_CLI_OPTS  package -Dmaven.test.skip -Declipselink.weave.skip

# Do the Maven build!
# Incremental docker builds will resume here when you change sources
ADD src src
RUN mvn $MAVEN_CLI_OPTS  package -DskipTests
RUN echo "done!"

# 2nd stage, build the runtime image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /helidon

# Copy the binary built in the 1st stage
COPY --from=build /helidon/target/helidon-gcp.jar ./
COPY --from=build /helidon/target/libs ./libs

CMD ["java", "-jar", "helidon-gcp.jar"]

EXPOSE 8080

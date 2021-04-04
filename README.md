# Helidon GCP #

This is a demonstrator application that hooks into Google Cloud Operations suite of Logging, Monitoring and Tracing.

## Docker Images ##

### 1. Latest Dependencies ###

* GraalVM 21.0.0.2
* Helidon Maven Plugin 2.1.3

```
REPOSITORY   TAG       SIZE
helidon      latest    235MB
helidon      jlink     127MB
helidon      native    93.5MB
```

### 2. Shrink Dependencies ###

Remove unused Microprofile dependencies (e.g. JWT, Fault Tolerance)

```
REPOSITORY   TAG       SIZE
helidon      latest    234MB
helidon      jlink     124MB
helidon      native    87.1MB
```

### 3. GCP Operations Suite ###

Add Logging, Monitoring and Tracing (inc. Log>Trace correlation).

```
REPOSITORY   TAG       SIZE
helidon      latest    263MB
helidon      jlink     159MB
helidon      native    130MB
```

## Running Locally ##

To be able to use the GCP credentials when running locally (via Docker for Desktop or similar):

```
gcloud auth application-default login
docker run --rm -it -p 8080:8080 `
  -e CLOUDSDK_CONFIG=/gcloud `
  -v $Env:APPDATA/gcloud/:/gcloud:ro `
  helidon:<tag>
```

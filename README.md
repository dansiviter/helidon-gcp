[![GitHub Workflow Status](https://img.shields.io/github/workflow/status/dansiviter/helidon-gcp/Build?style=flat-square)](https://github.com/dansiviter/helidon-gcp/actions/workflows/build.yaml) ![Java 16](https://img.shields.io/badge/-Java%2016%2B-informational?style=flat-square)

# Helidon GCP #

This is a demonstrator application using [Helidon](https://helidon.io) that hooks into Google Cloud Operations suite of Logging, Monitoring and Tracing:
* Cloud Logging: Java Util Log Handlers using asynchronous processing,
* Cloud Monitoring: Microprofile Metrics,
* Cloud Tracing: Microprofile Trace via OpenTelemetry API and log message correlation,
* Cloud Error Reporting: all `ERROR` messages flagged augmented with `serviceContext` version and name from `MANIFEST.MF`.

<img src="./etc/images/TraceScreenshot.png" width=50%>

## Docker Images ##

This has 3 types of images:
* vanilla: OpenJDK Java 16 Alpine JRE,
* jlink: Java Platform Module System OpenJDK Java 16 Alpine,
* native: GraalVM statically linked binary off `scratch` using MUSL libc.

They all have their benefits but also pain-points.

| Image                 | Size      | Start     | Stop    | Notes            |
|-----------------------|-----------|-----------|---------|------------------|
| `helidon:vanilla`     | 236MB     | 3,735ms   | 22ms    | APKO jre17 image |
| `helidon:jlink`       | 148MB     | 2,470ms   | 18ms    | APKO JLink image |
| ~~`helidon:native`~~  | ~~131MB~~ | ~~204ms~~ | ~~4ms~~ | Build failing    |

> :warning: Native image does have some issues around flushing final logs (see Limitations) so `STOP` is artificially fast.


## Running Locally ##

To be able to use the GCP credentials when running locally (via Docker for Desktop or similar):

```powershell
gcloud auth application-default login
docker run --rm -it `
  -p 8080:8080 `
  -e CLOUDSDK_CONFIG=/gcloud `
  -v $Env:APPDATA/gcloud/:/gcloud:ro `
  -v $pwd/error/:/error:rw `
  helidon-gcp:<tag>
```


## Build via Graal Locally ##

```powershell
docker run `
  -v $pwd/:/helidon:rw `
  -v $HOME/.m2/repository/:/root/.m2/repository/:rw `
  -v $Env:APPDATA/gcloud/:/gcloud:ro `
  -p 8080:8080 `
  --entrypoint bash `
  -it `
  --rm `
  helidon/jdk17-graalvm-maven:21.3.0
```

## Limitations ##

* [oracle/graal#3351](https://github.com/oracle/graal/issues/3351) Just SegFaults every time. On disabling Tracing, the issue appears to stop.

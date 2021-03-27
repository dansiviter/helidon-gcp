# Helidon GCP #

This is a demonstrator application that hooks into Google Cloud Operations suite of Logging, Monitoring and Tracing.


## Latest Dependencies #

* GraalVM 21.0.0.2
* Helidon Maven Plugin 2.1.3

```
REPOSITORY   TAG       IMAGE ID       CREATED          SIZE
helidon      native    507d32ead0ff   7 minutes ago    93.5MB
helidon      jlink     9d5a8bbccc1b   10 minutes ago   127MB
helidon      latest    eead063cc036   13 minutes ago   235MB
```

# Shrink Dependencies #

Remove unnecessary Microprofile libraries:

```
REPOSITORY   TAG       IMAGE ID       CREATED              SIZE
helidon      native    cb065d98f704   12 seconds ago       87.1MB
helidon      jlink     964fe08f3b68   About a minute ago   124MB
helidon      latest    b62d48cb651d   2 minutes ago        234MB
```



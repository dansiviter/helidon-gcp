package uk.dansiviter.helidon.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@ApplicationScoped
@ApplicationPath("/")
@OpenAPIDefinition(info = @Info(title = "Helidon Application", version = "tbd"))
public class HelidonApplication extends Application { }

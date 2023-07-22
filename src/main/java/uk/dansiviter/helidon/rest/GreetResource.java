package uk.dansiviter.helidon.rest;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static java.lang.String.format;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import uk.dansiviter.helidon.GreetingProvider;
import uk.dansiviter.helidon.model.Greeting;

/**
 * A simple JAX-RS resource to greet you. Examples:
 *
 * Get default greeting message: curl -X GET http://localhost:8080/greet
 *
 * Get greeting message for Joe: curl -X GET http://localhost:8080/greet/Joe
 *
 * Change greeting curl -X PUT -H "Content-Type: application/json" -d
 * '{"greeting" : "Howdy"}' http://localhost:8080/greet
 *
 * The message is returned as a JSON object.
 */
@Path("v1/greet")
@RequestScoped
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class GreetResource {
	@Inject
	private GreetingProvider greetingProvider;

	/**
	 * Return a worldly greeting message.
	 *
	 * @return {@link JsonObject}
	 */
	@GET
	public Greeting get() {
		return get("World");
	}

	/**
	 * Return a greeting message using the name that was provided.
	 *
	 * @param name the name to greet
	 * @return {@link JsonObject}
	 */
	@Path("{name}")
	@GET
	public Greeting get(@PathParam("name") String name) {
		return new Greeting(format("%s %s!", greetingProvider.getMessage(), name));
	}

	/**
	 * Set the greeting to use in future messages.
	 *
	 * @param jsonObject JSON containing the new greeting
	 * @return {@link Response}
	 */
	@PUT
	@RequestBody(name = "greeting", required = true, content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.STRING, example = "{\"greeting\" : \"Hola\"}")))
	@APIResponse(name = "normal", responseCode = "204", description = "Greeting updated")
	@APIResponse(name = "missing 'greeting'", responseCode = "400", description = "JSON did not contain setting for 'greeting'")
	public void updateGreeting(@Valid Greeting greeting) {
		greetingProvider.setMessage(greeting.greeting());
	}
}

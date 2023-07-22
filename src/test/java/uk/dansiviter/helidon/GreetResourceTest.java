package uk.dansiviter.helidon;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;

import org.junit.jupiter.api.Test;

import io.helidon.microprofile.tests.junit5.Configuration;
import io.helidon.microprofile.tests.junit5.HelidonTest;
import uk.dansiviter.helidon.model.Greeting;

@HelidonTest
@Configuration(profile = "test")
class GreetResourceTest {
	@Test
	void testHelloWorld(WebTarget target) {
		var actual = target.path("v1/greet").request().get(Greeting.class);
		assertEquals("Hello World!", actual.greeting(), "default message");

		actual = target.path("v1/greet/Joe").request().get(Greeting.class);
		assertEquals("Hello Joe!", actual.greeting(), "hello Joe message");

		var r = target.path("v1/greet").request()
				.put(Entity.entity(new Greeting("Hola"), APPLICATION_JSON));
		assertEquals(204, r.getStatus(), "PUT status code");

		r = target.path("v1/greet").request()
			.put(Entity.entity(new Greeting(" "), APPLICATION_JSON));
		assertEquals(400, r.getStatus(), "PUT status code");

		actual = target.path("v1/greet/Jose").request().get(Greeting.class);
		assertEquals("Hola Jose!", actual.greeting(), "hola Jose message");

		r = target.path("metrics").request().get();
		assertEquals(200, r.getStatus(), "GET metrics status code");

		r = target.path("health").request().get();
		assertEquals(200, r.getStatus(), "GET health status code");
	}
}

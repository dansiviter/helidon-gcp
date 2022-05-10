package uk.dansiviter.helidon;

import jakarta.json.JsonObject;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.helidon.microprofile.tests.junit5.Configuration;
import io.helidon.microprofile.tests.junit5.HelidonTest;

@HelidonTest
@Configuration(configSources = "META-INF/microprofile-config-test.properties") // awaiting oracle/helidon#3391
class MainTest {
	@Test
	void testHelloWorld(WebTarget target) {
		JsonObject jsonObject = target.path("v1/greet").request().get(JsonObject.class);
		Assertions.assertEquals("Hello World!", jsonObject.getString("message"), "default message");

		jsonObject = target.path("v1/greet/Joe").request().get(JsonObject.class);
		Assertions.assertEquals("Hello Joe!", jsonObject.getString("message"), "hello Joe message");

		Response r = target.path("v1/greet/greeting").request()
				.put(Entity.entity("{\"greeting\" : \"Hola\"}", MediaType.APPLICATION_JSON));
		Assertions.assertEquals(204, r.getStatus(), "PUT status code");

		jsonObject = target.path("v1/greet/Jose").request().get(JsonObject.class);
		Assertions.assertEquals("Hola Jose!", jsonObject.getString("message"), "hola Jose message");

		r = target.path("metrics").request().get();
		Assertions.assertEquals(200, r.getStatus(), "GET metrics status code");

		r = target.path("health").request().get();
		Assertions.assertEquals(200, r.getStatus(), "GET health status code");
	}
}

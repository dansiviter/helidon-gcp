package uk.dansiviter.helidon;

import java.util.List;

import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;

import org.junit.jupiter.api.Test;

import io.helidon.microprofile.tests.junit5.Configuration;
import io.helidon.microprofile.tests.junit5.HelidonTest;
import uk.dansiviter.helidon.model.Post;

@HelidonTest
@Configuration(profile = "test")
class PostResourceTest {
	private static final GenericType<List<Post>> POSTS = new GenericType<>() { };

	@Test
	void posts(WebTarget target) {
		var posts = target.path("v1/posts").request().get(POSTS);
		assert(!posts.isEmpty());
	}
}

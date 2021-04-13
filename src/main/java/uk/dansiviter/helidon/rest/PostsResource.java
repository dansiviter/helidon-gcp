package uk.dansiviter.helidon.rest;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import uk.dansiviter.helidon.model.Post;
import uk.dansiviter.helidon.service.JsonPlaceholderService;

@Path("v1/posts")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class PostsResource {
	@Inject
	@RestClient
	private JsonPlaceholderService service;

	@GET
	public List<Post> posts() {
		// oracle/helidon#2910
		return this.service.posts().toCompletableFuture().join();
	}

	@GET
	@Path("{id}") // /oracle/helidon#2913
	public Optional<Post> post(@PathParam("id") @Nonnull Integer id) {
		// oracle/helidon#2910
		return this.service.post(id).toCompletableFuture().join();
	}
}

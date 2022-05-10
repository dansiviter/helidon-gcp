package uk.dansiviter.helidon.rest;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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
	public CompletionStage<List<Post>> posts() {
		return this.service.posts();
	}

	@GET
	@Path("{id}")
	public CompletionStage<Optional<Post>> post(@PathParam("id") int id) {
		return this.service.post(id);
	}
}

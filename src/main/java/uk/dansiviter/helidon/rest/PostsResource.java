package uk.dansiviter.helidon.rest;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

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
	public CompletionStage<List<Post>> posts() {
		return this.service.posts();
	}

	@GET
	@Path("{id}")
	public CompletionStage<Optional<Post>> post(@PathParam("id") int id) {
		return this.service.post(id);
	}
}

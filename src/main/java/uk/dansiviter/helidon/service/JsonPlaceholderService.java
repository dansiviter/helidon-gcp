package uk.dansiviter.helidon.service;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import uk.dansiviter.helidon.model.Post;

@RegisterRestClient(baseUri = "https://jsonplaceholder.typicode.com/")
@Consumes(APPLICATION_JSON)
public interface JsonPlaceholderService {
	@GET
	@Path("posts")
	CompletionStage<List<Post>> posts();

	@GET
	@Path("posts/{id}")
	CompletionStage<Optional<Post>> post(@PathParam("id") int id);
}

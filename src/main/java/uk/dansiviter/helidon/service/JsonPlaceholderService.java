package uk.dansiviter.helidon.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import uk.dansiviter.helidon.model.Post;

@RegisterRestClient(baseUri = "https://jsonplaceholder.typicode.com/")
@Consumes(MediaType.APPLICATION_JSON)
public interface JsonPlaceholderService {
	@GET
	@Path("posts")
	CompletionStage<List<Post>> posts();

	@GET
	@Path("posts/{id}")
	CompletionStage<Optional<Post>> post(@PathParam("id") int id);
}

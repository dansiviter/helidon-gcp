package uk.dansiviter.helidon;

import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.opentracing.Traced;

/**
 * Provider for greeting message.
 */
@ApplicationScoped
public class GreetingProvider {
	private static final Logger LOG = Logger.getLogger(GreetingProvider.class.getName());
	private final AtomicReference<String> message = new AtomicReference<>();

	/**
	 * Create a new greeting provider, reading the message from configuration.
	 *
	 * @param message greeting to use
	 */
	@Inject
	public GreetingProvider(@ConfigProperty(name = "app.greeting") String message) {
		this.message.set(message);
	}

	@Traced
	String getMessage() {
		LOG.info("Getting message...");
		return message.get();
	}

	@Traced
	void setMessage(String message) {
		LOG.info("Setting message...");
		this.message.set(message);
	}
}

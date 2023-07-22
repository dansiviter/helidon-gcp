package uk.dansiviter.helidon;

import java.util.concurrent.atomic.AtomicReference;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.opentracing.Traced;

/**
 * Provider for greeting message.
 */
@ApplicationScoped
public class GreetingProvider {
	private final AtomicReference<String> message = new AtomicReference<>();

	@Inject
	private Logger log;

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
	public String getMessage() {
		this.log.gettingMessage();
		return message.get();
	}

	@Traced
	public void setMessage(String msg) {
		this.log.settingMessage(msg);
		this.message.set(msg);
	}
}

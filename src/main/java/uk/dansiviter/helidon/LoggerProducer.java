package uk.dansiviter.helidon;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;

import uk.dansiviter.jule.LogProducer;

@ApplicationScoped
public class LoggerProducer {
	@Produces
	Logger logger(InjectionPoint ip) {
		return LogProducer.log(Logger.class, ip.getMember().getDeclaringClass());
	}
}

package uk.dansiviter.helidon;

import static io.opentelemetry.context.propagation.TextMapPropagator.composite;

import java.util.NoSuchElementException;

import io.opentelemetry.api.baggage.propagation.W3CBaggagePropagator;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.opentracingshim.OpenTracingShim;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentracing.Tracer;
import io.opentracing.contrib.tracerresolver.TracerFactory;
import uk.dansiviter.gcp.opentelemetry.trace.Exporter;
import uk.dansiviter.jule.LogProducer;

public class OpenTelemetryFactory implements TracerFactory {
	private static final Logger LOG = LogProducer.log(Logger.class);

	@Override
	public Tracer getTracer() {
		try {
			var exporter = Exporter.builder().build();
			var tracerProvider = SdkTracerProvider.builder()
			.addSpanProcessor(BatchSpanProcessor.builder(exporter).build())
			// .setSampler(Sampler.parentBased(Sampler.alwaysOff())) // let parent decide
			.build();
			OpenTelemetrySdk.builder()
				.setTracerProvider(tracerProvider)
				.setPropagators(ContextPropagators.create(
						composite(
							W3CTraceContextPropagator.getInstance(),
							W3CBaggagePropagator.getInstance())))
				.buildAndRegisterGlobal();
		} catch (NoSuchElementException e) {
			LOG.traceExporterFail(e.getMessage());
		}
		return OpenTracingShim.createTracerShim();
	}
}

package uk.dansiviter.helidon;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.cloud.opentelemetry.trace.TraceExporter;

import io.opentelemetry.api.baggage.propagation.W3CBaggagePropagator;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.opentracingshim.OpenTracingShim;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentracing.Tracer;
import io.opentracing.contrib.tracerresolver.TracerFactory;

public class OpenTelemetryFactory implements TracerFactory {
	private static final Logger LOG = Logger.getLogger(OpenTelemetryFactory.class.getName());

	@Override
	public Tracer getTracer() {
		try {
			var exporter = TraceExporter.createWithDefaultConfiguration();
			var tracerProvider = SdkTracerProvider.builder()
			.addSpanProcessor(BatchSpanProcessor.builder(exporter).build())
			// .setSampler(Sampler.parentBased(Sampler.alwaysOff())) // let parent decide
			.build();
			OpenTelemetrySdk.builder().setTracerProvider(tracerProvider)
				.setPropagators(ContextPropagators.create(
						TextMapPropagator.composite(W3CTraceContextPropagator.getInstance(), W3CBaggagePropagator.getInstance())))
				.buildAndRegisterGlobal();
		} catch (IOException e) {
			LOG.log(Level.WARNING, "Unable to initialise trace exporter! {0}", e.getMessage());
		}
		return OpenTracingShim.createTracerShim();
	}
}

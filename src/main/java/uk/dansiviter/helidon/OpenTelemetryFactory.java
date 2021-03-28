package uk.dansiviter.helidon;

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
import uk.dansiviter.gcp.opentelemetry.trace.Exporter;

public class OpenTelemetryFactory implements TracerFactory {

	static {
		var tracerProvider = SdkTracerProvider.builder()
				.addSpanProcessor(BatchSpanProcessor.builder(Exporter.builder().build()).build())
				// .setSampler(Sampler.parentBased(Sampler.alwaysOff())) // let parent decide
				.build();
		OpenTelemetrySdk.builder().setTracerProvider(tracerProvider)
				.setPropagators(ContextPropagators.create(
						TextMapPropagator.composite(W3CTraceContextPropagator.getInstance(), W3CBaggagePropagator.getInstance())))
				.buildAndRegisterGlobal();
	}

	@Override
	public Tracer getTracer() {
		return OpenTracingShim.createTracerShim();
	}
}

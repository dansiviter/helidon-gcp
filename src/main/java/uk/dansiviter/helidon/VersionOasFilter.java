package uk.dansiviter.helidon;

import static java.lang.String.format;

import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.OpenAPI;

public class VersionOasFilter implements OASFilter {
	private static final String SNAPSHOT_SUFFIX = "-SNAPSHOT";

	@Override
	public void filterOpenAPI(OpenAPI openApi) {
		openApi.getInfo().version(majorMinor());
	}

	private String majorMinor() {
		var version = getClass().getPackage().getImplementationVersion();
		if (version == null || version.isBlank()) {
			return "dev";
		}

		var components = version.split("(\\.|-)");
		return format(
			"%s.%s%s",
			components[0],
			components[1],
			version.endsWith(SNAPSHOT_SUFFIX) ? SNAPSHOT_SUFFIX : "");
	}
}

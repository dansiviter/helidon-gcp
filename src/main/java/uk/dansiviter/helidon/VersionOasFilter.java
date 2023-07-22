package uk.dansiviter.helidon;

import static java.lang.String.format;
import static org.eclipse.microprofile.openapi.OASFactory.createInfo;

import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.OpenAPI;

public class VersionOasFilter implements OASFilter {
	private static final String SNAPSHOT_SUFFIX = "-SNAPSHOT";

	@Override
	public void filterOpenAPI(OpenAPI openApi) {
		var info = openApi.getInfo();
		if (info == null) {
			info = openApi.info(createInfo()).getInfo();
		}
		info.version(majorMinor());
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

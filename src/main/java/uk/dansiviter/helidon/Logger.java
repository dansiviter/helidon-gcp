package uk.dansiviter.helidon;

import static uk.dansiviter.jule.annotations.Message.Level.WARN;

import uk.dansiviter.jule.annotations.Log;
import uk.dansiviter.jule.annotations.Message;

@Log
public interface Logger {
	@Message(value = "Unable to initialise trace exporter! [%s]", level = WARN)
	void traceExporterFail(String msg);

	@Message("Getting message...")
	void gettingMessage();

	@Message("Setting message... [%s]")
	void settingMessage(String msg);
}

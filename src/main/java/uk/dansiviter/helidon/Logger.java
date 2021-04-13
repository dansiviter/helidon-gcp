package uk.dansiviter.helidon;

import uk.dansiviter.juli.annotations.Log;
import uk.dansiviter.juli.annotations.Message;

@Log
public interface Logger {
	@Message("Getting message...")
	void gettingMessage();

	@Message("Setting message...")
	void settingMessage();
}

package uk.dansiviter.helidon;

import uk.dansiviter.jule.annotations.Log;
import uk.dansiviter.jule.annotations.Message;

@Log
public interface Logger {
	@Message("Getting message...")
	void gettingMessage();

	@Message("Setting message...")
	void settingMessage();
}

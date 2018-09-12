package app.logging;

import com.google.api.gax.paging.Page;
import com.google.cloud.logging.LogEntry;
import com.google.cloud.logging.Logging;
import com.google.cloud.logging.Logging.EntryListOption;
import com.google.cloud.logging.LoggingOptions;

/**
 * This class is responsible for fetching logs from Stackdriver.
 * 
 * @author AdarshSinghal
 *
 */
public class CloudLogReceiver {

	public void listWrittenLogs() {
		// [START listlogs]
		// Instantiates a client
		LoggingOptions options = LoggingOptions.getDefaultInstance();

		String logName = "GNFAppLogger";

		try (Logging logging = options.getService()) {

			String logFilter = "logName=projects/" + options.getProjectId() + "/logs/" + logName;

			// List all log entries
			Page<LogEntry> entries = logging.listLogEntries(EntryListOption.filter(logFilter));
			do {
				for (LogEntry logEntry : entries.iterateAll()) {
					System.out.println(logEntry);
				}
				entries = entries.getNextPage();
			} while (entries != null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

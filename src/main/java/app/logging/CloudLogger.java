package app.logging;

import java.util.Collections;

import com.google.cloud.MonitoredResource;
import com.google.cloud.logging.LogEntry;
import com.google.cloud.logging.Logging;
import com.google.cloud.logging.LoggingOptions;
import com.google.cloud.logging.Payload.StringPayload;
import com.google.cloud.logging.Severity;

/**
 * This class is responsible for writing logs into Stackdriver.
 * 
 * @author AdarshSinghal
 *
 */
public class CloudLogger {

	private static final CloudLogger LOGGER = new CloudLogger();

	public static CloudLogger getLogger() {
		return LOGGER;
	}

	private CloudLogger() {
	}

	/**
	 * @param message
	 */
	public void info(String message) {
		log(message, Severity.INFO);
	}

	/**
	 * @param message
	 */
	public void warning(String message) {
		log(message, Severity.WARNING);
	}

	/**
	 * @param message
	 */
	public void error(String message) {
		log(message, Severity.ERROR);
	}

	/**
	 * @param message
	 * @param severity
	 */
	public void log(String message, Severity severity) {
		String monitoredResourceType = MonitoredResourceType.GAE_APP.toString();
		String logName = "GNFAppLogger";

		log(message, severity, monitoredResourceType, logName);
	}

	/**
	 * @param message
	 * @param severity
	 * @param monitoredResourceType
	 * @param logName
	 */
	public void log(String message, Severity severity, String monitoredResourceType, String logName) {
		try (Logging logging = LoggingOptions.getDefaultInstance().getService();) {
			MonitoredResource monitoredResource = MonitoredResource.newBuilder(monitoredResourceType).build();
			LogEntry entry = LogEntry.newBuilder(StringPayload.of(message)).setSeverity(severity).setLogName(logName)
					.setResource(monitoredResource).build();

			logging.write(Collections.singleton(entry));
			logging.flush();
		} catch (Exception e) {
			// Do nothing
		}

	}

}

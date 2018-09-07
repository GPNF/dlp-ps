package app.constants;

import app.util.ExternalProperties;

/**
 * @author AdarshSinghal
 *
 */
public class Constants {

	public static final String PROJECT_ID = ExternalProperties.getAppConfig("app.gc.project.id");
	// ServiceOptions.getDefaultProjectId()

	public static final String SUBSCRIPTION_ID = ExternalProperties.getAppConfig("app.gc.pubsub.subscription.s2");

	public static final String APPLICATION_DATE_FORMAT =ExternalProperties.getAppConfig("app.default.dateformat");
	public static final String APPLICATION_TIMEZONE = ExternalProperties.getAppConfig("app.default.timezone");
	
	public static final String TRUE = "true";
	public static final String IN_PROGRESS = "In-progress";
	public static final String DELIVERED = "Delivered";

}

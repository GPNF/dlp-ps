package app.constants;

import app.util.ExternalProperties;

public class Constants {

	public static final String PROJECT_ID = ExternalProperties.getAppConfig("app.gc.project.id");
	// ServiceOptions.getDefaultProjectId()

	public static final String SUBSCRIPTION_ID = ExternalProperties.getAppConfig("app.gc.pubsub.subscription");

}

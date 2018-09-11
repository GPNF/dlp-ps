package app.logging;

/**
 * @author AdarshSinghal
 *
 */
public enum MonitoredResourceType {
	GLOBAL("global"), GAE_APP("gae_app"), PUBSUB_TOPIC("pubsub_topic"), PUBSUB_SUBSCRIPTION("pubsub_subscription"),
	LOGGING_SINK("logging_sink"), GCS_BUCKET("gcs_bucket");

	private String type;

	MonitoredResourceType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return this.type;
	}
	
	

}

package app;

public class PublisherMain {
	public static void main(String[] args) throws Exception {
		String messages = "This is a test string.";
		new MessagePublisher().publishMessage(messages, "NSE", new StringBuilder());
	}
}

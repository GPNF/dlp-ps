package app.service;

import com.google.api.core.ApiFutureCallback;
import com.google.api.gax.rpc.ApiException;

/**
 * A callback for accepting the results of an ApiFuture.
 * 
 * @author AdarshSinghal
 *
 */
public class DefaultApiFutureCallback implements ApiFutureCallback<String> {

	private String inputMessage;
	private StringBuilder ouputMessageId;

	public StringBuilder getOuputMessageId() {
		return ouputMessageId;
	}

	public DefaultApiFutureCallback(String message) {
		this.inputMessage = message;
		this.ouputMessageId = new StringBuilder("");
	}

	@Override
	public void onFailure(Throwable throwable) {
		if (throwable instanceof ApiException) {
			ApiException apiException = ((ApiException) throwable);
			// details on the API exception
			System.out.println("Status Code: " + apiException.getStatusCode().getCode());
			System.out.println("Is Retryable: " + apiException.isRetryable());
		}
		System.out.println("Error publishing message : " + inputMessage);
	}

	@Override
	public void onSuccess(String messageId) {
		ouputMessageId.append(messageId);
	}

}

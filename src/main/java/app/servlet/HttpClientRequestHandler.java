package app.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author AmolPol
 *
 */
public class HttpClientRequestHandler {

	/**
	 * @param data
	 * @param url
	 * @return status code
	 * @throws IOException
	 */
	public int post(Object data, String url) throws IOException {

		HttpPost httpPost = getHttpPostRequest(data, url);

		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = client.execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		if (client != null)
			client.close();

		return statusCode;

	}

	/** Form Post request
	 * @param data
	 * @param url
	 * @return HttpPost
	 * @throws JsonProcessingException
	 * @throws UnsupportedEncodingException
	 */
	private HttpPost getHttpPostRequest(Object data, String url)
			throws JsonProcessingException, UnsupportedEncodingException {
		ObjectMapper mapper = new ObjectMapper();
		String requestJson = mapper.writeValueAsString(data);
		StringEntity entity = new StringEntity(requestJson);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(entity);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
		return httpPost;
	}

}

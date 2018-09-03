package app.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author AmolPol, AdarshSinghal
 *
 */
public class HttpClientRequestHandler {

	public HttpResponse sendGetReturnResponse(String url) throws ClientProtocolException, IOException {

		HttpClient client = HttpClientBuilder.create().build();
		
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		return response;
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
/*		HttpClientRequestHandler handler = new HttpClientRequestHandler();
		String res = handler.sendGetReturnContent("http://possible-haven-212003.appspot.com/api/topic/list");
		System.out.println(res);*/
	}

	/**
	 * @param url
	 * @return responseContent:String
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String sendGetReturnContent(String url) throws ClientProtocolException, IOException {

		HttpResponse response = sendGetReturnResponse(url);

		InputStream content = response.getEntity().getContent();
		BufferedReader rd = new BufferedReader(new InputStreamReader(content));

		StringBuilder result = new StringBuilder();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		return result.toString();
	}

	/**
	 * @param data
	 * @param url
	 * @return status code
	 * @throws IOException
	 */
	public int sendPostReturnStatus(Object data, String url) throws IOException {
		CloseableHttpResponse response = sendPostReturnResponse(data, url);
		int statusCode = response.getStatusLine().getStatusCode();
		return statusCode;
	}

	/**
	 * @param data
	 * @param url
	 * @return HttpResponse
	 * @throws IOException
	 */
	public CloseableHttpResponse sendPostReturnResponse(Object data, String url) throws IOException {
		HttpPost httpPost = getHttpPostRequest(data, url);
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = client.execute(httpPost);
		if (client != null)
			client.close();
		return response;
	}

	/**
	 * Form Post request
	 * 
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

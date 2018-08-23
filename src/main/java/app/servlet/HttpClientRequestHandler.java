package app.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.model.RequestMapper;

public class HttpClientRequestHandler {

	public String processRequest(RequestMapper req, String url) {
		String requestJson = null;
		StringEntity entity = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient client = null;
		HttpPost httpPost = null;
		ObjectMapper mapper = null;

		try {
			mapper = new ObjectMapper();
			requestJson = mapper.writeValueAsString(req);
			entity = new StringEntity(requestJson);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			client = HttpClients.createDefault();
			httpPost = new HttpPost(url);
			httpPost.setEntity(entity);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			response = client.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();

			System.out.println("Client Status Code " + statusCode);
			System.out.println("Client Status Code " + url);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return "success";

	}

}

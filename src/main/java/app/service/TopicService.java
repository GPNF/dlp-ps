package app.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminClient.ListTopicsPagedResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.pubsub.v1.ListTopicsRequest;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Topic;

import app.constants.Constants;

public class TopicService {

	public List<String> getTopicList() throws IOException {
		List<String> topicList = new ArrayList<>();

		// TopicAdminClient class provides the ability to make remote calls to the
		// backing service
		TopicAdminClient topicAdminClient = TopicAdminClient.create();
		ListTopicsRequest listTopicsRequest = ListTopicsRequest.newBuilder()
				.setProject(ProjectName.format(Constants.PROJECT_ID)).build();
		ListTopicsPagedResponse response = topicAdminClient.listTopics(listTopicsRequest);

		Spliterator<Topic> spliterator = response.iterateAll().spliterator();

		StreamSupport.stream(spliterator, false).filter(topic -> isValidTopicString(topic.getName())).forEach(topic -> {
			String[] topicStringTokens = topic.getName().split("/");
			topicList.add(topicStringTokens[3]);
		});

		return topicList;
	}

	public String getTopicListJson() throws IOException {
		List<String> topicList = this.getTopicList();

		if (topicList != null && !topicList.isEmpty()) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonArray topicJsonArray = gson.toJsonTree(topicList).getAsJsonArray();
			JsonObject jsonObject = new JsonObject();
			jsonObject.add("topics", topicJsonArray);
			return jsonObject.toString();
		}
		return "";
	}

	private boolean isValidTopicString(String topicName) {
		return topicName != null && topicName.split("/").length == 4;
	}

}

package app.service;

import java.io.IOException;
import java.util.List;

import com.google.cloud.dlp.v2.DlpServiceClient;
import com.google.privacy.dlp.v2.CharacterMaskConfig;
import com.google.privacy.dlp.v2.ContentItem;
import com.google.privacy.dlp.v2.DeidentifyConfig;
import com.google.privacy.dlp.v2.DeidentifyContentRequest;
import com.google.privacy.dlp.v2.DeidentifyContentResponse;
import com.google.privacy.dlp.v2.InfoType;
import com.google.privacy.dlp.v2.InfoTypeTransformations;
import com.google.privacy.dlp.v2.InfoTypeTransformations.InfoTypeTransformation;
import com.google.privacy.dlp.v2.InspectConfig;
import com.google.privacy.dlp.v2.PrimitiveTransformation;
import com.google.pubsub.v1.ProjectName;

import app.constants.Constants;

/**
 * @author Bhagyashree
 *
 */
public class DeIdentifier {

	public static String deIdentifyWithMask(String inputMessage, List<InfoType> infoTypes) throws IOException {

		Character maskingCharacter = 'x';
		int numberToMask = Integer.parseInt("0");

		ContentItem contentItem = ContentItem.newBuilder().setValue(inputMessage).build();

		CharacterMaskConfig characterMaskConfig = CharacterMaskConfig.newBuilder()
				.setMaskingCharacter(maskingCharacter.toString()).setNumberToMask(numberToMask).build();

		// Create the deidentification transformation configuration
		PrimitiveTransformation primitiveTransformation = PrimitiveTransformation.newBuilder()
				.setCharacterMaskConfig(characterMaskConfig).build();

		InfoTypeTransformation infoTypeTransformationObject = InfoTypeTransformation.newBuilder()
				.setPrimitiveTransformation(primitiveTransformation).build();

		InfoTypeTransformations infoTypeTransformationArray = InfoTypeTransformations.newBuilder()
				.addTransformations(infoTypeTransformationObject).build();

		InspectConfig inspectConfig = InspectConfig.newBuilder().addAllInfoTypes(infoTypes).build();

		DeidentifyConfig deidentifyConfig = DeidentifyConfig.newBuilder()
				.setInfoTypeTransformations(infoTypeTransformationArray).build();

		// Create the deidentification request object
		DeidentifyContentRequest request = DeidentifyContentRequest.newBuilder()
				.setParent(ProjectName.of(Constants.PROJECT_ID).toString()).setInspectConfig(inspectConfig)
				.setDeidentifyConfig(deidentifyConfig).setItem(contentItem).build();

		// Execute the deidentification request
		DeidentifyContentResponse response = DlpServiceClient.create().deidentifyContent(request);

		String result = response.getItem().getValue();
		System.out.println(result);
		return result;

	}

}
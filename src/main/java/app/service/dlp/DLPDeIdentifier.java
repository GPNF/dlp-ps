package app.service.dlp;

import java.io.IOException;
import java.util.ArrayList;
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
 * This class is responsible for DLP Deidentification. Provide infotype, masking
 * characters, number of characters to mask to configure.
 * 
 * @author Bhagyashree, AdarshSinghal
 *
 */
public class DLPDeIdentifier {

	private static final String ALL_BASIC = "ALL_BASIC";

	/**
	 * @param inputMessage
	 * @param infoTypes
	 * @return deidentifiedMessage
	 * @throws IOException
	 */
	public String deIdentifyWithMask(String inputMessage, List<InfoType> infoTypes) throws IOException {

		DeidentifyConfig deidentifyConfig = getDeidentifyConfig();
		ContentItem contentItem = ContentItem.newBuilder().setValue(inputMessage).build();
		InspectConfig inspectConfig = InspectConfig.newBuilder().addAllInfoTypes(infoTypes).build();

		// Create the deidentification request object
		DeidentifyContentRequest request = DeidentifyContentRequest.newBuilder()
				.setParent(ProjectName.of(Constants.PROJECT_ID).toString()).setInspectConfig(inspectConfig)
				.setDeidentifyConfig(deidentifyConfig).setItem(contentItem).build();

		// Execute the deidentification request
		DlpServiceClient client = DlpServiceClient.create();
		DeidentifyContentResponse response = client.deidentifyContent(request);

		String deidentifiedMessage = response.getItem().getValue();

		client.shutdown();

		return deidentifiedMessage;

	}

	private DeidentifyConfig getDeidentifyConfig() {
		String maskingCharacter = "x";
		int numberToMask = 0;

		CharacterMaskConfig characterMaskConfig = CharacterMaskConfig.newBuilder().setMaskingCharacter(maskingCharacter)
				.setNumberToMask(numberToMask).build();

		// Create the deidentification transformation configuration
		PrimitiveTransformation primitiveTransformation = PrimitiveTransformation.newBuilder()
				.setCharacterMaskConfig(characterMaskConfig).build();

		InfoTypeTransformation infoTypeTransformationObject = InfoTypeTransformation.newBuilder()
				.setPrimitiveTransformation(primitiveTransformation).build();

		InfoTypeTransformations infoTypeTransformationArray = InfoTypeTransformations.newBuilder()
				.addTransformations(infoTypeTransformationObject).build();

		DeidentifyConfig deidentifyConfig = DeidentifyConfig.newBuilder()
				.setInfoTypeTransformations(infoTypeTransformationArray).build();
		return deidentifyConfig;
	}

	/**
	 * @param inputMsg
	 * @return deidentifiedMessage
	 * @throws IOException
	 */
	public String getDeidentifiedString(String inputMsg) throws IOException {
		List<InfoType> infoTypes = new ArrayList<InfoType>();
		infoTypes.add(InfoType.newBuilder().setName(ALL_BASIC).build());
		return deIdentifyWithMask(inputMsg, infoTypes);
	}

}
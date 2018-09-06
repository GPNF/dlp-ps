package app.service.dlp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.cloud.dlp.v2.DlpServiceClient;
import com.google.privacy.dlp.v2.ByteContentItem;
import com.google.privacy.dlp.v2.ByteContentItem.BytesType;
import com.google.privacy.dlp.v2.ContentItem;
import com.google.privacy.dlp.v2.Finding;
import com.google.privacy.dlp.v2.InfoType;
import com.google.privacy.dlp.v2.InspectConfig;
import com.google.privacy.dlp.v2.InspectConfig.FindingLimits;
import com.google.privacy.dlp.v2.InspectContentRequest;
import com.google.privacy.dlp.v2.InspectContentResponse;
import com.google.privacy.dlp.v2.Likelihood;
import com.google.privacy.dlp.v2.ProjectName;
import com.google.protobuf.ByteString;

import app.constants.Constants;
import app.model.InspectResult;

/**
 * This class is responsible for DLP inspection. It contains DLP Inspection
 * configuration method segregated by configuration types.
 * 
 * @author BhagyaShree, AdarshSinghal
 *
 */
public class DLPInspector {

	private static final String LIKELY = "LIKELY";
	private static final String VERY_LIKELY = "VERY_LIKELY";
	private static final String ALL_BASIC = "ALL_BASIC";

	/**
	 * @param inputMessage
	 * @return List&lt;InspectResult&gt;
	 * @throws IOException
	 */
	public List<InspectResult> getInspectionResult(String inputMessage) throws IOException {

		InspectContentResponse inspectContentResponse = getInspectContentResponse(inputMessage);

		List<Finding> findingList = inspectContentResponse.getResult().getFindingsList();

		List<InspectResult> inspectResList = findingList.stream()
				.filter(finding -> isLikelyOrVeryLikely(finding))
				.map(finding -> getInspectResult(finding))
				.collect(Collectors.toList());

		return inspectResList;
	}

	/** Copy quote, infoType, likelihood from finding to InspectResult
	 * @param finding
	 * @return inspectResult
	 */
	private InspectResult getInspectResult(Finding finding) {
		String quote = finding.getQuote();
		String infoType = finding.getInfoType().getName();
		String likelihood = finding.getLikelihood().toString();
		InspectResult inspectResult = new InspectResult(quote, infoType, likelihood);
		return inspectResult;
	}

	private boolean isLikelyOrVeryLikely(Finding finding) {
		return finding.getLikelihood().toString().equals(LIKELY)
				|| finding.getLikelihood().toString().equals(VERY_LIKELY);
	}

	/**
	 * Create InspectContentRequest, Use DlpServiceClient to get response. The
	 * request can be configured using InspectConfig & ContentItem
	 * 
	 * @param inputMessage
	 * @return inspectContentResponse
	 * @throws IOException
	 */
	private InspectContentResponse getInspectContentResponse(String inputMessage) throws IOException {

		// Configuration like Infotypes, Likelihood, FindingLimits , IncludeQuote
		InspectConfig inspectConfig = getInspectConfig();

		ContentItem contentItem = getContentItem(inputMessage);

		String projectName = ProjectName.of(Constants.PROJECT_ID).toString();
		InspectContentRequest request = InspectContentRequest.newBuilder().setParent(projectName)
				.setInspectConfig(inspectConfig).setItem(contentItem).build();
		DlpServiceClient client = DlpServiceClient.create();
		InspectContentResponse inspectContentResponse = client.inspectContent(request);
		client.shutdown();
		return inspectContentResponse;
	}

	/**
	 * Configure Infotypes here. <br>
	 * <br>
	 * DO NOT MODIFY THIS METHOD FOR OTHER CONFIGURATIONS.
	 * 
	 * @return
	 */
	private InspectConfig getInspectConfig() {
		InfoType infotype = InfoType.newBuilder().setName(ALL_BASIC).build();
		List<InfoType> infoTypes = new ArrayList<InfoType>();
		infoTypes.add(infotype);
		return getInspectConfig(infoTypes);
	}

	/**
	 * Configure likelihood here. <br>
	 * <br>
	 * DO NOT USE THIS METHOD FOR CONFIGURATIONS OTHER THAN LIKELIHOOD.
	 * 
	 * @param infoTypes
	 * @return inspectConfig
	 */
	private InspectConfig getInspectConfig(List<InfoType> infoTypes) {
		String unspecifiedLikelihood = Likelihood.LIKELIHOOD_UNSPECIFIED.name();
		Likelihood minLikelihood = Likelihood.valueOf(unspecifiedLikelihood);
		return getInspectConfig(infoTypes, minLikelihood);
	}

	/**
	 * Configuration description of the scanning process. <br>
	 * You should configure includeQuote, findingLimits in this method (if required)
	 * <br>
	 * <br>
	 * DO NOT CONFIGURE INFOTYPE &amp; LIKELIHOOD HERE <br>
	 * 
	 * @param infoTypes
	 * @param likelihood
	 * @return inspectConfig
	 */
	private InspectConfig getInspectConfig(List<InfoType> infoTypes, Likelihood likelihood) {

		boolean includeQuote = true;
		int maxFindings = 0;
		FindingLimits findingLimits = FindingLimits.newBuilder().setMaxFindingsPerRequest(maxFindings).build();

		InspectConfig inspectConfig = InspectConfig.newBuilder().addAllInfoTypes(infoTypes).setMinLikelihood(likelihood)
				.setLimits(findingLimits).setIncludeQuote(includeQuote).build();
		return inspectConfig;
	}

	/**
	 * Content data to inspect or redact. Replaces `type` and `data`.
	 * 
	 * @param inputMessage
	 * @return contentItem
	 */
	private ContentItem getContentItem(String inputMessage) {
		ByteString utf8String = ByteString.copyFromUtf8(inputMessage);
		BytesType textUtf8Type = ByteContentItem.BytesType.TEXT_UTF8;

		// Container for bytes to inspect or redact.
		ByteContentItem byteContentItem = ByteContentItem.newBuilder().setType(textUtf8Type).setData(utf8String)
				.build();

		// Container structure for the content to inspect.
		ContentItem contentItem = ContentItem.newBuilder().setByteItem(byteContentItem).build();

		return contentItem;
	}

}

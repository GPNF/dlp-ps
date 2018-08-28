package app.service.dlp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.cloud.dlp.v2.DlpServiceClient;
import com.google.privacy.dlp.v2.ByteContentItem;
import com.google.privacy.dlp.v2.ContentItem;
import com.google.privacy.dlp.v2.Finding;
import com.google.privacy.dlp.v2.InfoType;
import com.google.privacy.dlp.v2.InspectConfig;
import com.google.privacy.dlp.v2.InspectContentRequest;
import com.google.privacy.dlp.v2.InspectContentResponse;
import com.google.privacy.dlp.v2.Likelihood;
import com.google.privacy.dlp.v2.ProjectName;
import com.google.privacy.dlp.v2.InspectConfig.FindingLimits;
import com.google.protobuf.ByteString;

import app.constants.Constants;
import app.model.InspectResult;

public class DLPInspector {

	private static final String LIKELY = "LIKELY";
	private static final String VERY_LIKELY = "VERY_LIKELY";
	private static final String ALL_BASIC = "ALL_BASIC";

	public List<InspectResult> getInspectionResult(String inputMessage) throws IOException {

		Likelihood minLikelihood = Likelihood.valueOf(Likelihood.LIKELIHOOD_UNSPECIFIED.name());
		int maxFindings = 0;
		boolean includeQuote = true;

		FindingLimits findingLimits = FindingLimits.newBuilder().setMaxFindingsPerRequest(maxFindings).build();
		List<InfoType> infoTypes = new ArrayList<InfoType>();
		infoTypes.add(InfoType.newBuilder().setName(ALL_BASIC).build());
		InspectConfig inspectConfig = InspectConfig.newBuilder().addAllInfoTypes(infoTypes)
				.setMinLikelihood(minLikelihood).setLimits(findingLimits).setIncludeQuote(includeQuote).build();

		ByteContentItem byteContentItem = ByteContentItem.newBuilder().setType(ByteContentItem.BytesType.TEXT_UTF8)
				.setData(ByteString.copyFromUtf8(inputMessage)).build();

		ContentItem contentItem = ContentItem.newBuilder().setByteItem(byteContentItem).build();

		InspectContentRequest request = InspectContentRequest.newBuilder()
				.setParent(ProjectName.of(Constants.PROJECT_ID).toString()).setInspectConfig(inspectConfig)
				.setItem(contentItem).build();
		DlpServiceClient client = DlpServiceClient.create();
		InspectContentResponse inspectContentResponse = client.inspectContent(request);
		// Finding finding = response.getResult().getFindingsList().get(0);
		List<Finding> findingList = inspectContentResponse.getResult().getFindingsList();

		if (inspectContentResponse.getResult().getFindingsCount() > 0) {
			List<InspectResult> inspectResList = new ArrayList<>();
			for (Finding finding : findingList) {
				if (finding.getLikelihood().toString().equals(LIKELY)
						|| finding.getLikelihood().toString().equals(VERY_LIKELY)) {
					String quote = finding.getQuote();
					String infoType = finding.getInfoType().getName();
					String lkhood = finding.getLikelihood().toString();
					InspectResult inspectRes = new InspectResult(quote, infoType, lkhood);
					inspectResList.add(inspectRes);
				}
			}
			client.shutdown();
			return inspectResList;

		}
		return null;
	}

}

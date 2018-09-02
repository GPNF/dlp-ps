package app.service.dlp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.privacy.dlp.v2.InfoType;

import app.exception.PANDataFoundSecurityViolationException;
import app.model.InspectResult;

/**
 * Responsible for Inspection, Risk analysis & De-Identification
 * 
 * @author adarshs1
 *
 */
public class DLPService {

	private static final String ALL_BASIC = "ALL_BASIC";
	List<String> sensitiveInfoTypes = Arrays.asList("CREDIT_CARD_NUMBER", "INDIA_PAN_INDIVIDUAL");

	public List<InspectResult> getInspectionResult(String inputMessage) throws IOException {
		DLPInspector dlpInspector = new DLPInspector();
		List<InspectResult> inspectResult = dlpInspector.getInspectionResult(inputMessage);
		return inspectResult;
	}

	public String getDeIdentifiedString(String inputMsg) throws IOException {
		List<InfoType> infoTypes = new ArrayList<InfoType>();
		infoTypes.add(InfoType.newBuilder().setName(ALL_BASIC).build());
		String deidentifiedRes = DLPDeIdentifier.deIdentifyWithMask(inputMsg, infoTypes);
		return deidentifiedRes;
	}

	public void checkForSensitiveData(String inputMessage) throws IOException, PANDataFoundSecurityViolationException {
		List<InspectResult> inspectionResults = getInspectionResult(inputMessage);
		if (inspectionResults == null || inspectionResults.isEmpty())
			return;

		boolean sensitiveDataPresent = inspectionResults.stream()
				.anyMatch(result -> sensitiveInfoTypes.contains(result.getInfoType()));

		if (sensitiveDataPresent) {
			throw new PANDataFoundSecurityViolationException();
		}

	}

}

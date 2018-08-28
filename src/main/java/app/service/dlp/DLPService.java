package app.service.dlp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.privacy.dlp.v2.InfoType;

import app.model.InspectResult;

/**
 * Responsible for Inspection, Risk analysis & De-Identification
 * 
 * @author adarshs1
 *
 */
public class DLPService {

	private static final String ALL_BASIC = "ALL_BASIC";

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

}

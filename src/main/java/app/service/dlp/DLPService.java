package app.service.dlp;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import app.exception.PANDataFoundSecurityViolationException;
import app.logging.CloudLogger;
import app.model.InspectResult;

/**
 * Responsible for DLP Inspection, Risk analysis & Deidentification <br>
 * <br>
 * Service class should be 'As Simple As Possible'. If you're modifying this
 * class, add operation &amp; delegate logics to other class
 * 
 * @author AdarshSinghal
 *
 */
public class DLPService {

	private CloudLogger LOGGER = CloudLogger.getLogger();

	/**
	 * @param inputMessage
	 * @return inspectResult
	 * @throws IOException
	 */
	public List<InspectResult> getInspectionResult(String inputMessage) throws IOException {
		LOGGER.info("Inside DLP Service. Performing DLP Inspection for message - " + inputMessage);
		DLPInspector dlpInspector = new DLPInspector();
		return dlpInspector.getInspectionResult(inputMessage);
	}

	/**
	 * @param inputMessage
	 * @return deidentifiedMessage
	 * @throws IOException
	 */
	public String getDeidentifiedString(String inputMessage) throws IOException {
		LOGGER.info("Inside DLP Service. Performing DLP Deidentification for message - " + inputMessage);
		DLPDeIdentifier dlpDeidentifier = new DLPDeIdentifier();
		return dlpDeidentifier.getDeidentifiedString(inputMessage);
	}

	/**
	 * @param inputMessage
	 * @throws IOException
	 * @throws PANDataFoundSecurityViolationException
	 */
	public void checkForSensitiveData(String inputMessage) throws IOException, PANDataFoundSecurityViolationException {
		List<InspectResult> inspectionResults = getInspectionResult(inputMessage);
		boolean sensitiveDataPresent = inspectionResults.stream().anyMatch(result -> hasSensitiveData(result));

		if (sensitiveDataPresent) {
			LOGGER.warning(
					"Inside DLP Service. Throwing PANDataFoundSecurityViolationException. Reason:- PAN data present. "
							+ "Redacted Message:- " + getDeidentifiedString(inputMessage));
			throw new PANDataFoundSecurityViolationException();
		}

		LOGGER.info("Inside DLP Service. PAN data not present. PII found: " + inspectionResults.size() + " \nMessage: "
				+ getDeidentifiedString(inputMessage));

	}

	private boolean hasSensitiveData(InspectResult inspectResult) {
		List<String> sensitiveInfoTypes = Arrays.asList("CREDIT_CARD_NUMBER", "INDIA_PAN_INDIVIDUAL");
		return sensitiveInfoTypes.contains(inspectResult.getInfoType());
	}

}

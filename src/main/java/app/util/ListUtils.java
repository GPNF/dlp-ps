package app.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Helper class to provide support for operations related to List
 * @author adarshsinghal
 *
 */
public class ListUtils {

	/** Converts a comma separated String into List of String
	 * @param csv comma separated values
	 * @return list of String
	 */
	public static List<String> getListFromCSV(String csv) {
		List<String> results;
		if (csv.contains(",")) {
			results = Arrays.asList(csv.split(","));
		} else {
			// if single value
			results = new ArrayList<>();
			results.add(csv);
		}
		return results;
	}
	
}

package analysisSubsystem.dataFetchingSubsystem;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import analysisSubsystem.Data;

/**
 * Responsible for data acquisition from the World Bank's data base. 
 * @author Joud El-Shawa
 */
public class Reader {
	
	/**
	 * Array of the years that data has been fetched for
	 */
	private int[] yearsList;
	
	/**
	 * Array of the data fetched for each year
	 */
	private double[] value;

	/**
	 * Issues an http GET request to get data from the World Bank data base based on the aspects provided. 
	 * Goes through the json received and adds the values to the value array and years list.
	 * @param analysisIndicator one of the indicators for the analysis type so it can fetch data for it specifically. 
	 * @param sel selection object to be used for the http GET request
	 * @return Data object containing a value array and a years array.
	 */
	// CHOOSE WHICH ONE TO KEEP 
//	public Data readData(String analysisIndicator, Selection sel) {
	/**
	 * Issues an http GET request to get data from the World Bank data base based on the aspects provided. 
	 * Goes through the json received and adds the values to the value array and years list.
	 * @param countryCode code of the country so it can be used in the http GET request.
	 * @param analysisIndicator one of the indicators for the analysis type so it can fetch data for it specifically.
	 * @param yearStart string consisting of the start year.
	 * @param yearEnd string consisting of the end year.
	 * @return Data object containing a value array and a years array.
	 */
	public Data readData(String countryCode, String analysisIndicator, String yearStart, String yearEnd) {
		System.out.println(analysisIndicator);

//		String urlString = String.format("http://api.worldbank.org/v2/country/%s/indicator/%s?date=%s:%s&format=json", sel.getCountryCode(), analysisIndicator, sel.getYearStart(), sel.getYearEnd());
		String urlString = String.format("http://api.worldbank.org/v2/country/%s/indicator/%s?date=%s:%s&format=json", countryCode, analysisIndicator, yearStart, yearEnd);

		System.out.println(urlString);

		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			if (responsecode == 200) {
				String inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();

				JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();

				int size = jsonArray.size();

				if (size >= 2 && jsonArray.get(1).isJsonArray()) {
					int sizeOfResults = jsonArray.get(1).getAsJsonArray().size();

					boolean nullData = true;

					yearsList = new int[sizeOfResults];
					value = new double[sizeOfResults];

					for (int i = 0; i < sizeOfResults; i++) {
						yearsList[i] = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("date").getAsInt();
						if (jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull())
							value[i] = 0;
						else {
							value[i] = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsDouble();
							nullData = false;	// if values not all null, then we have either full/partial data
						}
					}

					if (nullData) { 	// if all the data is null, value will be set to null instead of all 0s 
						value = null;
					}
				}
				if (size < 2) { 	// no data is available, so value is null
					value = null;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		}
		return new Data(value,yearsList);	// returns Data object consisting of the value array and years array
	}
}

package analysisSubsystem.dataFetchingSubsystem;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import analysisSubsystem.Data;
import frontEnd.selectionSubsystem.Selection;

public class Reader {
	private int[] yearsList;
	private double[] value;
	
	public Data readData(String analysisIndicator, Selection sel) { 
		System.out.println(analysisIndicator);
		
		String urlString = String.format("http://api.worldbank.org/v2/country/%s/indicator/%s?date=%s:%s&format=json", sel.getCountryCode(), analysisIndicator, sel.getYearStart(), sel.getYearEnd());
		
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
				
//				JsonParser parse = new JsonParser();
//				JsonElement jobj = parse.parse(inline);
				
				JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();
				
				int size = jsonArray.size();

//				if (jsonArray.get(0).getAsJsonObject().get("value") != null) {
//				if (!jobj.getAsJsonObject().getAsJsonObject("message").get("value").equals("The provided parameter value is not valid")) {
				if (size >= 2) {
					int sizeOfResults = jsonArray.get(1).getAsJsonArray().size();
					
					yearsList = new int[sizeOfResults];
					value = new double[sizeOfResults];
					
					for (int i = 0; i < sizeOfResults; i++) {
						yearsList[i] = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("date").getAsInt();
						if (jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull())
							value[i] = 0;
						else
							value[i] = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsInt();
					}
					
					for (int i = 0; i < yearsList.length; i++) {
						System.out.println(yearsList[i]);
					}
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		}
		return new Data(value,yearsList);
	}	
} 

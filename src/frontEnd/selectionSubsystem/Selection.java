package frontEnd.selectionSubsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Selection {
	private String[] analysisIndicators;
	private String analysisName;
	private String countryCode;
	private String countryName;
	private int yearStart;
	private int yearEnd;
	private List<String> viewersList = new ArrayList<String>();
	
	private HashMap<String,List<String>> restrictedCountries = new HashMap<String,List<String>>();
	private HashMap<String,List<String>> restrictedViewers = new HashMap<String,List<String>>();
	
	private static Selection instance;
	
	public static Selection getInstance() {
		if (instance == null)
			instance = new Selection();

		return instance;
	}
	
	private Selection() {
		readFiles();
	}
	
	private void readFiles() {
		
		// reading country restrictions based on analysis types
		try {	
		      File restrictFile = new File("restrictedCountries.txt");
		      Scanner scan = new Scanner(restrictFile);
		      while (scan.hasNextLine()) {
		        String data = scan.nextLine();
		        String[] analysisRestrict = data.split("/");
		  		
		        String analysisName = analysisRestrict[0].trim();
		        String[] restrictedCount = analysisRestrict[1].split(",");
		        
		        List<String> countries = Arrays.asList(restrictedCount);
		        
		        restrictedCountries.put(analysisName,countries);
		      }
		      scan.close();
		} 
		catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
		
		
		// reading valid viewers based on analysis types
		try {	
		      File restrictFile = new File("restrictedViewers.txt");
		      Scanner scan = new Scanner(restrictFile);
		      while (scan.hasNextLine()) {
		        String data = scan.nextLine();
		        String[] analysisRestrict = data.split("/");
		  		List<String> viewers = new ArrayList<String>();
		        
		        String analysisName = analysisRestrict[0].trim();
		        if (analysisRestrict.length > 1) {
		        	String[] restrictedView = analysisRestrict[1].split(",");
			        viewers = Arrays.asList(restrictedView);
		        }
		        
		        restrictedViewers.put(analysisName,viewers);
		      }
		      scan.close();
		} 
		catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
	}
	
	public boolean setSelection(String type, String value) {
		switch(type) {
			case "analysisName":
				this.analysisName = value;
				break;
			case "analysisIndicators":
				this.analysisIndicators = value.split(",");
				System.out.println(value);
				System.out.println(analysisIndicators[0]);
				break;
			case "countryName":
				return validateCountry(value);
			case "countryCode":
				this.countryCode = value;
				break;
			case "yearStart":
				return validateYears(value,String.valueOf(this.yearEnd));
			case "yearEnd":
				return validateYears(String.valueOf(this.yearStart),value);		
			case "addViewer":
				return validateAddViewer(value);
			case "removeViewer":
				return validateRemoveViewer(value);
		}
		return true;
	}
	
	private boolean validateRemoveViewer(String viewer) {
		if (!viewersList.isEmpty() && viewersList.contains(viewer)) {
			viewersList.remove(viewer);
			return true;
		}
		else return false;
	}

	private boolean validateAddViewer(String viewer) {
		if (restrictedViewers.get(this.analysisName).contains(viewer))
			return false;
		else {
			if (!viewersList.contains(viewer)) 
					viewersList.add(viewer);
			System.out.println("valid viewer");
			return true;
		}
	}

	private boolean validateCountry(String country) {		
		if (!restrictedCountries.get(analysisName).isEmpty() && restrictedCountries.get(analysisName).contains(country)) 
			return false;
		else {
			this.countryName = country;
			return true;
		}
	}
	
	private boolean validateYears(String start, String end) {
		int startYr = Integer.parseInt(start);
		int endYr = Integer.parseInt(end);
		
		if (startYr > endYr) return false;
		else if (endYr < startYr) return false;
		else {
			this.yearStart = startYr;
			this.yearEnd = endYr;
			return true;
		}
	}
	
	public String[] getAnalysisIndicators() {
		return this.analysisIndicators;
	}
	
	public String getAnalysisName() {
		return this.analysisName;
	}
	
	public String getCountryCode() {
		return this.countryCode;
	}
	
	public String getCountryName() {
		return this.countryName;
	}
	
	public String getYearStart() {
		return Integer.toString(this.yearStart);
	}
	
	public String getYearEnd() {
		return Integer.toString(this.yearEnd);
	}
	
	public List<String> getViewersList() {
		return this.viewersList;
	}
}

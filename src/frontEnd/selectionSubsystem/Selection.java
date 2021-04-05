package frontEnd.selectionSubsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Contains all aspects of analysis selected by the User. Also validates some of them when needed.
 * @author Joud El-Shawa - jelshawa - 251107864
 */
public class Selection {
	
	/**
	 * Name of the analysis type selected.
	 */
	private String analysisName;
	
	/**
	 * All indicators needed for the analysis type selected.
	 */
	private String[] analysisIndicators;

	/**
	 * Name of the country selected. 
	 */
	private String countryName;
	
	/**
	 * Corresponding country code of the country selected.
	 */
	private String countryCode;

	/**
	 * Start year for the analysis.
	 */
	private int yearStart = 2021;
	
	/**
	 * End year for the analysis.
	 */
	private int yearEnd = 2021;
	
	/**
	 * List of viewers selected by the User.
	 */
	private List<String> viewersList = new ArrayList<String>();
	
	/**
	 * Contains analysis names and corresponding list of restricted countries.
	 */
	private HashMap<String,List<String>> restrictedCountries = new HashMap<String,List<String>>();
	
	/**
	 * Contains analysis names and corresponding list of restricted viewers.
	 */
	private HashMap<String,List<String>> restrictedViewers = new HashMap<String,List<String>>();
	
	/**
	 * Instance of the class since only one selection object is needed. 
	 */
	private static Selection instance;
	
	/**
	 * Creates an instance of the class if it does not already exist.
	 * @return the instance of the Selection class
	 */
	public static Selection getInstance() {
		if (instance == null)
			instance = new Selection();

		return instance;
	}
	
	/**
	 * Constructor. Calls the readFiles method to populate the different hashmaps.
	 */
	private Selection() {
		readFiles();
	}
	
	/**
	 * Reads all files needed for validating aspects of the system. Stores data read in hashmaps.
	 */
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
		
		
		// reading restricted viewers based on analysis types
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
	
	/**
	 * Sets the value received to the type that is indicates. Calls a validate method if necessary depending on the type.
	 * @param type indicates which attribute is being set
	 * @param value the value that the attribute is trying to be set to
	 * @return false if value is invalid/incompatible, true otherwise.
	 */
	public boolean setSelection(String type, String value) {
		switch(type) {
			case "analysisName":
				this.analysisName = value;
				break;
			case "analysisIndicators":
				this.analysisIndicators = value.split(",");
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
	
	/**
	 * Removes all viewers in list of viewers.
	 */
	public void clearViewersList() {
		this.viewersList.clear();
	}
	
	/**
	 * Checks if the viewer is already in the list of viewers.
	 * @param viewer name of the viewer to be removed from the list of viewers.
	 * @return true if the list of viewers is not empty and the viewer is in it. false otherwise.
	 */
	private boolean validateRemoveViewer(String viewer) {
		if (!viewersList.isEmpty() && viewersList.contains(viewer)) {
			viewersList.remove(viewer);
			return true;
		}
		else return false;
	}

	/**
	 * Checks if the viewer is compatible with the analysis type by checking the hashmap of restricted viewers.
	 * @param viewer name of the viewer to be added to the list
	 * @return true if viewer is compatible, false otherwise.
	 */
	private boolean validateAddViewer(String viewer) {
		if (restrictedViewers.get(this.analysisName).contains(viewer))
			return false;
		else {
			if (!viewersList.contains(viewer)) 
					viewersList.add(viewer);
			return true;
		}
	}

	/**
	 * Checks if country is compatible with the analysis type by checking the hashmap of restricted countries.
	 * @param country name of the country selected.
	 * @return true if country is compatible with analysis, false otherwise.
	 */
	private boolean validateCountry(String country) {	
		if (!restrictedCountries.get(analysisName).isEmpty() && restrictedCountries.get(analysisName).contains(country)) 
			return false;
		else {
			this.countryName = country;
			return true;
		}
	}
	
	/**
	 * Checks if the start year precedes the end year. 
	 * @param start year that is the minimum in the range of years for the analysis
	 * @param end year that is the maximum in the range of years for the analysis
	 * @return true if start year is less than or equal to the end year. false otherwise.
	 */
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
	
	/**
	 * Gets the analysis indicators of the analysis type.
	 * @return string array containing the indicators.
	 */
	public String[] getAnalysisIndicators() {
		return this.analysisIndicators;
	}
	
	/**
	 * Gets the name of the analysis type.
	 * @return name of the analysis.
	 */
	public String getAnalysisName() {
		return this.analysisName;
	}
	
	/**
	 * Gets the country code of the country selected.
	 * @return country code of the country.
	 */
	public String getCountryCode() {
		return this.countryCode;
	}
	
	/**
	 * Gets the name of the country selected.
	 * @return name of the country.
	 */
	public String getCountryName() {
		return this.countryName;
	}
	
	/**
	 * Gets the start year of the analysis.
	 * @return the start year.
	 */
	public String getYearStart() {
		return Integer.toString(this.yearStart);
	}
	
	/**
	 * Gets the end year of the analysis.
	 * @return the end year.
	 */
	public String getYearEnd() {
		return Integer.toString(this.yearEnd);
	}
	
	/**
	 * Gets the viewers selected for the analysis.
	 * @return the list of viewers selected.
	 */
	public List<String> getViewersList() {
		return this.viewersList;
	}
}

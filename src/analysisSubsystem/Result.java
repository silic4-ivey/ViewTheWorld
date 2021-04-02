package analysisSubsystem;

/**
 * Consists of all the data that has been processed and needed for rendering viewers.
 * @author Joud El-Shawa
 */
public class Result {
	
	/**
	 * Name of the analysis type.
	 */
	private String analysisName;
	
	/**
	 * Different subparts of the analysis, used for the Viewer's legends.
	 */
	private String[] analysisParts;
	
	/**
	 * Array of values calculated/processed by the Analysis algorithms.
	 */
	private double[][] value;
	
	/**
	 * Array of years that the analysis has been calculated for.
	 */
	private int[] years;
	
	/**
	 * Name of the country that has been chosen for analysis.
	 */
	private String countryName;
	
	/**
	 * Constructor. If no parameters, initializes all values to 0/null/empty.
	 */
	public Result() {
		this.analysisName = "";
		this.analysisParts = new String[0];
		this.value = new double[0][0];
		this.years = new int[0];
	}
	
	/**
	 * Constructor. Initializes the attributes of this class to the parameters received.
	 * @param aName Name of the analysis.
	 * @param parts Different parts of the analysis.
	 * @param val Values calculated by the Analysis algorithms.
	 * @param yrs Years corresponding to the values calculated.
	 * @param country Name of the country chosen.
	 */
	public Result(String aName, String[] parts, double[][] val, int[] yrs, String country) {
		this.analysisName = aName;
		this.analysisParts = parts;
		this.value = val;
		this.years = yrs;
		this.countryName = country;
	}
	
	/**
	 * Sets the Result object.
	 * @param r Result object to be set to.
	 */
	public void setResult(Result r) {
		this.analysisName = r.getAnalysisName();
		this.value = r.getValue();
		this.years = r.getYears();
	}
	
	/**
	 * Gets name of analysis.
	 * @return analysis name (as a String).
	 */
	public String getAnalysisName() {
		return this.analysisName;
	}
	
	/**
	 * Gets name of the country.
	 * @return country name (as a String).
	 */
	public String getCountryName() {
		return this.countryName;
	}
	
	/**
	 * Gets the parts of the analysis.
	 * @return analysis parts as a String array.
	 */
	public String[] getAnalysisParts() {
		return this.analysisParts;
	}
	
	/**
	 * Gets the values that were calculated/processed.
	 * @return double array of values.
	 */
	public double[][] getValue() {
		return this.value;
	}
	
	/**
	 * Gets the years for which analysis has taken place.
	 * @return int array of years.
	 */
	public int[] getYears() {
		return this.years;
	}
}

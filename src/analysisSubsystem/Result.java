package analysisSubsystem;

public class Result {
	
	private String analysisName;
	private String[] analysisParts;
	private double[][] value;
	private int[] years;
	private String countryName;
//	private String[] viewers; // do we rlly need this?
	
	public Result() {
		this.analysisName = "";
		this.analysisParts = new String[0];
		this.value = new double[0][0];
		this.years = new int[0];
	}
	
	public Result(String aName, String[] parts, double[][] val, int[] yrs, String country) {
		this.analysisName = aName;
		this.analysisParts = parts;
		this.value = val;
		this.years = yrs;
		this.countryName = country;
	}
	
	public void setResult(Result r) {
		this.analysisName = r.getAnalysisName();
		this.value = r.getValue();
		this.years = r.getYears();
	}
	
	public String getAnalysisName() {
		return this.analysisName;
	}
	
	public String getCountryName() {
		return this.countryName;
	}
	
	public String[] getAnalysisParts() {
		return this.analysisParts;
	}
	
	public double[][] getValue() {
		return this.value;
	}
	
	public int[] getYears() {
		return this.years;
	}
}

package analysisSubsystem.analysisAlgorithms;

/**
 * Constructs and returns an Analysis object.
 * @author Joud El-Shawa - jelshawa - 251107864
 */
public class AnalysisFactory {

	/**
	 * Creates an Analysis object depending on its name. (Factory Design Pattern!)
	 * @param analysisName is the String containing the name of the Analysis object to be created.
	 * @return the Analysis object created.
	 */
	public Analysis createAnalysis(String analysisName) {
		switch (analysisName) {
		case "CO2 emissions vs Energy use vs PM2.5 air pollution":
			return new CO2VsEnergyVsPM2();
		
		case "PM2.5 air pollution vs Forest area":
			return new PM2VsForest();
			
		case "Ratio of CO2 emissions and GDP per capita":
			return new RCO2AndGDP();
			
		case "Average Forest area":
			return new AvgForest();
			
		case "Average of Government expenditure on education":
			return new AvgGovtExpEdu();
		
		case "Ratio of Hospital beds and Current health expenditure":
			return new RHspBedsAndHealthExp();
			
		case "Current health expenditure vs Mortality rate":
			return new HealthExpVsMort();
		
		default:	
			return new RGovtExpEduVsHealthExp();
		}			
	}
	
}

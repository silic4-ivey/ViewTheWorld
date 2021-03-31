package analysisSubsystem.analysisAlgorithms;

public class AnalysisFactory {
	
	public AnalysisFactory() {
	
	}

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
		
//		case "Ratio of Government expenditure on education vs Current health expenditure":
		default:	
			return new RGovtExpEduVsHealthExp();
		}			
	}
	
}

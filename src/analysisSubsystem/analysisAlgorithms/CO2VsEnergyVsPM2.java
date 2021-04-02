package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Data;
import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;

/**
 * Implements the interface defined by Analysis. Computes CO2 vs Energy vs PM2.5 data.
 * Uses Reader to get data and puts them into a value array along with the years. 
 * @author Joud El-Shawa
 */
public class CO2VsEnergyVsPM2 implements Analysis {
	
	/**
	 * Used to fetch data from the World Bank data base.
	 */
	private Reader reader;
	
	/**
	 * Final values that will be put into the Result object.
	 */
	private double[][] value;
	
	/**
	 * Different aspects of the analysis that will be used when rendering viewers for legends.
	 */
	private String[] parts = {"CO2 emissions (metric tons/capita)", "Energy use (kg of oil equivalent/capita)","PM2.5 air pollution (mg/m^3)"};
	
	/**
	 * Constructor. Instantiates reader.
	 */
	public CO2VsEnergyVsPM2() {
		this.reader = new Reader();
	}
	
	/**
	 * Uses reader to fetch the 3 different Data from the World Bank data base. Checks if they are null
	 * and stores the ones that are not into the value array. If all null then value is set to null.
	 * @param sel Selection object to be used when fetching data using reader.
	 * @return Result object containing analysis name, parts, values, years, and country name.
	 */
	@Override
	public Result calculate(Selection sel) {
		
		// Facade Design Pattern! (analysis algorithm knowing which data it needs to fetch from the World Bank database)
		Data co2 = reader.readData(sel.getCountryCode(), sel.getAnalysisIndicators()[0].toString(), sel.getYearStart(), sel.getYearEnd());
		Data energy = reader.readData(sel.getCountryCode(), sel.getAnalysisIndicators()[1].toString(), sel.getYearStart(), sel.getYearEnd());
		Data pm2 = reader.readData(sel.getCountryCode(), sel.getAnalysisIndicators()[2].toString(), sel.getYearStart(), sel.getYearEnd());
//		Data co2 = reader.readData(sel.getAnalysisIndicators()[0].toString(), sel);
//		Data energy = reader.readData(sel.getAnalysisIndicators()[1].toString(), sel);
//		Data pm2 = reader.readData(sel.getAnalysisIndicators()[2].toString(), sel);
		
		if (co2.getValue() != null || energy.getValue() != null || pm2.getValue() != null) {
			value = new double[3][co2.getValue().length];
			if (co2.getValue() != null) 
				value[0] = co2.getValue();
			if (energy.getValue() != null)
				value[1] = energy.getValue();
			if (pm2.getValue() != null)
				value[2] = pm2.getValue();
		}
		else {
			value = null;
		}
		
		return new Result(sel.getAnalysisName(), parts, value, co2.getYears(), sel.getCountryName());
	}

}

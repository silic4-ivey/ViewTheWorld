package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Data;
import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;

/**
 * Implements the interface defined by Analysis. Uses Reader to get data and computes the ratio of CO2 and GDP. 
 * @author Joud El-Shawa
 */
public class RCO2AndGDP implements Analysis{
	
	/**
	 * Used to fetch data from the World Bank data base.
	 */
	private Reader reader;
	
	/**
	 * The aspect of the analysis that will be used for legends when rendering viewers.
	 */
	private String[] parts = {"CO2 emissions (metric tons/capita) & GDP/capita"};
	
	/**
	 * Ratio computed that will be put into the Result object for rendering.
	 */
	private double[][] ratio;
	
	/**
	 * Constructor. Instantiates reader.
	 */
	public RCO2AndGDP() {
		this.reader = new Reader();
	}
	
	/**
	 * Uses reader to fetch the 2 different Data aspects from the World Bank data base.
	 * Checks to make sure that they are not null, and if they are not, proceeds with 
	 * calculating the ratio at each year.
	 * @param sel Selection object to be used when fetching data using reader.
	 * @return Result object containing analysis name, parts, ratios, years, and country name.
	 */
	@Override
	public Result calculate(Selection sel) {
		
		// Facade Design Pattern! (analysis algorithm knowing which data it needs to fetch from the World Bank database)
		Data co2 = reader.readData(sel.getCountryCode(), sel.getAnalysisIndicators()[0].toString(), sel.getYearStart(), sel.getYearEnd());
		Data gdp = reader.readData(sel.getCountryCode(), sel.getAnalysisIndicators()[1].toString(), sel.getYearStart(), sel.getYearEnd());

		
		if (co2.getValue() != null && gdp.getValue() != null) {
			ratio = new double[1][co2.getValue().length];
			for (int i = 0; i < co2.getValue().length; i++) {
				if (gdp.getValue()[i] != 0)
					ratio[0][i] = co2.getValue()[i]/(gdp.getValue()[i]);
				else
					ratio[0][i] = 0;
			}
		}
		return new Result(sel.getAnalysisName(), parts, ratio, co2.getYears(), sel.getCountryName());
	}

}

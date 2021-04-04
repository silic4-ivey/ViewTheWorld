package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Data;
import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;

/**
 * Class that is used to request data from the Reader and analyze the selection in terms of the 
 * ratio of total government expenditure on education vs expenditure on health.
 * @author Reese Collins 251080252 rcolli47@uwo.ca
 * 2021-04-04
 *
 */
public class RGovtExpEduVsHealthExp implements Analysis {
	
	/**
	 * Used to fetch data from the World Bank data base.
	 */
	private Reader reader;
	
	/**
	 * Different aspects of the analysis that will be used for legends when rendering viewers.
	 */
	private String[] parts = {"Ratio of Exp on Education & Exp on Health (% of GDP)"};
	
	/**
	 * Constructor. Instantiates reader.
	 */
	public RGovtExpEduVsHealthExp() {
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
		Data edu = reader.readData(sel.getCountryCode(), sel.getAnalysisIndicators()[0].toString(), sel.getYearStart(), sel.getYearEnd());
		Data health = reader.readData(sel.getCountryCode(), sel.getAnalysisIndicators()[1].toString(), sel.getYearStart(), sel.getYearEnd());

		double[][] value = null;
		if (edu.getValue() != null && health.getValue() != null) {
			value = new double[1][edu.getYears().length];
			for (int i = 0; i < edu.getValue().length; i++) {
				value[0][i] = edu.getValue()[i] / health.getValue()[i];
			}
		}
		else
			value = null;
		
		return new Result(sel.getAnalysisName(), parts, value, edu.getYears(), sel.getCountryName());
	}

}

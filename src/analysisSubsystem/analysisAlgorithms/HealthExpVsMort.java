package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Data;
import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;

/**
 * Analysis algorithm for health expenditure vs mortality rate
 * Implements analysis interface
 * @author Liam Vukasinovic
 */
public class HealthExpVsMort implements Analysis {
	/**
	 * Used for getting data from World Bank
	 */
	private Reader reader;
	/**
	 * Stores valued given to result
	 */
	private double[][] value;
	/**
	 * Used for legend
	 */
	private String[] parts = {"Current health expenditure (current US$)", "Mortality rate, infant (per 1,000 live births)"};
	
	/**
	 * Constructor for the class, creates a new reader
	 */
	public HealthExpVsMort() {
		this.reader = new Reader();
	}
	
	/**
	 * Using the given selection object, this method calculates the
	 * health expenditure vs mortality rate and returns it in new result object
	 * @param sel Selection object to help retrieve data
	 * @result Result object containing all data
	 */
	@Override
	public Result calculate(Selection sel) {
		//Facade design pattern
		Data healthExp = reader.readData(sel.getCountryCode(), sel.getAnalysisIndicators()[0].toString(), sel.getYearStart(), sel.getYearEnd());
		Data mort = reader.readData(sel.getCountryCode(), sel.getAnalysisIndicators()[1].toString(), sel.getYearStart(), sel.getYearEnd());

		value = new double[2][Integer.parseInt(sel.getYearEnd()) - Integer.parseInt(sel.getYearStart()) + 1];

		if (healthExp.getValue() != null || mort.getValue() != null) {
			if (healthExp.getValue() != null)
				value[0] = healthExp.getValue();
			else 
				value[0] = null;
			if (mort.getValue() != null)
				value[1] = mort.getValue();
			else
				value[1] = null;
		}
		else
			value = null;
		
		return new Result(sel.getAnalysisName(), parts, value, healthExp.getYears(), sel.getCountryName());
	}

}
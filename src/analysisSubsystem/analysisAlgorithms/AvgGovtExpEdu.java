package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Data;

import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;

/**
 * Analysis algorithm for the average of government expenditure on education
 * Implements analysis interface
 * @author Liam Vukasinovic
 *
 */
public class AvgGovtExpEdu implements Analysis {
	/**
	 * Used to get data from World Bank
	 */
	private Reader reader;
	/**
	 * Values to be put into result object
	 */
	private double[][] value;
	/**
	 * Used for legend.
	 */
	private String[] parts = {"Average Government exp on education (% of GDP)"};
	
	/**
	 * Constructor for the class, creates new reader
	 */
	public AvgGovtExpEdu() {
		this.reader = new Reader();
	}
	
	/**
	 * Using given selection object, this method calculates the average government
	 * expenditure on education and returns it in a new result object.
	 * @param sel Selection object used for getting data from reader
	 * @result Result containing all data
	 */
	@Override
	public Result calculate(Selection sel) {
		//Facade design pattern
		Data govExp = reader.readData(sel.getCountryCode(), sel.getAnalysisIndicators()[0].toString(), sel.getYearStart(), sel.getYearEnd());

		double average = 0;
		value = new double[1][1];
		if (govExp.getValue() != null) {
			
			for (int k = 0; k < govExp.getValue().length;k++) {
				average += average + govExp.getValue()[k];
			}
			value[0][0] = average/govExp.getValue().length/100;
		}
		else 
			value = null;

		
		return new Result(sel.getAnalysisName(),parts,value,govExp.getYears(),sel.getCountryName());
	}

}

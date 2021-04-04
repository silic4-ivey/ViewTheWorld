package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Data;
import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;
/**
 * Class that is used to create the result for the analysis of average forest size
 * @author Stefan Ilic, 321091076, silic4@uwo.ca
 * 2021-04-03
 *
 */
public class AvgForest implements Analysis {
	private Reader reader;
	private double[][] value;  // Array in which the data for each analysis is stored
	private String[] parts = {"Average forest area (% of land area)"};  // name of analysis
	
	/**
	 * Constructor for the class, initializes reader
	 */
	public AvgForest() {
		this.reader = new Reader();
	}
	
	/**
	 * Creates a result object storing the result of the analysis type
	 * @param Selection sel, the selected country analysis type and etc
	 * @return Result, the result that contains the calculated analysis
	 */
	@Override
	public Result calculate(Selection sel) {
		// Read data
		Data forest = reader.readData(sel.getCountryCode(), sel.getAnalysisIndicators()[0].toString(), sel.getYearStart(), sel.getYearEnd());
		
		final int PERCENT_CONVERTER = 100;
		
		double sum = 0;
		
		if (forest.getValue() != null) {
			
			value = new double[1][1];  // intialize value
			
			// Calculate average forest size over the range of years
			for (int i =0; i<forest.getValue().length; i++) {
				sum = sum + forest.getValue()[i];
			}
			value [0][0] = sum/forest.getValue().length/PERCENT_CONVERTER;  // store average in value
		}
		else
			value = null;
		
		return new Result(sel.getAnalysisName(), parts, value, forest.getYears(), sel.getCountryName());  // return the result
	}

}

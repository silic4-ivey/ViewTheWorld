package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Data;
import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;
/**
 * Class that is used to create the result for the analysis of average forest size versus air quality
 * @author Stefan Ilic, 321091076, silic4@uwo.ca
 * 2021-04-03
 *
 */
public class PM2VsForest implements Analysis{
	private Reader reader;  
	private double[][] value;  // Array in which the data for each analysis is stored
	private String[] parts = {"PM2.5 air pollution (mcg/m^3)", "Forest area (% of land)"};  // name of analysis
	
	/**
	 * Constructor for the class, initializes reader
	 */
	public PM2VsForest() {
		this.reader = new Reader();
	}
	
	/**
	 * Creates a result object storing the result of the analysis type
	 * @param sel Selection, the selected country analysis type and etc
	 * @return Result, the result that contains the calculated analysis
	 */
	@Override
	public Result calculate(Selection sel) {
		// Reads data
		Data pm2V = reader.readData(sel.getCountryCode(), sel.getAnalysisIndicators()[0].toString(), sel.getYearStart(), sel.getYearEnd());
		Data forest = reader.readData(sel.getCountryCode(), sel.getAnalysisIndicators()[1].toString(), sel.getYearStart(), sel.getYearEnd());

		value = new double[2][Integer.parseInt(sel.getYearEnd()) - Integer.parseInt(sel.getYearEnd()) + 1];  // initialize value array
		
		// Store data in value array
		if (pm2V.getValue() != null || forest.getValue() != null) { 
			if (pm2V.getValue() != null) 
				value[0] = pm2V.getValue();
			else
				value[0] = null;
			
			if (forest.getValue() != null)
				value[1] = forest.getValue();
			else
				value[1] = null;
		}
		else
			value = null;
		
		return new Result(sel.getAnalysisName(), parts, value, pm2V.getYears(), sel.getCountryName());  // returns value
	}

}

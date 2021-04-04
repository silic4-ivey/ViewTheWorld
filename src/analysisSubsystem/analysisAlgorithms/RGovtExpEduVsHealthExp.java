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
	private Reader reader;
	private String[] parts = {"Ratio of Exp on Education & Exp on Health (% of GDP)"};
	
	public RGovtExpEduVsHealthExp() {
		this.reader = new Reader();
	}
	
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

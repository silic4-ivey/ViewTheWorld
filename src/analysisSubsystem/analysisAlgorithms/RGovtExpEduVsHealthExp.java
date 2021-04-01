package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Data;
import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;

public class RGovtExpEduVsHealthExp implements Analysis {
	private Reader reader;
	private String[] parts = {"Total Expenditure on Education (% of GDP)", "Total Expenditure on Health (% of GDP)"};
	
	public RGovtExpEduVsHealthExp() {
		this.reader = new Reader();
	}
	
	@Override
	public Result calculate(Selection sel) {
		Data edu = reader.readData(sel.getAnalysisIndicators()[0], sel);
		Data health = reader.readData(sel.getAnalysisIndicators()[1], sel);
		
		double[][] value = null;
		if (edu.getValue() != null && health.getValue() != null) {
			value = new double[2][edu.getValue().length];
			value[0] = edu.getValue();
			value[1] = health.getValue();
		}
		return new Result(sel.getAnalysisName(), parts, value, edu.getYears());
	}

}

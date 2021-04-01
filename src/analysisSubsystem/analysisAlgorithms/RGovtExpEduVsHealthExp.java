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
			value = new double[1][edu.getValue().length];
			for (int i = 0; i < edu.getValue().length; i++) {
				value[0][i] = edu.getValue()[i] / health.getValue()[i];
			}
		}
		return new Result(sel.getAnalysisName(), parts, value, edu.getYears());
	}

}

package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Data;
import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;

public class HealthExpVsMort implements Analysis {
	private Reader reader;
	private double[][] value;
	private String[] parts = {"Current health expenditure (current US$)", "Mortality rate, infant (per 1,000 live births)"};
	
	public HealthExpVsMort() {
		this.reader = new Reader();
	}
	
	@Override
	public Result calculate(Selection sel) {
		Data healthExp = reader.readData(sel.getAnalysisIndicators()[0].toString(), sel);
		Data mort = reader.readData(sel.getAnalysisIndicators()[1].toString(), sel);
		
		value = new double[2][healthExp.getValue().length];

		if (healthExp.getValue() != null || mort.getValue() != null) {
			if (healthExp.getValue() != null)
				value[0] = healthExp.getValue();
			if (mort.getValue() != null)
				value[1] = mort.getValue();
		}
		else {
			value = null;
		}
		return new Result(sel.getAnalysisName(), parts, value, healthExp.getYears(), sel.getCountryName());
	}

}
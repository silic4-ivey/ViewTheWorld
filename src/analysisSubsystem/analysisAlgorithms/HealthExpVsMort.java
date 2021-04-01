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
		
		if (healthExp.getValue() != null && mort.getValue() != null) {
			value = new double[2][healthExp.getValue().length];
			value[0] = healthExp.getValue();
			value[1] = mort.getValue();
		}
		return new Result(sel.getAnalysisName(), parts, value, healthExp.getYears(), sel.getCountryName());
	}

}
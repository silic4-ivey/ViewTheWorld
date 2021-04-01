package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Data;

import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;

public class AvgGovtExpEdu implements Analysis {
	private Reader reader;
	private double[][] value;
	private String[] parts = {"Average Government expenditure on education (% of GDP)"};
	
	public AvgGovtExpEdu() {
		this.reader = new Reader();
	}
	
	
	@Override
	public Result calculate(Selection sel) {
		Data govExp = reader.readData(sel.getAnalysisIndicators()[0].toString(), sel);
		double average = 0;
		value = new double[1][1];
		if (govExp.getValue() != null) {
			
			for (int k = 0; k < govExp.getValue().length;k++) {
				average += average + govExp.getValue()[k];
			}
			
		}
		value[0][0] = average/govExp.getValue().length;
		
		return new Result(sel.getAnalysisName(),parts,value,govExp.getYears());
	}

}

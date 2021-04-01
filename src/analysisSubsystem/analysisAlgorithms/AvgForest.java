package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Data;
import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;

public class AvgForest implements Analysis {
	private Reader reader;
	private double[][] value;
	private String[] parts = {"Average forest area (% of land area)"};
	
	public AvgForest() {
		this.reader = new Reader();
	}
	
	
	@Override
	public Result calculate(Selection sel) {
		Data forest = reader.readData(sel.getAnalysisIndicators()[0].toString(), sel);
		final int PERCENT_CONVERTER = 100;
		
		double sum = 0;
		
		if (forest.getValue() != null) {
			
			value = new double[1][1];
			
			for (int i =0; i<forest.getValue().length; i++) {
				sum = sum + forest.getValue()[i];
				System.out.println(sum);
			}
			
			value [0][0] = sum/forest.getValue().length/PERCENT_CONVERTER;

		}
		
//		System.out.println(value[0][0]);

		return new Result(sel.getAnalysisName(), parts, value, forest.getYears(), sel.getCountryName());
	}

}

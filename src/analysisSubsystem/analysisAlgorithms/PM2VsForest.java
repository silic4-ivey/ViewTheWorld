package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Data;
import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;

public class PM2VsForest implements Analysis{
	private Reader reader;
	private double[][] value;
	private String[] parts = {"PM2.5 air pollution (mcg/m^3)", "Forest area (% of land)"};
	
	public PM2VsForest() {
		this.reader = new Reader();
	}
	
	@Override
	public Result calculate(Selection sel) {
//		Data pm2V = reader.readData(sel.getAnalysisIndicators()[0].toString(), sel);
		Data pm2V = reader.readData(sel.getCountryCode(), sel.getAnalysisIndicators()[0].toString(), sel.getYearStart(), sel.getYearEnd());

		Data forest = reader.readData(sel.getCountryCode(), sel.getAnalysisIndicators()[1].toString(), sel.getYearStart(), sel.getYearEnd());

//		Data forest = reader.readData(sel.getAnalysisIndicators()[1].toString(), sel);
		value = new double[2][pm2V.getValue().length];
		if (pm2V.getValue() != null || forest.getValue() != null) {
			if (pm2V.getValue() != null) 
					value[0] = pm2V.getValue();
			if (forest.getValue() != null)
				value[1] = forest.getValue();
		}
		else {
			value = null;
		}
		
		return new Result(sel.getAnalysisName(), parts, value, pm2V.getYears(), sel.getCountryName());
	}

}

package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Data;
import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;

public class RCO2AndGDP implements Analysis{
	private Reader reader;
	private String[] parts = {"CO2 emissions (metric tons/capita) & GDP/capita"};
	private double[][] ratio;
	
	public RCO2AndGDP() {
		this.reader = new Reader();
	}
	
	@Override
	public Result calculate(Selection sel) {
		Data co2 = reader.readData(sel.getAnalysisIndicators()[0].toString(), sel);
		Data gdp = reader.readData(sel.getAnalysisIndicators()[1].toString(), sel);
		
		if (co2.getValue() != null && gdp.getValue() != null) {
			ratio = new double[1][co2.getValue().length];
			for (int i = 0; i < co2.getValue().length; i++) {
				if (gdp.getValue()[i] != 0) {
					ratio[0][i] = co2.getValue()[i]/(gdp.getValue()[i]);
				}
				else
					ratio[0][i] = 0;
			}
		}
		return new Result(sel.getAnalysisName(), parts, ratio, co2.getYears(), sel.getCountryName());
	}

}

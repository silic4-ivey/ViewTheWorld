package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Data;
import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;

public class CO2VsEnergyVsPM2 implements Analysis {
	private Reader reader;
	private double[][] value;
	private String[] parts = {"CO2 emissions (metric tons/capita)", "Energy use (kg of oil equivalent/capita)","PM2.5 air pollution (mcg/m^3)"};
	
	public CO2VsEnergyVsPM2() {
		this.reader = new Reader();
	}
	
	@Override
	public Result calculate(Selection sel) {
		Data co2 = reader.readData(sel.getAnalysisIndicators()[0].toString(), sel);
		Data energy = reader.readData(sel.getAnalysisIndicators()[1].toString(), sel);
		Data pm2 = reader.readData(sel.getAnalysisIndicators()[2].toString(), sel);
		
		if (co2.getValue() != null && energy.getValue() != null && pm2.getValue() != null) {
			value = new double[3][Math.min(Math.min(co2.getValue().length, energy.getValue().length), pm2.getValue().length)];
//			value = new double[2][co2.getValue().length];
			value[0] = co2.getValue();
			value[1] = energy.getValue();
			value[2] = pm2.getValue();
		}
		return new Result(sel.getAnalysisName(), parts, value, co2.getYears());
	}

}

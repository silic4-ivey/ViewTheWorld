package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Data;
import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;

public class CO2VsEnergyVsPM2 implements Analysis {
	private Reader reader;
	private double[][] value;
	private String[] parts = {"CO2 emissions (metric tons/capita)", "Energy use (kg of oil equivalent/capita)","PM2.5 air pollution (mg/m^3)"};
	
	public CO2VsEnergyVsPM2() {
		this.reader = new Reader();
	}
	
	@Override
	public Result calculate(Selection sel) {
		Data co2 = reader.readData(sel.getAnalysisIndicators()[0].toString(), sel);
		Data energy = reader.readData(sel.getAnalysisIndicators()[1].toString(), sel);
		Data pm2 = reader.readData(sel.getAnalysisIndicators()[2].toString(), sel);
		
//		if (co2.getValue() != null && energy.getValue() != null && pm2.getValue() != null) {
		if (co2.getValue() != null || energy.getValue() != null || pm2.getValue() != null) {
			System.out.println("not all null!!!");
			value = new double[3][co2.getValue().length];
//			value = new double[2][co2.getValue().length];
			if (co2.getValue() != null) 
				value[0] = co2.getValue();
			if (energy.getValue() != null)
				value[1] = energy.getValue();
			if (pm2.getValue() != null)
				value[2] = pm2.getValue();
		}
		else {
			value = null;
//			System.out.println(co2.getValue().toString());
//			System.out.println(energy.getValue().toString());
//			System.out.println(pm2.getValue().toString());
			System.out.println("all null!!!");
		}
		
		
		return new Result(sel.getAnalysisName(), parts, value, co2.getYears(), sel.getCountryName());
	}

}

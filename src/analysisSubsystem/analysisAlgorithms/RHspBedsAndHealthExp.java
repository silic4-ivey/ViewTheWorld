package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Data;
import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;

public class RHspBedsAndHealthExp implements Analysis {
	private Reader reader;
	private double[][] ratio;
	private String[] parts = {"Hospital Beds/1000 people & Health exp/1000 people"};
	
	public RHspBedsAndHealthExp() {
		this.reader = new Reader();
	}

	@Override
	public Result calculate(Selection sel) {
		System.out.println(sel.getAnalysisIndicators()[0]);
		Data hspBeds = reader.readData(sel.getAnalysisIndicators()[0].toString(), sel);
		Data healthExp = reader.readData(sel.getAnalysisIndicators()[1].toString(), sel);
		
		if (hspBeds.getValue() != null && healthExp.getValue() != null) {
			ratio = new double[1][hspBeds.getValue().length];
			for (int i = 0; i < hspBeds.getValue().length; i++) {
				if (healthExp.getValue()[i] != 0) {
					ratio[0][i] = hspBeds.getValue()[i]/(healthExp.getValue()[i]*1000);
				}
				else
					ratio[0][i] = 0;
			}
		}
		return new Result(sel.getAnalysisName(), parts, ratio, hspBeds.getYears(), sel.getCountryName());
	}
}

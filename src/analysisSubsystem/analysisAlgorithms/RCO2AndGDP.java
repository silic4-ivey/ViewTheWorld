package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;

public class RCO2AndGDP implements Analysis{
	private Reader reader;
	private String[] parts = {"CO2 emissions (metric tons/capita) & GDP/capita"};
	
	public RCO2AndGDP() {
		this.reader = new Reader();
	}
	
	@Override
	public Result calculate(Selection sel) {
		// TODO Auto-generated method stub
		return null;
	}

}

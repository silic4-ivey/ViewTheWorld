package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;

public class HealthExpVsMort implements Analysis {
	private Reader reader;
	private String[] parts = {"Health exp/capita", "Infant mortality rate/1000 births"};
 	
	public HealthExpVsMort() {
		this.reader = new Reader();
	}
	
	@Override
	public Result calculate(Selection sel) {
		// TODO Auto-generated method stub
		return null;
	}

}

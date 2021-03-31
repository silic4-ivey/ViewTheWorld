package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;

public class PM2VsForest implements Analysis{
	private Reader reader;
	private String[] parts = {"PM2.5 air pollution (mcg/m^3)", "Forest area (% of land)"};
	
	public PM2VsForest() {
		this.reader = new Reader();
	}
	
	@Override
	public Result calculate(Selection sel) {
		// TODO Auto-generated method stub
		return null;
	}

}

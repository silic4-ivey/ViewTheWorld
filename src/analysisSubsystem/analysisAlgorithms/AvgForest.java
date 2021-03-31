package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;

public class AvgForest implements Analysis {
	private Reader reader;
	private String[] parts = {"Average forest area (% of land area)"};
	
	public AvgForest() {
		this.reader = new Reader();
	}
	
	
	@Override
	public Result calculate(Selection sel) {
		// TODO Auto-generated method stub
		return null;
	}

}

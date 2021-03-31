package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;

public class AvgGovtExpEdu implements Analysis {
	private Reader reader;
	
	public AvgGovtExpEdu() {
		this.reader = new Reader();
	}
	
	@Override
	public Result calculate(Selection sel) {
		// TODO Auto-generated method stub
		return null;
	}

}

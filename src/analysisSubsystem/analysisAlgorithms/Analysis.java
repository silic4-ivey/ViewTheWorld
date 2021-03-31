package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Result;
import frontEnd.selectionSubsystem.Selection;

public interface Analysis {
	public Result calculate(Selection sel);
}

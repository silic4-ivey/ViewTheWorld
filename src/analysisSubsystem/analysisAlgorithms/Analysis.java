package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Result;
import frontEnd.selectionSubsystem.Selection;

/**
 * Specifies the interface of the objects that can be manufactured by AnalysisFactory.
 * @author Joud El-Shawa
 */
public interface Analysis {
	public Result calculate(Selection sel);
}

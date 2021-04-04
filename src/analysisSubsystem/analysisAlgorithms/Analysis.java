package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Result;
import frontEnd.selectionSubsystem.Selection;

/**
 * Specifies the interface of the objects that can be manufactured by AnalysisFactory.
 * @author Joud El-Shawa
 */
public interface Analysis {
	/**
	 * Fetches Data and performs calculations if needed. 
	 * Stores (processed) data, years, country name, and parts of analysis
	 * in a Result object.
	 * @param sel Selection object to be used when fetching data using reader.
	 * @return Result object containing data needed for rendering.
	 */
	public Result calculate(Selection sel);
}

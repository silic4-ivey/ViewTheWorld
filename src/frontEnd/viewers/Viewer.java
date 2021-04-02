package frontEnd.viewers;

import analysisSubsystem.Result;

/**
 * Specifies the interface of the objects that can be manufactured by ViewerFactory.
 * @author Joud El-Shawa
 */
public interface Viewer {
	public void notify(Result res);
}

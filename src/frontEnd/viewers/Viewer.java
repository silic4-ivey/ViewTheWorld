package frontEnd.viewers;

import analysisSubsystem.Result;

/**
 * Specifies the interface of the objects that can be manufactured by ViewerFactory.
 * @author Joud El-Shawa
 */
public interface Viewer {
	/**
	 * Method that will construct a chart panel by using the Result object and will call the MainUI's display method.
	 * @param res Result object that contains the data to be rendered.
	 */
	public void notify(Result res);
}

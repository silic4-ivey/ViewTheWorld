package analysisSubsystem;

import java.util.List;
import analysisSubsystem.analysisAlgorithms.Analysis;
import analysisSubsystem.analysisAlgorithms.AnalysisFactory;
import frontEnd.MainUI;
import frontEnd.selectionSubsystem.Selection;
import frontEnd.viewers.Viewer;
import frontEnd.viewers.ViewerFactory;

/**
 * Calls the analysis object's calculate method and notifies viewers.
 * @author Joud El-Shawa
 */
public class ComputationServer {
	
	/**
	 * Used to do the calculations.
	 */
	private Analysis cAnalysis;
	
	/**
	 * List of viewers selected to be created and notified.
	 */
	private List<String> viewersList;
	
	/**
	 * Creates an Analysis object through its createAnalysis(a:String) method.
	 */
	private AnalysisFactory aFactory;
	
	/**
	 * Creates a Viewer object through its createViewer(v:String) method.
	 */
	private ViewerFactory vFactory;
	
	/**
	 * Contains the final data needed for rendering.
	 */
	private Result res;
	
	/**
	 * Constructor. Instantiates the AnalysisFactory and ViewerFactory objects.
	 */
	public ComputationServer() {
		this.aFactory = new AnalysisFactory();
		this.vFactory = new ViewerFactory();
	}
	
	/**
	 * Calls the aFactory's createAnalysis method to instantiate the cAnalysis object.
	 * @param analysisName name of the analysis object to be created.
	 */
	public void setAnalysis(String analysisName) {
		cAnalysis = aFactory.createAnalysis(analysisName); // Factory Design Pattern!
	}
	
	/**
	 * Calls the analysis object's calculate method, gets back a Result object, and either 
	 * displays an error if the Result is null or calls the notifyViewers method.
	 * @param sel Selection object that contains all of the User's choices.
	 */
	public void doAnalysis(Selection sel) {
		this.viewersList = sel.getViewersList();
		this.res = cAnalysis.calculate(sel);		
		if (res.getValue() == null) {	// if no data is available
			MainUI mainDisplay = MainUI.getInstance();
			mainDisplay.displayErrorMessage("No data available");
		}
		else
			notifyViewers();
	}
	
	/**
	 * Loops through list of viewers, uses vFractory to create them, and notifies each one with the Result.
	 */
	private void notifyViewers() {
		for (String v : viewersList) {
			Viewer curr = vFactory.createViewer(v);
			curr.notify(res); 
		}
	}	
}

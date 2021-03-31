package analysisSubsystem;

import java.util.List;
import analysisSubsystem.analysisAlgorithms.Analysis;
import analysisSubsystem.analysisAlgorithms.AnalysisFactory;
import frontEnd.MainUI;
import frontEnd.selectionSubsystem.Selection;
import frontEnd.viewers.Viewer;
import frontEnd.viewers.ViewerFactory;

public class ComputationServer {
	private Analysis cAnalysis;
	private List<String> viewersList;
	private AnalysisFactory aFactory;
	private ViewerFactory vFactory;
	private Result res;
	
	public ComputationServer() {
		this.aFactory = new AnalysisFactory();
		this.vFactory = new ViewerFactory();
		
		this.cAnalysis = aFactory.createAnalysis("CO2 emissions vs Energy use vs PM2.5 air pollution");
	}
	
	public void setAnalysis(String analysisName) {
		cAnalysis = aFactory.createAnalysis(analysisName);
	}
	
	public void doAnalysis(Selection sel) {
		this.viewersList = sel.getViewersList();
		this.res = cAnalysis.calculate(sel);
		if (res.getValue() == null) {	
			MainUI mainDisplay = MainUI.getInstance();
			mainDisplay.displayErrorMessage("No data available");
		}
		else
			notifyViewers();
	}
	
	private void notifyViewers() {
		for (String v : viewersList) {
			Viewer curr = vFactory.createViewer(v);
			curr.notify(res); 
		}
	}	
}

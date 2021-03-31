package frontEnd.viewers;

import analysisSubsystem.Result;
import frontEnd.MainUI;

public class TimeSeries implements Viewer {

	@Override
	public void notify(Result res) {
		MainUI mainDisplay = MainUI.getInstance();
		mainDisplay.display("TimeSeries", res);	
	}

}

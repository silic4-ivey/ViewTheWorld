package frontEnd.viewers;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import analysisSubsystem.Result;
import frontEnd.MainUI;
/**
 * Class that is used to create and display a pie chart graph. Used to represent percentage based data.
 * @author Stefan Ilic, 321091076, silic4@uwo.ca
 * 2021-04-03
 *
 */
public class PieChart implements Viewer {

	@Override
	public void notify(Result res) {
		MainUI mainDisplay = MainUI.getInstance();
		final int PIE_VALUE_LOC = 0;
		
		var dataset = new DefaultPieDataset();   // create new data set
		// Set the values on the data set. First point is the data to be displayed then teh second point is the rest of the pie chart
		dataset.setValue( res.getAnalysisParts()[PIE_VALUE_LOC] + " " +Math.ceil(res.getValue()[PIE_VALUE_LOC][PIE_VALUE_LOC]*100) + "%", res.getValue()[PIE_VALUE_LOC][PIE_VALUE_LOC]);
		dataset.setValue("Other "+ Math.floor((1-res.getValue()[PIE_VALUE_LOC][PIE_VALUE_LOC])*100) +"%", 1-res.getValue()[PIE_VALUE_LOC][PIE_VALUE_LOC]);
		 
		String title = res.getAnalysisName() + " in " + res.getCountryName();  // Sets the title
		
		JFreeChart pieChart = ChartFactory.createPieChart(title,
		dataset, true, true, false);
		
		// Sets dimensions and appearance of the piechart
		ChartPanel chartPanel = new ChartPanel(pieChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		mainDisplay.display(chartPanel);

	}
}

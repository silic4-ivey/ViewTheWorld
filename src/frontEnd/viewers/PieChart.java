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

public class PieChart implements Viewer {

	@Override
	public void notify(Result res) {
		MainUI mainDisplay = MainUI.getInstance();
		final int PIE_VALUE_LOC = 0;
		
		var dataset = new DefaultPieDataset(); 
		dataset.setValue( res.getAnalysisParts()[PIE_VALUE_LOC] + " " +Math.ceil(res.getValue()[PIE_VALUE_LOC][PIE_VALUE_LOC]*100) + "%", res.getValue()[PIE_VALUE_LOC][PIE_VALUE_LOC]);
		dataset.setValue("Other "+ Math.floor((1-res.getValue()[PIE_VALUE_LOC][PIE_VALUE_LOC])*100) +"%", 1-res.getValue()[PIE_VALUE_LOC][PIE_VALUE_LOC]);
		 
		JFreeChart pieChart = ChartFactory.createPieChart(res.getAnalysisName(),
		dataset, true, true, false);
		

		ChartPanel chartPanel = new ChartPanel(pieChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		mainDisplay.display(chartPanel);

	}
}

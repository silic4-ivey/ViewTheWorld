package frontEnd.viewers;

import java.awt.Color;
import java.awt.Dimension;
import java.time.Year;

import javax.swing.BorderFactory;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import analysisSubsystem.Result;
import frontEnd.MainUI;

/**
 * Class for creating the line chart viewer used for presenting data.
 * Implements the viewer interface.
 * @author Liam Vukasinovic
 */
public class LineChart implements Viewer {
	
	/**
	 * Creates line chart based off result object.
	 * @param res contains information needed for rendering.
	 */
	@Override
	public void notify(Result res) {
		MainUI mainDisplay = MainUI.getInstance();
		
		//Used for holding given data
		XYSeries[] series = new XYSeries[res.getValue().length];
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		//Puts data from res into the series and dataset variables
		for (int k = 0; k < res.getValue().length; k++) {
			series[k] = new XYSeries(res.getAnalysisParts()[k]);
			
			for(int i = 0; i < res.getValue()[k].length;i++) {
				int year = res.getYears()[i];
				series[k].add(year, res.getValue()[k][i]);
			}
			dataset.addSeries(series[k]);
		}
		
		//Following code sets up visuals for the chart
		String title = res.getAnalysisName() + " in " + res.getCountryName();

		JFreeChart lineChart = ChartFactory.createXYLineChart(title, "Year", "", dataset, PlotOrientation.VERTICAL, true, true, false);
		XYPlot plot = lineChart.getXYPlot();
		
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);
		
		lineChart.getLegend().setFrame(BlockBorder.NONE);
		lineChart.setTitle(new TextTitle(title));
		
		//Creates sizing and appearence for line chart
		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		mainDisplay.display(chartPanel);
	}
}

package frontEnd.viewers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;

import analysisSubsystem.Result;
import frontEnd.MainUI;

/**
 * Implements the interface defined by Viewer. Constructs the scatter chart panel based on the Result object.
 * @author Joud El-Shawa
 */
public class ScatterChart implements Viewer {
	
	/**
	 * Constructs a scatter chart panel based on the Result object.
	 * Calls the MainUI's display method and sends it the chart panel created so it can be displayed.
	 * @param res contains all the information necessary for rendering.
	 */
	@Override
	public void notify(Result res) {		
		TimeSeries[] series = new TimeSeries[res.getValue().length];
		TimeSeriesCollection[] datasets = new TimeSeriesCollection[res.getValue().length];
		
		// getting the data from the result object, storing it in series, and linking the series to datasets
		for (int i = 0; i < series.length; i++) {
			datasets[i] = new TimeSeriesCollection();
			series[i] = new TimeSeries(res.getAnalysisParts()[i]);
			for (int j = 0; j < res.getValue()[i].length; j++) {
				int year = res.getYears()[j];
				series[i].add(new Year(year), res.getValue()[i][j]);
			}
			datasets[i].addSeries(series[i]);
		}
		
		XYPlot plot = new XYPlot();
		
		//creating renderers
		XYItemRenderer[] itemrenderers = new XYItemRenderer[datasets.length];
		for (int k = 0; k < itemrenderers.length; k++) {
			itemrenderers[k] = new XYLineAndShapeRenderer(false, true);
			plot.setDataset(k,datasets[k]);
			plot.setRenderer(k,itemrenderers[k]);
			plot.setRangeAxis(k, new NumberAxis(""));
			plot.mapDatasetToRangeAxis(k, k);
		}
		
		DateAxis domainAxis = new DateAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis(""));
	
		String title = res.getAnalysisName() + " in " + res.getCountryName();
		
		JFreeChart scatterChart = new JFreeChart(title,new Font("Serif", java.awt.Font.BOLD, 18), plot, true);
	
		ChartPanel chartPanel = new ChartPanel(scatterChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		MainUI mainDisplay = MainUI.getInstance();
		mainDisplay.display(chartPanel); // calls the MainUI's display method so chart panel can be displayed to the User
	}
}

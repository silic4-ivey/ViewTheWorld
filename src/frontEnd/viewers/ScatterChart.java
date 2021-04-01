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

public class ScatterChart implements Viewer {

	@Override
	public void notify(Result res) {
		MainUI mainDisplay = MainUI.getInstance();
		
		TimeSeries[] series = new TimeSeries[res.getValue().length];
		TimeSeriesCollection[] datasets = new TimeSeriesCollection[res.getValue().length];
		
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
	
		JFreeChart scatterChart = new JFreeChart(res.getAnalysisName(),new Font("Serif", java.awt.Font.BOLD, 18), plot, true);
	
		ChartPanel chartPanel = new ChartPanel(scatterChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		mainDisplay.display(chartPanel);
	}
}

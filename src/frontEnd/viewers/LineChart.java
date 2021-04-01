package frontEnd.viewers;

import java.awt.Color;
import java.awt.Dimension;

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

public class LineChart implements Viewer {
	
	@Override
	public void notify(Result res) {
		MainUI mainDisplay = MainUI.getInstance();
		
		XYSeries[] series = new XYSeries[res.getValue().length];
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		for (int k = 0; k < res.getValue().length; k++) {
			series[k] = new XYSeries(res.getAnalysisParts()[k]);
			
			for(int i = 0; i < res.getValue()[k].length;i++) {
				int year = res.getYears()[i];
				series[k].add(year, res.getValue()[k][i]);
			}
			dataset.addSeries(series[k]);
		}

		
		JFreeChart lineChart = ChartFactory.createXYLineChart(res.getAnalysisName(), "Year", "", dataset, PlotOrientation.VERTICAL, true, true, false);
		XYPlot plot = lineChart.getXYPlot();
		
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);
		
		
		lineChart.getLegend().setFrame(BlockBorder.NONE);
		lineChart.setTitle(new TextTitle(res.getAnalysisName()));
		
		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		mainDisplay.display(chartPanel);
	}
}

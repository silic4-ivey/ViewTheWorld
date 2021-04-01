package frontEnd.viewers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import analysisSubsystem.Result;
import frontEnd.MainUI;

public class BarChart implements Viewer {
	
	@Override
	public void notify(Result res) {
		DefaultCategoryDataset dataset[] = new DefaultCategoryDataset[res.getValue().length];
		
//		dataset.setValue(5.6, "Mortality/1000 births", "2018");

		
		for (int i = 0; i < dataset.length; i++) {
			dataset[i] = new DefaultCategoryDataset();
//			double d = 0;
			for (int j = 0; j < res.getValue()[i].length; j++) {
				String year = Integer.toString(res.getYears()[j]);
				System.out.println("now for years\n" + year);
				dataset[i].setValue(res.getValue()[i][j],res.getAnalysisParts()[i].toString(),year);
//				d +=res.getValue()[i][j];
			}
//			averages[i] = d/(res.getValue()[i].length);
//			datasets[i].addSeries(series[i]);
		}
		
		CategoryPlot plot = new CategoryPlot();
		CategoryAxis domainAxis = new CategoryAxis("Year");
		plot.setDomainAxis(domainAxis);
		
		BarRenderer barrenderers[] = new BarRenderer[dataset.length];
		for (int k = 0; k < barrenderers.length; k++) {
			barrenderers[k] = new BarRenderer();
			plot.setDataset(k,dataset[k]);
			plot.setRenderer(k,barrenderers[k]);
			plot.setRangeAxis(k, new NumberAxis(""));
			plot.mapDatasetToRangeAxis(k, k);
		}
		

		
		JFreeChart barChart = new JFreeChart("Mortality vs Expenses & Hospital Beds",
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		MainUI mainDisplay = MainUI.getInstance();
		mainDisplay.display(chartPanel);
	}

}

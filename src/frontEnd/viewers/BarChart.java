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

/**
 * Implements the interface defined by Viewer. Constructs the bar chart panel based on the Result object.
 * @author Joud El-Shawa
 */
public class BarChart implements Viewer {
	
	/**
	 * Constructs a bar chart panel based on the Result object.
	 * Calls the MainUI's display method and sends it the chart panel created so it can be displayed.
	 * @param res contains all the information necessary for rendering.
	 */
	@Override
	public void notify(Result res) {
		DefaultCategoryDataset dataset[] = new DefaultCategoryDataset[res.getValue().length];

		// getting the data from the result object and putting into datasets  
		for (int i = 0; i < dataset.length; i++) {
			dataset[i] = new DefaultCategoryDataset();
			for (int j = 0; j < res.getValue()[i].length; j++) {
				String year = Integer.toString(res.getYears()[j]);
				dataset[i].setValue(res.getValue()[i][j],res.getAnalysisParts()[i].toString(),year);
			}
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
		
		String title = res.getAnalysisName() + " in " + res.getCountryName();
		
		JFreeChart barChart = new JFreeChart(title,
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);
		
		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		MainUI mainDisplay = MainUI.getInstance();	// gets instance of MainUI
		mainDisplay.display(chartPanel);	// calls the display method so bar chart panel can be displayed to the User
	}

}

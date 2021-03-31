package frontEnd.viewers;

public class ViewerFactory {
	public Viewer createViewer(String v) { 
		switch (v) {
		case "Bar Chart":
			return new BarChart();
		
		case "Pie Chart":
			return new PieChart();
			
		case "Line Chart":
			return new LineChart();
		
		case "Scatter Chart":
			return new ScatterChart();
		
		case "Time Series":
			return new TimeSeries(); 
			
		default:
			return new Report();
		}
	}
}

package frontEnd.viewers;

/**
 * Constructs and returns a Viewer.
 * @author Joud El-Shawa - jelshawa - 251107864
 */
public class ViewerFactory {
	
	/**
	 * Creates a Viewer object depending on its name. (Factory Design Pattern!)
	 * @param v is the String containing the name of the Viewer object to be created.
	 * @return the Viewer object created.
	 */
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
			
		default:	// if none of the above then must be a Report viewer
			return new Report();
		}
	}
}

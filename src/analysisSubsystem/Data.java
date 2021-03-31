package analysisSubsystem;

public class Data {
	private double[] value;
	private int[] years;
	
	public Data(double[] val, int[] yrs) {
		this.value = val;
		this.years = yrs;
	}
	
	public double[] getValue() {
		return this.value;
	}
	
	public int[] getYears() {
		return this.years;
	}
	
	public void setValue(double[] val) {
		this.value = val;
	}
	
	public void setYears(int[] yrs) {
		this.years = yrs;
	}
}

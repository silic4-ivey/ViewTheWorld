package analysisSubsystem;

/**
 * This class defines the object returned by the Reader when it reads data from the World Bank Database.
 * @author Joud El-Shawa
 */
public class Data {

	/**
	 * Each index is a value corresponding to the year in the years array.
	 */
	private double[] value;
	
	/**
	 * Array of years for which data was retrieved.
	 */
	private int[] years;
	
	/**
	 * Constructor. Instantiates the value and years arrays.
	 * @param val Values to be put into the value array.
	 * @param yrs Years to be put into the years array.
	 */
	public Data(double[] val, int[] yrs) {
		this.value = val;
		this.years = yrs;
	}
	
	/**
	 * Gets value in the object.
	 * @return array of doubles.
	 */
	public double[] getValue() {
		return this.value;
	}
	
	/**
	 * Gets years in the object.
	 * @return array of ints.
	 */
	public int[] getYears() {
		return this.years;
	}
	
	/**
	 * Sets the Data's value.
	 * @param val the value to be set.
	 */
	public void setValue(double[] val) {
		this.value = val;
	}
	
	/**
	 * Sets the Data's years.
	 * @param yrs the years to be set.
	 */
	public void setYears(int[] yrs) {
		this.years = yrs;
	}
}

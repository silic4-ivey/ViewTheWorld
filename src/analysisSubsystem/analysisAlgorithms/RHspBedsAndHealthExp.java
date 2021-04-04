package analysisSubsystem.analysisAlgorithms;

import analysisSubsystem.Data;
import analysisSubsystem.Result;
import analysisSubsystem.dataFetchingSubsystem.Reader;
import frontEnd.selectionSubsystem.Selection;

/**
 * Implements the interface defined by Analysis. Uses Reader to get data and computes 
 * the ratio of hospital beds and current health expenditure.  
 * @author Joud El-Shawa - jelshawa - 251107864
 */
public class RHspBedsAndHealthExp implements Analysis {
	
	/**
	 * Used to fetch data from the World Bank data base.
	 */
	private Reader reader;
	
	/**
	 * Ratio computed that will be put into the Result object for rendering.
	 */
	private double[][] ratio;
	
	/**
	 * The aspect of the analysis that will be used for legends when rendering viewers.
	 */
	private String[] parts = {"Hospital Beds/1000 people & Health exp/1000 people"};
	
	/**
	 * Constructor. Instantiates reader.
	 */
	public RHspBedsAndHealthExp() {
		this.reader = new Reader();
	}

	/**
	 * Uses reader to fetch the 2 different Data aspects from the World Bank data base.
	 * Checks to make sure that they are not null, and if they are not, proceeds with 
	 * calculating the ratio at each year.
	 * @param sel Selection object to be used when fetching data using reader.
	 * @return Result object containing analysis name, parts, ratios, years, and country name.
	 */
	@Override
	public Result calculate(Selection sel) {
		
		// Facade Design Pattern! (analysis algorithm knowing which data it needs to fetch from the World Bank database)
		Data hspBeds = reader.readData(sel.getCountryCode(), sel.getAnalysisIndicators()[0].toString(), sel.getYearStart(), sel.getYearEnd());
		Data healthExp = reader.readData(sel.getCountryCode(), sel.getAnalysisIndicators()[1].toString(), sel.getYearStart(), sel.getYearEnd());
		
		if (hspBeds.getValue() != null && healthExp.getValue() != null) {
			ratio = new double[1][hspBeds.getYears().length];
			for (int i = 0; i < hspBeds.getValue().length; i++) {
				if (healthExp.getValue()[i] != 0)
					ratio[0][i] = hspBeds.getValue()[i]/(healthExp.getValue()[i]*1000);
				else
					ratio[0][i] = 0;
			}
		}
		else
			ratio = null;
		
		return new Result(sel.getAnalysisName(), parts, ratio, hspBeds.getYears(), sel.getCountryName());
	}
}

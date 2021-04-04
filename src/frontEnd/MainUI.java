package frontEnd;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.HashMap;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import analysisSubsystem.ComputationServer;
import frontEnd.selectionSubsystem.Selection;

/**
 * This class consists of the MainUI that the User uses to select aspects of desired analysis
 * and displays corresponding Viewers once aspects are selected. 
 * @author Joud El-Shawa
 */
public class MainUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Drop-down menu that contains the countries.
	 */
	private JComboBox<String> countriesList;
	
	/**
	 * Drop-down menu that contains start years.
	 */
	private JComboBox<String> fromList;
	
	/**
	 * Drop-down menu that contains end years.
	 */
	private JComboBox<String> toList;
	
	/**
	 * Drop-down menu that contains the different analysis types available.
	 */
	private JComboBox<String> analysisList;
	
	/**
	 * Drop-down menu that contains the different viewer options.
	 */
	private JComboBox<String> viewersList;
	
	/**
	 * Button that is used to initiate the analysis when aspects are selected.
	 */
	private JButton recalculate = new JButton("Recalculate");
	
	/**
	 * Button that is used to add a viewer once the viewer type is selected.
	 */
	private JButton add = new JButton("+");
	
	/**
	 * Button that is used to remove a viewer once the viewer type is selected.
	 */
	private JButton remove = new JButton("-");
	
	/**
	 * Panel that will be used to add viewers to when they are created.
	 */
	private JPanel west = new JPanel();
	
	/**
	 * Consists of all country names available.
	 */
	private Vector<String> countryNames = new Vector<String>();
	
	/**
	 * Consists of all the analysis types available.
	 */
	private Vector<String> analysisNames = new Vector<String>();
	
	/**
	 * Consists of all the viewer types available.
	 */
	private Vector<String> viewers = new Vector<String>();
	
	/**
	 * Consists of all the years available.
	 */
	private Vector<String> years = new Vector<String>();
	
	/**
	 * Consists of the current viewers rendered on the panel.
	 */
	private Vector<JComponent> currentViewers = new Vector<JComponent>();
	
	/**
	 * Hashmap of all countries and their corresponding country codes.
	 */
	private HashMap<String,String> countries = new HashMap<String,String>();
	
	/**
	 * Hashmap of all analysis types and their corresponding indicators.
	 */
	private HashMap<String,String> analysisTypes = new HashMap<String,String>();
	
	/**
	 * ComputationServer object that will be used to do the analysis when the recalculate button is pressed.
	 */
	private ComputationServer cServer = new ComputationServer();
	
	/**
	 * Instance of the Selection class.
	 */
	private Selection selection = Selection.getInstance();
	
	/**
	 * Instance of this class. (Singleton Design Pattern)
	 */
	private static MainUI instance;
	
	/**
	 * Method that creates an instance of the class if one does not exist already.
	 * @return the instance of this class.
	 */
	public static MainUI getInstance() {
		if (instance == null)
			instance = new MainUI();

		return instance;
	}
	
	/**
	 * Constructor. Sets up the MainUI panel, adds action listeners to the different aspects, and initializes
	 * Selection with default values.
	 */
	private MainUI() {
		super("ViewTheWorld");		// Setting window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dim = new Dimension(screenDim.width-20,screenDim.height-30);
		setResizable(false);
        setPreferredSize(dim);
		setLocation(screenDim.width/2-dim.width/2, 0);
		pack();

		scanFiles();
		
		// setting top bar
		JLabel chooseCountryLabel = new JLabel("Choose a country: ");
		countriesList = new JComboBox<String>(countryNames);
		countriesList.addActionListener(this);
		
		JLabel from = new JLabel("From");
		fromList = new JComboBox<String>(years);
		fromList.addActionListener(this);
		
		JLabel to = new JLabel("To");
		toList = new JComboBox<String>(years);
		toList.addActionListener(this);
		
		JPanel north = new JPanel();
		north.add(chooseCountryLabel);
		north.add(countriesList);
		north.add(from);
		north.add(fromList);
		north.add(to);
		north.add(toList);		
		
		// setting bottom bar
		JLabel viewsLabel = new JLabel("Available Views: ");		
		viewersList = new JComboBox<String>(viewers);
		viewersList.addActionListener(this);
		
		add.addActionListener(this);
		remove.addActionListener(this);
		
		JLabel analysisLabel = new JLabel("Choose analysis method:");
		analysisList = new JComboBox<String>(analysisNames);
		analysisList.addActionListener(this);
		
		recalculate.addActionListener(this);
		
		JPanel south = new JPanel();
		south.add(viewsLabel);
		south.add(viewersList);
		south.add(add);
		south.add(remove);
		south.add(analysisLabel);
		south.add(analysisList);
		south.add(recalculate);
		
		JPanel east = new JPanel();
		west.setLayout(new GridLayout(2, 0));
		
		getContentPane().add(north, BorderLayout.NORTH);
		getContentPane().add(south, BorderLayout.SOUTH);
		getContentPane().add(east, BorderLayout.EAST);
		getContentPane().add(west, BorderLayout.WEST);
		
		
		// Populate defaults
		selection.setSelection("analysisName", analysisList.getSelectedItem().toString());
		selection.setSelection("analysisIndicators", analysisTypes.get(analysisList.getSelectedItem().toString()));
		cServer.setAnalysis(analysisList.getSelectedItem().toString());

		countriesList.setSelectedItem("Australia");		
		selection.setSelection("countryName", countriesList.getSelectedItem().toString());
		selection.setSelection("countryCode", countries.get(countriesList.getSelectedItem().toString()));
		
		fromList.setSelectedItem("2011");		
		selection.setSelection("yearStart", fromList.getSelectedItem().toString());
		toList.setSelectedItem("2017");
		selection.setSelection("yearEnd", toList.getSelectedItem().toString());		

		selection.setSelection("addViewer","Scatter Chart");		
		selection.setSelection("addViewer","Line Chart");
		selection.setSelection("addViewer","Bar Chart");
		selection.setSelection("addViewer","Time Series");
		selection.setSelection("addViewer","Report");
	}
	
	/**
	 * Scans the different files needed for the drop-down menus for the UI and stores values in Vectors and/or hashmaps.
	 */
	private void scanFiles() {
		
		//getting countries
		try {	
		      File countryFile = new File("countryList.txt");
		      Scanner scan = new Scanner(countryFile);
		      while (scan.hasNextLine()) {
		        String data = scan.nextLine();
		        String[] c = data.split(",");
		  		        
		        String name = c[0].trim();
		        String code = c[1].trim();
		        
		        countryNames.add(name);	// adding to country list for drop-down menu
		        countries.put(name, code);	// adding to hashmap 
		      }
		      scan.close();
		} 
		catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
		
		
		//getting viewer types
		try {	
		      File viewersFile = new File("viewersDatabase.txt");
		      Scanner scan = new Scanner(viewersFile);
		      while (scan.hasNextLine()) {
		    	  String viewer = scan.nextLine();
		    	  viewers.add(viewer);	// adding to viewer list for drop-down menu
		      }
		      scan.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		//getting analysis types
		try {	
		      File analysisFile = new File("analysisTypes.txt");
		      Scanner scan = new Scanner(analysisFile);
		      while (scan.hasNextLine()) {
		    	  String analysisLine = scan.nextLine();
		    	  String[] currAnalysis = analysisLine.split("/");	// separating name from indicators
		    	  
		    	  String analysisName = currAnalysis[0];
		    	  String indicators = currAnalysis[1];
		    	  
		    	  analysisNames.add(analysisName);	// adding to analysis types list for drop-down menu
		    	  analysisTypes.put(analysisName,indicators);	// adding both aspects to hashmap
		      }
		      scan.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		//getting years
		try {
			File analysisFile = new File("yearsRange.txt");	// only has two lines. first line is min year, second is max
		    Scanner scan = new Scanner(analysisFile);
		    String yearMin = scan.nextLine();
		    String yearMax = scan.nextLine();

		    // going from max to min and adding to years list for drop-down menu
			for (int i = Integer.valueOf(yearMax); i >= Integer.valueOf(yearMin); i--) {
				years.add("" + i);
			}
			
			scan.close();
		}	
		catch (FileNotFoundException e) {
		System.out.println("An error occurred.");
		e.printStackTrace();
		}
	}	
	
	/**
	 * Makes panel visible and calls ComputationServer to do analysis so panel is populated with initial values and viewers.
	 */
	public void launchUI() {
		instance.setSize(900, 600);
		instance.pack();
		instance.setVisible(true);
		instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cServer.doAnalysis(selection);
	}
	
	/**
	 * Displays an error message to the User.
	 * @param message to be displayed
	 */
	public void displayErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
		
	/**
	 * Handles different actions done by User on the MainUI panel.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// if User selected analysis type
		if (e.getSource().equals(analysisList)) {
			// if new analysis type is selected, stores new values and clears viewers
			if (!selection.getAnalysisName().equals(analysisList.getSelectedItem().toString())) {	
				selection.setSelection("analysisName", analysisList.getSelectedItem().toString());
				selection.setSelection("analysisIndicators", analysisTypes.get(analysisList.getSelectedItem()).toString());
				cServer.setAnalysis(analysisList.getSelectedItem().toString());
				clearViewers();
			}
		}
		
		// if User selected a country
		if (e.getSource().equals(countriesList)) {
			if (selection.setSelection("countryName", countriesList.getSelectedItem().toString())) { // if valid country
				selection.setSelection("countryCode", countries.get(countriesList.getSelectedItem().toString())); // sets country code as well			
			}
			else {
				displayErrorMessage("Invalid Country");	// if invalid country, displays error
				countriesList.setSelectedItem(selection.getCountryName());	// sets selection back to valid country
			}
		}
		
		// if User selected start year
		if (e.getSource().equals(fromList)) {
			if (!selection.setSelection("yearStart", fromList.getSelectedItem().toString())){	// if invalid start year
				displayErrorMessage("Must select a start year that precedes the end year");
				Object curr = toList.getSelectedItem();		// to make start year same as end year so its valid
				fromList.setSelectedItem(curr);
			}
		}
		
		
		// if User selected end year
		if (e.getSource().equals(toList)) {
			if (!selection.setSelection("yearEnd", toList.getSelectedItem().toString())){	// if invalid end year
				displayErrorMessage("Must select an end year that follows the start year");
				Object curr = fromList.getSelectedItem();	// to make end year same as start year so its valid
				toList.setSelectedItem(curr);
			}
		}
		
		
		// if User selected add viewer
		if (e.getSource().equals(add)) {
			if (!selection.setSelection("addViewer", viewersList.getSelectedItem().toString()))	// if incompatible viewer
				displayErrorMessage("Incompatible viewer type");
		}
		
		// if User selected remove viewer
		if (e.getSource().equals(remove)) {
			if (!selection.setSelection("removeViewer", viewersList.getSelectedItem().toString())) // if viewer not in list
				displayErrorMessage("Viewer not in list");
		}
		
		// if User selected the recalculate button
		if (e.getSource().equals(recalculate)) {
			if (selection.setSelection("countryName", countriesList.getSelectedItem().toString()))
				cServer.doAnalysis(selection);
			else // handling if User changes analysis and presses Recalculate right away and country is invalid
				displayErrorMessage("Invalid Country");	
		}
	}
	
	/**
	 * Removes all current viewers on MainUI panel. Also removes them from Selection.
	 */
	private void clearViewers() {
		west.setVisible(false);	// to refresh panel
		for (JComponent v : currentViewers) {
			west.remove(v);
		}
		while (!selection.getViewersList().isEmpty()) {
			String view = selection.getViewersList().get(0);
			selection.setSelection("removeViewer", view.toString());
		}
		currentViewers.clear();
		west.setVisible(true);
	}

	/**
	 * Called by notify method of the Viewer classes. Displays the panel received on the MainUI panel.
	 * @param panel to be displayed on the MainUI.
	 */
	public void display(JComponent panel) { 
		west.setVisible(false);	// to refresh the panel so additions are visible
		currentViewers.add(panel);
		west.add(panel);
		west.setVisible(true);
	}
}

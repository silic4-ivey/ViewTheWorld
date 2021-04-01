package frontEnd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.HashMap;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;

import analysisSubsystem.ComputationServer;
import analysisSubsystem.Result;
import frontEnd.selectionSubsystem.Selection;


public class MainUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> countriesList;
	private JComboBox<String> fromList;
	private JComboBox<String> toList;
	private JComboBox<String> analysisList;
	private JComboBox<String> viewersList;
	
	private JButton recalculate = new JButton("Recalculate");
	private JButton add = new JButton("+");
	private JButton remove = new JButton("-");
	
	private JPanel west = new JPanel();
	
	private Vector<String> countryNames = new Vector<String>();
	private Vector<String> analysisNames = new Vector<String>();
	private Vector<String> viewers = new Vector<String>();
	private Vector<String> years = new Vector<String>();
	
	private HashMap<String,String> countries = new HashMap<String,String>();
	private HashMap<String,String> analysisTypes = new HashMap<String,String>();
	
	private ComputationServer cServer = new ComputationServer();
	private Selection selection = Selection.getInstance();
	private static MainUI instance;

	public static MainUI getInstance() {
		if (instance == null)
			instance = new MainUI();

		return instance;
	}
	
	private MainUI() {
		super("ViewTheWorld");		// Setting window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dim = new Dimension(screenDim.width-20,screenDim.height-30);
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
		selection.setSelection("countryName", countriesList.getSelectedItem().toString());
		selection.setSelection("countryCode", countries.get(countriesList.getSelectedItem().toString()));
		selection.setSelection("yearStart", fromList.getSelectedItem().toString());
		selection.setSelection("yearEnd", toList.getSelectedItem().toString());		
	}
	
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
		        
		        countryNames.add(name);
		        countries.put(name, code);
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
		    	  viewers.add(viewer);
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
		    	  String[] currAnalysis = analysisLine.split("/");
		    	  
		    	  String analysisName = currAnalysis[0];
		    	  String indicators = currAnalysis[1];
		    	  //String[] indicators = currAnalysis[1].split(",");
		    	  
		    	  analysisNames.add(analysisName);
		    	  analysisTypes.put(analysisName,indicators);		    	  
		      }
		      scan.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		//getting years
		try {
			File analysisFile = new File("yearsRange.txt");
		    Scanner scan = new Scanner(analysisFile);
		    String yearMin = scan.nextLine();
		    String yearMax = scan.nextLine();

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
	
	
	public static void launchUI() {
		instance.setSize(900, 600);
		instance.pack();
		instance.setVisible(true);
		instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void displayErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
	
	//****initialize selection w proper values when we initialize the system!!
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// if User selected analysis type
		if (e.getSource().equals(analysisList)) {
			System.out.println(analysisList.getSelectedItem());
			selection.setSelection("analysisName", analysisList.getSelectedItem().toString());
			selection.setSelection("analysisIndicators", analysisTypes.get(analysisList.getSelectedItem()).toString());
			cServer.setAnalysis(analysisList.getSelectedItem().toString());
		}
		
		// if User selected a country
		if (e.getSource().equals(countriesList)) {
			if (selection.setSelection("countryName", countriesList.getSelectedItem().toString())) {
				selection.setSelection("countryCode", countries.get(countriesList.getSelectedItem().toString()));			
			}
			else
				displayErrorMessage("Invalid Country");
//				JOptionPane.showMessageDialog(this, "Invalid Country");
		}
		
		// if User selected start year
		if (e.getSource().equals(fromList)) {
			if (!selection.setSelection("yearStart", fromList.getSelectedItem().toString())){
//				JOptionPane.showMessageDialog(this, "Must select a start year that precedes the end year");
				displayErrorMessage("Must select a start year that precedes the end year");
				Object curr = toList.getSelectedItem(); // to make start year same as end year so its valid
				fromList.setSelectedItem(curr);
			}
		}
		
		
		// if User selected end year
		if (e.getSource().equals(toList)) {
			if (!selection.setSelection("yearEnd", toList.getSelectedItem().toString())){
//				JOptionPane.showMessageDialog(this, "Must select an end year that follows the start year");
				displayErrorMessage("Must select an end year that follows the start year");
				Object curr = fromList.getSelectedItem();
				toList.setSelectedItem(curr);
			}
		}
		
		
		// if User selected add viewer
		if (e.getSource().equals(add)) {
			if (!selection.setSelection("addViewer", viewersList.getSelectedItem().toString()))
				displayErrorMessage("Incompatible viewer type");
//				JOptionPane.showMessageDialog(this, );
		}
		
		// if User selected remove viewer
		if (e.getSource().equals(remove)) {
			if (!selection.setSelection("removeViewer", viewersList.getSelectedItem().toString()))
				displayErrorMessage("Viewer not in list");
//				JOptionPane.showMessageDialog(this, "Viewer not in list");
		}
		
		// if User selected the recalculate button
		if (e.getSource().equals(recalculate)) {
			cServer.doAnalysis(selection);
		}
	}

	public void display(JComponent panel) {
		west.setVisible(false);
		west.add(panel);
		west.setVisible(true);
	}
}

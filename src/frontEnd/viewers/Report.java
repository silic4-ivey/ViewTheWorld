package frontEnd.viewers;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import analysisSubsystem.Result;
import frontEnd.MainUI;

public class Report implements Viewer {

	@Override
	public void notify(Result res) {
		MainUI mainDisplay = MainUI.getInstance();
		
		StringBuilder builder = new StringBuilder();

		String title = res.getAnalysisName() + " in " + res.getCountryName();

		builder.append(title + "\n");
		builder.append("=".repeat(res.getAnalysisName().length()) + "\n");
		
		for (int i = 0; i < res.getYears().length; i++) {
			builder.append("Year " + res.getYears()[i] + "\n");
			for (int n = 0; n < res.getValue().length; n++) {
				if (res.getValue()[n] == null) {
					builder.append("\t" + res.getAnalysisParts()[n] + " => " + 0.0 + "\n");
				}
				else
					builder.append("\t" + res.getAnalysisParts()[n] + " => " + res.getValue()[n][i] + "\n");
			}
		}
		
		JTextArea jTextArea = new JTextArea(builder.toString());
		jTextArea.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(jTextArea);
		scrollPane.setPreferredSize(new Dimension(400, 300));
		scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		scrollPane.setBackground(Color.white);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		mainDisplay.display(scrollPane);
	}
}

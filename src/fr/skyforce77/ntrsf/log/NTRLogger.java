package fr.skyforce77.ntrsf.log;

import javax.swing.JTextArea;

public class NTRLogger {
	
	private static JTextArea pane;
	
	public static void setArea(JTextArea pan) {
		pane = pan;
	}
	
	public static void print(String message) {
		pane.setText(pane.getText()+message);
	}
	
	public static void println(String message) {
		pane.setText(pane.getText()+message+"\n");
	}

}
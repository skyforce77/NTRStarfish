package fr.skyforce77.ntrsf.ui;

import javax.swing.JFrame;

public class StarfishWindow extends JFrame {

	private static final long serialVersionUID = -5863090618666010902L;

	public StarfishWindow() {
		//setJMenuBar(new StarfishMenuBar());
		
		add(new StarfishLogPanel());
		
		setTitle("NTRStarfish");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

}

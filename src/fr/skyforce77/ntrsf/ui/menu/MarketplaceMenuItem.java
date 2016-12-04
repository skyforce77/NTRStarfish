package fr.skyforce77.ntrsf.ui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class MarketplaceMenuItem extends JMenuItem implements ActionListener {

	private static final long serialVersionUID = -3377118504726540956L;
	
	public MarketplaceMenuItem() {
		super("Marketplace");
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("OK");
	}

}

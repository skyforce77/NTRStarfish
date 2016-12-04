package fr.skyforce77.ntrsf.ui.menu;

import javax.swing.JMenu;

public class StarfishMenu extends JMenu {

	private static final long serialVersionUID = -5777302828755301709L;
	
	public StarfishMenu() {
		add("Starfish", new MarketplaceMenuItem());
	}

}

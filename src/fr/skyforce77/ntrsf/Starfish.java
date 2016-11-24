package fr.skyforce77.ntrsf;

import fr.skyforce77.ntrsf.network.NetworkManager;

public class Starfish {
	
	private NetworkManager manager;
	
	public Starfish() {
		manager = new NetworkManager();
	}
	
	public void start() {
		manager.start("192.168.0.53", 8000);
	}
	
	public static void main(String[] args) {
		new Starfish().start();
	}

}

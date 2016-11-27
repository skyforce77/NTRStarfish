package fr.skyforce77.ntrsf;

import fr.skyforce77.ntrsf.api.PluginManager;
import fr.skyforce77.ntrsf.network.NetworkManager;

public class Starfish {
	
	private static NetworkManager networkManager;
	private static PluginManager pluginManager;
	
	public static NetworkManager getNetworkManager() {
		return networkManager;
	}

	public static PluginManager getPluginManager() {
		return pluginManager;
	}

	public static void main(String[] args) {
		networkManager = new NetworkManager();
		networkManager.start("192.168.0.53", 8000);
		
		pluginManager = new PluginManager();
	}

}

package fr.skyforce77.ntrsf;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import fr.skyforce77.ntrsf.api.PluginManager;
import fr.skyforce77.ntrsf.commands.CommandHello;
import fr.skyforce77.ntrsf.commands.CommandHelp;
import fr.skyforce77.ntrsf.commands.CommandProcesses;
import fr.skyforce77.ntrsf.console.ConsoleManager;
import fr.skyforce77.ntrsf.data.DataManager;
import fr.skyforce77.ntrsf.network.NetworkManager;
import fr.skyforce77.ntrsf.ui.StarfishWindow;

public class Starfish {
	
	private static NetworkManager networkManager;
	private static PluginManager pluginManager;
	private static DataManager dataManager;
	private static ConsoleManager consoleManager;
	
	public static NetworkManager getNetworkManager() {
		return networkManager;
	}

	public static PluginManager getPluginManager() {
		return pluginManager;
	}
	
	public static DataManager getDataManager() {
		return dataManager;
	}
	
	public static ConsoleManager getConsoleManager() {
		return consoleManager;
	}

	public static void main(String[] args) {
		networkManager = new NetworkManager();
		consoleManager = new ConsoleManager();
		dataManager = new DataManager();
		dataManager.init();
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		
		String s = "";
		while(true) {
			String ret = (String)JOptionPane.showInputDialog(null, "Enter your 3ds ip and port\n(Example: 192.168.0.1:8000)"+s, "Connection", JOptionPane.INFORMATION_MESSAGE,
					null, null, dataManager.getConfig().getValue("autoconnect.ip", ""));
			if(ret == null) {
				System.exit(0);
			} else {
				dataManager.getConfig().setValue("autoconnect.ip", ret);
				dataManager.getConfig().save();
			}
			
			String[] splt = ret.split(":");
			if(splt.length < 2)
				continue;
			String ip = splt[0];
			Integer port = Integer.parseInt(splt[1]);
			try {
				if(networkManager.start(ip, port)) {
					break;
				} else {
					s = "\nError: Can't connect to server";
				}
			} catch(IllegalArgumentException e) {
				s = "\nError: Incorrect IP/Port";
			}
		}
		
		pluginManager = new PluginManager();
		
		pluginManager.registerCommands(
				new CommandHelp(),
				new CommandHello(),
				new CommandProcesses()
		);
		
		new StarfishWindow();
	}

}

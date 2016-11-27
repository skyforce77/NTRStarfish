package fr.skyforce77.ntrsf.api;

import java.util.LinkedList;
import java.util.List;

public class PluginManager {
	
	private List<Plugin> plugins;
	
	public PluginManager() {
		plugins = new LinkedList<Plugin>();
	}
	
	public List<Plugin> getPlugins() {
		return plugins;
	}

}

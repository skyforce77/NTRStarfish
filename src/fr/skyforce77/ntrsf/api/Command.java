package fr.skyforce77.ntrsf.api;

public abstract class Command {
	
	private Plugin plugin;
	
	public Command(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public Plugin getPlugin() {
		return plugin;
	}
	
	public abstract String[] getLabel();
	
	public abstract String getDescription();
	
	public abstract void onCommand(String label, String[] args);

}

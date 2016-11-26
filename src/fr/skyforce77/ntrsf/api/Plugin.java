package fr.skyforce77.ntrsf.api;

public abstract class Plugin {
	
	public abstract void onEnable();
	public abstract void onDisable();
	public abstract String[] aimedGames();

}

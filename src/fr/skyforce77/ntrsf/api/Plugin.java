package fr.skyforce77.ntrsf.api;

public abstract class Plugin {
	
	private boolean enabled = false;
	
	public boolean isEnabled() {
		return enabled;
	}
	
	void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public abstract void onEnable();
	public abstract void onDisable();
	public abstract String[] aimedGames();

}

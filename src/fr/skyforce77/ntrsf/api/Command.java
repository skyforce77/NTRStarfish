package fr.skyforce77.ntrsf.api;

public abstract class Command {
	
	public abstract String[] getLabel();
	
	public abstract String getDescription();
	
	public abstract void onCommand(String label, String[] args);

}

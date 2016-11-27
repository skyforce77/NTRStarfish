package fr.skyforce77.ntrsf.api.event;

public interface Cancellable {

	public boolean isCancelled();
	
	public void setCancelled(boolean cancelled);
	
}

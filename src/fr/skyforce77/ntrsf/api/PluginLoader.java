package fr.skyforce77.ntrsf.api;

import fr.skyforce77.ntrsf.Starfish;
import fr.skyforce77.ntrsf.api.event.EventHandler;
import fr.skyforce77.ntrsf.api.event.Listener;
import fr.skyforce77.ntrsf.api.event.process.ProcessStartedEvent;
import fr.skyforce77.ntrsf.api.event.process.ProcessStoppedEvent;
import fr.skyforce77.ntrsf.console.ConsoleProcess;

class PluginLoader implements Listener {
	
	private PluginManager manager;
	
	public PluginLoader(PluginManager manager) {
		this.manager = manager;
	}
	
	@EventHandler
	public void onProcessStarted(ProcessStartedEvent e) {
		for(Plugin p : manager.getPlugins()) {
			if(!p.isEnabled()) {
				for(String s : p.aimedGames()) {
					if(s.equalsIgnoreCase(e.getProcess().getTitleId())) {
						p.setEnabled(true);
						p.onEnable();
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onProcessStopped(ProcessStoppedEvent e) {
		for(Plugin p : manager.getPlugins()) {
			if(p.isEnabled()) {
				boolean hasAimed = false;
				for(String s : p.aimedGames()) {
					for(ConsoleProcess pr : Starfish.getConsoleManager().getProcesses()) {
						if(pr.getTitleId().equalsIgnoreCase(s)) {
							hasAimed = true;
						}
					}
				}
				if(!hasAimed) {
					p.setEnabled(false);
					p.onDisable();
					manager.unregisterCommands(p);
					manager.unregisterEvents(p);
				}
			}
		}
	}
	

}

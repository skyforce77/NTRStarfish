package fr.skyforce77.ntrsf.api;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import fr.skyforce77.ntrsf.api.event.Event;
import fr.skyforce77.ntrsf.api.event.EventHandler;
import fr.skyforce77.ntrsf.api.event.Listener;
import fr.skyforce77.ntrsf.log.NTRLogger;

public class PluginManager {
	
	private List<Plugin> plugins;
	private Map<Class<? extends Event>, List<ListenerMethod>> listeners;
	private Map<String, Command> commands;
	
	public PluginManager() {
		plugins = new LinkedList<Plugin>();
		listeners = new LinkedHashMap<Class<? extends Event>, List<ListenerMethod>>();
		commands = new LinkedHashMap<String, Command>();
	}
	
	public List<Plugin> getPlugins() {
		return plugins;
	}
	
	@SuppressWarnings("unchecked")
	public void registerEvents(Plugin plugin, Listener listener) {
		for(Method m : listener.getClass().getMethods()) {
			EventHandler handler = m.getAnnotation(EventHandler.class);
			if(handler != null && m.getParameterTypes().length == 1 && Event.class.isAssignableFrom(m.getParameterTypes()[0])) {
				Class<? extends Event> clazz = (Class<? extends Event>)m.getParameterTypes()[0];
				if(listeners.get(clazz) == null) {
					listeners.put(clazz, new LinkedList<ListenerMethod>());
				}
				listeners.get(clazz).add(new ListenerMethod(plugin, listener, m));
			}
		}
	}
	
	public void unregisterEvents(Plugin plugin) {
		for(Entry<Class<? extends Event>, List<ListenerMethod>> entry : listeners.entrySet()) {
			for(ListenerMethod method : entry.getValue()) {
				if(method.getPlugin().equals(plugin)) {
					entry.getValue().remove(method);
				}
			}
		}
	}
	
	public void callEvent(Event event) {
		List<ListenerMethod> methods = listeners.get(event.getClass());
		if(methods != null) {
			for(ListenerMethod method : methods) {
				method.call(event);
			}
		}
	}
	
	public void registerCommand(Command command) {
		for(String label : command.getLabel()) {
			commands.put(label, command);
		}
	}
	
	public void registerCommands(Command... commands) {
		for(Command command : commands) {
			registerCommand(command);
		}
	}
	
	public void callCommand(String label, String[] args) {
		if(commands.containsKey(label)) {
			commands.get(label).onCommand(label, args);
		} else {
			NTRLogger.println("Unknown command ! Type 'help' for help");
		}
	}

	public Map<String, Command> getCommands() {
		return commands;
	}

}

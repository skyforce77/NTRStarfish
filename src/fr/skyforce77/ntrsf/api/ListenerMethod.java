package fr.skyforce77.ntrsf.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import fr.skyforce77.ntrsf.api.event.Event;
import fr.skyforce77.ntrsf.api.event.Listener;

class ListenerMethod {
	
	private Plugin plugin;
	private Listener listener;
	private Method method;
	
	public ListenerMethod(Plugin plugin, Listener listener, Method method) {
		this.plugin = plugin;
		this.listener = listener;
		this.method = method;
	}

	public Plugin getPlugin() {
		return plugin;
	}

	public Listener getListener() {
		return listener;
	}

	public Method getMethod() {
		return method;
	}
	
	public void call(Event event) {
		try {
			method.invoke(listener, event);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}

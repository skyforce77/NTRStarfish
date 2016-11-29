package fr.skyforce77.ntrsf.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.management.IntrospectionException;

import fr.skyforce77.ntrsf.Starfish;
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
		
		registerEvents(null, new PluginLoader(this));
		
		File pl = Starfish.getDataManager().getOrCreateDir("plugins");
		for(File f : pl.listFiles()) {
			if(f.getName().endsWith(".jar")) {
				initJavaPlugin(f);
			}
		}
	}
	
	@SuppressWarnings("resource")
	private void initJavaPlugin(File f) {
		try {
			addURLToSystemClassLoader(f.toURI().toURL());
			JarFile jar = new JarFile(f.getPath());
			JarEntry entry = jar.getJarEntry("main.txt");
			if (entry == null) {
			throw new FileNotFoundException("Jar does not contain main.txt");
			}
			BufferedReader rdr = new BufferedReader(new InputStreamReader(jar.getInputStream(entry)));
			String main = rdr.readLine();
			Class<?> cls = ClassLoader.getSystemClassLoader().loadClass(main);
			Plugin p = (Plugin) cls.newInstance();
			plugins.add(p);
			System.out.println("Registered plugin "+f.getName());
		} catch (Exception e) {
			System.err.println("Can't launch " + f.getName() + " plugin.");
			e.printStackTrace();
		}
	}
	
	private void addURLToSystemClassLoader(URL url) throws IntrospectionException {
		URLClassLoader systemClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class<URLClassLoader> classLoaderClass = URLClassLoader.class;
		try {
			Method method = classLoaderClass.getDeclaredMethod("addURL", new Class[]{URL.class});
			method.setAccessible(true);
			method.invoke(systemClassLoader, new Object[]{url});
		} catch (Throwable t) {
			t.printStackTrace();
			throw new IntrospectionException("Error when adding url to system ClassLoader ");
		}
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
	
	public void unregisterCommands(Plugin plugin) {
		for(Entry<String, Command> entry : commands.entrySet()) {
			if(entry.getValue().getPlugin().equals(plugin))
				commands.remove(entry.getKey());
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

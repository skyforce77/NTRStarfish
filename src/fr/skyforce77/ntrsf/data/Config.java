package fr.skyforce77.ntrsf.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.skyforce77.ntrsf.Starfish;

public class Config implements Serializable {
	
	private static final long serialVersionUID = -6666062735832520130L;
	private Map<String, Serializable> values;
	private List<String> permissions;
	
	public Config() {
		this.values = new LinkedHashMap<String, Serializable>();
		this.permissions = new LinkedList<String>();
	}

	public Serializable getValue(String key) {
		return values.get(key);
	}
	
	public Serializable getValue(String key, Serializable def) {
		Serializable val = values.get(key);
		return val == null ? def : val;
	}
	
	public void setValue(String key, Serializable value) {
		values.put(key, value);
	}

	public boolean hasPermission(String permission) {
		return permissions.contains(permission);
	}
	
	public void addPermission(String permission) {
		if(!hasPermission(permission))
			permissions.add(permission);
	}
	
	public void removePermission(String permission) {
		if(hasPermission(permission))
			permissions.remove(permission);
	}
	
	public void save() {
		File f = Starfish.getDataManager().getFile("/config/main.dat");
		if(!f.exists()) {
			f.getParentFile().mkdirs();
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(f));
			stream.writeObject(this);
			stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static Config load() {
		File f = Starfish.getDataManager().getFile("/config/main.dat");
		if(!f.exists()) {
			return new Config();
		}
		try {
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream(f));
			Object o = stream.readObject();
			stream.close();
			if(o instanceof Config) {
				return (Config)o;
			} else {
				return new Config();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return new Config();
	}

}

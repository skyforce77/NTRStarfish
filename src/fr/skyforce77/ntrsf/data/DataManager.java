package fr.skyforce77.ntrsf.data;

import java.io.File;

public class DataManager {
	
	private static String DIRECTORY = "NTRStarfish";
	
	private File directory = null;
	private Config config;

	public void init() {
		config = Config.load();
	}
	
	public Config getConfig() {
		return config;
	}
	
	public File getFile(String file) {
		return new File(getDirectory(), file);
	}
	
	public File getDirectory() {
		if(directory != null) {
			return directory;
		}

		File f = null;
		String OS = System.getProperty("os.name").toUpperCase();

		if (OS.contains("WIN"))
			f = new File(System.getenv("APPDATA"), "/."+DIRECTORY);
		else if (OS.contains("MAC"))
			f = new File(System.getProperty("user.home") + "/Library/Application Support", "/."+DIRECTORY);
		else if (OS.contains("NUX"))
			f = new File(System.getProperty("user.home"), "/."+DIRECTORY);
		else
			f = new File(System.getProperty("user.dir"), "/."+DIRECTORY);

		directory = f;
		return f;
	}

}

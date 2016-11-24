package fr.skyforce77.ntrsf.network;

import java.io.InputStream;

public class InThread extends Thread {
	
	private InputStream stream;
	private NetworkManager manager;
	
	public InThread(NetworkManager manager, InputStream stream) {
		this.manager = manager;
		this.stream = stream;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}

}

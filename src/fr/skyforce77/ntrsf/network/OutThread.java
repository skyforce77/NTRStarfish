package fr.skyforce77.ntrsf.network;

import java.io.OutputStream;

public class OutThread extends Thread {
	
	private OutputStream stream;
	private NetworkManager manager;
	
	public OutThread(NetworkManager manager, OutputStream stream) {
		this.manager = manager;
		this.stream = stream;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}

}

package fr.skyforce77.ntrsf.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;

public class NetworkManager {
	
	public static long MAGIC = 0x12345678;
	
	Queue<NTRPacket> waiting;
	List<NTRPacketListener> listeners;
	private Socket s;
	
	public NetworkManager() {
		waiting = new LinkedBlockingQueue<NTRPacket>();
		listeners = new Vector<NTRPacketListener>();
	}
	
	public void start(String ip, int port) {
		try {
			s = new Socket(ip, port);
			
			while(!s.isConnected()) {
				try {
					Thread.sleep(10L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Connected");
			
			InputStream is = s.getInputStream();
			new InThread(this, is).start();
			
			OutputStream os = s.getOutputStream();
			new OutThread(this, os).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		if(s != null) {
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isConnected() {
		return s.isConnected() && !s.isClosed();
	}
	
	public void send(NTRPacket packet) {
		waiting.add(packet);
	}

	public void registerListener(NTRPacketListener listener) {
		listeners.add(listener);
	}
}

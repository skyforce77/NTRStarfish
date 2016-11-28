package fr.skyforce77.ntrsf.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;

public class NetworkManager {
	
	public static long MAGIC = 0x12345678;
	
	Queue<NTRPacket> waiting;
	List<NTRPacketListener> listeners;
	private Socket s;
	private long sequence = 0l;
	private Map<Long, ResponseListener> callbacks;
	
	public NetworkManager() {
		waiting = new LinkedBlockingQueue<NTRPacket>();
		listeners = new Vector<NTRPacketListener>();
		callbacks = new LinkedHashMap<Long, ResponseListener>();
		
		listeners.add(new InternalPacketListener());
	}
	
	public boolean start(String ip, int port) {
		try {
			s = new Socket();
			
			try {
				s.connect(new InetSocketAddress(ip, port), 5000);
			} catch(ConnectException e) {
				return false;
			} catch(NoRouteToHostException e) {
				return false;
			}
			
			if(!s.isConnected()) {
				return false;
			}
			
			InputStream is = s.getInputStream();
			new InThread(this, is).start();
			
			OutputStream os = s.getOutputStream();
			new OutThread(this, os).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public Long lockSequenceNumber() {
		return sequence++;
	}
	
	public void registerResponseListener(NTRPacket packet, ResponseListener listener) {
		callbacks.put(packet.getSequence(), listener);
	}
	
	public ResponseListener recover(NTRPacket packet) {
		if(callbacks.containsKey(packet.getSequence())) {
			ResponseListener callback = callbacks.get(packet.getSequence());
			callbacks.remove(packet.getSequence());
			return callback;
		}
		return null;
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

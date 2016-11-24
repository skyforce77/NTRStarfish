package fr.skyforce77.ntrsf.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

import fr.skyforce77.ntrsf.network.packet.NTRPacket;
import fr.skyforce77.ntrsf.network.packet.NTRPacketType;

public class NetworkManager {
	
	private Socket s;
	
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
			OutputStream os = s.getOutputStream();
			os.write(new NTRPacket(NTRPacketType.HELLO, null, null).serialize());
			System.out.println("Hello");
			os.write(new NTRPacket(NTRPacketType.HEARTBEAT, null, null).serialize());
			System.out.println("Heartbeat");
			
			byte[] buf = new byte[84];
			is.read(buf);
			System.out.println(Arrays.toString(buf));
			
			s.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

package fr.skyforce77.ntrsf.network;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

public class OutThread extends Thread {
	
	private static byte[] HEARTBEAT = new NTRPacket(NTRPacketType.HEARTBEAT).serialize();
	private static byte[] LIST_PROCESSES = new NTRPacket(NTRPacketType.LIST_PROCESSES).serialize();
	
	private OutputStream stream;
	private NetworkManager manager;
	private long nextHeartbeat = 0L;
	private long nextProcessList = 0L;
	
	public OutThread(NetworkManager manager, OutputStream stream) {
		this.manager = manager;
		this.stream = stream;
	}
	
	@Override
	public void run() {
		while(manager.isConnected()) {
			try {
				if(nextHeartbeat < Calendar.getInstance().getTimeInMillis()) {
					stream.write(HEARTBEAT);
					stream.flush();
					nextHeartbeat = Calendar.getInstance().getTimeInMillis()+5000L;
				}
				if(nextProcessList < Calendar.getInstance().getTimeInMillis()) {
					stream.write(LIST_PROCESSES);
					stream.flush();
					nextProcessList = Calendar.getInstance().getTimeInMillis()+20000L;
				}
				NTRPacket packet;
				while((packet = manager.waiting.poll()) != null) {
					packet.serialize(stream);
					stream.flush();
				}
				Thread.sleep(10L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		super.run();
	}

}

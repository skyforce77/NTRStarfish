package fr.skyforce77.ntrsf.network;

import java.io.IOException;
import java.io.InputStream;

import fr.skyforce77.ntrsf.data.BinaryUtils;

public class InThread extends Thread {
	
	private InputStream stream;
	private NetworkManager manager;
	
	public InThread(NetworkManager manager, InputStream stream) {
		this.manager = manager;
		this.stream = stream;
	}
	
	@Override
	public void run() {
		while(manager.isConnected()) {
			try {
				byte[] buf = new byte[84];
				stream.read(buf);
				
				int offset = 0;
				long magic = BinaryUtils.getUnsigned(buf, offset);
				if(magic != NetworkManager.MAGIC) {
					manager.stop();
					System.err.println("Bad magic !");
					break;
				}
				
				long sequence = BinaryUtils.getUnsigned(buf, offset+=4);
				long type = BinaryUtils.getUnsigned(buf, offset+=4);
				long command = BinaryUtils.getUnsigned(buf, offset+=4);
				
				long[] args = new long[16];
				for (int i = 0; i < 16; i++) {
					args[i] = BinaryUtils.getUnsigned(buf, offset+=4);
				}
				
				int dataLen = (int)BinaryUtils.getUnsigned(buf, offset+=4);
				byte[] data = new byte[dataLen];
				if(dataLen != 0)
					stream.read(data);
				
				NTRPacket packet = new NTRPacket(type, command, sequence, args, data);
				
				for(NTRPacketListener listener : manager.listeners) {
					listener.onPacketReceived(packet);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

package fr.skyforce77.ntrsf.network;

import java.nio.charset.Charset;

import fr.skyforce77.ntrsf.Starfish;
import fr.skyforce77.ntrsf.log.NTRLogger;

class InternalPacketListener implements NTRPacketListener {

	@Override
	public void onPacketReceived(NTRPacket packet) {
		System.out.println(packet);
		if(packet.getPacketType() == NTRPacketType.HEARTBEAT) {
			if(packet.getData().length() != 0) {
				String ret = new String(packet.getData().toArray(), Charset.forName("UTF-8"));
				if(analyse(packet, ret)) {
					NTRLogger.println("[CFW] "+ret);
				}
			}
		}
		ResponseListener listener = Starfish.getNetworkManager().recover(packet);
		if(listener != null) {
			listener.received(packet);
		}
	}
	
	public boolean analyse(NTRPacket packet, String ret) {
		if(ret.contains("pid:") && ret.contains("end of process list.")) {
			int i = ret.indexOf("pid:");
			int j = ret.indexOf("end of process list.");
			Starfish.getConsoleManager().updateProcesses(packet, ret.substring(i, j));
			if(i != 0) {
				NTRLogger.println(ret.substring(0, i));
			}
			if(j+20 <= ret.length()-1) {
				NTRLogger.println(ret.substring(j+20, ret.length()-1));
			}
			return false;
		}
		return true;
	}

}

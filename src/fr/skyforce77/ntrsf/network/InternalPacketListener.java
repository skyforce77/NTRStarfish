package fr.skyforce77.ntrsf.network;

import java.nio.charset.Charset;

import fr.skyforce77.ntrsf.Starfish;
import fr.skyforce77.ntrsf.log.NTRLogger;

class InternalPacketListener implements NTRPacketListener {

	@Override
	public void onPacketReceived(NTRPacket packet) {
		//System.out.println(packet);
		if(packet.getPacketType() == NTRPacketType.HEARTBEAT) {
			if(packet.getData().length() != 0) {
				String ret = new String(packet.getData().toArray(), Charset.forName("UTF-8"));
				if(analyse(packet, ret)) {
					NTRLogger.println("[CFW] "+ret);
				}
			}
		}
	}
	
	public boolean analyse(NTRPacket packet, String ret) {
		if(ret.startsWith("pid:")) {
			Starfish.getConsoleManager().updateProcesses(packet, ret);
			return false;
		}
		return true;
	}

}

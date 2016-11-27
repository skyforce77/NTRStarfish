package fr.skyforce77.ntrsf.network;

import java.nio.charset.Charset;

import fr.skyforce77.ntrsf.log.NTRLogger;

class InternalPacketListener implements NTRPacketListener {

	@Override
	public void onPacketReceived(NTRPacket packet) {
		System.out.println(packet);
		if(packet.getPacketType() == NTRPacketType.HEARTBEAT) {
			if(packet.getData().length() != 0) {
				NTRLogger.println("[CFW] "+new String(packet.getData().toArray(), Charset.forName("UTF-8")));
			}
		}
	}

}

package fr.skyforce77.ntrsf.network;

import java.nio.charset.Charset;

class InternalPacketListener implements NTRPacketListener {

	@Override
	public void onPacketReceived(NTRPacket packet) {
		if(packet.getPacketType() == NTRPacketType.HEARTBEAT) {
			if(packet.getData().length != 0)
				System.out.println(new String(packet.getData(), Charset.forName("UTF-8")));
		}
	}

}

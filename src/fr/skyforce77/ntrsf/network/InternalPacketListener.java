package fr.skyforce77.ntrsf.network;

import java.nio.charset.Charset;

class InternalPacketListener implements NTRPacketListener {

	@Override
	public void onPacketReceived(NTRPacket packet) {
		System.out.println(packet);
		if(packet.getPacketType() == NTRPacketType.HEARTBEAT) {
			if(packet.getData().length() != 0) {
				packet.getData().print(System.out, Charset.forName("UTF-8"));
				System.out.println();
			}
		}
	}

}

package fr.skyforce77.ntrsf.network;

class InternalPacketListener implements NTRPacketListener {

	@Override
	public void onPacketReceived(NTRPacket packet) {
		if(packet.getPacketType() == NTRPacketType.HEARTBEAT) {
			if(packet.getData().length != 0)
				System.out.println(new String(packet.getData()));
		}
	}

}

package fr.skyforce77.ntrsf.console;

import fr.skyforce77.ntrsf.network.NTRPacket;
import fr.skyforce77.ntrsf.network.ResponseListener;

class MemoryCallback implements ResponseListener {
	
	private MemoryResponse response;
	
	public MemoryCallback(MemoryResponse response) {
		this.response = response;
	}
	
	@Override
	public void received(NTRPacket packet) {
		response.readMemory(packet.getArguments()[0], packet.getArguments()[1], packet.getData().toArray());
	}

}

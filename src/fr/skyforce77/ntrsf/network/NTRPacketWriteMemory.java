package fr.skyforce77.ntrsf.network;

import fr.skyforce77.ntrsf.data.CosmicBuffer;

public class NTRPacketWriteMemory extends NTRPacket {
	
	public NTRPacketWriteMemory(long pid, long addr, byte[] data) {
		super(NTRPacketType.WRITE_MEMORY, new long[]{
				pid, addr, data.length
		}, data);
	}
	
	public NTRPacketWriteMemory(long pid, long addr, CosmicBuffer data) {
		super(NTRPacketType.WRITE_MEMORY, new long[]{
				pid, addr, data.length()
		}, data);
	}

}

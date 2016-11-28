package fr.skyforce77.ntrsf.network;

public class NTRPacketReadMemory extends NTRPacket {
	
	public NTRPacketReadMemory(long pid, long addr, long size) {
		super(NTRPacketType.READ_MEMORY, new long[]{
				pid, addr, size
		});
	}

}

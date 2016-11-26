package fr.skyforce77.ntrsf.network;

public enum NTRPacketType {
	
	HEARTBEAT(0, 0),
	HELLO(0, 3),
	RELOAD(0, 4),
	READ_MEMORY(0, 9),
	
	SAVE_FILE(1, 1),
	WRITE_MEMORY(1, 10),
	
	UNKNOWN(0xFFFFFFFF, 0xFFFFFFFF);
	
	private long type;
	private long command;
	
	private NTRPacketType(long type, long command) {
		this.type = type;
		this.command = command;
	}

	public long getType() {
		return type;
	}

	public long getCommand() {
		return command;
	}
	
	public static NTRPacketType byIds(long type, long command) {
		for(NTRPacketType t : values()) {
			if(t.getType() == type && t.getCommand() == command)
				return t;
		}
		return NTRPacketType.UNKNOWN;
	}

}

package fr.skyforce77.ntrsf.network;

public enum NTRPacketType {
	
	HEARTBEAT(0, 0),
	HELLO(0, 3),
	RELOAD(0, 4),
	LIST_PROCESSES(0, 5),
	ATTACH_PROCESS(0, 6), // Args: PID, patchAddr (0)
	LIST_THREADS(0, 7), // Args: PID
	MEM_LAYOUT(0, 8), // Args: PID
	READ_MEMORY(0, 9),
	QUERY_HANDLE(0, 12), // Args: PID
	
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

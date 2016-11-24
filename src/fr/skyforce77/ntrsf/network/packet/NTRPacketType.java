package fr.skyforce77.ntrsf.network.packet;

public enum NTRPacketType {
	
	HEARTBEAT(0, 0),
	HELLO(0, 3),
	RELOAD(0, 4),
	READ_MEMORY(0, 9),
	
	SAVE_FILE(1, 1),
	WRITE_MEMORY(1, 10);
	
	private int type;
	private int command;
	
	private NTRPacketType(int type, int command) {
		this.type = type;
		this.command = command;
	}

	public int getType() {
		return type;
	}

	public int getCommand() {
		return command;
	}

}

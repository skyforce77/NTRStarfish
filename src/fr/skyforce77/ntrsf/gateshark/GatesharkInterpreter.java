package fr.skyforce77.ntrsf.gateshark;

import fr.skyforce77.ntrsf.Starfish;
import fr.skyforce77.ntrsf.data.BinaryUtils;

public class GatesharkInterpreter {
	
	private long pid;
	
	public GatesharkInterpreter(long pid) {
		this.pid = pid;
	}
	
	public void exec(String instruction, String args) {
		if(instruction.startsWith("0")) {
			Long location = Long.decode("0x"+instruction.substring(1));
			Long value = Long.decode("0x"+args);
			Starfish.getConsoleManager().writeMemory(pid, location, BinaryUtils.toUnsignedBytes(value));
		} else if(instruction.startsWith("2")) {
			Long location = Long.decode("0x"+instruction.substring(1));
			int value = Integer.decode("0x"+args);
			Starfish.getConsoleManager().writeMemory(pid, location, new byte[]{(byte)value});
		}
	}

}

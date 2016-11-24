package fr.skyforce77.ntrsf.network.packet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class NTRPacket {
	
	private int type;
	private int command;
	private int[] arguments;
	private byte[] data;
	
	public NTRPacket(NTRPacketType type, int[] arguments, byte[] data) {
		this.type = type.getType();
		this.command = type.getCommand();
		this.arguments = arguments;
		this.data = data;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCommand() {
		return command;
	}

	public void setCommand(int command) {
		this.command = command;
	}

	public int[] getArguments() {
		return arguments;
	}

	public void setArguments(int[] arguments) {
		this.arguments = arguments;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	public byte[] serialize() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream(84);
		
		// Magic number
		stream.write(0x12345678);
		
		// Sequence number
		stream.write(0x00000000);
		
		stream.write(type);
		stream.write(command);
		
		for (int i = 0; i < 16; i++) {
			int arg = 0;
			if(arguments != null) {
				arg = arguments[i];
			}
			stream.write(arg);
		}
		
		if(data != null) {
			stream.write(data.length);
			try {
				stream.write(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			stream.write(0);
		}
		
		return stream.toByteArray();
	}

	public void send() {
		
	}
}

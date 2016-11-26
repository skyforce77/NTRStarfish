package fr.skyforce77.ntrsf.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import fr.skyforce77.ntrsf.data.BinaryUtils;

public class NTRPacket {
	
	private static byte[] MAGIC = BinaryUtils.toUnsignedBytes(NetworkManager.MAGIC);
	private static byte[] ZERO = BinaryUtils.toUnsignedBytes(0x00000000);
	
	private long sequence = 0;
	private long type;
	private long command;
	private long[] arguments;
	private byte[] data;
	
	public NTRPacket(NTRPacketType type, long[] arguments, byte[] data) {
		this.type = type.getType();
		this.command = type.getCommand();
		this.arguments = arguments;
		this.data = data;
	}
	
	public NTRPacket(NTRPacketType type, long sequence, long[] arguments, byte[] data) {
		this.type = type.getType();
		this.sequence = sequence;
		this.command = type.getCommand();
		this.arguments = arguments;
		this.data = data;
	}

	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	public long getCommand() {
		return command;
	}

	public void setCommand(long command) {
		this.command = command;
	}

	public long[] getArguments() {
		return arguments;
	}

	public void setArguments(long[] arguments) {
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
		
		try {
			stream.write(MAGIC);
			
			// Sequence number
			stream.write(BinaryUtils.toUnsignedBytes(sequence));
			
			stream.write(BinaryUtils.toUnsignedBytes(type));
			stream.write(BinaryUtils.toUnsignedBytes(command));
			
			for (int i = 0; i < 16; i++) {
				if(arguments != null) {
					stream.write(BinaryUtils.toUnsignedBytes(arguments[i]));
				} else {
					stream.write(ZERO);
				}
			}
			
			if(data != null) {
				stream.write(BinaryUtils.toUnsignedBytes(data.length));
				try {
					stream.write(data);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				stream.write(BinaryUtils.toUnsignedBytes(0));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return stream.toByteArray();
	}

	public void send() {
		
	}
}

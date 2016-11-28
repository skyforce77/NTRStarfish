package fr.skyforce77.ntrsf.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import fr.skyforce77.ntrsf.Starfish;
import fr.skyforce77.ntrsf.data.BinaryUtils;
import fr.skyforce77.ntrsf.data.CosmicBuffer;

public class NTRPacket {
	
	private static byte[] MAGIC = BinaryUtils.toUnsignedBytes(NetworkManager.MAGIC);
	private static byte[] ZERO = BinaryUtils.toUnsignedBytes(0x00000000);
	
	private long sequence = Starfish.getNetworkManager().lockSequenceNumber();
	private long type;
	private long command;
	private long[] arguments;
	private CosmicBuffer data = new CosmicBuffer();
	
	public NTRPacket(NTRPacketType type) {
		this.type = type.getType();
		this.command = type.getCommand();
	}
	
	public NTRPacket(NTRPacketType type, long[] arguments) {
		this.type = type.getType();
		this.command = type.getCommand();
		this.arguments = arguments;
	}
	
	public NTRPacket(NTRPacketType type, long sequence) {
		this.type = type.getType();
		this.command = type.getCommand();
		this.sequence = sequence;
	}
	
	public NTRPacket(NTRPacketType type, long sequence, long[] arguments) {
		this.type = type.getType();
		this.command = type.getCommand();
		this.sequence = sequence;
		this.arguments = arguments;
	}
	
	public NTRPacket(NTRPacketType type, long[] arguments, byte[] data) {
		this.type = type.getType();
		this.command = type.getCommand();
		this.arguments = arguments;
		this.data = new CosmicBuffer(data);
	}
	
	public NTRPacket(NTRPacketType type, long sequence, long[] arguments, byte[] data) {
		this.type = type.getType();
		this.command = type.getCommand();
		this.sequence = sequence;
		this.arguments = arguments;
		this.data = new CosmicBuffer(data);
	}
	
	public NTRPacket(long type, long command, long[] arguments, byte[] data) {
		this.type = type;
		this.command = command;
		this.arguments = arguments;
		this.data = new CosmicBuffer(data);
	}
	
	public NTRPacket(long type, long command, long sequence, long[] arguments, byte[] data) {
		this.type = type;
		this.command = command;
		this.sequence = sequence;
		this.arguments = arguments;
		this.data = new CosmicBuffer(data);
	}
	
	public NTRPacket(NTRPacketType type, long[] arguments, CosmicBuffer data) {
		this.type = type.getType();
		this.command = type.getCommand();
		this.arguments = arguments;
		this.data = data;
	}
	
	public NTRPacket(NTRPacketType type, long sequence, long[] arguments, CosmicBuffer data) {
		this.type = type.getType();
		this.command = type.getCommand();
		this.sequence = sequence;
		this.arguments = arguments;
		this.data = data;
	}
	
	public NTRPacket(long type, long command, long[] arguments, CosmicBuffer data) {
		this.type = type;
		this.command = command;
		this.arguments = arguments;
		this.data = data;
	}
	
	public NTRPacket(long type, long command, long sequence, long[] arguments, CosmicBuffer data) {
		this.type = type;
		this.command = command;
		this.sequence = sequence;
		this.arguments = arguments;
		this.data = data;
	}

	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public NTRPacketType getPacketType() {
		return NTRPacketType.byIds(type, command);
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

	public CosmicBuffer getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = new CosmicBuffer(data);
	}
	
	public void send() {
		Starfish.getNetworkManager().send(this);
	}
	
	public byte[] serialize() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream(84+data.length());
		serialize(stream);
		return stream.toByteArray();
	}
	
	public void serialize(OutputStream stream) {
		try {
			stream.write(MAGIC);
			
			// Sequence number
			stream.write(BinaryUtils.toUnsignedBytes(sequence));
			
			stream.write(BinaryUtils.toUnsignedBytes(type));
			stream.write(BinaryUtils.toUnsignedBytes(command));
			
			for (int i = 0; i < 16; i++) {
				if(arguments != null && arguments.length > i) {
					stream.write(BinaryUtils.toUnsignedBytes(arguments[i]));
				} else {
					stream.write(ZERO);
				}
			}
			
			if(data != null) {
				stream.write(BinaryUtils.toUnsignedBytes(data.length()));
				data.copy(stream);
			} else {
				stream.write(BinaryUtils.toUnsignedBytes(0));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("NTRPacket(Sequence: ");
		builder.append(sequence);
		builder.append(", Type: ");
		builder.append(type);
		builder.append(", Command: ");
		builder.append(command);
		builder.append(", Args: ");
		builder.append(Arrays.toString(arguments));
		builder.append(", DataLength: ");
		builder.append(data.length());
		builder.append(" )");
		return builder.toString();
	}
	
}

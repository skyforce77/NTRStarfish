package fr.skyforce77.ntrsf.data;

public class BinaryUtils {

	public static byte[] toUnsignedBytes(long convert) {
		byte[] bytes = new byte[4];
		for(int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) (convert >> i*8);
		}
		return bytes;
	}
	
	public static long getUnsigned(byte[] bytes, int offset) {
		long convert = Byte.toUnsignedInt(bytes[offset]);
		convert |= Byte.toUnsignedInt(bytes[offset+1]) << 8;
		convert |= Byte.toUnsignedInt(bytes[offset+2]) << 16;
		convert |= Byte.toUnsignedInt(bytes[offset+3]) << 24;
		return convert;
	}
	
}

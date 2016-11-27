package fr.skyforce77.ntrsf.data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

public class CosmicBuffer {
	
	private int segmentLength;
	private List<byte[]> parts = new LinkedList<byte[]>();
	
	public CosmicBuffer() {
		segmentLength = 500;
	}
	
	public CosmicBuffer(int segmentLength) {
		this.segmentLength = segmentLength;
	}
	
	public CosmicBuffer(byte[] data) {
		segmentLength = 500;
		
		if(data != null) {
			InputStream stream = new ByteArrayInputStream(data);
			fill(stream, data.length);
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void fill(InputStream stream, int length) {
		int o = 0;
		while(o < length) {
			byte[] buff;
			if(segmentLength < length-o) {
				buff = new byte[segmentLength];
			} else {
				buff = new byte[length-o];
			}
			try {
				int len = 0;
				while(len != buff.length) {
					len+=stream.read(buff, len, buff.length-len);
				}
				parts.add(buff);
				o+=buff.length;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void copy(OutputStream stream) {
		for(byte[] part : parts) {
			try {
				stream.write(part);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int length() {
		int len = 0;
		for(byte[] part : parts) {
			len+=part.length;
		}
		return len;
	}
	
	public byte[] toArray() {
		byte[] buffer = new byte[length()];
		int o = 0;
		for(byte[] part : parts) {
			System.arraycopy(part, 0, buffer, o, part.length);
			o+=part.length;
		}
		return buffer;
	}
	
	public void print(PrintStream stream, Charset charset) {
		for(byte[] part : parts) {
			stream.print(new String(part, charset));
		}
		stream.flush();
	}

}

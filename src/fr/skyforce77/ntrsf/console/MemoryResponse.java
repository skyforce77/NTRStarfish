package fr.skyforce77.ntrsf.console;

public interface MemoryResponse {
	
	public void readMemory(long pid, long address, byte[] data);

}

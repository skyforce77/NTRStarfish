package fr.skyforce77.ntrsf.commands;

import fr.skyforce77.ntrsf.Starfish;
import fr.skyforce77.ntrsf.console.MemoryResponse;
import fr.skyforce77.ntrsf.data.BinaryUtils;
import fr.skyforce77.ntrsf.log.NTRLogger;

public class CommandReadMemory extends InternalCommand {

	@Override
	public String[] getLabel() {
		return new String[]{"readmem"};
	}
	
	@Override
	public String getDescription() {
		return "Read memory at <addr> [size] [pid]";
	}

	@Override
	public void onCommand(String label, String[] args) {
		if(args.length >= 1) {
			long pid = 0l;
			if(args.length >= 3)
				pid = Long.decode(args[2]);
			long addr = Long.decode(args[0]);
			long size = 0x100l;
			if(args.length >= 2)
				size = Long.decode(args[1]);
			NTRLogger.println("Sent read request: {pid: "+pid+", addr: "+addr+", size: "+size+"}");
			Starfish.getConsoleManager().requestMemory(pid, addr, size, new ReadMemory());
		} else {
			NTRLogger.println("Use: readmem <addr> [size] [pid]");
		}
	}
	
	private static class ReadMemory implements MemoryResponse {

		@Override
		public void readMemory(long pid, long address, byte[] data) {
			int o = 0;
			for(int i = 0; i < data.length/4; i++) {
				if(i%4==0) {
					NTRLogger.print(format(o+address)+" | ");
				}
				for(int j = 0; j < 4; j++) {
					if(data.length > o+j) {
						NTRLogger.print(format(data[o+j]));
					}
				}
				o+=4;
				if(i%4==3)
					NTRLogger.println("");
				else
					NTRLogger.print(" ");
			}
			NTRLogger.println("");
		}
		
		private static String format(byte data) {
			String str = Integer.toHexString(Byte.toUnsignedInt(data));
			if(str.length() == 1)
				str = "0"+str;
			return str;
		}
		
		private static String format(long index) {
			byte[] data = BinaryUtils.toUnsignedBytes(index);
			String s = "";
			for(byte b : data) {
				s+=format(b);
			}
			return s;
		}
		
	}

}

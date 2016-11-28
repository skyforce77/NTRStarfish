package fr.skyforce77.ntrsf.commands;

import fr.skyforce77.ntrsf.api.Command;
import fr.skyforce77.ntrsf.log.NTRLogger;
import fr.skyforce77.ntrsf.network.NTRPacketReadMemory;

public class CommandReadMemory extends Command {

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
			new NTRPacketReadMemory(pid, addr, size).send();
			NTRLogger.println("Sent read request: {pid: "+pid+", addr: "+addr+", size: "+size+"}");
		} else {
			NTRLogger.println("Use: readmem <addr> [size] [pid]");
		}
	}

}

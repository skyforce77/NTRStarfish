package fr.skyforce77.ntrsf.commands;

import fr.skyforce77.ntrsf.log.NTRLogger;
import fr.skyforce77.ntrsf.network.NTRPacket;
import fr.skyforce77.ntrsf.network.NTRPacketType;

public class CommandThreads extends InternalCommand {

	@Override
	public String[] getLabel() {
		return new String[]{"threads"};
	}
	
	@Override
	public String getDescription() {
		return "List of threads for <pid>";
	}

	@Override
	public void onCommand(String label, String[] args) {
		if(args.length >= 1) {
			Long pid = Long.decode(args[0]);
			new NTRPacket(NTRPacketType.LIST_THREADS, new long[]{pid});
		} else {
			NTRLogger.println("Use: threads <pid>");
		}
	}

}

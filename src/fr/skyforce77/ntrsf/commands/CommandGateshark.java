package fr.skyforce77.ntrsf.commands;

import fr.skyforce77.ntrsf.gateshark.GatesharkInterpreter;
import fr.skyforce77.ntrsf.log.NTRLogger;

public class CommandGateshark extends InternalCommand {

	@Override
	public String[] getLabel() {
		return new String[]{"gateshark"};
	}
	
	@Override
	public String getDescription() {
		return "Execute simple gateshark instructions";
	}

	@Override
	public void onCommand(String label, String[] args) {
		if(args.length >= 3) {
			long pid = Long.decode(args[0]);
			new GatesharkInterpreter(pid).exec(args[1], args[2]);
		} else {
			NTRLogger.println("Use: gateshark <pid> <instruction> <argument>");
		}
	}

}

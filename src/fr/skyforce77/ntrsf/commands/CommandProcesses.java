package fr.skyforce77.ntrsf.commands;

import fr.skyforce77.ntrsf.Starfish;
import fr.skyforce77.ntrsf.console.ConsoleProcess;
import fr.skyforce77.ntrsf.log.NTRLogger;

public class CommandProcesses extends InternalCommand {

	@Override
	public String[] getLabel() {
		return new String[]{"processes"};
	}
	
	@Override
	public String getDescription() {
		return "List of currently running processes";
	}

	@Override
	public void onCommand(String label, String[] args) {
		for(ConsoleProcess p : Starfish.getConsoleManager().getProcesses()) {
			NTRLogger.println(p);
		}
	}

}

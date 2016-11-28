package fr.skyforce77.ntrsf.commands;

import fr.skyforce77.ntrsf.log.NTRLogger;

public class CommandClear extends InternalCommand {

	@Override
	public String[] getLabel() {
		return new String[]{"clear"};
	}
	
	@Override
	public String getDescription() {
		return "Clear the log screen";
	}

	@Override
	public void onCommand(String label, String[] args) {
		NTRLogger.clear();
	}

}

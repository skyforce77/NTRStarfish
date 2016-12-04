package fr.skyforce77.ntrsf.commands;

import fr.skyforce77.ntrsf.Starfish;
import fr.skyforce77.ntrsf.data.CosmicBuffer;
import fr.skyforce77.ntrsf.log.NTRLogger;

public class CommandTxtFile extends InternalCommand {

	@Override
	public String[] getLabel() {
		return new String[]{"txtfile"};
	}
	
	@Override
	public String getDescription() {
		return "Create a textfile in SDCard";
	}

	@Override
	public void onCommand(String label, String[] args) {
		if(args.length >= 2) {
			Starfish.getConsoleManager().setFile(args[0], new CosmicBuffer(args[1].getBytes()));
		} else {
			NTRLogger.println("Use: txtfile <filename> <content>");
		}
	}

}

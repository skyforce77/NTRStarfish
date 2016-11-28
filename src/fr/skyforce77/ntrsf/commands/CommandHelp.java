package fr.skyforce77.ntrsf.commands;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import fr.skyforce77.ntrsf.Starfish;
import fr.skyforce77.ntrsf.api.Command;
import fr.skyforce77.ntrsf.log.NTRLogger;

public class CommandHelp extends Command {

	@Override
	public String[] getLabel() {
		return new String[]{"help"};
	}
	
	@Override
	public String getDescription() {
		return "Prints a list of commands";
	}

	@Override
	public void onCommand(String label, String[] args) {
		NTRLogger.println("---- Commands ----");
		Collection<Command> values = new HashSet<Command>(Starfish.getPluginManager().getCommands().values());
		for(Command command : values) {
			if(command.getLabel().length == 1) {
				NTRLogger.print(command.getLabel()[0]);
			} else {
				NTRLogger.print(Arrays.toString(command.getLabel()));
			}
			NTRLogger.println(": "+command.getDescription());
		}
		NTRLogger.println("------------------");
	}

}

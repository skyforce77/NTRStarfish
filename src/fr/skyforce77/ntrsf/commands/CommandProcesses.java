package fr.skyforce77.ntrsf.commands;

import fr.skyforce77.ntrsf.api.Command;
import fr.skyforce77.ntrsf.network.NTRPacket;
import fr.skyforce77.ntrsf.network.NTRPacketType;

public class CommandProcesses extends Command {

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
		new NTRPacket(NTRPacketType.LIST_PROCESSES).send();
	}

}

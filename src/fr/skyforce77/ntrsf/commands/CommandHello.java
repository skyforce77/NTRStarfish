package fr.skyforce77.ntrsf.commands;

import fr.skyforce77.ntrsf.api.Command;
import fr.skyforce77.ntrsf.network.NTRPacket;
import fr.skyforce77.ntrsf.network.NTRPacketType;

public class CommandHello extends Command {

	@Override
	public String[] getLabel() {
		return new String[]{"hello"};
	}
	
	@Override
	public String getDescription() {
		return "3ds screen flicker";
	}

	@Override
	public void onCommand(String label, String[] args) {
		new NTRPacket(NTRPacketType.HELLO).send();
	}

}

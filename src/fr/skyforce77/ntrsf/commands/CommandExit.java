package fr.skyforce77.ntrsf.commands;

public class CommandExit extends InternalCommand {

	@Override
	public String[] getLabel() {
		return new String[]{"exit","quit"};
	}
	
	@Override
	public String getDescription() {
		return "Shutdown NTRStarfish";
	}

	@Override
	public void onCommand(String label, String[] args) {
		System.exit(0);
	}

}

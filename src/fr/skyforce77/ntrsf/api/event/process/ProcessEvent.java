package fr.skyforce77.ntrsf.api.event.process;

import fr.skyforce77.ntrsf.api.event.Event;
import fr.skyforce77.ntrsf.console.ConsoleProcess;

public class ProcessEvent extends Event {
	
	private ConsoleProcess process;

	public ProcessEvent(ConsoleProcess process) {
		this.process = process;
	}

	public ConsoleProcess getProcess() {
		return process;
	}

}

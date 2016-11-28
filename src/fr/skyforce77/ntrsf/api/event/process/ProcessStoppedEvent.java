package fr.skyforce77.ntrsf.api.event.process;

import fr.skyforce77.ntrsf.console.ConsoleProcess;

public class ProcessStoppedEvent extends ProcessEvent {

	public ProcessStoppedEvent(ConsoleProcess process) {
		super(process);
	}

}

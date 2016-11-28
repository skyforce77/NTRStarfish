package fr.skyforce77.ntrsf.api.event.process;

import fr.skyforce77.ntrsf.console.ConsoleProcess;

public class ProcessStartedEvent extends ProcessEvent {

	public ProcessStartedEvent(ConsoleProcess process) {
		super(process);
	}

}

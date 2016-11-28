package fr.skyforce77.ntrsf.console;

import java.util.LinkedList;
import java.util.List;

import fr.skyforce77.ntrsf.Starfish;
import fr.skyforce77.ntrsf.api.event.process.ProcessStartedEvent;
import fr.skyforce77.ntrsf.api.event.process.ProcessStoppedEvent;
import fr.skyforce77.ntrsf.network.NTRPacket;
import fr.skyforce77.ntrsf.network.NTRPacketReadMemory;

public class ConsoleManager {
	
	private List<ConsoleProcess> processes;
	
	public ConsoleManager() {
		processes = new LinkedList<ConsoleProcess>();
	}
	
	public List<ConsoleProcess> getProcesses() {
		return processes;
	}
	
	public void requestMemory(long pid, long address, long size, MemoryResponse reponse) {
		NTRPacket packet = new NTRPacketReadMemory(pid, address, size);
		Starfish.getNetworkManager().registerResponseListener(packet, new MemoryCallback(reponse));
		packet.send();
	}

	public void updateProcesses(NTRPacket packet, String ret) {
		List<ConsoleProcess> list = new LinkedList<ConsoleProcess>();
		String[] lines = ret.split("\n");
		for(String line : lines) {
			if(line.startsWith("pid: ")) {
				long pid = Long.decode(line.substring(5, 15));
				String pname = line.substring(23, 32).trim();
				String tid = line.substring(39, 55);
				long kpobj = Long.decode("0x"+line.substring(64, 72));
				
				ConsoleProcess process = new ConsoleProcess(line, pid, pname, tid, kpobj);
				list.add(process);
			}
		}
		
		List<ConsoleProcess> added = new LinkedList<ConsoleProcess>();
		List<ConsoleProcess> removed = new LinkedList<ConsoleProcess>();
		added.addAll(list);
		removed.addAll(processes);
		removed.removeAll(added);
		added.removeAll(processes);
		
		for(ConsoleProcess process : added) {
			Starfish.getPluginManager().callEvent(new ProcessStartedEvent(process));
		}
		
		for(ConsoleProcess process : removed) {
			Starfish.getPluginManager().callEvent(new ProcessStoppedEvent(process));
		}
		
		processes = list;
	}

}

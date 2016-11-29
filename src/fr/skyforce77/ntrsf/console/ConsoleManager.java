package fr.skyforce77.ntrsf.console;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import fr.skyforce77.ntrsf.Starfish;
import fr.skyforce77.ntrsf.api.event.process.ProcessStartedEvent;
import fr.skyforce77.ntrsf.api.event.process.ProcessStoppedEvent;
import fr.skyforce77.ntrsf.data.CosmicBuffer;
import fr.skyforce77.ntrsf.network.NTRPacket;
import fr.skyforce77.ntrsf.network.NTRPacketReadMemory;
import fr.skyforce77.ntrsf.network.NTRPacketType;

public class ConsoleManager {
	
	private List<ConsoleProcess> processes;
	
	public ConsoleManager() {
		processes = new LinkedList<ConsoleProcess>();
	}
	
	public List<ConsoleProcess> getProcesses() {
		return processes;
	}
	
	public ConsoleProcess getProcess(String... tid) {
		for(String s : tid) {
			for(ConsoleProcess p : processes) {
				if(p.getTitleId().equalsIgnoreCase(s))
					return p;
			}
		}
		return null;
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
	
	public void setFile(String path, CosmicBuffer file) {
		byte[] filename = new byte[0x200];
		byte[] nameBytes = path.getBytes(Charset.forName("UTF-8"));
		if(nameBytes.length > filename.length) {
			throw new IllegalArgumentException("Path exceed 512 characters");
		}
		System.arraycopy(nameBytes, 0, filename, 0, nameBytes.length);
		
		CosmicBuffer buffer = new CosmicBuffer();
		buffer.fill(filename);
		buffer.fill(file);
		
		new NTRPacket(NTRPacketType.SAVE_FILE, null, buffer).send();
	}
	
	public void setFile(String path, File file) throws IOException {
		InputStream is = new FileInputStream(file);
		CosmicBuffer buffer = new CosmicBuffer();
		buffer.fill(is, is.available());
		setFile(path, buffer);
	}
	
	public void setFile(String path, InputStream file) throws IOException {
		CosmicBuffer buffer = new CosmicBuffer();
		buffer.fill(file, file.available());
		setFile(path, buffer);
	}

}

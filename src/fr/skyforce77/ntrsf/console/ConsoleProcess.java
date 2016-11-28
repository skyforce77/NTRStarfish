package fr.skyforce77.ntrsf.console;

public class ConsoleProcess {
	
	private String line;
	private long pid;
	private String pname;
	private String tid;
	private long kpobj;
	
	public ConsoleProcess(String line, long pid, String pname, String tid, long kpobj) {
		this.line = line;
		this.pid = pid;
		this.pname = pname;
		this.tid = tid;
		this.kpobj = kpobj;
	}

	public long getPid() {
		return pid;
	}
	
	public String getPname() {
		return pname;
	}
	
	public String getTid() {
		return tid;
	}
	
	public long getKpobj() {
		return kpobj;
	}
	
	@Override
	public String toString() {
		return line;
	}
	
	@Override
	public int hashCode() {
		return (int)(pid ^ tid.length() ^ pname.length() ^ kpobj);
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj.hashCode() == hashCode();
	}

}

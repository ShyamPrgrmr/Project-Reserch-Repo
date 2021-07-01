package com.test.pr;

import java.util.ArrayList;

public class MonitorProcesses{
	private ArrayList<Process> processes;
	
	MonitorProcesses(){
		this.processes = new ArrayList<Process>();
	}
	
	public ArrayList<Process> getAllProcesses() {
		return processes;
	}
	
	public void addProcess(Process process) {
		processes.add(process);
	}
}


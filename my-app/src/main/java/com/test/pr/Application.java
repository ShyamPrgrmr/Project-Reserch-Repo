package com.test.pr;

import java.util.ArrayList;

import oshi.software.os.OSProcess;

public class Application {
	private String applicationName;
	private String cmdPath;
	private ArrayList<OSProcess> processes;
	private boolean isChecking = false;
	
	public Application(){
		processes = new ArrayList<OSProcess>();
	}
	
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getCmdPath() {
		return cmdPath;
	}
	public void setCmdPath(String cmdPath) {
		this.cmdPath = cmdPath;
	}
	public ArrayList<OSProcess> getProcesses() {
		return processes;
	}
	
	public boolean isChecking() {
		return isChecking;
	}

	public void setChecking(boolean isChecking) {
		this.isChecking = isChecking;
	}

	public void addProcess(OSProcess pr) {
		processes.add(pr);
	}

	@Override
	public String toString() {
		return "Application [applicationName=" + applicationName + ", cmdPath=" + cmdPath + ", processes=" + processes
				+ ", isChecking=" + isChecking + "]";
	}
	
}

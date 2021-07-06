package com.test.pr;

import java.util.ArrayList;

import oshi.software.os.OSProcess;


/*
 * Data holder class, for holding application specific data like process which are running to run the app.
 * it stores name and cmdpath of the process.
 * */
public class Application {
	private String applicationName;
	private String cmdPath;
	private ArrayList<OSProcess> processes;
	private boolean isChecking = false;
	private String applicationID;
	private String packageName;
	private String ports = "";
	
	public String getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
	}

	public Application(){
		processes = new ArrayList<OSProcess>();
	}
	
	public String getPorts() {
		return ports;
	}

	public void setPorts(String ports) {
		this.ports = ports;
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

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
}

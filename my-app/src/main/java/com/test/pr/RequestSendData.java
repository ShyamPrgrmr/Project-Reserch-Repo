package com.test.pr;


/*
 * Prototype for request
 * */
public class RequestSendData {
	private String appicationId;
	private String applicationName;
	private long memoryUsage;
	private long numberOfThread;
	private long diskUsage;
	private String cpuUsage;
	private String startTime;
	

	RequestSendData(){
		this.appicationId="";
		this.applicationName="";
		this.cpuUsage="0.0";
		this.diskUsage=0;
		this.numberOfThread=0;
		this.memoryUsage=0;
		this.startTime="";
	}
	
	@Override
	public String toString() {
		return "RequestSendData [appicationId=" + appicationId + ", applicationName=" + applicationName
				+ ", memoryUsage=" + memoryUsage + ", numberOfThread=" + numberOfThread + ", diskUsage=" + diskUsage
				+ ", cpuUsage=" + cpuUsage +", startTime="+ startTime +"]";
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getAppicationId() {
		return appicationId;
	}
	public void setAppicationId(String appicationId) {
		this.appicationId = appicationId;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public long getMemoryUsage() {
		return memoryUsage;
	}
	public void setMemoryUsage(long memoryUsage) {
		this.memoryUsage = memoryUsage;
	}
	public long getNumberOfThread() {
		return numberOfThread;
	}
	public void setNumberOfThread(long numberOfThread) {
		this.numberOfThread = numberOfThread;
	}
	public long getDiskUsage() {
		return diskUsage;
	}
	public void setDiskUsage(long diskUsage) {
		this.diskUsage = diskUsage;
	}
	public String getCpuUsage() {
		return cpuUsage;
	}
	public void setCpuUsage(String cpuUsage) {
		this.cpuUsage = cpuUsage;
	}
	
	public void addAndSetData(long memoryUsage,long diskUsage,long numberOfThread) {
		this.memoryUsage += memoryUsage;
		this.diskUsage += diskUsage;
		this.numberOfThread += numberOfThread;
	}
	
}

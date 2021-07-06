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
	
	RequestSendData(){
		this.appicationId="";
		this.applicationName="";
		this.cpuUsage="0.0";
		this.diskUsage=0;
		this.numberOfThread=0;
		this.memoryUsage=0;
	}
	
	@Override
	public String toString() {
		return "RequestSendData [appicationId=" + appicationId + ", applicationName=" + applicationName
				+ ", memoryUsage=" + memoryUsage + ", numberOfThread=" + numberOfThread + ", diskUsage=" + diskUsage
				+ ", cpuUsage=" + cpuUsage + "]";
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


/*
 * Application ID :: 1
    Application Name :: eclipse
	Memory Usage :: 659 MB
	Disk Usage ::619 MB
	Number of Threads :: 86
	UP time :: 310 Min.
	Start time :: 2021-07-01 13:04:31.175
	CPU Usage :: 3943.71

 * 
 * 
 * 
 * */

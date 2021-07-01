package com.test.pr;

import oshi.software.os.OSProcess;

public class Process{
	private long pid;
	private String name;
	private String path;
	private OSProcess processData;
	private boolean findByPid = false;
	
	public boolean isFindByPid() {
		return findByPid;
	}
	public void setFindByPid(boolean findByPid) {
		this.findByPid = findByPid;
	}
	public OSProcess getProcessData() {
		return processData;
	}
	public void setProcessData(OSProcess processData) {
		this.processData = processData;
	}
	public long getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	 
}

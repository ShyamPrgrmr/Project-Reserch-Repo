package com.test.oshi;

import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

public class CheckProcessThread extends Thread{
	
	private int processId;
	private OSProcess process;
	private String path;
	private String processName;
	private Boolean check;
	private ProcessThreshold processThreshold;
	
	public CheckProcessThread(OSProcess process, String path, String name,int processId,ProcessThreshold processThreshold) {
		this.process = process;
		this.path = path;
		this.processName = name;
		this.processId = processId;
		this.processThreshold = processThreshold;
	}

	public OSProcess getProcess() {
		return process;
	}

	public String getPath() {
		return path;
	}

	public String getProcessName() {
		return processName;
	}
	
	@Override
	public void run() {
		while(this.check) {
			try {
				
				SystemInfo si = new SystemInfo();
				OperatingSystem os =si.getOperatingSystem(); 
				process = os.getProcess(this.processId); 
				
				if(process.getState().equals("STOPPED")) {
					this.check = false;
				}
				
				if(process.getState().equals("RUNNING")) {
					//checking memory usage
					int used = (int) (process.getResidentSetSize()/1024)/1024;
					System.out.println((process.getResidentSetSize()/1024)/1024 + " MB" );
					
					if(processThreshold.getMaxMemoryUse()< used) {
						//Operations we have to perform//
						System.out.println("....Consuming More Memory");
					}
				}
				
				this.wait(5000);
				
			} catch (InterruptedException e) {
				this.check = !this.check;
				e.printStackTrace();
			}
		}
	}
	
}
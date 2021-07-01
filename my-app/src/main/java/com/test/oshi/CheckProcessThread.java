package com.test.oshi;



import org.slf4j.Logger;

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
	private Logger logger;
	
	public CheckProcessThread(String path, String name,int processId,ProcessThreshold processThreshold) {
		this.path = path;
		this.processName = name;
		this.processId = processId;
		this.processThreshold = processThreshold;
		this.check=true;
		run();
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
		System.out.println("Checking Thread is Running for"+processName);
		while(this.check) {
			try {
				
				SystemInfo si = new SystemInfo();
				OperatingSystem os =si.getOperatingSystem(); 
				process = os.getProcess(this.processId); 
				
				
				
			  int used =(int) (process.getResidentSetSize()/1024)/1024;
			  System.out.println((process.getResidentSetSize()/1024)/1024 + " MB" );
			  System.out.println(processName+" "+"Process is running....");
			  
			  if(processThreshold.getMaxMemoryUse()< used) { 

				  System.out.println("Consuming More Memory");
			  }
				
				
			  if(process.getState().equals("STOPPED")) { 
				this.check = false;
			  	logger.info(processName+" "+"Process is Stopped...."); 
			  }
			  
			  if(process.getState().equals("RUNNING")) { 
					  //checking memory usage 
					   
				  
			  }
				 
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				this.check = !this.check;
				e.printStackTrace();
			}
		}
	}
	
}
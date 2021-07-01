package com.test.oshi;

import java.util.Iterator;
import java.util.List;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

public class TestProcessData{
	
	public static int getProcessId(String processName, String processPath) {
		SystemInfo si = new SystemInfo();
		OperatingSystem os = si.getOperatingSystem();
		List<OSProcess> listofprocesses = os.getProcesses();
		@SuppressWarnings("rawtypes")
		Iterator itr = listofprocesses.iterator();
		
		while(itr.hasNext()) {
			OSProcess process = (OSProcess)itr.next();
			String path = new String(process.getCommandLine()).replaceAll("\"", "");
			
			if(process.getName().equals(processName) || path.contains(processPath)) {
				System.out.println("Process "+process.getProcessID());
				System.out.println("Parent Process "+process.getParentProcessID());
				return process.getProcessID();
			}
		}
		
		return 0;
	}
	
}
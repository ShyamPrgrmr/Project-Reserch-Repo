package com.test.oshi;

import java.util.Iterator;
import java.util.List;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PhysicalMemory;
import oshi.hardware.PowerSource;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

public class App 
{
    public static void main( String[] args )
    {
    	
		String checkApp = "‪C:\\Program Files\\Mozilla Firefox\\firefox.exe";
		String name = "eclipse";
		ProcessThreshold processThreshold = new ProcessThreshold();
		processThreshold.setMaxMemoryUse(2);
		//int id = new TestProcessData().getProcessId(name, checkApp);
		CheckProcessThread checkThread;
		int id=10824;
		if(id==0) 
			System.out.println("Process not found");
		else {
			
			SystemInfo si = new SystemInfo();
			OperatingSystem os = si.getOperatingSystem();
			List<OSProcess>processes =  os.getChildProcesses(id, 0, null);
			System.out.println(processes.toString());
			
		}
			//checkThread = new CheckProcessThread(checkApp,name,id,processThreshold);
    }
}

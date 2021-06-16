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
		String checkApp = "‪C:\\Users\\Asus\\eclipse\\java-2020-06\\eclipse\\eclipse.exe";
		String name = "eclipse";
		ProcessThreshold processThreshold = new ProcessThreshold();
		processThreshold.setMaxMemoryUse(1024);
		
		int pid=5764;
		SystemInfo si = new SystemInfo();
		OperatingSystem os =si.getOperatingSystem(); 
		OSProcess process = os.getProcess(pid); 
		System.out.println(process.getCommandLine());
          
    }
    
    
    
}

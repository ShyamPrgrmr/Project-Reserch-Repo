package com.test.oshi;

import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

public class Main {

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

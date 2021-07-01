package com.test.pr;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

public class Test {
	public static void main(String args[]) throws IOException {
		Process pr = new Process();
		pr.setName("firefox");
		pr.setPath("‪C:\\Program Files\\Mozilla Firefox\\firefox.exe");
		
		/*
		 * 
		 * Implementation of getting process id from port....
		 * 
		 * 
		 * */
		
		Process pr1 = new Process();
		pr1.setPid(11772);
		pr1.setFindByPid(true);
		
		
		MonitorProcesses mp = new MonitorProcesses();
		mp.addProcess(pr);
		mp.addProcess(pr1);
		
		CheckMainThread cmt = new CheckMainThread(mp);
		cmt.run();
	}	
}

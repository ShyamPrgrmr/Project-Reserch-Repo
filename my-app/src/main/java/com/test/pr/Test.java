package com.test.pr;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

public class Test {
	public static void main(String args[]) throws IOException {
//		Process pr = new Process();
//		pr.setName("firefox");
//		pr.setPath("‪C:\\Program Files\\Mozilla Firefox\\firefox.exe");
//		
		/*
		 * 
		 * Implementation of getting process id from port....
		 * 
		 * 
		 * */
		
//		try {
//			System.out.println(givepid("3306"));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		Process pr1 = new Process();
//		pr1.setPid(11772);
//		pr1.setFindByPid(true);
		
//		
//		MonitorProcesses mp = new MonitorProcesses();
//		mp.addProcess(pr);
////		mp.addProcess(pr1);
		
		CheckMainThread cmt = new CheckMainThread();
		cmt.run();
	}	
	
	
	public static String givepid(String portno) throws Exception{
		String pid;
		String cmd1 = "cmd.exe /c netstat -ano | findstr "+portno;
		Runtime run1 = Runtime.getRuntime();
		java.lang.Process pr1 = run1.exec(cmd1);
		pr1.waitFor();
		BufferedReader buf = new BufferedReader(
				new InputStreamReader(pr1.getInputStream()));
		String line2,l2 = "";
		int i=1;
		while ((line2=buf.readLine())!=null) {
			if(i==1) {
				i=2;
				l2=line2;
			}
		}
		pid = l2.substring(l2.lastIndexOf(" ")+1);
		
		return pid;
	}
	
}

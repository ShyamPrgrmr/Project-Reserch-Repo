package com.test.pr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Helper {
	
	public static String getAppCpuUsageForLinux(String app_name){
		try {
			java.lang.Process p1 = Runtime.getRuntime().exec("ps -C "+app_name+" -o %cpu");
			p1.waitFor();
			BufferedReader buf = new BufferedReader(new InputStreamReader(p1.getInputStream()));
			String line2,l2 = "";
			int i=1;
			while ((line2=buf.readLine())!=null) {
				if(i==2) {l2=line2; }
				i++;
			}
			return l2;
		}catch(IOException | InterruptedException e) {
			return "0.0";
		}
	}
	
	public static String getAppCpuUsageForWindows(String app_name){
		
		if(app_name.equals("Idle")) {
			return "0.00000";
		}
		
		try {
			java.lang.Process p1 = Runtime.getRuntime().exec("cmd.exe /c typeperf \"\\Process("+app_name+")\\% Processor Time\" -sc 2");
			BufferedReader buf = new BufferedReader(new InputStreamReader(p1.getInputStream()));
			String line2,l2 = "";
			int i = 0;
			while ((line2=buf.readLine())!=null) {
				if(i==3) {l2 = line2; break;}
				i++;
			}
			String[] l2arr = l2.split(",");
			String s=l2arr[1].replace("\"", "");
			
			return s;
		}catch(IOException e) {
			return "0.0";
		}	
	}
	
	public static HashMap< Integer,ArrayList<Integer> > getProcessAndPortForLinux(){
		return null;
	} 
	
	
	public static HashMap< Integer,ArrayList<Integer> > getProcessAndPortForWindows() {
		HashMap<Integer,ArrayList<Integer>> pap = new HashMap<Integer,ArrayList<Integer>>();
		try {
			String cmd1 = "cmd.exe /c netstat -ano";
			Runtime run1 = Runtime.getRuntime();
			java.lang.Process pr1 = run1.exec(cmd1);
			BufferedReader buf = new BufferedReader(new InputStreamReader(pr1.getInputStream()));
			String line2="";
			int i=1;
			while ((line2=buf.readLine())!=null) {
				if(i>4) {
					String l1 = line2.trim().replaceAll(" ","|");
					String l2 = l1.substring(7,l1.substring(7).indexOf("|")+7);
					String l3 =  new StringBuffer(l2.substring(l2.indexOf(":"),l2.length())).reverse().toString();
					String port = new StringBuffer(l3.substring(0,l3.indexOf(":"))).reverse().toString();
					String l4 = new StringBuffer(l1).reverse().toString();
					String pid = new StringBuffer(l4.substring(0,l4.indexOf("|"))).reverse().toString();
					
					if(pap.containsKey(pid)) {
						ArrayList<Integer> arr = pap.get(pid); 
						arr.add(Integer.parseInt(port));
						pap.remove(pid);
						pap.put(Integer.parseInt(pid), arr);
					}else {
						ArrayList<Integer> arr = new ArrayList<Integer>();
						arr.add(Integer.parseInt(port));
						pap.put(Integer.parseInt(pid), arr);
					}
				}
				
				i++;
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		return pap;
	}
	
	
	public static String IntegerArrayListToString(ArrayList<Integer> port) {
		String portdata = "";
		Iterator<Integer> itr = port.iterator();
		while(itr.hasNext()) {
			portdata += ","+itr.next().toString();
		}
		return portdata;
	}
	
}

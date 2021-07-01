package com.test.pr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

public class CheckMainThread implements Runnable{
	private MonitorProcesses monitorProcesses;
	//private ArrayList<Process> allProcess;
	//private HashMap<String,Application> applicationHash; 
	
	
	public CheckMainThread(MonitorProcesses monitorProcesses) {
		this.monitorProcesses = monitorProcesses;
	}
	
	public String[] getPathandName(int pid) {
		String ret[] = new String[2];
		SystemInfo si = new SystemInfo(); 
		OperatingSystem os = si.getOperatingSystem();
		OSProcess pro= os.getProcess(pid);
		ret[0] = pro.getPath();
		ret[1] = pro.getName();
		return ret;
	}
	
	public HashMap<String,Application> getAllProcesses(){
		ArrayList<Process> tempProcesses = new ArrayList<Process>();	
    	SystemInfo si = new SystemInfo(); 
		OperatingSystem os = si.getOperatingSystem();
		List<OSProcess> processes =  os.getProcesses();
		Iterator<OSProcess> itr = processes.iterator();
		HashMap<String,Application> apHash = new HashMap<String,Application>();
		while(itr.hasNext()) {
			OSProcess pr = itr.next();
			String name = pr.getName();
			if(apHash.containsKey(name)) {
				Application apn = apHash.get(name);
				apn.addProcess(pr);
				apHash.remove(name);
				apHash.put(name, apn);
			}else {
				Application apn = new Application();
				apn.setApplicationName(pr.getName());
				apn.setCmdPath(pr.getPath());
				apn.addProcess(pr);
				apHash.put(name,apn);
			}
		
		}
		return apHash;
	}

	
	
	@Override
	public void run() {
		
			try {
				
				while(true) {
					ArrayList<Process> userDefinedProcess = monitorProcesses.getAllProcesses();
					HashMap<String,Application> applicationHash = getAllProcesses();
					
					for(Process pr : userDefinedProcess) {
						String name = pr.getName();
						String path = pr.getPath();
						Iterator itr = applicationHash.entrySet().iterator();
						boolean flag = false;
						
						if(pr.isFindByPid()) {
							String data[] = getPathandName((int)pr.getPid()); 
							pr.setName(data[1]);
							pr.setPath(data[0]);
						}
						
						while(itr.hasNext()) {
							Map.Entry mp = (Map.Entry)itr.next();
							Application apn = (Application) mp.getValue();
							
							if(apn.getApplicationName().equals(pr.getName()) || apn.getCmdPath().equals(pr.getPath())) {
								IndividualApplicationChecker iac = new IndividualApplicationChecker(apn);
								iac.run();
								flag = true;
							}
							if(flag) break;
						}
					}
					Thread.sleep(2000); //wait for 2 seconds
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
}
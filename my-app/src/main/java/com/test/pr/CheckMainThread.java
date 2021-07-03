package com.test.pr;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

public class CheckMainThread implements Runnable
{
	private MonitorProcesses monitorProcesses;
	
	 //Storing application data
	private HashMap<String,Application> applicationHashStore;  
	
	public CheckMainThread() {
		applicationHashStore = new HashMap<String,Application>();
	}
	
	
	/*
	 * This method is  use for getting name and path of the application for which only port no. is passed.
	 * This return array of two string where first is Command Path and second is name of application
	 * */
	public String[] getPathandName(int pid) {
		String ret[] = new String[2];
		SystemInfo si = new SystemInfo(); 
		OperatingSystem os = si.getOperatingSystem();
		OSProcess pro= os.getProcess(pid);
		ret[0] = pro.getPath();
		ret[1] = pro.getName();
		return ret;
	}
	
	
	/*
	 * This method is use for getting Hashmap<String:name of application,
	 * 										  Application:application data with all process information>
	 * */
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


	/*
	 * Make a request to the server and get id for particular application...
	 * */
	public String getIdFromServer(Application apn) {
		//testing :: returning mock id
		return( " "+ ((int) (Math.random()*10000000) ));
	}
	
	/*
	 * Send data to server
	 * */
	public void sendDataToServer(HashMap<String,Application> hash) {
		//lets print all applications
		//working as expected
		Iterator itr = hash.entrySet().iterator();
		System.out.println("\n\nApplication Name -- Application ID");
		while(itr.hasNext()) {
			Map.Entry mp = (Map.Entry)itr.next();
			Application apn = (Application) mp.getValue();
			System.out.println(apn.getApplicationName()+" -- "+apn.getApplicationID());
		}
	}
	
	
	/*
	 * THis method causes a check in which it will compare all the currently running processes 
	 * and user defined processes (Process which are part of the application which we are checking)
	 * 
	 * */
	
	@Override
	public void run() {
		
			try {
				
				while(true) {
					HashMap<String,Application> applicationHash = getAllProcesses();
					Iterator itr = applicationHash.entrySet().iterator();
					HashMap<String,Application> temp = new HashMap<String,Application>();
					
					while(itr.hasNext()) {
						
						Map.Entry mp = (Map.Entry)itr.next();
						Application apn = (Application) mp.getValue();
						String keyName = (String)mp.getKey();
						
						//application hash store will contain the application which has application id
						//so here we are checking if it available
						
						if(this.applicationHashStore.containsKey(keyName)) {
							//Application id is available
							//getting id from store and storing it into temp hash.
							Application newapn = this.applicationHashStore.get(keyName);
							String id = newapn.getApplicationID();
							apn.setApplicationID(id);
							temp.put(keyName, apn);
							
						}else {
							//application id is not available
							//send request to the server 
							//server will return application id
							//mock code :: mocking id
							//this id will come from server this is for test only
							//see the getIdFromServer method implementation...
							String id = getIdFromServer(apn);
							//mock end
							//now adding it into store and temp hash
							apn.setApplicationID(id);
							this.applicationHashStore.put(keyName,apn);
							temp.put(keyName, apn);
						}
						
					}
					
					//we will have hash map which will conatin's all the id's now we can make request.... 
					sendDataToServer(temp);
					Thread.sleep(2000); //wait for 2 seconds
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
}






//for(Process pr : userDefinedProcess) {
//	String name = pr.getName();
//	String path = pr.getPath();
//	Iterator itr = applicationHash.entrySet().iterator();
//	boolean flag = false;
//	
//	if(pr.isFindByPid()) {
//		String data[] = getPathandName((int)pr.getPid()); 
//		pr.setName(data[1]);
//		pr.setPath(data[0]);
//	}
//	
//	while(itr.hasNext()) {
//		Map.Entry mp = (Map.Entry)itr.next();
//		Application apn = (Application) mp.getValue();
//		
//		if(apn.getApplicationName().equals(pr.getName()) || apn.getCmdPath().equals(pr.getPath())) {
//			IndividualApplicationChecker iac = new IndividualApplicationChecker(apn);
//			iac.run();
//			flag = true;
//		}
//		if(flag) break;
//	}
//	
//}
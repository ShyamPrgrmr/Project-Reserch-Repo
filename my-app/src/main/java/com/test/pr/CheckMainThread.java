package com.test.pr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

public class CheckMainThread implements Runnable
{
	private MonitorProcesses monitorProcesses;
	private GetSystemParameters gsps;
	 //Storing application data
	private HashMap<String,Application> applicationHashStore;  
	
	public CheckMainThread() {
		applicationHashStore = new HashMap<String,Application>();
		gsps = new GetSystemParameters();
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
		HashMap<Integer,ArrayList<Integer>> pap;
		
		if(this.gsps.getSystemFamily().equalsIgnoreCase("Windows"))
			pap = Helper.getProcessAndPortForWindows();
		else
			pap = new HashMap<Integer,ArrayList<Integer>>();
		
		ArrayList<Process> tempProcesses = new ArrayList<Process>();	
    	SystemInfo si = new SystemInfo(); 
		OperatingSystem os = si.getOperatingSystem();
		List<OSProcess> processes =  os.getProcesses();
		Iterator<OSProcess> itr = processes.iterator();
		HashMap<String,Application> apHash = new HashMap<String,Application>();
		while(itr.hasNext()) {
			OSProcess pr = itr.next();
			String name = pr.getName();
			int pid = pr.getProcessID();
			String ports="";
			
			if(pap.containsKey(pid)) {
				ArrayList<Integer> portlist = pap.get(pid);
				ports += Helper.IntegerArrayListToString(portlist);
			}
			
			if(apHash.containsKey(name)) {
				Application apn = apHash.get(name);
				apn.addProcess(pr);
				apn.setPorts(apn.getPorts()+ports);
				apHash.remove(name);
				apHash.put(name, apn);
			}else {
				Application apn = new Application();
				apn.setApplicationName(pr.getName());
				apn.setCmdPath(pr.getPath());
				apn.addProcess(pr);
				apn.setPorts(apn.getPorts()+ports);
				apHash.put(name,apn);
			}
		
		}
		return apHash;
	}

	
	//
	public HashMap<String,String> portWithProcess() {
		HashMap<String,String> data = new HashMap<String,String>();
		
		
		return data;
	}

	/*
	 * Make a request to the server and get id for particular application...
	 * */
	public String getIdFromServer(Application apn) {
		
		//Params :: Application Name, Package name, Path, Port Number
		String applicationName = apn.getApplicationName();
		String path = apn.getCmdPath()==null ? "" : apn.getCmdPath();
		String port = apn.getPorts();
		String packageName="";
		JSONObject obj = new JSONObject();
		obj.put("applicationName", applicationName);
		obj.put("path", path);
		if(port.length()!=0) obj.put("port", port.substring(1,port.length()));
		else obj.put(port, "");
		obj.put("packageName", packageName);
		
		System.out.println(obj.toString());
		//testing :: returning mock id
		return( " "+ ((int) (Math.random()*10000000) ));
	}
	
	/*
	 * Send data to server
	 * */
	public void sendDataToServer(HashMap<String,Application> hash) {
		Iterator itr = hash.entrySet().iterator();
		JSONArray jarray = new JSONArray();
		
		while(itr.hasNext()) {
			Map.Entry mp = (Map.Entry)itr.next();
			Application apn = (Application) mp.getValue();
			RequestSendData rsd = getDataForApplication(apn);
			JSONObject obj = new JSONObject();
			obj.put("applicationID", rsd.getAppicationId());
			obj.put("applicationName", rsd.getApplicationName());
			obj.put("memoryUsage", rsd.getMemoryUsage());
			obj.put("cpuUsage", rsd.getCpuUsage());
			obj.put("diskUsage", rsd.getDiskUsage());
			obj.put("numberOfThread", rsd.getNumberOfThread());
			jarray.add(obj);
		}
		
		//send jarray as a request body
		
		System.out.println(jarray.toString());
	}
	
	public RequestSendData getDataForApplication(Application apn) {
		RequestSendData rsd = new RequestSendData();
		rsd.setAppicationId(apn.getApplicationID());
		rsd.setApplicationName(apn.getApplicationName());
		
		if(this.gsps.getSystemFamily().equalsIgnoreCase("Windows"))
			rsd.setCpuUsage(Helper.getAppCpuUsageForWindows(apn.getApplicationName()));
		else {
			rsd.setCpuUsage(Helper.getAppCpuUsageForLinux(apn.getApplicationName()));
		}
		ArrayList<OSProcess> processes = apn.getProcesses();
		Iterator<OSProcess> itr = processes.iterator();
		while(itr.hasNext()) {
			OSProcess pr = itr.next();
			long memoryUsage = pr.getResidentSetSize();
			long diskUsage = pr.getBytesRead() + pr.getBytesWritten();
			long numberOfThread = pr.getThreadCount();
			rsd.addAndSetData(memoryUsage, diskUsage, numberOfThread);
		}
		return rsd;
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
					Thread.sleep(20000); //wait for 2 seconds
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
}

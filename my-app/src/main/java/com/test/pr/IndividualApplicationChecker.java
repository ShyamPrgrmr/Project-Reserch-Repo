package com.test.pr;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

public class IndividualApplicationChecker implements Runnable{
	Application apn;
	
	public IndividualApplicationChecker(Application apn) {
		this.apn = apn;
	}
	
	@Override
	public String toString(){
		return ("\nMemory Usage :: "+ ((CheckedParameter.memoryUsage/1024)/1024)+" MB"
				+ "\nDisk Usage ::" + (((CheckedParameter.bytesRead + CheckedParameter.bytesWrite)/1024)/1024)+" MB"
				+ "\nNumber of Threads :: "+(CheckedParameter.threadCount)
				+ "\nUP time :: "+(CheckedParameter.upTime/60000 +" Min.")
				+ "\nStart time :: "+  new Timestamp(CheckedParameter.starTime).toString()
				+ "\nCPU Usage :: "+((double)CheckedParameter.cpuUsage)
				);
	}
	
	@Override 
	public void run() {
		String name = apn.getApplicationName();
		ArrayList<OSProcess> processes = apn.getProcesses();
		Iterator<OSProcess> itr = processes.iterator();
		
		reset();
		
		while(itr.hasNext()) {
			OSProcess process = itr.next();
			CheckedParameter.memoryUsage += process.getResidentSetSize();
			CheckedParameter.bytesRead += process.getBytesRead();
			CheckedParameter.bytesWrite += process.getBytesWritten();
			CheckedParameter.threadCount +=process.getThreadCount();
			CheckedParameter.numberofOpenedFile += process.getOpenFiles();
			
			
			//BUG
			
			SystemInfo systemInfo = new SystemInfo();
	        CentralProcessor processor = systemInfo.getHardware().getProcessor();
	        int cpuNumber = processor.getLogicalProcessorCount();
	        long previousTime = 0;
	        long currentTime = process.getKernelTime() + process.getUserTime();
	        long timeDifference = currentTime - previousTime;

	        double processCpu = (100 * (timeDifference / 5000d)) / cpuNumber;
	        previousTime = currentTime;
			
	        //BUG
			
			CheckedParameter.cpuUsage =processCpu;
			
			if(CheckedParameter.upTime < process.getUpTime()) {
				CheckedParameter.upTime = process.getUpTime();
			}
			
			if(CheckedParameter.starTime < process.getStartTime()) {
				CheckedParameter.starTime = process.getStartTime();
			}
		}
		
		System.out.println(toString());
	}
	
	private double getCpuTime(OSProcess pr) {
		OSProcess process;
		long currentTime,previousTime = 0,timeDifference;
		double cpu=0.0;
		int pid = pr.getProcessID();
		SystemInfo si = new SystemInfo();
		OperatingSystem os = si.getOperatingSystem();
		CentralProcessor processor = si.getHardware().getProcessor();
		int cpuNumber = processor.getLogicalProcessorCount();
		
		process = os.getProcess(pid);
		if (process != null) {
			currentTime = process.getKernelTime() + process.getUserTime();
			if (previousTime != -1) {
				timeDifference = currentTime - previousTime;
				cpu = (100d * (timeDifference / ((double) 1000)))  / (os.getFamily().equalsIgnoreCase("windows")?cpuNumber:1 );;
			}     

			previousTime = currentTime;

		}
		
		return cpu;
	}
	
	
	private void reset() {
		CheckedParameter.bytesRead=0;
		CheckedParameter.bytesWrite=0;
		CheckedParameter.cpuUpTime=0;
		CheckedParameter.cpuUsage=0.0;
		CheckedParameter.kernelTime=0;
		CheckedParameter.memoryUsage=0;
		CheckedParameter.numberofOpenedFile=0;
		CheckedParameter.starTime=0;
		CheckedParameter.threadCount=0;
		CheckedParameter.upTime=0;
	}
	
	private static class CheckedParameter{
		private static long memoryUsage=0;
		private static double cpuUsage=0.0;
		private static long kernelTime=0;
		private static long starTime=0;
		private static long bytesRead=0;
		private static long bytesWrite=0;
		private static long numberofOpenedFile=0;
		private static double cpuUpTime=0;
		private static long threadCount=0;
		private static long upTime=0;
	}	
	
	
}

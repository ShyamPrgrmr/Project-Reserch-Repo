package com.test.oshi;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

public class TestProcessData{
	public static void main(String[] args) throws InterruptedException {
		OSProcess process;
		long currentTime,previousTime = 0,timeDifference;
		double cpu;
		int pid = 5764;
		SystemInfo si = new SystemInfo();
		OperatingSystem os = si.getOperatingSystem();
		CentralProcessor processor = si.getHardware().getProcessor();
		int cpuNumber = processor.getLogicalProcessorCount();
		boolean processExists = true;
		process = os.getProcess(pid);
		System.out.println( (process.getResidentSetSize()/1024)/1024 + " MB" );
		System.out.println( process.getThreadCount());
		System.out.println( process.getThreadDetails().toString());
		
		/*
		 * while (processExists) { process = os.getProcess(pid); if (process != null) {
		 * 
		 * currentTime = process.getKernelTime() + process.getUserTime();
		 * 
		 * if (previousTime != -1) {
		 * 
		 * timeDifference = currentTime - previousTime; cpu = (100d * (timeDifference /
		 * ((double) 1000))) / cpuNumber; System.out.println(cpu); }
		 * 
		 * previousTime = currentTime;
		 * 
		 * Thread.sleep(1000); } else { processExists = false; } }
		 */
	}
}
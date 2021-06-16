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

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		
		  int pid = 5764;
		  SystemInfo si = new SystemInfo();
		  oshi.software.os.OperatingSystem os = si.getOperatingSystem();
		  CentralProcessor processor = si.getHardware().getProcessor(); int cpuNumber =
		  processor.getLogicalProcessorCount(); boolean processExists = true; OSProcess
		  process = os.getProcess(pid); System.out.print(process.getName());
//		  System.out.print("\n"+process.getPath());
//		  System.out.print("\n"+process.getUpTime()/60000);
//		  System.out.print("\n"+process.getProcessCpuLoadCumulative());
		  System.out.print("\n"+process.getCommandLine());
//		  System.out.print("\n"+process.getState());
		 
		
		/*
		 * SystemInfo systemInfo = new SystemInfo(); HardwareAbstractionLayer hardware =
		 * systemInfo.getHardware(); CentralProcessor processor =
		 * hardware.getProcessor();
		 * 
		 * CentralProcessor.ProcessorIdentifier processorIdentifier =
		 * processor.getProcessorIdentifier();
		 * 
		 * System.out.println("System Info :: ");
		 * System.out.println("Processor Vendor: " + processorIdentifier.getVendor());
		 * System.out.println("Processor Name: " + processorIdentifier.getName());
		 * System.out.println("Processor ID: " + processorIdentifier.getProcessorID());
		 * System.out.println("Identifier: " + processorIdentifier.getIdentifier());
		 * System.out.println("Microarchitecture: " +
		 * processorIdentifier.getMicroarchitecture());
		 * System.out.println("Frequency (Hz): " + processorIdentifier.getVendorFreq());
		 * System.out.println("Frequency (GHz): " + processorIdentifier.getVendorFreq()
		 * / 1000000000.0); System.out.println("Memory Info : "); GlobalMemory
		 * globalMemory = hardware.getMemory();
		 * System.out.println(" "+globalMemory.toString()); List<PhysicalMemory> li =
		 * globalMemory.getPhysicalMemory(); Iterator itr = li.iterator();
		 * while(itr.hasNext()) {
		 * System.out.println(String.format(" %s",itr.next().toString())); }
		 */
          
          
          
//          HardwareAbstractionLayer hal = systemInfo.getHardware();
//          for (PowerSource pSource : hal.getPowerSources()) {
//              System.out.println(String.format("%n %s", pSource.getName(), pSource.getRemainingCapacityPercent() * 100d));
//          }
//    	
          
          
    }
    
    
    
}

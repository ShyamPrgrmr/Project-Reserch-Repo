package com.test.pr;

import java.util.Iterator;
import java.util.List;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PhysicalMemory;
import oshi.software.os.OperatingSystem;

public class GetSystemParameters{
	private String osName;
	private int memorySize; //memory size
	private long diskSize;
	private int cpuCores; //number of cpu
	private String systemType; //32bit or 64bit
	private long systemUptime; 
	private String systemFamily; // windows or linux
	private String  manufacturer;
	
	public GetSystemParameters() {
		SystemInfo si = new SystemInfo(); 
		OperatingSystem os = si.getOperatingSystem();
		HardwareAbstractionLayer hal = si.getHardware();
		CentralProcessor cpu = hal.getProcessor();
		
		memorySize = getMemorySize(hal.getMemory().getPhysicalMemory());
		cpuCores = cpu.getLogicalProcessorCount();
		systemType = os.getBitness() + "bit";
		systemUptime = os.getSystemUptime();
		systemFamily = os.getFamily();
		manufacturer = os.getManufacturer();
		diskSize = (((getDiskSize(hal.getDiskStores())/1024)/1024)/1024); //in GB
	}
		
	@Override
	public String toString() {
		return "GetSystemParameters [osName=" + osName + ", memorySize=" + memorySize + ", diskSize=" + diskSize
				+ ", cpuCores=" + cpuCores + ", systemType=" + systemType + ", systemUptime=" + systemUptime
				+ ", systemFamily=" + systemFamily + ", manufacturer=" + manufacturer + "]";
	}

	public String getOsName() {
		return osName;
	}

	public int getMemorySize() {
		return memorySize;
	}

	public long getDiskSize() {
		return diskSize;
	}

	public int getCpuCores() {
		return cpuCores;
	}

	public String getSystemType() {
		return systemType;
	}

	public long getSystemUptime() {
		return systemUptime;
	}

	public String getSystemFamily() {
		return systemFamily;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	private int getMemorySize(List<PhysicalMemory> mem) {
		int memory=0;
		Iterator<PhysicalMemory> itr = mem.iterator();
		while(itr.hasNext()) { 
			memory += itr.next().getCapacity();
		}
		return memory;
	}
	
	private long getDiskSize(List<HWDiskStore> disk) {
		long diskSize = 0;
		Iterator<HWDiskStore> itr = disk.iterator();
		while(itr.hasNext()) {
			diskSize += itr.next().getSize();
		}
		return diskSize;
	}
}

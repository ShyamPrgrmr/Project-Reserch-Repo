package com.test.pr;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;

import javax.management.MBeanServerConnection;

import org.json.simple.JSONObject;

import com.sun.management.OperatingSystemMXBean;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

public class SystemDetailsUpdaterThread implements Runnable{

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		while(true) {
			try {
				JSONObject obj = new JSONObject();
				SystemInfo si = new SystemInfo(); 
				OperatingSystem os = si.getOperatingSystem();
				HardwareAbstractionLayer hal = si.getHardware();
				
				int numberOfThread = os.getThreadCount();
				obj.put("numberOfThread", numberOfThread);
				obj.put("usedMemory", ((hal.getMemory().getTotal() - hal.getMemory().getAvailable()) / (1024*1024*1024))+" GB");
				obj.put("availableMemory",(hal.getMemory().getAvailable()/ (1024*1024*1024))+" GB");
				
				MBeanServerConnection mbsc = ManagementFactory.getPlatformMBeanServer();

				OperatingSystemMXBean osMBean;
				try {
					osMBean = ManagementFactory.newPlatformMXBeanProxy(mbsc, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
					long nanoBefore = System.nanoTime();
					long cpuBefore = osMBean.getProcessCpuTime();			
					long cpuAfter = osMBean.getProcessCpuTime();
					long nanoAfter = System.nanoTime();
					double percent;
					if (nanoAfter > nanoBefore)
					 percent = ((cpuAfter-cpuBefore)*100L)/(nanoAfter-nanoBefore);
					else percent = 0+1;
					
					obj.put("cpuLoad",(percent/1000)+" %");
				
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				long total = 0L;
				long available = 0L;
				long used = 0L;
				
				NumberFormat nf = NumberFormat.getNumberInstance();
				for (Path root : FileSystems.getDefault().getRootDirectories()) {
				    try {
				        FileStore store = Files.getFileStore(root);
				        total += store.getTotalSpace()/(1024*1024*1024);
				        available += store.getUsableSpace()/(1024*1024*1024);
				        used = total - available;
				        
				    } catch (IOException e) {
				        //System.out.println("error querying space: " + e.toString());
				    }
				}
				
				obj.put("usedDisk",used +" GB");
				obj.put("availableDisk",available+" GB");
				obj.put("totalDisk",total+" GB");
				
				System.out.println(obj);
				
				
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

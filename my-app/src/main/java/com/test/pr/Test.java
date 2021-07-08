package com.test.pr;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
	
	private static final int numThread = 2; 
	public static void main(String args[])  {
		
		ExecutorService es = Executors.newFixedThreadPool(numThread);
		
		CheckMainThread cmt = new CheckMainThread();
		SystemDetailsUpdaterThread sdut = new SystemDetailsUpdaterThread();
		
		es.execute(cmt);
		es.execute(sdut);
		
	}	
}
	
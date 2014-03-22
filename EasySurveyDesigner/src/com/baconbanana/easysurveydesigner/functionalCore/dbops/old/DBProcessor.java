package com.baconbanana.easysurveydesigner.functionalCore.dbops.old;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class DBProcessor extends Thread{
	
	private Semaphore hasFinished;	
	private static LinkedList<Thread> cmdList = new LinkedList<Thread>();
	
	public DBProcessor(Semaphore ne, Semaphore hf){
		hasFinished = hf;
	}
	
	//This class is the producer
	public synchronized void addStatement(Thread stmt){
		cmdList.add(stmt);
		}
		
	public synchronized Thread removeStatement(){
		return cmdList.removeFirst();
	}
	
	public void run(){
		while(true){
			if(!cmdList.isEmpty()){
				Thread current = removeStatement();
				current.start();
				hasFinished.release();
			}
		}
	}
}

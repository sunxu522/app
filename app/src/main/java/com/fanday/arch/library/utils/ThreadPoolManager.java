package com.fanday.arch.library.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ThreadPoolManager {
	private ExecutorService service;
	
	private ThreadPoolManager(){
		int num = Runtime.getRuntime().availableProcessors();
		service = Executors.newFixedThreadPool(num);
	}

	public static ThreadPoolManager getInstance(){
		return ThreadPoolManagerHolder.instance;
	}

	public void addTask(Runnable runnable){
		service.execute(runnable);
	}


	private static class ThreadPoolManagerHolder{
		private static final ThreadPoolManager instance= new ThreadPoolManager();
	}
}

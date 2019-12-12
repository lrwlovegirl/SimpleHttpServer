package com.lrw.threadpool.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.lrw.threadpool.ThreadPool;

public class DefaultThreadPool<Job extends Runnable > implements ThreadPool<Job> {
	//线程池最大限制数
	private static final int MAX_WORKER_NUMBERS= 10 ;
	//线程池的默认数量
	private static final int DEFAULT_WORKER_NUMBERS= 5 ;
	//线程池的最小数量
	private static final int MIN_WORKER_NUMBERS= 1;
	//工作列表，将会向里面插入工作
	private final LinkedList<Job> jobs = new LinkedList<Job>();
	//工作者列表
	private final List<Worker> workers = (List<Worker>) Collections.synchronizedList(new ArrayList<Worker>());
	//工作者线程数量
	private int workerNum = DEFAULT_WORKER_NUMBERS;
	//线程编号生成
	private AtomicLong threadNum = new AtomicLong();

	public DefaultThreadPool() {
		//initializeWorkers(DEFAULT_WORKER_NUMBERS);
	}
	public DefaultThreadPool(int num) {

		workerNum = num > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS :
			num <MIN_WORKER_NUMBERS ? MIN_WORKER_NUMBERS : num;
		initializeWorkers(workerNum);
	}

	@Override
	public void execute(Job job) {
		// TODO Auto-generated method stub
		if(job!= null) {
			synchronized (jobs) {
				jobs.addLast(job);	
				jobs.notifyAll();
			}
		}

	}

	@Override
	public void shutDown() {
		for (Worker worker : workers) {
          worker.shutDown();
		}
	}

	@Override
	public void addWorkers(int num) {
		synchronized(jobs) {
			 //限制新增的Worker数量不超过最大值
			 if(num+this.workerNum>MAX_WORKER_NUMBERS) {
				 num = MAX_WORKER_NUMBERS - this.workerNum;
			 }
			 initializeWorkers(num);
			 this.workerNum += num;
		 }

	}
	@Override
	public void removeWorker(int num) {
	  synchronized (jobs) {
	     if(num>=this.workerNum) {
	    	 throw new IllegalArgumentException("beyond workNum");
	     }	
	     int count = 0;
	     while(count<num) {
	    	 Worker worker= workers.get(count);
	    	 if(workers.remove(worker)) {
	    		 worker.shutDown();
	    		 count++;
	    	 }
	     }
	     this.workerNum -= count;
	}

	}

	@Override
	public int getJobSize() {
		return jobs.size();
	}

	//#################################################################
	private void initializeWorkers(int num) {
		for (int i = 0; i < num; i++) {
           Worker worker = new Worker();
           workers.add(worker);
           Thread thread = new Thread(worker,"ThreadPool-Worker-"+threadNum.incrementAndGet());
           thread.start();
		}
	}
	class Worker implements Runnable{
		//是否工作
		private volatile boolean running = true;

		@Override
		public void run() {
			while(running) {
				Job job = null;
				synchronized (jobs) {
					while(jobs.isEmpty()) {
						try {
							jobs.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							//感知到外部对WorkerThread的中断操作，返回
							Thread.currentThread().interrupt();
							return ;
						}
					}	
					job = jobs.removeFirst(); 
				}
				if(job!=null) {
					job.run();
				}
			}
		}//run 方法结束
		public void shutDown() {
			running = false;
		}
	}
}





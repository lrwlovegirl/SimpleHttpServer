package com.lrw.threadpool;

public interface ThreadPool<Job extends Runnable> {
    //ִ��һ��job�����job��Ҫʵ��Runnable�ӿ�
	void execute(Job job);
	//�ر��̳߳�
	void shutDown();
	//���ӹ������߳�
	void addWorkers(int num);
	//���ٹ������߳�
	void removeWorker(int num);
	//�õ����ڵȴ�ִ�е���������
	int getJobSize();
	
}

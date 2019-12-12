package com.lrw.connectionpool;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPool {
    //����һ��������ӵ�����˫�����
	private LinkedList<Connection> pool = new LinkedList<>();
	//���췽����ʼ��
	public ConnectionPool(int initialSize) {
		if(initialSize>0) {
			for (int i = 0; i < initialSize; i++) {
				pool.addLast(ConnectionDriver.createConnection());//addLast ��ָ����Ԫ��׷�ӵ����б��ĩβ
			}
		}
	}
	
	//�ͷ�����
	public void releaseConnection(Connection connection) {
		if(connection!=null) {
			synchronized (pool) {
			    //�����ͷź���Ҫ����֪ͨ�����������������ܹ���֪�����ӳ����Ѿ��黹��һ������
				pool.addLast(connection);
				pool.notifyAll();
			}
		}
	}
	//��mills���޷���ȡ�����ӽ��᷵��null;
	public Connection fetchConnection(long mills) throws InterruptedException {
		synchronized (pool) {
			//��ȫ��ʱ
			if(mills<=0) {
				while(pool.isEmpty()) {
					pool.wait();
				}
				return pool.removeFirst();
			}else {
				long future = System.currentTimeMillis()+mills;
		        long remaining = mills;
		        while (pool.isEmpty()&&remaining>0) {
		        	pool.wait();
		        	remaining = future-System.currentTimeMillis();
		        }
		        Connection result = null;
		        if(!pool.isEmpty()) {
		        	result = pool.removeFirst();
		        }
		        return result;
			}
		}
	}
	
	
	
	
}

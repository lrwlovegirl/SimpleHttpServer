package com.lrw.connectionpool;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPool {
    //创建一个存放连接的有序双向队列
	private LinkedList<Connection> pool = new LinkedList<>();
	//构造方法初始化
	public ConnectionPool(int initialSize) {
		if(initialSize>0) {
			for (int i = 0; i < initialSize; i++) {
				pool.addLast(ConnectionDriver.createConnection());//addLast 将指定的元素追加到此列表的末尾
			}
		}
	}
	
	//释放连接
	public void releaseConnection(Connection connection) {
		if(connection!=null) {
			synchronized (pool) {
			    //连接释放后需要进行通知，这样其他消费者能够感知到连接池中已经归还了一个连接
				pool.addLast(connection);
				pool.notifyAll();
			}
		}
	}
	//在mills内无法获取到连接将会返回null;
	public Connection fetchConnection(long mills) throws InterruptedException {
		synchronized (pool) {
			//完全超时
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

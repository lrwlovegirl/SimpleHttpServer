package com.lrw.connectionpool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPoolTest {

	static ConnectionPool pool = new ConnectionPool(10);
	//��֤����ConnectionRunner�ܹ�ͬʱ��ʼ
	static CountDownLatch start  = new CountDownLatch(1);

	//main�߳̽���ȴ�����ConnectionRunner��������ܼ���ִ��
	static CountDownLatch end;

	public static void main(String[] args) throws Exception {
		//�߳�����,�����޸��߳��������й۲�
		int threadCount = 10;
		end = new CountDownLatch(threadCount);

		int count =20;
		AtomicInteger got = new AtomicInteger();
		AtomicInteger notGot = new AtomicInteger();
		for (int i = 0; i < threadCount; i++) {
			Thread thread = new Thread(new ConnectionRunner(count, got, notGot),"ConnectionRunnerThread");
			thread.start();
		}
		start.countDown();
		end.await();
		System.out.println("total invoke: "+(threadCount*count));
		System.out.println("Got Connection: "+(got));
		System.out.println("not Got Connection: "+(notGot));
	}

	static class ConnectionRunner implements Runnable{
		int count;
		AtomicInteger got;
		AtomicInteger notGot;
		public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
			this.count = count;
			this.got = got;
			this.notGot = notGot;
		}
		@Override
		public void run() {
			try {
				start.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			while(count>0) {
				try {
					//���̳߳��л�ȡ���ӣ����1000ms���޷���ȡ�������᷵��null
					//�ֱ�ͳ�����ӹ�ȥ������got��δ��ȡ��������notGot
					Connection connection = pool.fetchConnection(1000);
					if(connection!= null) {
						try {
							connection.createStatement();
							connection.commit();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						finally {
							pool.releaseConnection(connection);
							got.incrementAndGet();
						}
					}else {
						notGot.incrementAndGet();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					count --;
				}
			}
			end.countDown();
		}

	}


}

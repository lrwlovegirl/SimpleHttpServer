package com.lrw.test;




public class VolatileTest {
	public static void main(String[] args) throws InterruptedException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("prepare.....");
				while(!Test.flag) {
                    //System.out.println("正在执行.....");
				}
				System.out.println("end.....");
			}
		}).start();

		Thread.sleep(2000);

		new Thread(new Runnable() {
			@Override
			public void run() {
				Test.flag=true;
			}
		}).start();

	}

//	public static void prepare() {
//		System.out.println("准备数据。。。。。");
//		flag =true;
//		System.out.println("准备结束。。。。。");
//	} 


}

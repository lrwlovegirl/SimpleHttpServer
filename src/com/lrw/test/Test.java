package com.lrw.test;

public class Test {
	public static boolean flag=false;
	
	
	public static void main(String[] args) {
		Integer a = new Integer(100);
		Integer b = new Integer(100);
		int c=100;
		System.out.println("a==b:"+(a==b));//false
		System.out.println("a==c:"+(a==c)); //true
		int d=10000;
		Integer e = new Integer(10000);
		Integer f = new Integer(10000);
		System.out.println("e==d:"+(e==d));//true
		System.out.println("e==f:"+(e==f));//false
		/**
		 * �ܽ᣺��1.8�У�Integer��������-127~128֮��Ļ���
		 *      ��int ��Integer�Ƚ�ʱ,Integer���Զ����䣬Ȼ����ȥ�Ƚ�
		 */
		
		
	 
	}
	

}

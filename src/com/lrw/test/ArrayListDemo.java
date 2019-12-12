package com.lrw.test;

import java.util.ArrayList;
import java.util.List;

public class ArrayListDemo {
	public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        long time1 = System.currentTimeMillis();
        for (int i = 1; i < 10000000; i++) {
            list.add(i);
        }
        long time2 = System.currentTimeMillis();
        System.out.println(time2 - time1);
        
	       /* List<Integer> list = new ArrayList<>(10000000);
	        long time1 = System.currentTimeMillis();
	        for (int i = 1; i < 10000000; i++) {
	            list.add(i);
	        }
	        long time2 = System.currentTimeMillis();
	        System.out.println(time2 - time1);*/
        
        
        
    }
}

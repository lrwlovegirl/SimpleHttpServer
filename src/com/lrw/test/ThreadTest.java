package com.lrw.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Ticket{
	private int number=30;
	public void sale() {
		Lock  lock = new ReentrantLock();
		lock.lock();
		try {
			if(number>0) {
				System.out.println(number--);
			} 
		} finally {
			lock.unlock();
		}
	}	
}
class Book{
	private int i;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (i != other.i)
			return false;
		return true;
	}
	
}


public class ThreadTest {
	public static void main(String[] args) {
		
		
		Book book= new Book();
		Book book1=new Book();
      Map map = new HashMap<>();
      map.put(book, "1");
      System.out.println(map.get(book1));
      
      
         
        
	} 
	

	public static List<String> getDayByMonth(int yearParam,int monthParam){
		List list = new ArrayList();
		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		aCalendar.set(yearParam,monthParam,1);
		int year = aCalendar.get(Calendar.YEAR);//年份
		int month = aCalendar.get(Calendar.MONTH) + 1;//月份
		int day = aCalendar.getActualMaximum(Calendar.DATE);
		for (int i = 1; i <= day; i++) {
			String aDate=null;
			if(month<10&&i<10){
				aDate = String.valueOf(year)+"-0"+month+"-0"+i;
			}
			if(month<10&&i>=10){
				aDate = String.valueOf(year)+"-0"+month+"-"+i;
			}
			if(month>=10&&i<10){
				aDate = String.valueOf(year)+"-"+month+"-0"+i;
			}
			if(month>=10&&i>=10){
				aDate = String.valueOf(year)+"-"+month+"-"+i;
			}

			list.add(aDate);
		}
		return list;
}


}

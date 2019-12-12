package com.lrw.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForEachDemo {
 public static void main(String[] args) {
	List<String> list = new ArrayList<String>();
	list.add("1");
	list.add("2");
	list.add("3");
	list.forEach(obj->System.out.println("测试： "+ obj));
	Map<String,String> map = new HashMap<String,String>();
	map.put("a", "1");
	map.put("b", "2");
	map.forEach((k,v)->System.out.println("map的lamda表达式方式: "+k
		   +"--->"+v
			));
}
}

package com.lrw.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamApi {
	//按每3个一组分割
	private static final Integer MAX_NUMBER = 4;

	/**
	* 计算切分次数
	*/
	private static Integer countStep(Integer size) {
		return (size + MAX_NUMBER - 1) / MAX_NUMBER;
	}

	public static void main(String[] args) {
	      List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
	      int limit = countStep(list.size());
	      //方法一：使用流遍历操作
	      List<List<Integer>> mglist = new ArrayList<>();
	      
	      Stream.iterate(0, n -> n + 1)
	      .limit(limit).forEach(i -> {
	          mglist.add(list.stream()
	         .skip(i * MAX_NUMBER).limit(MAX_NUMBER)
	         .collect(Collectors.toList()));
	      });

	      System.out.println(mglist);
	     
	}

}

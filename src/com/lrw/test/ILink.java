package com.lrw.test;

public interface  ILink{
	/**
	 * 返回索引位置的值
	 * @param index
	 * @return
	 */
	Object get(int index);
	/**
	 * 添加元素
	 * @param data
	 * @return
	 */
	boolean add(Object data);

	/**
	 * 查找是否存在
	 * @param data
	 * @return
	 */
	int contains(Object data);

	/**
	 * 删除元素
	 * @param data
	 * @return
	 */
	void remove(Object data);

	/**
	 * 修改元素
	 * @param index
	 * @param newData
	 * @return
	 */
	Object set(int index, Object newData);

	/**
	 * 链表清空
	 */
	void clear();

	/**
	 * 将链表转为数组
	 * @return
	 */
	Object[] toArray();

	/**
	 * 链表长度
	 * @return
	 */
	int size();
	/**
	 * 遍历链表
	 */
	void printLink();
} 

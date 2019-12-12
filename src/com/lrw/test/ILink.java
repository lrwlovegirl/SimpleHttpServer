package com.lrw.test;

public interface  ILink{
	/**
	 * ��������λ�õ�ֵ
	 * @param index
	 * @return
	 */
	Object get(int index);
	/**
	 * ���Ԫ��
	 * @param data
	 * @return
	 */
	boolean add(Object data);

	/**
	 * �����Ƿ����
	 * @param data
	 * @return
	 */
	int contains(Object data);

	/**
	 * ɾ��Ԫ��
	 * @param data
	 * @return
	 */
	void remove(Object data);

	/**
	 * �޸�Ԫ��
	 * @param index
	 * @param newData
	 * @return
	 */
	Object set(int index, Object newData);

	/**
	 * �������
	 */
	void clear();

	/**
	 * ������תΪ����
	 * @return
	 */
	Object[] toArray();

	/**
	 * ������
	 * @return
	 */
	int size();
	/**
	 * ��������
	 */
	void printLink();
} 

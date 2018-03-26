package com.pantou.cityChain.vo;

/*
 * 两个元素的元组，用于在一个方法里返回两种类型的值
 */
public class TwoTuple<A, B> {
	public final A first;
	public final B second;

	public TwoTuple(A a, B b) {
		this.first = a;
		this.second = b;
	}
}

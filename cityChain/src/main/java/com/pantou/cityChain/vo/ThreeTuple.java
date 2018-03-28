package com.pantou.cityChain.vo;

/**
 * 三元组
 */
public class ThreeTuple<A, B, C> extends TwoTuple<A, B> {

	public final C third;

	public ThreeTuple() {
		super();
		third = null;
	}

	public ThreeTuple(A a, B b, C c) {
		super(a, b);
		third = c;
	}
}

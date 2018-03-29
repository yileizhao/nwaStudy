package com.pantou.cityChain.vo;

/**
 * 四元组
 */
public class FourTuple<A, B, C, D> extends TwoTuple<A, B> {

	public final C third;
	public final D fourd;

	public FourTuple() {
		super();
		third = null;
		fourd = null;
	}

	public FourTuple(A a, B b, C c, D d) {
		super(a, b);
		third = c;
		fourd = d;
	}

	public C getThird() {
		return third;
	}

	public D getFourd() {
		return fourd;
	}

}

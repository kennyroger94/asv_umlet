package com.baselet.element.old.relation;

public class Qualifier extends RectangleExtra {

	public Qualifier(String s, int a, int b, int c, int d) {
		super(a, b, c, d);
		_string = s;
	}

	@Override
	public String getString(Object obj) {
		Qualifier other = (Qualifier) obj;
		return other._string;
	}

}

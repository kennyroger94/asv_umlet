package com.baselet.element.old.relation;

public class Port extends RectangleExtra {

	public Port(String s, int a, int b, int c, int d) {
		super(a, b, c, d);
		_string = s;
	}

	@Override
	public String getString(Object obj) {
		Multiplicity other = (Multiplicity) obj;
		return other._string;
	}

}

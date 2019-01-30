package com.baselet.element.old.relation;

public class Multiplicity extends RectangleExtra {

	public Multiplicity(String s, int a, int b, int c, int d) {
		super(a, b, c, d);
		_string = s;
	}

	@Override
	public String getString(Object obj) {
		Multiplicity other = (Multiplicity) obj;
		return other._string;
	}

}

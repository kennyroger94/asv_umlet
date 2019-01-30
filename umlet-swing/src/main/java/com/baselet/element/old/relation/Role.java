package com.baselet.element.old.relation;

public class Role extends RectangleExtra {

	public Role(String s, int a, int b, int c, int d) {
		super(a, b, c, d);
		_string = s;
	}

	@Override
	public String getString(Object obj) {
		Role other = (Role) obj;
		return other._string;
	}

}

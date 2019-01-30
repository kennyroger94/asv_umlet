package com.baselet.element.old.relation;

import com.baselet.control.basics.geom.Rectangle;

public class RectangleExtra extends Rectangle {

	String _string;

	public String getString() {
		return _string;
	}

	public String getString(Object obj) {
		RectangleExtra other = (RectangleExtra) obj;
		return other._string;
	}

	public RectangleExtra(int a, int b, int c, int d) {
		super(a, b, c, d);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (_string == null ? 0 : _string.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		String otherString = getString(obj);
		if (_string == null) {
			if (otherString != null) {
				return false;
			}
		}
		else if (!_string.equals(otherString)) {
			return false;
		}
		return true;
	}
}

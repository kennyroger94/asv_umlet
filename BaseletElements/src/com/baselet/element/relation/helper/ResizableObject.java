package com.baselet.element.relation.helper;

import com.baselet.control.geom.Rectangle;

public interface ResizableObject {
	public void setPointMinSize(int index, Rectangle rectFromCenter);

	public void resetPointMinSize(int index);
}
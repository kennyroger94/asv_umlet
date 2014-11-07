package com.baselet.elementnew.facet.common;

import com.baselet.control.FacetConstants;
import com.baselet.diagram.draw.DrawHandler;
import com.baselet.elementnew.facet.KeyValueFacet;
import com.baselet.elementnew.facet.PropertiesParserState;

public class LineWidthFacet extends KeyValueFacet {

	public static LineWidthFacet INSTANCE = new LineWidthFacet();

	private LineWidthFacet() {}

	@Override
	public KeyValue getKeyValue() {
		return new KeyValue("lw", false, FacetConstants.LINE_WIDTH_DEFAULT + "", "linewidth as decimal number (1.5, 2, ...)");
	}

	@Override
	public void handleValue(String value, DrawHandler drawer, PropertiesParserState state) {
		drawer.setLineWidth(Float.valueOf(value));
	}

	@Override
	public Priority getPriority() {
		return Priority.HIGHEST;
	}

}
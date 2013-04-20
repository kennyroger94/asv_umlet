package com.baselet.diagram;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.baselet.control.Main;
import com.baselet.diagram.draw.geom.Rectangle;
import com.baselet.element.GridElement;
import com.baselet.element.Group;
import com.baselet.element.Selector;
import com.umlet.custom.CustomElement;


public class SelectorOld implements Selector {

	private static SelectorOld currentSelector;// to determin what selector is active right now (to set that element blue)

	private GridElement dominantEntity;
	private Vector<GridElement> selectedEntities = new Vector<GridElement>();
	private DrawPanel panel;
	private boolean _selectorframeactive;
	private SelectorFrame _selectorframe;

	public Vector<GridElement> getSelectedEntities() {
		return selectedEntities;
	}

	@Override
	public boolean isSelected(GridElement ge) {
		return selectedEntities.contains(ge);
	}

	public SelectorOld(DrawPanel panel) {
		this.panel = panel;
		this._selectorframeactive = false;
		this._selectorframe = new SelectorFrame();
	}

	// AB: usually this is the first selected entity
	// If a group has been selected this entity can be set explicitly
	public GridElement getDominantEntity() {
		if ((dominantEntity == null) && !selectedEntities.isEmpty()) { return selectedEntities.firstElement(); }
		return dominantEntity;
	}

	// AB: if you set this entity, make sure the selectedEntities Vector contains a group entity that contains the dominantEntity.
	public void setDominantEntity(GridElement dominantEntity) {
		this.dominantEntity = dominantEntity;
	}

	public SelectorFrame getSelectorFrame() {
		return this._selectorframe;
	}

	public void setSelectorFrameActive(boolean active) {
		this._selectorframeactive = active;
		if (!active) this._selectorframe.reset();
	}

	public boolean isSelectorFrameActive() {
		return this._selectorframeactive;
	}

	public void deselectAll() {
		deselect(selectedEntities.toArray(new GridElement[selectedEntities.size()]));
	}

	// needed for custom element exchange
	public void singleSelectWithoutUpdatePropertyPanel(GridElement e) {
		deselectAllWithoutUpdatePropertyPanel();
		selectedEntities.add(e);
		e.setSelected(true);
		if (Main.getInstance().getGUI() != null) updateGUIInformation();
		Main.getInstance().setPropertyPanelToCustomElement(e);
	}

	public void deselectAllWithoutUpdatePropertyPanel() {
		for (GridElement e : this.selectedEntities)
			e.setSelected(false);
		dominantEntity = null;
		selectedEntities.clear();
	}

	@Override
	public void select(GridElement ... elements) {
		handleSelect(Arrays.asList(elements));
	}

	public void select(List<GridElement> entities) {
		handleSelect(entities);
	}

	public void selectAll() {
		handleSelect(panel.getAllEntitiesNotInGroup());
	}

	private void handleSelect(List<GridElement> entities) {
		for (GridElement e : entities) {
			if (selectedEntities.contains(e) || e.isPartOfGroup()) continue;
			selectedEntities.add(e);
			e.setSelected(true);
		}
		updateSelectorInformation();
	}

	@Override
	public void deselect(GridElement ... elements) {
		for (GridElement e : elements) {
			Iterator<GridElement> iter = selectedEntities.iterator();
			while (iter.hasNext()) {
				if (iter.next().equals(e)) {
					iter.remove();
					e.setSelected(false);
					updateSelectorInformation();
				}
			}
		}
	}

	private void updateGUIInformation() {
		Main.getInstance().getGUI().elementsSelected(selectedEntities.size());
		boolean ungroupEnabled = (selectedEntities.size() == 1) && (selectedEntities.get(0) instanceof Group);
		Main.getInstance().getGUI().setUngroupEnabled(ungroupEnabled);

		boolean customElementSelected = (selectedEntities.size() == 1) && (selectedEntities.get(0) instanceof CustomElement);
		Main.getInstance().getGUI().setCustomElementSelected(customElementSelected);
	}

	// updates the GUI with the current selector information (that includes the propertypanel
	public void updateSelectorInformation() {
		// update the current blue selected elements
		if (currentSelector == null) currentSelector = this;
		else if (currentSelector != this) {
			currentSelector.setElementsSelected(false);
			currentSelector = this;
			this.setElementsSelected(true);
		}

		// every time something is selected - update the current diagram to this element
		Main.getInstance().setCurrentDiagramHandler(this.panel.getHandler());
		if (Main.getInstance().getGUI() != null) {
			updateGUIInformation();
			if (selectedEntities.size() == 1) Main.getInstance().setPropertyPanelToGridElement(selectedEntities.elementAt(0));
			else Main.getInstance().setPropertyPanelToGridElement(null);
		}
	}

	private void setElementsSelected(boolean selected) {
		for (GridElement e : this.selectedEntities)
			e.setSelected(selected);
	}

	public void singleSelect(GridElement e) {
		this.deselectAll();
		this.select(e);
	}

	public void multiSelect(Rectangle rect) {
		for (GridElement e : panel.getAllEntities()) {
			if (e.isInRange(rect)) select(e);
		}
	}
}
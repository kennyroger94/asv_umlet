package com.umlet.gui.standalone;

import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;

import com.umlet.control.BrowserLauncher;
import com.umlet.control.Umlet;
import com.umlet.control.command.Copy;
import com.umlet.control.command.Cut;
import com.umlet.control.command.Paste;
import com.umlet.control.diagram.DiagramHandler;
import com.umlet.control.diagram.DrawPanel;
import com.umlet.control.diagram.Selector;
import com.umlet.gui.base.AboutPanel;
import com.umlet.gui.base.OptionPanel;
import com.umlet.gui.base.UmletGUI;

public class StandaloneMenuListener extends MenuListener {

	public StandaloneMenuListener() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);

		DiagramHandler handler = Umlet.getInstance().getDiagramHandler();
		UmletGUI gui = Umlet.getInstance().getGUI();
		DrawPanel currentdiagram = gui.getCurrentDiagram(); // current active diagram
		Selector selector = null;
		if (e.getSource() instanceof JMenuItem) {
			JMenuItem b = (JMenuItem) e.getSource();

			if (handler != null) // only execute if at least one diagram is available
			{
				selector = handler.getDrawPanel().getSelector();
				if ((b.getText() == "Undo") && (selector != null)) {
					selector.deselectAll();
					handler.getController().undo();
					if (gui instanceof StandaloneGUI) ((StandaloneGUI) gui).updateGreyedOutMenuItems(handler);
				}
				else if (b.getText() == "Redo") {
					handler.getController().redo();
					if (gui instanceof StandaloneGUI) ((StandaloneGUI) gui).updateGreyedOutMenuItems(handler);
				}
				else if (b.getText().equals("Copy")) {
					(new Copy()).execute(handler);
				}
				else if (b.getText().equals("Paste")) {
					handler.getController().executeCommand(new Paste());
				}
				else if (b.getText().equals("Cut")) {
					handler.getController().executeCommand(new Cut());
				}
				else if (b.getText().equals("Select All")) {
					selector.selectAll();
				}
			}

			if (currentdiagram != null) // only for diagram not for palette
			{
				selector = currentdiagram.getSelector();
				if (b.getText() == "Save") currentdiagram.getHandler().doSave();
				else if (b.getText() == "Save as...") currentdiagram.getHandler().doSaveAs("uxf");
				else if (b.getText() == "BMP...") currentdiagram.getHandler().doSaveAs("bmp");
				else if (b.getText() == "EPS...") currentdiagram.getHandler().doSaveAs("eps");
				else if (b.getText() == "GIF...") currentdiagram.getHandler().doSaveAs("gif");
				else if (b.getText() == "JPG...") currentdiagram.getHandler().doSaveAs("jpg");
				else if (b.getText() == "PDF...") currentdiagram.getHandler().doSaveAs("pdf");
				else if (b.getText() == "PNG...") currentdiagram.getHandler().doSaveAs("png");
				else if (b.getText() == "SVG...") currentdiagram.getHandler().doSaveAs("svg");
				else if (b.getText() == "Print...") currentdiagram.getHandler().doPrint();
			}

			if (b.getText() == "New") {
				Umlet.getInstance().doNew();
			}
			else if (b.getText() == "Open...") {
				Umlet.getInstance().doOpen();
			}
			else if (b.getText() == "Mail to...") {
				Umlet.getInstance().getGUI().setMailPanelEnabled(!Umlet.getInstance().getGUI().isMailPanelVisible());
			}
			else if (b.getText().equals("Options...")) {
				OptionPanel.getInstance().showOptionPanel();
			}
			else if (b.getText() == "Online Help...") {
				BrowserLauncher.openURL("http://www.umlet.com/faq.htm");
			}
			else if (b.getText() == "Online Sample Diagrams...") {
				BrowserLauncher.openURL("http://www.itmeyer.at/umlet/uml2/");
			}
			else if (b.getText() == "UMLet Homepage...") {
				BrowserLauncher.openURL("http://www.umlet.com/");
			}
			else if (b.getText() == "Rate UMLet at Eclipse Marketplace...") {
				BrowserLauncher.openURL("http://marketplace.eclipse.org/content/umlet-uml-tool-fast-uml-diagrams");
			}
			else if (b.getText() == "About UMLet") {
				AboutPanel.getInstance().setVisible();
			}
			else if (b.getText() == "Exit") {
				Umlet.getInstance().getGUI().closeWindow();
			}
			else if (b.getText().equals("Edit Current Palette")) {
				Umlet.getInstance().doOpen(Umlet.getInstance().getPalette().getFileHandler().getFullPathName());
			}
			else if (b.getText().equals("New...")) {
				if (Umlet.getInstance().getGUI().getCurrentCustomHandler().closeEntity()) {
					Umlet.getInstance().getGUI().setCustomPanelEnabled(true);
					Umlet.getInstance().getGUI().getCurrentCustomHandler().getPanel().setCustomElementIsNew(true);
					Umlet.getInstance().getGUI().getCurrentCustomHandler().newEntity();
				}
			}
			else if (b.getText().equals("Custom Elements Tutorial...")) {
				BrowserLauncher.openURL("http://www.umlet.com/ce/ce.htm");
			}
		}
	}

}
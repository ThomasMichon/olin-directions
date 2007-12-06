/*
 * PanZoomBehavior.java
 *
 * Created on December 4, 2007, 9:45 PM
 */

package edu.olin.maps.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;

/**
 *
 * @author tmichon
 */
public class PanZoomBehavior extends UIBehavior {
	
	Point2D panControl;
	
	private MouseListener mouseListener = new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			Point2D mouseWorld = blueprint.convertToWorld(e.getPoint());
			
			switch (e.getButton()) {
				case MouseEvent.BUTTON2:
					panControl = mouseWorld;
					break;
			}
		}
		
		public void mouseReleased(MouseEvent e) {
			switch (e.getButton()) {
				case MouseEvent.BUTTON2:
					panControl = null;
					break;
			}
		}
	};
	
	private MouseMotionListener mouseMotionListener = new MouseMotionAdapter() {
		public void mouseMoved(MouseEvent e) {
		}
		
		public void mouseDragged(MouseEvent e) {
			if ((e.getModifiersEx() & MouseEvent.BUTTON2_DOWN_MASK) > 0) {
				if (panControl != null) {
					blueprint.alignPoint(e.getPoint(), panControl);
				}
			}			
		}
	};
	
	private MouseWheelListener mouseWheelListener = new MouseWheelListener() {
		public void mouseWheelMoved(MouseWheelEvent e) {
			if (e.getUnitsToScroll() < 0) blueprint.setScale(blueprint.getScale() * 1.2, e.getPoint());
			else blueprint.setScale(blueprint.getScale() / 1.2, e.getPoint());
		}
	};
	
	protected void attachListeners() {
		blueprint.addMouseListener(mouseListener);
		blueprint.addMouseMotionListener(mouseMotionListener);
		blueprint.addMouseWheelListener(mouseWheelListener);
	}
	
}

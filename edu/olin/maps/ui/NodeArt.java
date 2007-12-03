/*
 * NodeArt.java
 *
 * Created on November 30, 2007, 1:33 PM
 */

package edu.olin.maps.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * 
 * @author Thomas Michon
 */
public class NodeArt {
	
	private Point2D position = new Point2D.Double();
	private double radius = 0.01D;
	
	/** Creates a new instance of NodeArt */
	public NodeArt() {
	}
	
	public Point2D getPosition() {
		return this.position;
	}
	
	public void setPosition(Point2D position) {
		this.position = position;
	}
	
	public void draw(Graphics2D gfx) {
		Ellipse2D ellipse = new Ellipse2D.Double(this.position.getX() - radius, this.position.getY() - radius, radius * 2.0, radius * 2.0);
		gfx.setColor(Color.BLACK);
		gfx.setStroke(null);
		gfx.fill(ellipse);
	}
}

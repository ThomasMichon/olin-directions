/*
 * UIBehavior.java
 *
 * Created on December 4, 2007, 9:47 PM
 */

package edu.olin.maps.ui;

/**
 *
 * @author tmichon
 */
public abstract class UIBehavior {
	
	protected Blueprint blueprint;
	
	/** Creates a new instance of UIBehavior */
	public UIBehavior() {
	}
	
	public void setBlueprint(Blueprint blueprint) {
		if (this.blueprint != null) detachListeners();
		this.blueprint = blueprint;
		if (this.blueprint != null) attachListeners();
	}
	
	public Blueprint getBlueprint() {
		return this.blueprint;
	}
	
	protected abstract void attachListeners();
	protected void detachListeners() {
		
	}
	
}

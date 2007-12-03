/*
 * Blueprint.java
 *
 * Created on November 29, 2007, 2:03 PM
 */

package edu.olin.maps.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JOptionPane;

/**
 *
 * @author  tmichon
 */
public class Blueprint extends javax.swing.JPanel {
	
	private double scale = 10.0D;
	private Point2D center = new Point2D.Double();
	
	private boolean drawGridEnabled = true;
	
	private AffineTransform currentTransform = new AffineTransform();
	
	private Point2D gridResolution = new Point2D.Double(1.0, 1.0);
	
	private KeyListener keyListener = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_EQUALS:
					setGridResolution(new Point2D.Double(gridResolution.getX() / 2.0, gridResolution.getY() / 2.0));
					break;
				case KeyEvent.VK_MINUS:
					setGridResolution(new Point2D.Double(gridResolution.getX() * 2.0, gridResolution.getY() * 2.0));
					break;
			}
		}
	};
	
	private MouseListener mouseListener = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
		}
	};
	
	private MouseWheelListener mouseWheelListener = new MouseWheelListener() {
		public void mouseWheelMoved(MouseWheelEvent e) {
			if (e.getUnitsToScroll() > 0) setScale(getScale() * 1.2);
			else setScale(getScale() / 1.2, e.getPoint());
		}
	};
	
	/** Creates new form Blueprint */
	public Blueprint() {
		initComponents();
		
		this.setFocusable(true);
		
		this.getInputMap().clear();
		
		this.addMouseListener(mouseListener);
		this.addKeyListener(keyListener);
		this.addMouseWheelListener(mouseWheelListener);
		
		//this.is
	}
	
	public boolean isDrawGridEnabled() {
		return this.drawGridEnabled;
	}
	
	public void setDrawGridEnabled(boolean drawGridEnabled) {
		this.drawGridEnabled = drawGridEnabled;
	}
	
	public Point2D getGridResolution() {
		return this.gridResolution;
	}
	
	public void setGridResolution(Point2D gridResolution) {
		this.gridResolution = gridResolution;
		this.repaint();
	}
	
	public Point2D getCenter() {
		return this.center;
	}
	
	public void setCenter(Point2D center) {
		this.center = center;
	}
	
	public double getScale() {
		return this.scale;
	}
	
	public void setScale(double scale) {
		this.scale = scale;
		rebuildTransform();
		repaint();
	}
	
	public void setScale(double scale, Point2D focus) {
		Point2D screen = new Point2D.Double();
		currentTransform.transform(focus, screen);
		this.scale = scale;
		rebuildTransform();
		try {
			currentTransform.inverseTransform(screen, screen);
		} catch (NoninvertibleTransformException ex) {
			ex.printStackTrace();
		}
		this.center = new Point2D.Double(screen.getX() - focus.getX(), screen.getY() - focus.getY());
		rebuildTransform();
		repaint();
	}
	
	private void rebuildTransform() {
		AffineTransform transform = new AffineTransform();
		transform.translate(getWidth() / 2, getHeight() / 2);
		transform.scale(scale, -scale);
		transform.translate(-center.getX(), -center.getY());
		currentTransform = transform;
	}
	
	private static final Color
		COLOR_OUTLINE = Color.black,
		
		COLOR_GRID = new Color(210, 210, 240),
		COLOR_GRID_AXIS = new Color(90, 90, 160),

		COLOR_HOVER = Color.orange,
		COLOR_SELECT = Color.cyan,
		
		COLOR_BOX = new Color(60, 60, 240),

		COLOR_AXIS = new Color(100, 200, 100);
	
	// Strokes
	private static final Stroke
		STROKE_GRID = new BasicStroke(1.0e-3F),
		STROKE_BOX_LINE = new BasicStroke(1.0e-2F),
		STROKE_BOX_RECT = new BasicStroke(1.0e-3F);
	
	public void paintComponent(Graphics g) {
		Graphics2D gfx = (Graphics2D) g.create();
		
		rebuildTransform();
		gfx.transform(currentTransform);
		
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		this.drawGrid(gfx);
	}
	
	private void drawGrid(Graphics2D gfx) {
		Rectangle2D rect = gfx.getClipBounds();
		
		gfx.setStroke(STROKE_GRID);
		gfx.setColor(COLOR_GRID);
		
		Line2D line;
		if (isDrawGridEnabled()) {
			for (double x = 0; x < rect.getX() + rect.getWidth(); x += gridResolution.getX()) {
				line = new Line2D.Double(x, rect.getY(), x, rect.getY() + rect.getHeight());
				gfx.draw(line);
			}
			for (double y = 0; y < rect.getY() + rect.getHeight(); y += gridResolution.getY()) {
				line = new Line2D.Double(rect.getX(), y, rect.getX() + rect.getWidth(), y);
				gfx.draw(line);
			}
		}

		gfx.setColor(COLOR_GRID_AXIS);

		line = new Line2D.Double(rect.getX(), 0, rect.getX() + rect.getWidth(), 0);
		gfx.draw(line);
		line = new Line2D.Double(0, rect.getY(), 0, rect.getY() + rect.getHeight());
		gfx.draw(line);
	}
	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(null);

        setMinimumSize(new java.awt.Dimension(100, 100));
        setPreferredSize(new java.awt.Dimension(320, 240));
    }// </editor-fold>//GEN-END:initComponents
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
	
}

/*
 * Blueprint.java
 *
 * Created on November 29, 2007, 2:03 PM
 */

package edu.olin.maps.ui;

import edu.olin.maps.graph.Path;
import edu.olin.maps.graph.Point3D;
import edu.olin.maps.graph.shortestpath.DijkstraSolver;
import edu.olin.maps.graph.shortestpath.SpaceGraphSolver;
import edu.olin.maps.graph.weighted.space.SpaceEdge;
import edu.olin.maps.graph.weighted.space.SpaceGraph;
import edu.olin.maps.graph.weighted.space.SpaceVertex;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author  tmichon
 */
public class Blueprint extends javax.swing.JPanel {
	
	private double scale = 5.0D;
	private Point2D center = new Point2D.Double();
	
	private boolean drawGridEnabled = true;
	
	private AffineTransform currentTransform = new AffineTransform();
	
	private Point2D gridResolution = new Point2D.Double(10.0, 10.0);
	
	private SpaceGraph graph = null;
	private Path path = null;
	
	SpaceVertex closestVertex = null, activeVertex = null;
	private Set<SpaceVertex> focusVertices = new HashSet<SpaceVertex>();
	
	private SpaceVertex origin = null, dest = null;
	
	private SpaceVertex from = null, to = null;
	
	private List<UIBehavior> behaviors = new ArrayList<UIBehavior>();
	
	private KeyListener keyListener = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			Point2D mouseWorld = convertToWorld(getMousePosition());
			closestVertex = getClosestVertex(mouseWorld);
			switch (e.getKeyCode()) {
				case KeyEvent.VK_EQUALS:
					setGridResolution(new Point2D.Double(gridResolution.getX() / 2.0, gridResolution.getY() / 2.0));
					break;
				case KeyEvent.VK_MINUS:
					setGridResolution(new Point2D.Double(gridResolution.getX() * 2.0, gridResolution.getY() * 2.0));
					break;
				case KeyEvent.VK_O:
					setOrigin(closestVertex);
					break;
				case KeyEvent.VK_D:
					setDestination(closestVertex);
					break;
				case KeyEvent.VK_G:
					setDrawGridEnabled(! isDrawGridEnabled());
					break;
					
				case KeyEvent.VK_B:
					SpaceVertex vertex = new SpaceVertex(new Point3D(mouseWorld.getX(), mouseWorld.getY(), 0));
					graph.addVertex(vertex);
					focusVertices.add(vertex);
					graph.recalculateEdgeWeights();
					break;
			}
			repaint();
		}
	};
	
	private MouseMotionListener mouseMotionListener = new MouseMotionAdapter() {
		public void mouseMoved(MouseEvent e) {
			Point2D mouseWorld = convertToWorld(e.getPoint());
			closestVertex = getClosestVertex(mouseWorld);
			if (origin != null) setDestination(closestVertex);
			repaint();
		}
		
		public void mouseDragged(MouseEvent e) {
			for (SpaceVertex vertex : focusVertices) {
			}
			repaint();
		}
	};
	
	private MouseListener mouseListener = new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			Point2D mouseWorld = convertToWorld(e.getPoint());
			closestVertex = getClosestVertex(mouseWorld);
			switch (e.getButton()) {
				case 1:
					activeVertex = closestVertex;
					break;
			}
			repaint();
		}
		
		public void mouseReleased(MouseEvent e) {
			Point2D mouseWorld = convertToWorld(e.getPoint());
			closestVertex = getClosestVertex(mouseWorld);
			switch(e.getButton()) {
				case 1:
					activeVertex = null;
					if (! e.isShiftDown()) focusVertices.clear();
					if (closestVertex != null) {
						focusVertices.add(closestVertex);
					}
					break;
			}
			repaint();
		}
		
		public void mouseExited(MouseEvent e) {
		}
		
		public void mouseClicked(MouseEvent e) {
			/*try {
				currentTransform.inverseTransform(e.getPoint(), center);
			} catch (NoninvertibleTransformException ex) {
				ex.printStackTrace();
			}
			rebuildTransform();
			repaint();*/
			//alignPoint(e.getPoint(), new Point2D.Double(0, 0));
		}
	};
		
	private ComponentListener componentListener = new ComponentAdapter() {
		public void componentResized(ComponentEvent e) {
			rebuildTransform();
		}
	};
	
	/** Creates new form Blueprint */
	public Blueprint() {
		initComponents();
		
		this.setFocusable(true);
		
		this.getInputMap().clear();
		
		addBehavior(new PanZoomBehavior());
		
		this.addMouseListener(mouseListener);
		this.addMouseMotionListener(mouseMotionListener);
		this.addKeyListener(keyListener);
		this.addComponentListener(componentListener);
	}
	
	public void addBehavior(UIBehavior behavior) {
		this.behaviors.add(behavior);
		behavior.setBlueprint(this);
	}
	
	public void removeBehavior(UIBehavior behavior) {
		this.behaviors.remove(behavior);
	}
	
	public SpaceGraph getGraph() {
		return this.graph;
	}
	
	public void setGraph(SpaceGraph graph) {
		this.graph = graph;
		repaint();
	}
	
	public Path getPath() {
		return this.path;
	}
	
	public void setPath(Path path) {
		this.path = path;
		repaint();
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
	}
	
	public Point2D getCenter() {
		return this.center;
	}
	
	public void setCenter(Point2D center) {
		this.center = center;
		rebuildTransform();
	}
	
	public double getScale() {
		return this.scale;
	}
	
	public void setScale(double scale) {
		this.scale = scale;
		rebuildTransform();
		repaint();
	}
	
	public void setScale(double scale, Point2D screen) {
		Point2D old = new Point2D.Double();
		try {
			currentTransform.inverseTransform(screen, old);
		} catch (NoninvertibleTransformException ex) {
			ex.printStackTrace();
		}
		//System.out.println("Old: " + old);
		this.scale = scale;
		rebuildTransform();
		alignPoint(screen, old);
		repaint();
	}
	
	public void setOrigin(SpaceVertex vertex) {
		origin = vertex;
		calculatePath();
	}
	
	public void setDestination(SpaceVertex vertex) {
		dest = vertex;
		calculatePath();
	}
	
	private void calculatePath() {
		if (origin == null || dest == null) {
			path = null;
			return;
		};
		SpaceGraphSolver s = new DijkstraSolver(graph);
		path = s.getShortestPath(origin, dest);
	}
	
	public AffineTransform getCurrentTransform() {
		return this.currentTransform;
	}
	
	public Point2D convertToWorld(Point2D screen) {
		if (screen == null) return null;
		Point2D point = new Point2D.Double();
		try {
			currentTransform.inverseTransform(screen, point);
		} catch (NoninvertibleTransformException ex) {
			ex.printStackTrace();
			return null;
		}
		return point;
	}
	
	public Point2D convertToScreen(Point2D world) {
		if (world == null) return null;
		Point2D point = new Point2D.Double();
		currentTransform.transform(world, point);
		return point;
	}
	
	public void alignPoint(Point2D screen, Point2D world) {
		Point2D current = new Point2D.Double();
		try {
			currentTransform.inverseTransform(screen, current);
		} catch (NoninvertibleTransformException ex) {
			ex.printStackTrace();
		}
		/*System.out.println("Align:");
		System.out.println("	Current: " + current);
		System.out.println("	World: " + world);*/
		this.center = new Point2D.Double(center.getX() + world.getX() - current.getX(), center.getY() + world.getY() - current.getY());
		rebuildTransform();
	}
	
	private void rebuildTransform() {
		AffineTransform transform = new AffineTransform();
		transform.translate(getWidth() / 2, getHeight() / 2);
		transform.scale(scale, -scale);
		//transform.shear(-0.5, 0.5);
		transform.translate(-center.getX(), -center.getY());
		currentTransform = transform;
	}
	
	private SpaceVertex getClosestVertex(Point2D world, double maximum) {
		double lowest = Double.POSITIVE_INFINITY;
		SpaceVertex winner = null;
		
		for (SpaceVertex vertex : graph.getVertices()) {
			double distance = world.distance(convertVertex(vertex));
			if (distance < lowest && distance <= maximum) {
				winner = vertex;
				lowest = distance;
			}
		}
		
		return winner;
	}
	
	private SpaceVertex getClosestVertex(Point2D world) {
		return getClosestVertex(world, 20 / scale);
	}
	
	private Point2D convertVertex(SpaceVertex vertex) {
		return new Point2D.Double(vertex.getLocation().getX(), vertex.getLocation().getY());
	}
	
	private double roundToGrid(double v, double res) {
		return Math.floor(v / res) * res;
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
		STROKE_GRID = new BasicStroke(0.1F),
		STROKE_BOX_LINE = new BasicStroke(1.0e-2F),
		STROKE_BOX_RECT = new BasicStroke(1.0e-3F);
	
	public void paintComponent(Graphics g) {
		Graphics2D screen = (Graphics2D) g.create();
		Graphics2D world = (Graphics2D) g.create();
		
		screen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		world.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		world.transform(currentTransform);
		
		Shape clip = new Rectangle2D.Double(0, 0, getWidth(), getHeight()), inverseClip = null;
		screen.setClip(clip);
		try {
			inverseClip = currentTransform.createInverse().createTransformedShape(clip);
		} catch (NoninvertibleTransformException ex) {
			ex.printStackTrace();
		}
		screen.setColor(Color.white);
		screen.fillRect(0, 0, getWidth(), getHeight());
		
		world.setClip(inverseClip);
		this.drawGrid(screen, world);
		
		this.drawGraph(screen, world);
	}
	
	private void drawGraph(Graphics2D screen, Graphics2D world) {
		if (graph == null) return;
		
		Line2D line;
		for (SpaceEdge edge : graph.getEdges()) {
			Point2D origin = convertVertex(edge.getOrigin()), dest = convertVertex(edge.getDest());
			line = new Line2D.Double(convertVertex(edge.getOrigin()), convertVertex(edge.getDest()));
			if (! world.getClipBounds().intersectsLine(line)) continue;
			screen.setColor(Color.BLACK);
			screen.setStroke(new BasicStroke(1F));
			screen.draw(currentTransform.createTransformedShape(line));
		}
		
		Ellipse2D ellipse;
		for (SpaceVertex vertex : graph.getVertices()) {
			Point2D focus = convertVertex(vertex);
			double radius = vertex.getLocation().getZ() / 100.0 * 0.5 + 0.5;
			ellipse = new Ellipse2D.Double(focus.getX() - radius, focus.getY() - radius, 2 * radius, 2 * radius);
			Color c = Color.BLUE;
			if (vertex == closestVertex) c = Color.ORANGE;
			else if (focusVertices.contains(vertex)) c = Color.GREEN;
			world.setColor(c);
			world.fill(ellipse);
		}
		
		drawPath(screen, world);
		drawPathTerminal(origin, screen, world);
		drawPathTerminal(dest, screen, world);
	}
	
	private void drawPath(Graphics2D screen, Graphics2D world) {
		if (path == null) return;
		
		Line2D line;
		for (SpaceEdge edge : graph.getEdges()) {
			if (! path.getEdges().contains(edge)) continue;
			Point2D origin = convertVertex(edge.getOrigin()), dest = convertVertex(edge.getDest());
			line = new Line2D.Double(convertVertex(edge.getOrigin()), convertVertex(edge.getDest()));
			if (! world.getClipBounds().intersectsLine(line)) continue;
			screen.setColor(Color.GREEN);
			screen.setStroke(new BasicStroke(3F));
			screen.draw(currentTransform.createTransformedShape(line));			
		}
	}
	
	private void drawPathTerminal(SpaceVertex vertex, Graphics2D screen, Graphics2D world) {
		if (vertex == null) return;
		Point2D focus = convertToScreen(convertVertex(vertex));
		Ellipse2D ellipse = new Ellipse2D.Double(focus.getX() - 7, focus.getY() - 7, 14, 14);
		screen.setColor(Color.BLUE);
		screen.setStroke(new BasicStroke(1F));
		screen.draw(ellipse);
	}
	
	private void drawGrid(Graphics2D screen, Graphics2D world) {
		Rectangle2D rect = world.getClipBounds();
		
		screen.setStroke(STROKE_GRID);
		screen.setColor(COLOR_GRID);
		
		Line2D line;
		if (isDrawGridEnabled()) {
			for (double x = roundToGrid(rect.getX(), gridResolution.getX()); x < rect.getX() + rect.getWidth(); x += gridResolution.getX()) {
				line = new Line2D.Double(x, rect.getY(), x, rect.getY() + rect.getHeight());
				screen.draw(currentTransform.createTransformedShape(line));
			}
			for (double y = roundToGrid(rect.getY(), gridResolution.getY()); y < rect.getY() + rect.getHeight(); y += gridResolution.getY()) {
				line = new Line2D.Double(rect.getX(), y, rect.getX() + rect.getWidth(), y);
				screen.draw(currentTransform.createTransformedShape(line));
			}
		}

		screen.setColor(COLOR_GRID_AXIS);
		
		line = new Line2D.Double(rect.getX(), 0, rect.getX() + rect.getWidth(), 0);
		screen.draw(currentTransform.createTransformedShape(line));
		line = new Line2D.Double(0, rect.getY(), 0, rect.getY() + rect.getHeight());
		screen.draw(currentTransform.createTransformedShape(line));
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
        setPreferredSize(new java.awt.Dimension(640, 480));
    }// </editor-fold>//GEN-END:initComponents
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
	
}

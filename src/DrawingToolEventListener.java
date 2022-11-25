import Shapes.*;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class DrawingToolEventListener implements GLEventListener, MouseListener, MouseMotionListener {

	private GL2 gl;
	Painting painting = new Painting();
	boolean done = true;
	boolean stop = false;
	boolean okpoly = true;
	Point orig = null;
	Point prev = null;
	String shape = "";
	ArrayList<Ellipse> brushPointsList = new ArrayList<Ellipse>();
	ArrayList<Line> polygonPoints = new ArrayList<Line>();
	int brushRadius = 20;

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public Painting getPainting() {
		return painting;
	}

	public void setPainting(Painting painting) {
		this.painting = painting;
	}

	@Override
	public void init(GLAutoDrawable glAutoDrawable) {
		gl = glAutoDrawable.getGL().getGL2();
		// gl.glClearColor(1, 1, 1, 1); // white background
		gl.glClearColor(0, 0, 0, 0); // black background

	}

	@Override
	public void dispose(GLAutoDrawable glAutoDrawable) {

	}

	@Override
	public void display(GLAutoDrawable glAutoDrawable) {
		gl = glAutoDrawable.getGL().getGL2();

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		if (!painting.getList().isEmpty()) {
			for (int i = 0; i < painting.getList().size(); i++) {
				if ((painting.getList().get(i)).returnShapeType().compareTo("Polygon") == 0) {
					Polygon poly = (Polygon) painting.getList().get(i);
					poly.draw();
				} else if ((painting.getList().get(i)).returnShapeType().compareTo("Ellipse") == 0) {
					Ellipse ellipse = (Ellipse) painting.getList().get(i);
					ellipse.draw();
				} else if ((painting.getList().get(i)).returnShapeType().compareTo("BrushPoints") == 0) {
					BrushPoints brush = (BrushPoints) painting.getList().get(i);
					brush.draw();
				} else if ((painting.getList().get(i)).returnShapeType().compareTo("Line") == 0) {
					Line line = (Line) painting.getList().get(i);
					line.draw2();
				}
			}
		}
	}

	@Override
	public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
		gl = glAutoDrawable.getGL().getGL2();

		int maxWidth = 600; // canvas max width
		int maxHeight = 700; // canvas max height

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(0, maxWidth, 0, maxHeight, -1, 1);
		// gl.glOrtho(-450, 450, -350, 350, -1 , 1);
		// gl.glViewport(0, 0, maxWidth * 2 - 1, maxHeight * 2 - 1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		int[] rgb = MainFrame.mf.getRGB();
		Color color = new Color(rgb[0], rgb[1], rgb[2]);

		if (done && shape.equals("Ellipse")) {
			Ellipse ellipse = new Ellipse(e.getX(), 700 - e.getY(), brushRadius, brushRadius, color, gl);
			painting.getList().add(ellipse);
			done = false;
			shape = "";
		} else if (shape.equals("Polygon")) {
			done = false;
			if (!stop) {
				if (okpoly == true) {
					Line line = new Line(e.getX(), 700 - e.getY(), e.getX() + 1, 700 - e.getY() + 1, color, gl);
					getPainting().getList().add(line);
					polygonPoints.add(line);
					orig = new Point();
					orig.x = e.getX();
					orig.y = 700 - e.getY();
					okpoly = false;
				} else {
					Line line = new Line(prev.x, prev.y, e.getX() + 1, 700 - e.getY() + 1, color, gl);
					getPainting().getList().add(line);
					polygonPoints.add(line);
				}
			}
		} else if (done && shape.equals("Line")) {
			Line line = new Line(e.getX(), 700 - e.getY(), e.getX() + 1, 700 - e.getY() + 1, color, gl);
			painting.getList().add(line);
			done = false;
			shape = "";
		} else if (done && shape.equals("BrushPoints")) {
			// brushPointsList.clear();
			brushPointsList = new ArrayList<Ellipse>();

			BrushPoints brushPoints = new BrushPoints(brushPointsList, brushRadius, gl, color);
			painting.getList().add(brushPoints);

			Ellipse circle = new Ellipse(e.getX(), 700 - e.getY(), brushRadius, brushRadius, color, gl);
			brushPointsList.add(circle);
			done = false;
			shape = "BrushPointsCircle";
		} else {
			done = true;
			if (shape.equals("BrushPointsCircle")) {
				BrushPoints brushPoints = (BrushPoints) painting.getList().get(painting.getList().size() - 1);
				brushPoints.setBrushPointsList(brushPointsList);
			}
			shape = "";
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		int[] rgb = MainFrame.mf.getRGB();
		Color color = new Color(rgb[0], rgb[1], rgb[2]);

		if (!done && !painting.getList().isEmpty()) {
			if (painting.getList().get(painting.getList().size() - 1).returnShapeType().equals("Ellipse")) {
				Ellipse el = (Ellipse) painting.getList().get(painting.getList().size() - 1);
				el.setxCenter(e.getX());
				el.setyCenter(700 - e.getY());
			} else if (shape == "Polygon") {
				if (okpoly == false) {
					Line l = (Line) painting.getList().get(painting.getList().size() - 1);
					l = new Line(l.getX1(), l.getY1(), e.getX(), 700 - e.getY(), color, gl);
					painting.getList().set(painting.getList().size() - 1, l);
					Ellipse check = new Ellipse(orig.x, orig.y, 5, 5, color, gl);
					if (check.IsInside(l.getX2(), l.getY2()) && polygonPoints.size() >= 3) {
						int x[] = new int[polygonPoints.size()];
						int y[] = new int[polygonPoints.size()];

						for (int i = 0; i < polygonPoints.size(); i++) {
							x[i] = polygonPoints.get(i).getX1();
							y[i] = polygonPoints.get(i).getY1();
						}
						Polygon poly = new Polygon(x, y, polygonPoints.size(), color, gl);
						painting.getList().add(poly);
						okpoly = true;
						orig = null;
						prev = null;
						shape = "";
						polygonPoints.clear();
					} else {
						prev = new Point();
						prev.x = e.getX();
						prev.y = 700 - e.getY();
					}
				}
			} else if (painting.getList().get(painting.getList().size() - 1).returnShapeType().equals("BrushPoints")) {
				Ellipse circle = new Ellipse(e.getX(), 700 - e.getY(), brushRadius, brushRadius, color, gl);
				brushPointsList.add(circle);
			} else if (painting.getList().get(painting.getList().size() - 1).returnShapeType().equals("Line")) {
				Line line = (Line) painting.getList().get(painting.getList().size() - 1);
				line = new Line(line.getX1(), line.getY1(), e.getX(), 700 - e.getY(), color, gl);
				painting.getList().set(painting.getList().size() - 1, line);
			}
		}
	}

	public void clearCanvas() {
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glClearColor(0, 0, 0, 0); // background

		painting.getList().clear();
		polygonPoints.clear();
		// brushPointsList.clear();
	}
}
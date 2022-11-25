//package EventListeners;
//
//import com.jogamp.opengl.GL2;
//import com.jogamp.opengl.GLAutoDrawable;
//import com.jogamp.opengl.GLEventListener;
//
//import Shapes.Ellipse;
//import Shapes.Line;
//import Shapes.Painting;
//import Shapes.Polygon;
//
//import java.awt.Color;
//import java.awt.Point;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
//import java.util.ArrayList;
//
//public class DrawingToolEventListener implements GLEventListener, MouseListener, MouseMotionListener {
//
//	private GL2 gl;
//	Painting painting = new Painting();
//	boolean done = true;
//	boolean okpoly = true;
//	boolean stop = false;
//	int mx = 0;
//	int my = 0;
//	Point prev = null;
//	Point orig = null;
//	ArrayList<Line> polylines = new ArrayList<>();
//	public String shape = "";
//
//	public String getshape() {
//		return shape;
//	}
//
//	public void setshape(String shape) {
//		shape = shape;
//	}
//
//	public boolean isDone() {
//		return done;
//	}
//
//	public void setDone(boolean done) {
//		this.done = done;
//	}
//
//	public Painting getPainting() {
//		return painting;
//	}
//
//	public void setPainting(Painting painting) {
//		this.painting = painting;
//	}
//
//	@Override
//	public void init(GLAutoDrawable glAutoDrawable) {
//		gl = glAutoDrawable.getGL().getGL2();
//		// gl.glClearColor(1, 1, 1, 1); // white background
//		gl.glClearColor(0, 0, 0, 0); // black background
//
//	}
//
//	@Override
//	public void dispose(GLAutoDrawable glAutoDrawable) {
//
//	}
//
//	@Override
//	public void display(GLAutoDrawable glAutoDrawable) {
//		gl = glAutoDrawable.getGL().getGL2();
//		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
//		if (!painting.getList().isEmpty()) {
//			for (int i = 0; i < painting.getList().size(); i++) {
//				if (painting.getList().get(i).getClass().getName().compareTo("Shapes.Polygon") == 0) {
//					Shapes.Polygon poly = (Shapes.Polygon) painting.getList().get(i);
//					poly.draw();
//				} else if (painting.getList().get(i).getClass().getName().compareTo("Shapes.Ellipse") == 0) {
//					Ellipse el = (Ellipse) painting.getList().get(i);
//					el.draw();
//				} else if (painting.getList().get(i).getClass().getName().compareTo("Shapes.Line") == 0) {
//					Line el = (Line) painting.getList().get(i);
//					el.draw();
//				}
//			}
//		}
//	}
//
//	@Override
//	public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
//		gl = glAutoDrawable.getGL().getGL2();
//
//		int maxWidth = 600; // canvas max width
//		int maxHeight = 700; // canvas max height
//
//		gl.glMatrixMode(GL2.GL_PROJECTION);
//		gl.glLoadIdentity();
//		gl.glOrtho(0, maxWidth, 0, maxHeight, -1, 1);
//		// gl.glOrtho(-450, 450, -350, 350, -1 , 1);
//		// gl.glViewport(0, 0, maxWidth * 2 - 1, maxHeight * 2 - 1);
//		gl.glMatrixMode(GL2.GL_MODELVIEW);
//	}
//
//	@Override
//	public void mouseClicked(MouseEvent e) {
//
//	}
//
//	@Override
//	public void mousePressed(MouseEvent e) {
//		if (done == true) {
//			if (shape == "Ellipse") {
//				Ellipse el = new Ellipse(e.getX(), 700 - e.getY(), 50, 50, Color.WHITE, gl);
//				getPainting().getList().add(el);
//				done = false;
//			}
//		} else {
//			done = true;
//		}
//		if (!stop) {
//			if (okpoly == true) {
//				if (shape == "Polygon") {
//					Line line = new Line(e.getX(), 700 - e.getY(), e.getX() + 1, 700 - e.getY() + 1, Color.WHITE, gl);
//					getPainting().getList().add(line);
//					polylines.add(line);
//					orig = new Point();
//					orig.x = e.getX();
//					orig.y = 700 - e.getY();
//					okpoly = false;
//				}
//			} else {
//				Line line = new Line(prev.x, prev.y, e.getX() + 1, 700 - e.getY() + 1, Color.WHITE, gl);
//				getPainting().getList().add(line);
//				polylines.add(line);
//			}
//		} 
//	}
//
//	@Override
//	public void mouseReleased(MouseEvent e) {
//
//	}
//
//	@Override
//	public void mouseEntered(MouseEvent e) {
//
//	}
//
//	@Override
//	public void mouseExited(MouseEvent e) {
//
//	}
//
//	@Override
//	public void mouseDragged(MouseEvent e) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void mouseMoved(MouseEvent e) {
//		if (done == false) {
//			if (!painting.getList().isEmpty()) {
//				if (painting.getList().get(painting.getList().size() - 1).returnShapeType() == "Ellipse") {
//					Ellipse el = (Ellipse) painting.getList().get(painting.getList().size() - 1);
//					el.setxCenter(e.getX());
//					el.setyCenter(700 - e.getY());
//				}
//			}
//		}
//		if (okpoly == false) {
//			if (painting.getList().get(painting.getList().size() - 1).returnShapeType() == "Line") {
//				Line l = (Line) painting.getList().get(painting.getList().size() - 1);
//				l = new Line(l.getX1(), l.getY1(), e.getX(), 700 - e.getY(), Color.WHITE, gl);
//				painting.getList().set(painting.getList().size() - 1, l);
//				Ellipse check = new Ellipse(orig.x, orig.y, 5,5, Color.WHITE, gl);
//				if (check.IsInside(l.getX2(), l.getY2())&&polylines.size()>=3) {
//					int x[] = new int[polylines.size()];
//					int y[] = new int[polylines.size()];
//
//					for (int i = 0; i < polylines.size() ; i++) {
//						x[i] = polylines.get(i).getX1();
//						y[i] = polylines.get(i).getY1();
//					}
//					Polygon poly = new Polygon(x, y, polylines.size(), Color.WHITE, gl);
//					painting.getList().add(poly);
//					okpoly = true;
//					stop=false;
//					prev=null;
//					orig=null;
//					polylines.clear();
//				} else {
//					prev = new Point();
//					prev.x = e.getX();
//					prev.y = 700 - e.getY();
//				}
//			}
//		}
//	}
//}
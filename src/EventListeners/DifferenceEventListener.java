package EventListeners;

import Shapes.BrushPoints;
import Shapes.Ellipse;
import Shapes.Painting;
import Shapes.Polygon;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import java.awt.*;
import java.util.Collections;
import java.util.Random;

public class DifferenceEventListener implements GLEventListener {

	private GL2 gl;
	Painting painting = new Painting();
	int numberOfPieces = 5;
	boolean init = false;
	Painting origpainting = new Painting();

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
		if (!init) {
			painting = initializeDifferent(numberOfPieces);
		}
		for (int i = 0; i < painting.getList().size(); i++) {
			if (painting.getList().get(i).returnShapeType().equals("Polygon")) {
				Polygon poly = (Polygon) painting.getList().get(i);
				poly.draw();
			} else if (painting.getList().get(i).returnShapeType().equals("Ellipse")) {
				Ellipse el = (Ellipse) painting.getList().get(i);
				el.draw();
			} else if (painting.getList().get(i).returnShapeType().equals("BrushPoints")) {
				BrushPoints brsh = (BrushPoints) painting.getList().get(i);
				brsh.draw();
			}
		}

		for (int i = 0; i < painting.getList().size(); i++) {
			if (painting.getList().get(i).isClicked()) {
				if (painting.getList().get(i).returnShapeType().equals("Polygon")) {
					Polygon poly = (Polygon) painting.getList().get(i);
					int length = 100;
					if (origpainting.getList().get(i).returnShapeType().equals("Ellipse")) {
						painting.getList().set(i, origpainting.getList().get(i));
					} else
						poly.setColor(origpainting.getList().get(i).getColor());
					// drawX(gl, ((maxX - minX) / 2), ((maxY - minY) / 2), length);

				} else if (painting.getList().get(i).returnShapeType().equals("Ellipse")) {
					Ellipse el = (Ellipse) painting.getList().get(i);
					el.setColor(origpainting.getList().get(i).getColor());
					int length = 100;
					// drawX(gl, el.getxCenter(), el.getyCenter(), length);

				}
			}
		}
	}

	public void drawX(GL2 gl, double x, double y, int length) {
		gl.glColor3f(1, 0, 0);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex2d(x, y);
		gl.glVertex2d(x + length, y + length);
		gl.glVertex2d(x, y);
		gl.glVertex2d(x - length, y - length);
		gl.glVertex2d(x, y);
		gl.glVertex2d(x + length, y - length);
		gl.glVertex2d(x, y);
		gl.glVertex2d(x - length, y + length);
		gl.glEnd();
	}

	public Painting initializeDifferent(int n) {
		Painting newP = new Painting();
		Random r = new Random();
		if (!painting.getList().isEmpty()) {
			for (int i = 0; i < painting.getList().size(); i++) {
				if (painting.getList().get(i).returnShapeType().equals("Polygon")) {
					Polygon poly = (Polygon) painting.getList().get(i);
					poly = new Polygon(poly.getX(), poly.getY(), poly.getCx(), poly.getCy(), poly.getN(),
							poly.getColor(), poly.getGl());
					newP.getList().add(poly);
				} else if (painting.getList().get(i).returnShapeType().equals("Ellipse")) {
					Ellipse el = (Ellipse) painting.getList().get(i);
					if (el.getXradius() <= 1 && el.getYradius() <= 1) {
						Polygon poly = new Polygon(el.getxCenter(), el.getyCenter(), 100, 100, 3, el.getColor(),
								el.getGl());
						newP.getList().add(poly);
					} else {
						el = new Ellipse(el.getxCenter(), el.getyCenter(), el.getXradius(), el.getYradius(),
								el.getColor(), el.getGl());
						newP.getList().add(el);
					}
				}
			}
			Collections.shuffle(newP.getList());
			for (int i = 0; i < newP.getList().size(); i++) {
				if (newP.getList().get(i).returnShapeType().equals("Polygon")) {
					Polygon poly = (Polygon) newP.getList().get(i);
					poly = new Polygon(poly.getX(), poly.getY(), poly.getCx(), poly.getCy(), poly.getN(),
							poly.getColor(), poly.getGl());
					origpainting.getList().add(poly);
				} else if (newP.getList().get(i).returnShapeType().equals("Ellipse")) {
					Ellipse el = (Ellipse) newP.getList().get(i);
					if (el.getXradius() <= 1 && el.getYradius() <= 1) {
						Polygon poly = new Polygon(el.getxCenter(), el.getyCenter(), 100, 100, 3, el.getColor(),
								el.getGl());
						origpainting.getList().add(poly);
					} else {
						el = new Ellipse(el.getxCenter(), el.getyCenter(), el.getXradius(), el.getYradius(),
								el.getColor(), el.getGl());
						origpainting.getList().add(el);
					}
				}
			}
//			while (newP.getList().size() < n+5) {
//				newP.getList().add(new Ellipse(r.nextInt(600), r.nextInt(700), 100, 100, Color.PINK, gl));
//			}
			for (int i = 0; i < newP.getList().size(); i++) {
				if (newP.getList().get(i).returnShapeType().equals("Ellipse")) {
					Ellipse el = (Ellipse) newP.getList().get(i);
					int rand = r.nextInt(6);
					if (rand == 0 || rand == 2 || rand == 1) {
						rand = 3;
					}
					Polygon poly = new Polygon(el.getxCenter(), el.getyCenter(), el.getXradius(), el.getYradius(), rand,
							el.getColor(), el.getGl());
					newP.getList().set(i, poly);
				}
				newP.getList().get(i).setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
				newP.getList().get(i).setDifferent(true);
			}
		}
		init = true;
		return newP;
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

	public int getN() {
		return numberOfPieces;
	}

	public void setN(int n) {
		this.numberOfPieces = n;
	}

	public Painting getPainting() {
		return painting;
	}

	public void setPainting(Painting painting) {
		this.painting = painting;
	}
}
package Shapes;

import com.jogamp.opengl.GL2;

import java.awt.*;
import java.awt.geom.Point2D;
import java.nio.ByteBuffer;

public class Polygon extends Shapes {

	private int[] x;
	private int[] y;
	private double cx;
	private double cy;
	private int width;
	private int height;
	private int n;
	private Color color;
	private GL2 gl;
	// int count;

	public Polygon() {
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Polygon(int[] x, int[] y, int n, Color color, GL2 gl) {
		this.x = x;
		this.y = y;
		this.n = n;
		this.color = color;
		this.gl = gl;
	}

	public Polygon(int cx, int cy, int width, int height, int n, Color color, GL2 gl) {
		this.cx = cx;
		this.cy = cy;
		if (n == 6) {
			int[] x = { cx - width / 4, cx + width / 4, cx + width / 2, cx + width / 4, cx - width / 4,
					cx - width / 2 };
			int[] y = { cy - height / 2, cy - height / 2, cy, cy + height / 2, cy - height / 2, cy };
			this.x = x;
			this.y = y;
		} else if (n == 5) {
			int[] x = { cx - width / 2, cx + width / 2, cx + width / 2, cx, cx - width / 2 };
			int[] y = { cy - height / 2, cy - height / 2, cy + height / 2, cy + height / 2 + 30, cy + height / 2 };
			this.x = x;
			this.y = y;
		} else if (n == 4) {
			int[] x = { cx - width / 2, cx + width / 2, cx + width / 2, cx - width / 2 };
			int[] y = { cy - height / 2, cy - height / 2, cy + height / 2, cy + height / 2 };
			this.x = x;
			this.y = y;
		} else if (n == 3) {
			int[] x = { cx - width / 2, cx + width / 2, cx };
			int[] y = { cy - height / 2, cy - height / 2, cy + height / 2 + 20 };
			this.x = x;
			this.y = y;
		}
		this.n = n;
		this.color = color;
		this.gl = gl;
	}

	public Polygon(int[] x, int[] y, double cx, double cy, int n, Color color, GL2 gl) {
		this.x = x;
		this.y = y;
		this.cx = cx;
		this.cy = cy;
		this.n = n;
		this.color = color;
		this.gl = gl;
	}

	public int[] getX() {
		return x;
	}

	public void setX(int[] x) {
		this.x = x;
	}

	public int[] getY() {
		return y;
	}

	public void setY(int[] y) {
		this.y = y;
	}

	public double getCx() {
		return cx;
	}

	public void setCx(double d) {
		this.cx = d;
	}

	public double getCy() {
		return cy;
	}

	public void setCy(double d) {
		this.cy = d;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public GL2 getGl() {
		return gl;
	}

	public void setGl(GL2 gl) {
		this.gl = gl;
	}

	public boolean isInside(int mx, int my) {
		java.awt.Polygon p = new java.awt.Polygon();
		for (int i = 0; i < n - 1; i++) {
			p.addPoint(x[i], y[i]);
		}
		if (n != 3)
			return p.contains(new Point(mx, my));
		else
			return InTri(mx, my, x[0], x[1], x[2], y[0], y[1], y[2]);

	}

	public Point2D Centroid(int[] x, int[] y) {
		double A = SignedArea(x, y);
		double Cx = 0;
		double Cy = 0;
		for (int i = 0; i < x.length - 1; i++) {
			Cx += (x[i] + x[i + 1]) * (x[i] * y[i + 1] - x[i + 1] * y[i]);
			Cy += (y[i] + y[i + 1]) * (x[i] * y[i + 1] - x[i + 1] * y[i]);
		}
		Cx = Cx * (1 / 6) * (1 / A);
		Cy = Cy * (1 / 6) * (1 / A);
		Point p = new Point();
		p.setLocation(Cx, Cy);
		return p;
	}

	public double SignedArea(int[] x, int[] y) {
		double A = 0;
		for (int i = 0; i < x.length - 1; i++) {
			A += x[i] * y[i + 1] - x[i + 1] * y[i];
		}
		A = A * 1 / 2;
		return A;
	}

	// utility function
	private double TriArea(double x1, double x2, double x3, double y1, double y2, double y3) {
		return Math.abs(((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2))) / 2.0);
	}

	// utility function
	private boolean InTri(double x, double y, double x1, double x2, double x3, double y1, double y2, double y3) {
		double a = TriArea(x1, x2, x3, y1, y2, y3);
		double a1 = TriArea(x, x2, x3, y, y2, y3);
		double a2 = TriArea(x1, x, x3, y1, y, y3);
		double a3 = TriArea(x1, x2, x, y1, y2, y);
		return (a == a1 + a2 + a3);
	}

	public void draw() {
		if (n < 3 || n > 31)
			return;
		int currentX = x[n - 1];
		int currentY = y[n - 1];
		Line line = new Line(currentX, currentY, x[0], y[0], color, gl);
		// ` line.draw(); // connect last with first
		for (int i = 1; i < n - 1; i++) {
			line.updateLine(x[i], y[i], x[i + 1], y[i + 1]);
			// line.draw();
		}
		scanFill();
	}

	public void boundaryFill4(int x, int y, Color boundaryColor, Color fillColor) {
		// count++;
		/*
		 * if(count >= 200) return;
		 */

		ByteBuffer pixel = ByteBuffer.allocate(3);
		gl.glReadPixels(x * 2 - 1, y * 2 - 1, 1, 1, gl.GL_RGB, gl.GL_UNSIGNED_BYTE, pixel);
		int red = pixel.get(0) & 0xFF, green = pixel.get(1) & 0xFF, blue = pixel.get(2) & 0xFF;

		/*
		 * System.out.println("x: " + x + " y: "+y);
		 * System.out.println("current red --> "+red);
		 * System.out.println("current green --> "+green);
		 * System.out.println("current blue --> "+blue);
		 */

		if (!(red == boundaryColor.getRed() && green == boundaryColor.getGreen() && blue == boundaryColor.getBlue())
				&& !(red == fillColor.getRed() && green == fillColor.getGreen() && blue == fillColor.getBlue())) {

			// set color
			gl.glColor3f((float) fillColor.getRed() / 255, (float) fillColor.getGreen() / 255,
					(float) fillColor.getBlue() / 255); // new color
			gl.glBegin(GL2.GL_POINTS);
			gl.glVertex2i(x, y);
			gl.glEnd();

			boundaryFill4(x + 1, y, boundaryColor, fillColor);
			boundaryFill4(x, y + 1, boundaryColor, fillColor);
			boundaryFill4(x - 1, y, boundaryColor, fillColor);
			boundaryFill4(x, y - 1, boundaryColor, fillColor);
		}
	}

	public void scanFill() {
		int hits = 0;

		int x = 0;
		int y = 0;

		ByteBuffer pixel = ByteBuffer.allocate(3);
		gl.glReadPixels(x * 2 - 1, y * 2 - 1, 1, 1, gl.GL_RGB, gl.GL_UNSIGNED_BYTE, pixel);
		int red = pixel.get(0) & 0xFF, green = pixel.get(1) & 0xFF, blue = pixel.get(2) & 0xFF;

		int lastx = 0;
		int lasty = 0;
		int curx, cury;

		// Walk the edges of the polygon
		for (int i = 0; i < n; lastx = curx, lasty = cury, i++) {
			curx = 0;
			cury = 0;

			if (cury == lasty) {
				continue;
			}

			if (!(red == color.getRed() && green == color.getGreen() && blue == color.getBlue())) {
				break;
			}

			int leftx;
			if (curx < lastx) {
				if (x >= lastx) {
					continue;
				}
				leftx = curx;
			} else {
				if (x >= curx) {
					continue;
				}
				leftx = lastx;
			}

			double test1, test2;
			if (cury < lasty) {
				if (y < cury || y >= lasty) {
					continue;
				}
				if (x < leftx) {
					hits++;
					continue;
				}
				test1 = x - curx;
				test2 = y - cury;
			} else {
				if (y < lasty || y >= cury) {
					continue;
				}
				if (x < leftx) {
					hits++;
					continue;
				}
				test1 = x - lastx;
				test2 = y - lasty;
			}

			if (test1 < (test2 / (lasty - cury) * (lastx - curx))) {
				hits++;
			}
		}
		fill();
	}

	private void fill() {
		gl.glColor3f((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255); // new
																													// color
		gl.glBegin(GL2.GL_POLYGON);
		for (int i = 0; i < n - 1; i++) {
			gl.glVertex2i(x[i], y[i]);
			gl.glVertex2i(x[i + 1], y[i + 1]);
		}
		gl.glEnd();
	}

	public String returnShapeType() {
		return "Polygon";
	}

}
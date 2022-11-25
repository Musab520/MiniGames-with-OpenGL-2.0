package Shapes;

import com.jogamp.opengl.GL2;

import java.awt.*;

// The big advantage of this algorithm is that it uses only integer calculations, thus increasing precision.
public class Line extends Shapes {

	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private Color color;
	private GL2 gl;

	public Line() {
	}

	public Line(int x1, int y1, int x2, int y2, Color color, GL2 gl) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;
		this.gl = gl;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
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

	public void updateLine(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public void draww() {
        int dx = Math.abs(x2-x1); int dy = Math.abs(y2-y1);
        int x = x1; int y = y1;

        gl.glColor3f(color.getRed()/255f,color.getGreen()/255f,color.getBlue()/255f); // color
        gl.glBegin(GL2.GL_POINTS);
        gl.glVertex2i(x, y);

        if(dx > dy) {
            // m < 1
            int p = 2*dy - dx;
            int xEnd = 0;
            int twoDy = 2*dy;
            int twoDyDx = 2*(dy-dx);

            if(x1>x2) {
                x = x2;
                y = y2;
                xEnd = x1;
            } else {
                x = x1;
                y = y1;
                xEnd = x2;
            }
            gl.glVertex2i(x, y);


            while(x < xEnd) {
                x++;
                if(p<0)
                    p=p+twoDy;
                else {
                    y = y2-y1 < 0 ? y-1: y+1;
                    p = p+twoDyDx;
                }
                gl.glVertex2i(x, y);
            }
        } else {
            // m >= 1

            int p = 2*dx - dy;
            int yEnd=0; int xEnd = 0;
            int twoDx = 2*dx;
            int twoDxDy =  2*(dx-dy);

            if(y1>y2) {
                x = x2;
                y = y2;
                yEnd = y1;
                xEnd = x1;
            } else {
                x = x1;
                y = y1;
                yEnd = y2;
                xEnd = x2;
            }

            gl.glVertex2i(x, y);

            while(y < yEnd) {
                y++;
                if(p<0)
                    p=p+twoDx;
                else {
                    x = xEnd- x < 0 ? x-1: x+1;
                    p = p+twoDxDy;
                }
                gl.glVertex2i(x, y);
            }
        }
        gl.glEnd();
	}

	public void draw2() {
		gl.glColor3f(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f); // color
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex2f(x1, y1);
		gl.glVertex2f(x2, y2);
		gl.glEnd();
	}

	public String returnShapeType() {
		return "Line";
	}
}

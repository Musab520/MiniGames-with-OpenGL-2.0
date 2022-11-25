package Shapes;

import com.jogamp.opengl.GL2;

import java.awt.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class BrushPoints extends Shapes {

    // in circles, x and y radius are the same

    private ArrayList<Ellipse> brushPointsList = new ArrayList<Ellipse>();
    private GL2 gl;
    private Color color = Color.WHITE; // boundary
    private int radius;
    private int xCenter;
    private int yCenter;

    public BrushPoints() {
    }

    public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public BrushPoints(ArrayList<Ellipse> brushPointsList, int radius, GL2 gl, Color color) {
        this.brushPointsList = brushPointsList;
        this.gl = gl;
        this.color = color;
        this.radius = radius;
    }

    public ArrayList<Ellipse> getBrushPointsList() {
        return brushPointsList;
    }

    public void setBrushPointsList(ArrayList<Ellipse> brushPoints) {
        this.brushPointsList = brushPoints;
    }

    public int returnNumberOfPoints() {
        return brushPointsList.size();
    }

    public GL2 getGl() {
        return gl;
    }

    public void setGl(GL2 gl) {
        this.gl = gl;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String returnShapeType() {
        return "BrushPoints";
    }

    public void draw() {
       for(int i=0; i < brushPointsList.size(); i++) {
           Ellipse circle;
           circle = brushPointsList.get(i);
           circle.draw();
       }
    }

    private void scanFill() {

        int hits = 0;
        int x =0;
        int y = 0;
        int count=0;

        ByteBuffer pixel = ByteBuffer.allocate(3);
        gl.glReadPixels(x*2 -1,y*2 -1,1,1,gl.GL_RGB,gl.GL_UNSIGNED_BYTE, pixel);
        int red = pixel.get(0) & 0xFF, green = pixel.get(1) & 0xFF, blue = pixel.get(2) & 0xFF;

        int lastx = 0;
        int lasty = 0;
        int curx, cury;

        // Walk the edges of the polygon
        for (int i = 0; i < count; lastx = curx, lasty = cury, i++) {
            curx = 0;
            cury = 0;

            if (cury == lasty) {
                continue;
            }


            if( !(red == color.getRed() && green == color.getGreen() && blue == color.getBlue())) {
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
    }
}
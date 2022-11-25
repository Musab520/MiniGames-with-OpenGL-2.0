package Shapes;
import com.jogamp.opengl.GL2;
import java.awt.*;
import java.nio.ByteBuffer;

// implements Shapes.Ellipse Midpoint Algorithm

public class Ellipse extends Shapes {

    private int xCenter;
    private int yCenter;
    private int Xradius;
    private int Yradius;
    private GL2 gl;
    private Color color = Color.WHITE; // boundary
    int count;

    public Ellipse() {
        super();
    }

    public Ellipse(int xCenter, int yCenter, int Xradius, int Yradius, Color color, GL2 gl) {
        super();
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.Xradius = Xradius;
        this.Yradius = Yradius;
        this.color = color;
        this.gl = gl;
    }

    public int getxCenter() {
        return xCenter;
    }

    public void setxCenter(int xCenter) {
        this.xCenter = xCenter;
    }

    public int getyCenter() {
        return yCenter;
    }

    public void setyCenter(int yCenter) {
        this.yCenter = yCenter;
    }

    public int getXradius() {
        return Xradius;
    }

    public void setXradius(int xradius) {
        Xradius = xradius;
    }

    public int getYradius() {
        return Yradius;
    }

    public void setYradius(int yradius) {
        Yradius = yradius;
    }

    public GL2 getGl() {
        return gl;
    }

    public void setGl(GL2 gl) {
        this.gl = gl;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void updateEllipse(int xCenter, int yCenter, int Xradius, int Yradius){
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.Xradius = Xradius;
        this.Yradius = Yradius;
    }

    public boolean IsInside(double x, double y) {
        double first = Math.pow(x - xCenter, 2) / (Math.pow(Xradius, 2));
        double second = Math.pow(y - yCenter, 2) / (Math.pow(Yradius, 2));
        return first + second <= 1;
    }

    public void draw() {
        float dx, dy, d1, d2;
        int x = 0;
        int y = Yradius;

        // Initial decision parameter of region 1
        d1 = (Yradius * Yradius) - (Xradius * Xradius * Yradius) +
                (0.25f * Xradius * Xradius);
        dx = 2 * Yradius * Yradius * x;
        dy = 2 * Xradius * Xradius * y;
        // For region 1
        while (dx < dy)  {

            plotOthers(x, y);

            // Checking and updating value of
            // decision parameter based on algorithm
            if (d1 < 0) {
                x++;
                dx = dx + (2 * Yradius * Yradius);
                d1 = d1 + dx + (Yradius * Yradius);
            }
            else {
                x++;
                y--;
                dx = dx + (2 * Yradius * Yradius);
                dy = dy - (2 * Xradius * Xradius);
                d1 = d1 + dx - dy + (Yradius * Yradius);
            }
        }

        // Decision parameter of region 2
        d2 = ((Yradius * Yradius) * ((x + 0.5f) * (x + 0.5f)))
                + ((Xradius * Xradius) * ((y - 1) * (y - 1)))
                - (Xradius * Xradius * Yradius * Yradius);

        // Plotting points of region 2
        while (y >= 0) {

            plotOthers(x, y);

            // Checking and updating parameter
            // value based on algorithm
            if (d2 > 0) {
                y--;
                dy = dy - (2 * Xradius * Xradius);
                d2 = d2 + (Xradius * Xradius) - dy;
            }
            else {
                y--;
                x++;
                dx = dx + (2 * Yradius * Yradius);
                dy = dy - (2 * Xradius * Xradius);
                d2 = d2 + dx - dy + (Xradius * Xradius);
            }
        }

        scanFill();
    }

    private void plotOthers(int x, int y) {
        gl.glColor3f(color.getRed()/255f,color.getGreen()/255f,color.getBlue()/255f); // color

        gl.glBegin(GL2.GL_POINTS);
        gl.glVertex2i(xCenter+x, yCenter+y);
        gl.glVertex2i(xCenter-x, yCenter+y);
        gl.glVertex2i(xCenter+x, yCenter-y);
        gl.glVertex2i(xCenter-x, yCenter-y);
        gl.glEnd();

        gl.glBegin(GL2.GL_LINES);
        gl.glVertex2i(xCenter, yCenter);
        gl.glVertex2i(xCenter+x, yCenter+y);
        gl.glVertex2i(xCenter, yCenter);
        gl.glVertex2i(xCenter-x, yCenter+y);
        gl.glVertex2i(xCenter, yCenter);
        gl.glVertex2i(xCenter+x, yCenter-y);
        gl.glVertex2i(xCenter, yCenter);
        gl.glVertex2i(xCenter-x, yCenter-y);
        gl.glEnd();


        Line line = new Line(xCenter, yCenter, xCenter+x, yCenter+y, color, gl);
        //draw();
        line.updateLine(xCenter, yCenter, xCenter-x, yCenter+y);
        //draw();
        line.updateLine(xCenter, yCenter, xCenter+x, yCenter-y);
        //draw();
        line.updateLine(xCenter, yCenter, xCenter-x, yCenter-y);
        //draw();
    }

    public void boundaryFill4(int x, int y, Color boundaryColor, Color fillColor) {
        count++;
/*        if(count >= 200)
            return;*/

        ByteBuffer pixel = ByteBuffer.allocate(3);
        gl.glReadPixels(x*2 -1,y*2 -1,1,1,gl.GL_RGB,gl.GL_UNSIGNED_BYTE, pixel);
        int red = pixel.get(0) & 0xFF, green = pixel.get(1) & 0xFF, blue = pixel.get(2) & 0xFF;

/*        System.out.println("x: " + x + " y: "+y);
        System.out.println("current red --> "+red);
        System.out.println("current green --> "+green);
        System.out.println("current blue --> "+blue);*/



        if( !(red == boundaryColor.getRed() && green == boundaryColor.getGreen() && blue == boundaryColor.getBlue()) &&
                !(red == fillColor.getRed() && green == fillColor.getGreen() && blue == fillColor.getBlue())) {

            // set color
            gl.glColor3f((float) fillColor.getRed()/255,(float)fillColor.getGreen()/255,(float) fillColor.getBlue()/255); // new color
            gl.glBegin(GL2.GL_POINTS);
            gl.glVertex2i(x, y);
            gl.glEnd();

            boundaryFill4(x+1, y, boundaryColor, fillColor);
            boundaryFill4(x, y+1, boundaryColor, fillColor);
            boundaryFill4(x-1, y, boundaryColor, fillColor);
            boundaryFill4(x, y-1, boundaryColor, fillColor);
        }
    }

    private void scanFill() {

        int hits = 0;
        int x =0;
        int y = 0;

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

    public String returnShapeType() {
        return "Ellipse";
    }

}
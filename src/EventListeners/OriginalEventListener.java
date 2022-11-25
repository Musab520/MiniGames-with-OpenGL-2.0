package EventListeners;

import Shapes.Ellipse;
import Shapes.Painting;
import Shapes.Polygon;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.GLBuffers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class OriginalEventListener implements GLEventListener {

	private GL2 gl;
	boolean begin = false;
	boolean stop = false;
	public Painting painting = new Painting();

	public GL2 getGl() {
		return gl;
	}

	public void setGl(GL2 gl) {
		this.gl = gl;
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
		setGl(gl);
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		for (int i = 0; i < painting.getList().size(); i++) {
			if (painting.getList().get(i).getClass().getName().compareTo("Shapes.Polygon") == 0) {
				Shapes.Polygon poly = (Shapes.Polygon) painting.getList().get(i);
				poly.draw();
			} else if (painting.getList().get(i).getClass().getName().compareTo("Shapes.Ellipse") == 0) {
				Ellipse el = (Ellipse) painting.getList().get(i);
				el.draw();
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
		// gl.glViewport(0, 0, maxWidth *2 -1, maxHeight *2 -1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}

	protected void saveImage(GL2 gl, int width, int height) {

		try {
			BufferedImage screenshot = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics graphics = screenshot.getGraphics();

			ByteBuffer buffer = GLBuffers.newDirectByteBuffer(width * height * 4);
			// be sure you are reading from the right fbo (here is supposed to be the
			// default one)
			// bind the right buffer to read from
			gl.glReadBuffer(GL3.GL_BACK);
			// if the width is not multiple of 4, set unpackPixel = 1
			gl.glReadPixels(0, 0, width, height, GL3.GL_RGBA, GL3.GL_UNSIGNED_BYTE, buffer);

			for (int h = 0; h < height; h++) {
				for (int w = 0; w < width; w++) {
					// The color are the three consecutive bytes, it's like referencing
					// to the next consecutive array elements, so we got red, green, blue..
					// red, green, blue, and so on..+ ", "
					graphics.setColor(new Color((buffer.get() & 0xff), (buffer.get() & 0xff), (buffer.get() & 0xff)));
					buffer.get(); // consume alpha
					graphics.drawRect(w, height - h, 1, 1); // height - h is for flipping the image
				}
			}

			File outputfile = new File("Painting.png");
			ImageIO.write(screenshot, "png", outputfile);
			stop = true;
		} catch (IOException ex) {
		}
	}
}
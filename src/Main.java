
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import javax.swing.*;

public class Main {

	public static void main(String[] args) {
		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);

		EventListeners.OriginalEventListener render = new EventListeners.OriginalEventListener();

		// Window
		final GLCanvas glcanvas = new GLCanvas(capabilities);
		Animator animator = new Animator(glcanvas);
		glcanvas.addGLEventListener(render);
		glcanvas.setSize(600, 700); // 400, 500

		final JFrame frame = new JFrame("Museum Game Simulation");
		frame.getContentPane().add(glcanvas);
		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// animator.start();
		glcanvas.requestFocus();
	}
}
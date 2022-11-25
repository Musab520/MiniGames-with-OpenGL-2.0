import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class PuzzlePanel {
	ArrayList<JToggleButton> buttons = new ArrayList<>();
	ArrayList<Icon> origicons = new ArrayList<>();
	ArrayList<Icon> mixicons = new ArrayList<>();
	ButtonGroup grp = new ButtonGroup();
	int pieces;
	File f;
	Image image;
	JToggleButton prevbtn;

	public PuzzlePanel(int p, File f) {
		pieces = p;
		buttons = new ArrayList<>();
		origicons = new ArrayList<>();
		mixicons = new ArrayList<>();
		try {

			image = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PuzzlePanel() {
		// TODO Auto-generated constructor stub
	}

	public void CreatePuzzlePanel(JPanel frame) {
		BufferedImage buffered = (BufferedImage) image;
		int x = 0;
		int y = 0;
		int incx = 0;
		int incy = 0;
		int count = 0;
		int width = 200;
		int height = 700;
		JToggleButton button = new JToggleButton();

		if (pieces == 9) {
			x = 0;
			y = 0;
			incx = width;
			incy = height / 3;
			for (int i = 0; i < 9; i++) {
				Icon icon = new ImageIcon(buffered.getSubimage(x, y, incx, incy));
				button = new JToggleButton(icon);
				button.addActionListener(btnlisten());
				button.setBounds(x, y, incx, incy);
				buttons.add(button);
				origicons.add(icon);
				mixicons.add(icon);
				grp.add(button);
				frame.add(button);
				count++;
				if (count == 3) {
					x = 0;
					y = y + incy;
					count = 0;
				} else {
					x = x + incx;
				}
			}
			Collections.shuffle(mixicons);
			for (int i = 0; i < buttons.size(); i++) {
				buttons.get(i).setIcon(mixicons.get(i));
			}
		} else if (pieces == 36) {
			incx = width / 2;
			incy = (height / 3) / 2;
			for (int i = 0; i < 36; i++) {
				Icon icon = new ImageIcon(buffered.getSubimage(x, y, incx, incy));
				button = new JToggleButton(icon);
				button.addActionListener(btnlisten());
				button.setBounds(x, y, incx, incy);
				buttons.add(button);
				grp.add(button);
				origicons.add(icon);
				mixicons.add(icon);
				frame.add(button);
				count++;
				if (count == 6) {
					x = 0;
					y = y + incy;
					count = 0;
				} else {
					x = x + incx;
				}
			}
			Collections.shuffle(mixicons);
			for (int i = 0; i < buttons.size(); i++) {
				buttons.get(i).setIcon(mixicons.get(i));
			}
		} else if (pieces == 81) {
			incx = width / 3;
			incy = (height / 3) / 3;
			for (int i = 0; i < 81; i++) {
				Icon icon = new ImageIcon(buffered.getSubimage(x, y, incx, incy));
				button = new JToggleButton(icon);
				button.addActionListener(btnlisten());
				button.setBounds(x, y, incx, incy);
				buttons.add(button);
				grp.add(button);
				origicons.add(icon);
				mixicons.add(icon);
				frame.add(button);
				count++;
				if (count == 9) {
					x = 0;
					y = y + incy;
					count = 0;
				} else {
					x = x + incx;
				}
			}
			Collections.shuffle(mixicons);
			for (int i = 0; i < buttons.size(); i++) {
				buttons.get(i).setIcon(mixicons.get(i));
			}
		}
	}
	public ActionListener btnlisten() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JToggleButton btn = (JToggleButton) e.getSource();
				if (btn.isSelected()) {
					if (prevbtn != null && prevbtn != btn) {
						Icon i = btn.getIcon();
						btn.setIcon(prevbtn.getIcon());
						prevbtn.setSize(btn.getWidth(), btn.getHeight());
						prevbtn.setIcon(i);
						prevbtn = null;
					} else if (prevbtn == null) {
						btn.setSize(btn.getWidth() - 10, btn.getHeight() - 10);
						prevbtn = btn;
					}
				}

			}

		};

	}
	public boolean check() {
		boolean check = true;
		for (int i = 0; i < origicons.size(); i++) {
			if (!origicons.get(i).equals(buttons.get(i).getIcon())) {
				check = false;
				break;
			}
		}
		if (check == false) {
			JOptionPane.showMessageDialog(new JPanel(), "NOT SOLVED");
		} else
			JOptionPane.showMessageDialog(new JPanel(), " SOLVED");
		return check;
	}
}

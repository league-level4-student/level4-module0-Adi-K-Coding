package _02_Pixel_Art;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;

public class PixelArtMaker implements MouseListener, ActionListener {
	private JFrame window;
	private GridInputPanel gip;
	private GridPanel gp;
	ColorSelectionPanel csp;

	public void start() {
		gip = new GridInputPanel(this);
		window = new JFrame("Pixel Art");
		window.setLayout(new FlowLayout());
		window.setResizable(false);

		window.add(gip);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	public void submitGridData(int w, int h, int r, int c) {
		gp = new GridPanel(w, h, r, c);
		csp = new ColorSelectionPanel();
		csp.saveButton.addActionListener(this);
		csp.loadButton.addActionListener(this);
		window.remove(gip);
		window.add(gp);
		window.add(csp);
		gp.repaint();
		gp.addMouseListener(this);
		window.pack();
	}

	public static void main(String[] args) {
		new PixelArtMaker().start();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		gp.setColor(csp.getSelectedColor());
		System.out.println(csp.getSelectedColor());
		gp.clickPixel(e.getX(), e.getY());
		gp.repaint();
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
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == csp.saveButton) {
			System.out.println("Save");
			try (FileOutputStream fos = new FileOutputStream(new File("text4"));
					ObjectOutputStream oos = new ObjectOutputStream(fos)) {
				oos.writeObject(gp);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			System.out.println("Load");
			try (FileInputStream fis = new FileInputStream(new File("text4"));
					ObjectInputStream ois = new ObjectInputStream(fis)) {
				gp.pixels = ((GridPanel) ois.readObject()).pixels;
				gp.repaint();
			} catch (IOException e2) {
				e2.printStackTrace();
			} catch (ClassNotFoundException e2) {
				// This can occur if the object we read from the file is not
				// an instance of any recognized class
				e2.printStackTrace();
			}
		}
	}
}

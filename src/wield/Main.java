package wield;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Main extends Applet implements Runnable, MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;

	public static void main() {
		Main main = new Main();
		main.start();
	}

	private Image image;
	private Graphics second;
	private Game game;
	
	public final int width = 1280;
	public final int height = 720;

	@Override
	public void init() {

		this.setSize(this.width, this.height);
		this.setBackground(Color.WHITE);
		this.setFocusable(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Wield");
		this.game = new Game();

		// Image Setups

	}

	@Override
	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void run() {
		while (true) {
			double deltaTime = 17; // TODO
			game.update(deltaTime);
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createVolatileImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}
		second.clearRect(0, 0, this.width, this.height);
		game.paint(second);

		g.drawImage(image, 0, 0, this);
	}

	@Override
	public void paint(Graphics g) {

	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent evt) {
		game.mousePressed(evt.getPoint());
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		game.mouseReleased(evt.getPoint());
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		game.mouseDragged(evt.getPoint());
	}

	@Override
	public void mouseMoved(MouseEvent evt) {
		// TODO Auto-generated method stub
		
	}

}
package pruebasZombis.juego;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Juego extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 310;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 3;
	public static final String NAME = "Undead-Invasion";
	
	private JFrame frame;
	private boolean running;
	private int contadorTick;
	private BufferedImage imagen;
	private int[] pixels;
	private ArrayList<Zombi> zombis;
	private Grupos grupos;
	
	Juego() {
		setContadorTick(0);
		running = false;
		imagen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		setPixels(((DataBufferInt) imagen.getRaster().getDataBuffer()).getData());
		zombis = new ArrayList<Zombi>();
		grupos = new Grupos(zombis);
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent arg0) {
				Zombi zombi = new Zombi(WIDTH * SCALE, HEIGHT * SCALE);
				zombi.setPosX(arg0.getX());
				zombi.setPosY(arg0.getY());
				zombis.add(zombi);
				grupos.setZombis(zombis);
			}

			public void mouseEntered(MouseEvent arg0) {}

			public void mouseExited(MouseEvent arg0) {}

			public void mousePressed(MouseEvent arg0) {}

			public void mouseReleased(MouseEvent arg0) {}
		});
	}

	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}
	
	public synchronized void stop() {
		running = false;
	}
	
	public void run() {
		long ultimoTiempoNs = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;
		int ticks = 0;
		int frames = 0;
		long ultimoTiempoMs = System.currentTimeMillis();
		double delta = 0;
		while (running) {
			long ahora = System.nanoTime();
			delta += (ahora - ultimoTiempoNs) / nsPerTick;
			ultimoTiempoNs = ahora;
			boolean deberiaRenderizar = false;
			
			while (delta >= 1) {
				ticks++;
				tick();
				delta--;
				deberiaRenderizar = true;
			}
			//try {
				//Thread.sleep(2);
			//} catch (InterruptedException e) {
				//e.printStackTrace();
			//}
			if (deberiaRenderizar) {
				frames++;
				render();
			}
			
			if (System.currentTimeMillis() - ultimoTiempoMs > 1000) {
				ultimoTiempoMs += 1000;
				frames = 0;
				ticks = 0;
			}
		}
	}
	
	public void tick() {
		setContadorTick(getContadorTick() + 1);
		grupos.controlGrupos();
		
		//for (int i = 0; i < pixels.length; i++) {
			//pixels[i] = i + contadorTick;
		//}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(imagen, 0, 0, getWidth(), getHeight(), null);
		grupos.mover(g);
		for (int i = 0; i < zombis.size(); i++) {
			g = zombis.get(i).mover(g);
		}
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		new Juego().start();
	}

	public int getContadorTick() {
		return contadorTick;
	}

	public void setContadorTick(int contadorTick) {
		this.contadorTick = contadorTick;
	}

	public int[] getPixels() {
		return pixels;
	}

	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}
}

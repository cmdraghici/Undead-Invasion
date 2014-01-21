package pruebasZombis.juego;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Pantalla {
	//tamaño mapa
	public final static int MAP_WIDTH = 2360;
	public final static int MAP_HEIGHT = 2510;
	public final static int RESPAWN = 200;
	//tamaño pantalla
	private int width;
	private int height;
	//zombis
	private ArrayList<Zombi> zombis;
	private Grupos grupos;
	//localizacion pantalla
	private int x;
	private int y;
	private int xapuntar;
	private int yapuntar;
	//jugador
	private Jugador jugador;
	//movimiento
	private boolean arriba;
	private boolean abajo;
	private boolean izquierda;
	private boolean derecha;
	//disparos
	Disparos disparos;
	private Image mapa;
	private BufferedImage mapaColision;
	
	private BufferedImage player;
	private BufferedImage zombie1; 
	private BufferedImage zombie2; 
	
	public Cursor puntomira;
	private Image imagenCursor;
	
	Pantalla(int width, int height, Juego j) {
		this.setX(0);
		this.setY(0);
		disparos = new Disparos(MAP_WIDTH, MAP_HEIGHT);
		this.width = width;
		this.height = height;
		jugador = new Jugador(this.width, this.height);
		zombis = new ArrayList<Zombi>();
		grupos = new Grupos(zombis);
		setArriba(false);
		setAbajo(false);
		setIzquierda(false);
		setDerecha(false);
		mapa = Toolkit.getDefaultToolkit().getImage("mapazombies.png");
		imagenCursor = Toolkit.getDefaultToolkit().getImage("puntodemira.png");
		this.puntomira = Toolkit.getDefaultToolkit().createCustomCursor(imagenCursor, 
				new Point(16, 16), "PuntoMira");
		j.setCursor(this.puntomira);
		try {
			mapaColision = ImageIO.read(new File("mapacolisiones.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try { 
			player = ImageIO.read(new File("humanS.png"));
			zombie1 = ImageIO.read(new File("zombie1.png"));
			zombie2 = ImageIO.read(new File("zombie2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void addZombi(int x, int y) {
		Zombi zombi = new Zombi(MAP_WIDTH, MAP_HEIGHT, mapaColision);
		zombi.setPosX(x + this.x);
		zombi.setPosY(y + this.y);
		zombis.add(zombi);
		grupos.setZombis(zombis);
	}
	
	public void controlGrupos() {
		grupos.controlGrupos();
	}
	
	public void moverCamara() {
		if (arriba && izquierda && derecha && abajo) {
		
		} else if (arriba && izquierda && derecha) {
			
		} else if (arriba && izquierda && abajo) {
			
		} else if (arriba && derecha && abajo) {
			
		} else if (abajo && izquierda && derecha) {
			
		} else if (izquierda && derecha) {
			
		} else if (arriba && abajo) {
			
		} else if (arriba && izquierda) {
			moverArriba((int) Math.round(jugador.getVely() / 2.0D));
			moverIzquierda((int) Math.round(jugador.getVelx() / 2.0D));
		} else if (arriba && derecha) {
			moverArriba((int) Math.round(jugador.getVely() / 2.0D));
			moverDerecha((int) Math.round(jugador.getVelx() / 2.0D));
		} else if (abajo && izquierda) {
			moverAbajo((int) Math.round(jugador.getVely() / 2.0D));
			moverIzquierda((int) Math.round(jugador.getVelx() / 2.0D));
		} else if (abajo && derecha) {
			moverAbajo((int) Math.round(jugador.getVely() / 2.0D));
			moverDerecha((int) Math.round(jugador.getVelx() / 2.0D));
		} else if (arriba) {
			moverArriba((int) Math.round(jugador.getVely() / 2.0D));
		} else if (abajo) {
			moverAbajo((int) Math.round(jugador.getVely() / 2.0D));
		} else if (izquierda) {
			moverIzquierda((int) Math.round(jugador.getVelx() / 2.0D));
		} else if (derecha) {
			moverDerecha((int) Math.round(jugador.getVelx() / 2.0D));
		}
	}
	
	public void apuntar(int x, int y) {
		jugador.apuntar(x, y);
		this.xapuntar = x;
		this.yapuntar = y;
	}
	
	public void render(Graphics g, Juego j) {
		g.drawImage(mapa, 0 - x, 0 - y, MAP_WIDTH, MAP_HEIGHT, j);
		Graphics2D g2d = ( Graphics2D ) g ; 
		grupos.mover();
		dibujarLimites(g);
		AffineTransform at = g2d.getTransform(); 
		dibujarJugador(g2d); 
		g2d.setTransform(at); 
		for (int i = 0; i < zombis.size(); i++) {
			zombis.get(i).setJugadorPos((int)jugador.getCentroX(), (int)jugador.getCentroY());
			zombis.get(i).mover();
			detectarMordisco(zombis.get(i));
		}
		for (int i = 0; i < zombis.size(); i++) {
			int x = zombis.get(i).getPosX();
			int y = zombis.get(i).getPosY();
			x -= this.x;
			y -= this.y;
			at = g2d.getTransform(); 
			dibujarZombie(g2d, x, y, i);
			g2d.setTransform(at); 
		}
		disparos.actualizar(g, zombis, x, y, mapaColision);
	}
	
	private void detectarMordisco(Zombi zombi) {
		if (Operaciones.distancia(jugador.getCentroX(), jugador.getCentroY(),
				zombi.getCentroX(), zombi.getCentroY()) < (Jugador.TAM_JUGADOR)) {
			jugador.mordisco();
			if (jugador.getVida() <= 0) {
				JOptionPane.showMessageDialog(new JFrame(),
					      "Los Zombis se Comieron tu cerebro", "Estas Muerto",JOptionPane.WARNING_MESSAGE);
					    System.exit(0);
			}
		}
	}

	public void dibujarZombie(Graphics2D g2d, int x, int y, int actual) { 
		g2d.translate(x + 9, y + 9);
		TexturePaint texturePaint = null;
		if (zombis.get(actual).pie <= 10) {
			texturePaint = new TexturePaint(zombie1, 
					new Rectangle2D.Double(-9, -9, Jugador.TAM_JUGADOR, Jugador.TAM_JUGADOR)); 
			zombis.get(actual).pie++;
		} else if (zombis.get(actual).pie <= 20) {
			texturePaint = new TexturePaint(zombie2, 
					new Rectangle2D.Double(-9, -9, Jugador.TAM_JUGADOR, Jugador.TAM_JUGADOR)); 
			zombis.get(actual).pie++;
		} else if (zombis.get(actual).pie == 21) {
			zombis.get(actual).pie = 0;
			texturePaint = new TexturePaint(zombie1, 
					new Rectangle2D.Double(-9, -9, Jugador.TAM_JUGADOR, Jugador.TAM_JUGADOR)); 
		}
		g2d.setPaint(texturePaint);
		double elangulo = zombis.get(actual).getAngulo();
		g2d.rotate(Math.toRadians(270 - elangulo));
		g2d.fill(new Rectangle2D.Double(-9, -9, Jugador.TAM_JUGADOR, Jugador.TAM_JUGADOR));
	}
	
	public void dibujarJugador(Graphics2D g2d) { //CAMBIADO
		calcPosicionJugador();
		g2d.translate(jugador.getPosXPantalla() + 9, jugador.getPosYPantalla() + 9);
		TexturePaint texturePaint = new TexturePaint(player, 
        new Rectangle2D.Double(-9, -9, Jugador.TAM_JUGADOR, Jugador.TAM_JUGADOR));   
		g2d.setPaint(texturePaint);
    	double elangulo = Operaciones.angulo(jugador.getCentroX(), jugador.getCentroY(), xapuntar + x, yapuntar + y);
    	g2d.rotate(Math.toRadians(270 - elangulo));
    	g2d.fill(new Rectangle2D.Double(-9, -9, Jugador.TAM_JUGADOR, Jugador.TAM_JUGADOR));
	}
	
	public void dibujarLimites(Graphics g) {
		g.setColor(Color.red);
		g.drawLine(0 - x, 0 - y, MAP_WIDTH - x, 0 - y);
		g.drawLine(0 - x, 0 - y, 0 - x, MAP_HEIGHT - y);
		g.drawLine(MAP_WIDTH - x, 0 - y, MAP_WIDTH - x, MAP_HEIGHT - y);
		g.drawLine(0 - x, MAP_HEIGHT - y, MAP_WIDTH - x, MAP_HEIGHT - y);
	}
	
	public void calcPosicionJugador() {
		jugador.calcPosicion(this.x, this.y);
	}
	
	public void moverAbajo(int y) {
		this.y += y;
	}
	
	public void moverArriba(int y) {
		this.y -= y;
	}
	
	public void moverDerecha(int x) {
		this.x += x;
	}
	
	public void moverIzquierda(int x) {
		this.x -= x;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isArriba() {
		return arriba;
	}

	public void setArriba(boolean arriba) {
		this.arriba = arriba;
	}

	public boolean isAbajo() {
		return abajo;
	}

	public void setAbajo(boolean abajo) {
		this.abajo = abajo;
	}

	public boolean isIzquierda() {
		return izquierda;
	}

	public void setIzquierda(boolean izquierda) {
		this.izquierda = izquierda;
	}

	public boolean isDerecha() {
		return derecha;
	}

	public void setDerecha(boolean derecha) {
		this.derecha = derecha;
	}

	public void disparo(int x2, int y2) {
		disparos.add(jugador.getCentroX(), jugador.getCentroY(), x2 + x, y2 + y);
		for (int i = 0; i < zombis.size(); i++) {
			if (zombis.get(i).detectarDisparo(jugador.getCentroX(), jugador.getCentroY())) {
				zombis.get(i).setObjetivo(jugador.getCentroX(), jugador.getCentroY());
			}
		}
	}

	public void setDisparo(boolean b) {
		disparos.setDisparo(b);
	}

	public void addRandomZombi() {
		Zombi zombi = new Zombi(MAP_WIDTH, MAP_HEIGHT, mapaColision);
		zombi.setPosX((int)Math.round(MAP_WIDTH * Math.random()));
		zombi.setPosY((int)Math.round(MAP_HEIGHT * Math.random()));
		if (Operaciones.distancia(zombi.getPosX(), zombi.getPosY(), 
				jugador.getCentroX(), jugador.getCentroY()) > RESPAWN) {
			zombis.add(zombi);
			grupos.setZombis(zombis);
		}
	}
}

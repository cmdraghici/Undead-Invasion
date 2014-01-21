package pruebasZombis.juego;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Pantalla {
	//tamaño mapa
	public final static int MAP_WIDTH = 2360;
	public final static int MAP_HEIGHT = 2510;
	//tamaño pantalla
	private int width;
	private int height;
	//zombis
	private ArrayList<Zombi> zombis;
	private Grupos grupos;
	//localizacion pantalla
	private int x;
	private int y;
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
	
	Pantalla(int width, int height) {
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
	}
	
	public void addZombi(int x, int y) {
		Zombi zombi = new Zombi(MAP_WIDTH, MAP_HEIGHT);
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
	}
	
	public void render(Graphics g, Juego j) {
		g.drawImage(mapa, 0 - x, 0 - y, MAP_WIDTH, MAP_HEIGHT, j);
		grupos.mover();
		dibujarLimites(g);
		dibujarJugador(g);
		for (int i = 0; i < zombis.size(); i++) {
			zombis.get(i).mover();
		}
		for (int i = 0; i < zombis.size(); i++) {
			int x = zombis.get(i).getPosX();
			int y = zombis.get(i).getPosY();
			x -= this.x;
			y -= this.y;
			g.setColor(Color.white);
			g.fillOval(x, y, Zombi.TAM, Zombi.TAM);
		}
		disparos.actualizar(g, zombis, x, y);
	}
	
	public void dibujarJugador(Graphics g) {
		calcPosicionJugador();
		g.setColor(Color.blue);
		g.fillOval(jugador.getPosXPantalla(), jugador.getPosYPantalla(),
				Jugador.TAM_JUGADOR, Jugador.TAM_JUGADOR);
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
	}

	public void setDisparo(boolean b) {
		disparos.setDisparo(b);
	}
}

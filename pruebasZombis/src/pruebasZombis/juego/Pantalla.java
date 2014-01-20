package pruebasZombis.juego;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Pantalla {
	// tamaño mapa
	public final static int MAP_WIDTH = 3000;
	public final static int MAP_HEIGHT = 4000;
	//tamaño pantalla
	private int width;
	private int height;
	//zombis
	private ArrayList<Zombi> zombis;
	private Grupos grupos;
	// localizacion pantalla
	private int x;
	private int y;
	//jugador
	private Jugador jugador;
	
	Pantalla(int width, int height) {
		this.setX(0);
		this.setY(0);
		this.width = width;
		this.height = height;
		jugador = new Jugador(this.width, this.height);
		zombis = new ArrayList<Zombi>();
		grupos = new Grupos(zombis);
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
	
	public void apuntar(int x, int y) {
		jugador.apuntar(x, y);
		System.out.println(jugador.getAngulo());
	}
	
	public void render(Graphics g) {
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
	
	public void moverAbajo() {
		this.y += jugador.getVely();
	}
	
	public void moverArriba() {
		this.y -= jugador.getVely();
	}
	
	public void moverDerecha() {
		this.x += jugador.getVelx();
	}
	
	public void moverIzquierda() {
		this.x -= jugador.getVelx();
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
}

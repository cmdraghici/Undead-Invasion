package pruebasZombis.juego;

import java.awt.Color;
import java.awt.Graphics;

public class Zombi {
	public final static int TAM  = 10; //el tamaño del zombi
	public final static int VISION = 100; //el diametro de vision del zombi
	public final static double VELOCIDAD_MAX = 1; //la velocidad maxima del zombi
	public final static int ACTUALIZA_ANGULO = 50; //cada cuantos movimientos se actualiza la direccion del zombi
	private double posX;
	private double posY;
	private double centroX;
	private double centroY;
	private int width;
	private int height;
	private int angulo;
	private int actualizarAngulo;
	
	Zombi(int width, int height) {
		actualizarAngulo = 0;
		this.setWidth(width);
		this.setHeight(height);
		posX = (int) Math.round(Math.random() * width);
		posY = (int) Math.round(Math.random() * height);
		centroX = (posX + TAM) / 2D;
		centroY = (posY + TAM) / 2D;
	}
	
	public Graphics mover(Graphics g) {
		angulo = getAngulo();
		double seno = Math.sin(angulo);
		double coseno = Math.cos(angulo);
		posX += coseno * VELOCIDAD_MAX;
		posY += seno * VELOCIDAD_MAX;
		centroX = posX + (TAM / 2D);
		centroY = posY + (TAM / 2D);
		g.setColor(Color.white);
		g.fillOval(getPosX(), getPosY(), Zombi.TAM, Zombi.TAM);
		g.setColor(Color.red);
		g.drawArc((int)Math.round(centroX - (VISION / 2D)), (int)Math.round(centroY - (VISION / 2D)), VISION, VISION, 0, 360);
		return g;
	}
	
	public int getAngulo() {
		int angulo = 0;
		// caso en el que debemos cambiar la direccion en la que el zombi se desplaza
		if(actualizarAngulo == 0) {
			angulo = (int) Math.round(Math.random() * 360D);
			actualizarAngulo++;
		} else { //caso en el que se mantiene la direccion
			angulo = this.angulo;
			actualizarAngulo++;
			if (actualizarAngulo == ACTUALIZA_ANGULO + 1) {
				actualizarAngulo = 0;
			}
		}
		return angulo;
	}
	
	public int getPosX() {
		return (int) Math.round(posX);
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return (int) Math.round(posY);
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public double getCentroX() {
		return centroX;
	}
	public void setCentroX(int centroX) {
		this.centroX = centroX;
	}
	public double getCentroY() {
		return centroY;
	}
	public void setCentroY(int centroY) {
		this.centroY = centroY;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}

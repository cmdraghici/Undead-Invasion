package pruebasZombis.juego;

import java.awt.Color;
import java.awt.Graphics;

public class Zombi {
	public final static int TAM  = 10; // el tamanio del zombi
	public final static int VISION = 100; // el diametro de vision del zombi
	public final static double VELOCIDAD_MAX = 1; // la velocidad maxima del zombi
	public final static int ACTUALIZA_ANGULO = 50; // cada cuantos movimientos se actualiza la direccion del zombi
	private double posX;
	private double posY;
	private double centroX;
	private double centroY;
	private int width;
	private int height;
	private int angulo;
	private int actualizarAngulo;
	private boolean lider;
	private int grupo;
	
	Zombi(int width, int height) {
		actualizarAngulo = 0;
		setGrupo(-1);
		setLider(false);
		this.setWidth(width);
		this.setHeight(height);
		posX = (int) Math.round(Math.random() * width);
		posY = (int) Math.round(Math.random() * height);
		centroX = (posX + TAM) / 2D;
		centroY = (posY + TAM) / 2D;
	}
	
	public Graphics mover(Graphics g) {
		if (grupo == -1) {
			angulo = getAngulo();
			double seno = Math.sin(angulo);
			double coseno = Math.cos(angulo);
			posX += coseno * VELOCIDAD_MAX;
			posY += seno * VELOCIDAD_MAX;
			centroX = posX + (TAM / 2D);
			centroY = posY + (TAM / 2D);
		}
		g.setColor(Color.white);
		g.fillOval(getPosX(), getPosY(), Zombi.TAM, Zombi.TAM);
		g.setColor(Color.red);
		g.drawArc((int)Math.round(centroX - (VISION / 2D)),
				(int)Math.round(centroY - (VISION / 2D)), VISION, VISION, 0, 360);
		controlBordes(g);
		return g;
	}
	
	public Graphics mover(Graphics g, int angulo) {
		if (grupo != -1) {
			double seno = Math.sin(angulo);
			double coseno = Math.cos(angulo);
			posX += coseno * VELOCIDAD_MAX;
			posY += seno * VELOCIDAD_MAX;
			centroX = posX + (TAM / 2D);
			centroY = posY + (TAM / 2D);
		}
		g.setColor(Color.white);
		g.fillOval(getPosX(), getPosY(), Zombi.TAM, Zombi.TAM);
		g.setColor(Color.red);
		g.drawArc((int)Math.round(centroX - (VISION / 2D)),
				(int)Math.round(centroY - (VISION / 2D)), VISION, VISION, 0, 360);
		return g;
	}
	
	public boolean detectarZombi(double x, double y) {
		double catetoX = Math.abs(x - centroX);
		double catetoY = Math.abs(y - centroY);
		double distancia = Math.sqrt((catetoX * catetoX) + (catetoY * catetoY));
		int detectado = (VISION / 2) + (TAM / 2);
		if (distancia < detectado) {
			return true;
		}
		return false;
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
	
	public void controlBordes(Graphics g) {
		// borde izquierdo
		if ((centroX - (VISION / 2)) < 0) {
			g.setColor(Color.red);
			g.drawArc((int)Math.round((centroX + width) - (VISION / 2D)),
					(int)Math.round(centroY - (VISION / 2D)), VISION, VISION, 0, 360);
			if ((centroX - (TAM / 2)) < 0) {
				g.setColor(Color.white);
				g.fillOval(getPosX() + width, getPosY(), Zombi.TAM, Zombi.TAM);
			}
			if (centroX + (VISION / 2) < 0) {
				posX += width;
				centroX = posX + (TAM / 2D);
			}
		}
		// borde derecho
		if ((centroX + (VISION / 2)) > width) {
			g.setColor(Color.red);
			g.drawArc((int)Math.round((centroX - width) - (VISION / 2D)),
					(int)Math.round(centroY - (VISION / 2D)), VISION, VISION, 0, 360);
			if ((centroX + (TAM / 2)) > width) {
				g.setColor(Color.white);
				g.fillOval(getPosX() - width, getPosY(), Zombi.TAM, Zombi.TAM);
			}
			if (centroX - (VISION / 2) > width) {
				posX -= width;
				centroX = posX + (TAM / 2D);
			}
		}
		// borde superior
		if ((centroY - (VISION / 2)) < 0) {
			g.setColor(Color.red);
			g.drawArc((int)Math.round(centroX - (VISION / 2D)),
					(int)Math.round((centroY + height) - (VISION / 2D)), VISION, VISION, 0, 360);
			if ((centroY - (TAM / 2)) < 0) {
				g.setColor(Color.white);
				g.fillOval(getPosX(), getPosY() + height, Zombi.TAM, Zombi.TAM);
			}
			if (centroY + (VISION / 2) < 0) {
				posY += height;
				centroY = posY + (TAM / 2D);
			}
		}
		// borde inferior
		if ((centroY + (VISION / 2)) > height) {
			g.setColor(Color.red);
			g.drawArc((int)Math.round(centroX - (VISION / 2D)),
					(int)Math.round((centroY - height) - (VISION / 2D)), VISION, VISION, 0, 360);
			if ((centroY + (TAM / 2)) > height) {
				g.setColor(Color.white);
				g.fillOval(getPosX(), getPosY() - height, Zombi.TAM, Zombi.TAM);
			}
			if (centroY - (VISION / 2) > height) {
				posY -= height;
				centroY = posY + (TAM / 2D);
			}
		}
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

	public boolean isLider() {
		return lider;
	}

	public void setLider(boolean lider) {
		this.lider = lider;
	}

	public int getGrupo() {
		return grupo;
	}

	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}
}

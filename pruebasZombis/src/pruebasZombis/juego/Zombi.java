package pruebasZombis.juego;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Zombi {
	public final static int TAM  = 18;
	public final static int INGRESAR_MIEMBRO = 100;
	public final static int VISION = 300;
	public final static int OIDO = 700;
	public final static int ACTUALIZA_ANGULO = 50;
	public final static int VIDA_INICIAL = 4;
	private static final double VEL_MAX = 1.5;
	private double agresividad;
	private double posX;
	private double posY;
	private double centroX;
	private double centroY;
	private int width;
	private int height;
	private int angulo;
	private double velx;
	private double vely;
	private int actualizarAngulo;
	private boolean lider;
	private int grupo;
	private double vida;
	public int pie;
	BufferedImage mapaColision;
	private int xJugador;
	private int yJugador;
	private double objetivoX;
	private double objetivoY;
	
	Zombi(int width, int height, BufferedImage mapaColision) {
		setObjetivoX(-1);
		setObjetivoY(-1);
		this.mapaColision = mapaColision;
		agresividad = 1;
		actualizarAngulo = 0;
		setGrupo(-1);
		setLider(false);
		this.setWidth(width);
		this.setHeight(height);
		posX = (int) Math.round(Math.random() * width);
		posY = (int) Math.round(Math.random() * height);
		centroX = (posX + TAM) / 2D;
		centroY = (posY + TAM) / 2D;
		vida = VIDA_INICIAL;
	}
	
	public void mover() {
		if (grupo == -1) {
			if (detectarHumano()) {
				double angulo = Operaciones.angulo(xJugador, yJugador, centroX, centroY);
				angulo = 180 - angulo;
				double seno = Math.sin(Math.toRadians(angulo));
				double coseno = Math.cos(Math.toRadians(angulo));
				velx = coseno * agresividad * VEL_MAX;
				vely = seno * agresividad * VEL_MAX;
			} else {
				if (actualizarAngulo == 50) {
					angulo = (int) Math.round(360 * Math.random());
					actualizarAngulo = 0;
				} else {
					actualizarAngulo++;
				}
				double seno = Math.sin(angulo);
				double coseno = Math.cos(angulo);
				velx = coseno * agresividad * VEL_MAX;
				vely = seno * agresividad * VEL_MAX;
			}
			colisionBordes();
			if ((colisionMapa((int)posX, (int)posY)) && //arriba izquierda
					((colisionMapa((int)posX + TAM, (int)posY)))) {//ariba derecha
				vely = Math.abs(vely);
				actualizarAngulo = 50;
			} else if ((colisionMapa((int)posX, (int)posY + TAM) &&//abajo izquierda
					(colisionMapa((int)posX + TAM, (int)posY+ TAM)))) {//abajo derecha
				vely = - Math.abs(vely);
				actualizarAngulo = 50;
			} else if ((colisionMapa((int)posX, (int)posY)) &&//arriba izquierda
					(colisionMapa((int)posX, (int)posY + TAM))) {//abajo izquierda
				velx = Math.abs(velx);
				actualizarAngulo = 50;
			} else if ((colisionMapa((int)posX + TAM, (int)posY) &&//ariba derecha
					(colisionMapa((int)posX + TAM, (int)posY+ TAM)))) {//abajo derecha
				velx = - Math.abs(velx);
				actualizarAngulo = 50;
			} else if (colisionMapa((int)posX, (int)posY)) {//arriba izquierda
				vely = Math.abs(vely);
				velx = Math.abs(velx);
				actualizarAngulo = 50;
			} else if (colisionMapa((int)posX + TAM, (int)posY)) {//ariba derecha
				vely = Math.abs(vely);
				velx = - Math.abs(velx);
				actualizarAngulo = 50;
			} else if (colisionMapa((int)posX, (int)posY + TAM)) {//abajo izquierda
				vely = - Math.abs(vely);
				velx = Math.abs(velx);
				actualizarAngulo = 50;
			} else if (colisionMapa((int)posX + TAM, (int)posY+ TAM)) {//abajo derecha
				vely = - Math.abs(vely);
				velx = - Math.abs(velx);
				actualizarAngulo = 50;
			}

			posX += velx;
			posY += vely;
			centroX = posX + (TAM / 2D);
			centroY = posY + (TAM / 2D);
		}
	}
	
	public void moverHumano() {
		double angulo = Operaciones.angulo(xJugador, yJugador, centroX, centroY);
		angulo = 180 - angulo;
		double seno = Math.sin(Math.toRadians(angulo));
		double coseno = Math.cos(Math.toRadians(angulo));
		velx = coseno * agresividad * VEL_MAX;
		vely = seno * agresividad * VEL_MAX;
		colisionBordes();
		if ((colisionMapa((int)posX, (int)posY)) && //arriba izquierda
				((colisionMapa((int)posX + TAM, (int)posY)))) {//ariba derecha
			vely = Math.abs(vely);
			actualizarAngulo = 50;
		} else if ((colisionMapa((int)posX, (int)posY + TAM) &&//abajo izquierda
				(colisionMapa((int)posX + TAM, (int)posY+ TAM)))) {//abajo derecha
			vely = - Math.abs(vely);
			actualizarAngulo = 50;
		} else if ((colisionMapa((int)posX, (int)posY)) &&//arriba izquierda
				(colisionMapa((int)posX, (int)posY + TAM))) {//abajo izquierda
			velx = Math.abs(velx);
			actualizarAngulo = 50;
		} else if ((colisionMapa((int)posX + TAM, (int)posY) &&//ariba derecha
				(colisionMapa((int)posX + TAM, (int)posY+ TAM)))) {//abajo derecha
			velx = - Math.abs(velx);
			actualizarAngulo = 50;
		} else if (colisionMapa((int)posX, (int)posY)) {//arriba izquierda
			vely = Math.abs(vely);
			velx = Math.abs(velx);
			actualizarAngulo = 50;
		} else if (colisionMapa((int)posX + TAM, (int)posY)) {//ariba derecha
			vely = Math.abs(vely);
			velx = - Math.abs(velx);
			actualizarAngulo = 50;
		} else if (colisionMapa((int)posX, (int)posY + TAM)) {//abajo izquierda
			vely = - Math.abs(vely);
			velx = Math.abs(velx);
			actualizarAngulo = 50;
		} else if (colisionMapa((int)posX + TAM, (int)posY+ TAM)) {//abajo derecha
			vely = - Math.abs(vely);
			velx = - Math.abs(velx);
			actualizarAngulo = 50;
		}

		posX += velx;
		posY += vely;
		centroX = posX + (TAM / 2D);
		centroY = posY + (TAM / 2D);
	}

	public void moverV() {
		double realVelx = velx;
		double realVely = vely;
		while ((Math.abs(realVelx) + Math.abs(realVely)) > VEL_MAX) {
			realVelx /= 1.02D;
			realVely /= 1.02D;
		}
		if ((colisionMapa((int)posX, (int)posY)) && //arriba izquierda
				((colisionMapa((int)posX + TAM, (int)posY)))) {//ariba derecha
			vely = Math.abs(vely);
		} else if ((colisionMapa((int)posX, (int)posY + TAM) &&//abajo izquierda
				(colisionMapa((int)posX + TAM, (int)posY+ TAM)))) {//abajo derecha
			vely = - Math.abs(vely);
		} else if ((colisionMapa((int)posX, (int)posY)) &&//arriba izquierda
				(colisionMapa((int)posX, (int)posY + TAM))) {//abajo izquierda
			velx = Math.abs(velx);
		} else if ((colisionMapa((int)posX + TAM, (int)posY) &&//ariba derecha
				(colisionMapa((int)posX + TAM, (int)posY+ TAM)))) {//abajo derecha
			velx = - Math.abs(velx);
		} else if (colisionMapa((int)posX, (int)posY)) {//arriba izquierda
			vely = Math.abs(vely);
			velx = Math.abs(velx);
		} else if (colisionMapa((int)posX + TAM, (int)posY)) {//ariba derecha
			vely = Math.abs(vely);
			velx = - Math.abs(velx);
		} else if (colisionMapa((int)posX, (int)posY + TAM)) {//abajo izquierda
			vely = - Math.abs(vely);
			velx = Math.abs(velx);
		} else if (colisionMapa((int)posX + TAM, (int)posY+ TAM)) {//abajo derecha
			vely = - Math.abs(vely);
			velx = - Math.abs(velx);
		}
		posX = posX + (realVelx * agresividad);
		posY = posY + (realVely * agresividad);
		centroX = posX + (TAM / 2D);
		centroY = posY + (TAM / 2D);
		colisionBordes();
	}
	
	private boolean colisionMapa(int x, int y) {
		x += velx;
		y += vely;
		if ((x < mapaColision.getWidth()) && (y < mapaColision.getHeight()) 
				&& (x > 0) && (y > 0)) {
			Color color = new Color(mapaColision.getRGB(x, y));
			if (color.getRed() != 255) {
				return true;
			} else if (color.getBlue() != 255) {
				return true;
			} else if (color.getGreen() != 255) {
				return true;
			}
		}
		return false;
	}
	
	public void colisionBordes() {
		if (posX < 0) {
			velx = Math.abs(velx);
		}
		if ((posX + TAM) > width) {
			velx = - Math.abs(velx);
		}
		if (posY < 0) {
			vely = Math.abs(vely);
		}
		if ((posY + TAM) > height) {
			vely = - Math.abs(vely);
		}
	}
	
	public boolean disparo(double danio) {
		vida -= danio;
		if (vida <= 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public double distancia(Zombi z) {
		double catetoX = Math.abs(z.getCentroX() - centroX);
		double catetoY = Math.abs(z.getCentroY() - centroY);
		return Math.sqrt((catetoX * catetoX) + (catetoY * catetoY));
	}
	
	public double distancia(int x, int y) {
		double catetoX = Math.abs(x - centroX);
		double catetoY = Math.abs(y - centroY);
		return Math.sqrt((catetoX * catetoX) + (catetoY * catetoY));
	}
	
	public boolean detectarZombi(double x, double y) {
		double catetoX = Math.abs(x - centroX);
		double catetoY = Math.abs(y - centroY);
		double distancia = Math.sqrt((catetoX * catetoX) + (catetoY * catetoY));
		int detectado = (INGRESAR_MIEMBRO / 2) + (TAM / 2);
		if (distancia < detectado) {
			return true;
		}
		return false;
	}
	
	public double getAngulo() {
		double x = posX + velx;
		double y = posY + vely;
		return Operaciones.angulo(posX, posY, x, y);
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

	public double getVelx() {
		return velx;
	}

	public void setVelx(double velx) {
		this.velx = velx;
	}

	public double getVely() {
		return vely;
	}

	public void setVely(double vely) {
		this.vely = vely;
	}

	public double getAgresividad() {
		return agresividad;
	}

	public void setAgresividad(double agresividad) {
		this.agresividad = agresividad;
	}
	
	public void setJugadorPos(int x, int y) {
		xJugador = x;
		yJugador = y;
	}

	public boolean detectarHumano() {
		if (distancia(xJugador, yJugador) < VISION) {
			return true;
		}
		return false;
	}

	public int getxJugador() {
		return xJugador;
	}

	public void setxJugador(int xJugador) {
		this.xJugador = xJugador;
	}

	public int getyJugador() {
		return yJugador;
	}

	public void setyJugador(int yJugador) {
		this.yJugador = yJugador;
	}

	public boolean detectarDisparo(double centroX2, double centroY2) {
		if (Operaciones.distancia(centroX2, centroY2, centroX, centroY) < OIDO) {
			return true;
		}
		objetivoX = -1;
		objetivoY = -1;
		return false;
	}

	public void setObjetivo(double centroX2, double centroY2) {
		setObjetivoX(centroX2);
		setObjetivoY(centroY2);
	}

	public double getObjetivoX() {
		return objetivoX;
	}

	public void setObjetivoX(double objetivoX) {
		this.objetivoX = objetivoX;
	}

	public double getObjetivoY() {
		return objetivoY;
	}

	public void setObjetivoY(double objetivoY) {
		this.objetivoY = objetivoY;
	}
}

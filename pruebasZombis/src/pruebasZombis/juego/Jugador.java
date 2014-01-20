package pruebasZombis.juego;

public class Jugador {
	public final static int TAM_JUGADOR = 20;
	private double posXPantalla;
	private double posYPantalla;
	private double posXMapa;
	private double posYMapa;
	private double centroX;
	private double centroY;
	private double velx;
	private double vely;
	private double apuntaX;
	private double apuntaY;
	
	Jugador(int width, int height) {
		setPosXPantalla((width / 2.0D) - (TAM_JUGADOR / 2.0D));
		setPosYPantalla((height / 2.0D) - (TAM_JUGADOR / 2.0D));
		setVelx(5);
		setVely(5);
		apuntaX = 0;
		apuntaY = 0;
	}
	
	public void apuntar(int x, int y) {
		apuntaX = x;
		apuntaY = y;
	}

	public int getPosXPantalla() {
		return (int) Math.round(this.posXPantalla);
	}

	public void setPosXPantalla(double posXPantalla) {
		this.posXPantalla = posXPantalla;
	}

	public int getPosYPantalla() {
		return (int) Math.round(this.posYPantalla);
	}

	public void setPosYPantalla(double posYPantalla) {
		this.posYPantalla = posYPantalla;
	}

	public void calcPosicion(int x, int y) {
		posXMapa = x + posXPantalla;
		posYMapa = y + posYPantalla;
		setCentroX(posXMapa + (TAM_JUGADOR / 2.0D));
		setCentroY(posYMapa + (TAM_JUGADOR / 2.0D));
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

	public double getCentroX() {
		return centroX;
	}

	public void setCentroX(double centroX) {
		this.centroX = centroX;
	}

	public double getCentroY() {
		return centroY;
	}

	public void setCentroY(double centroY) {
		this.centroY = centroY;
	}

	public double getAngulo() {
		double angulo = 0;
		double a = Math.abs(apuntaY - centroY);
		double b = Math.abs(apuntaX - centroX);
		angulo = Math.atan(a / b);
		return Math.toDegrees(angulo);
	}
}

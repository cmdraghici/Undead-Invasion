package pruebasZombis.juego;

public class Jugador {
	public final static int TAM_JUGADOR = 18;
	public final static int VIDA = 1;
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
	private int vida;
	
	Jugador(int width, int height) {
		setPosXPantalla((width / 2.0D) - (TAM_JUGADOR / 2.0D));
		setPosYPantalla((height / 2.0D) - (TAM_JUGADOR / 2.0D));
		setVelx(6);
		setVely(6);
		apuntaX = 0;
		apuntaY = 0;
		setVida(VIDA);
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
		return Operaciones.angulo(centroX, centroY, apuntaX, apuntaY);
	}

	public void mordisco() {
		setVida(getVida() - 1);
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}
}

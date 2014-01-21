package pruebasZombis.juego;

public class Disparo {
	private int posX;
	private int posY;
	private double angulo;
	private double velx;
	private double vely;
	private double danio;
	
	Disparo(int x, int y, double a, double v, double d) {
		posX = x;
		posY = y;
		angulo = a;
		danio= d;
		double seno = Math.sin(Math.toRadians(-angulo));
		double coseno = Math.cos(Math.toRadians(-angulo));
		velx = coseno * v;
		vely = seno * v;
	}
	
	public void actualizar() {
		posX += velx;
		posY += vely;
	}
	
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public double getAngulo() {
		return angulo;
	}
	public void setAngulo(double angulo) {
		this.angulo = angulo;
	}

	public double getDanio() {
		return danio;
	}
	public void setDanio(double danio) {
		this.danio = danio;
	}
}

package pruebasZombis.juego;

public class Velocidad {
	private double velx;
	private double vely;
	
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
	
	public void addX(double x) {
		velx += x;
	}
	
	public void addY(double y) {
		vely += y;
	}
	
	public void normalizaX(double x) {
		velx /= x;
	}
	
	public void normalizaY(double y) {
		vely /= y;
	}
	
	public void subX(double x) {
		velx -= x;
	}
	
	public void subY(double y) {
		vely -= y;
	}
	
	public void suma(Velocidad v) {
		velx += v.getVelx();
		vely += v.getVely();
	}
}

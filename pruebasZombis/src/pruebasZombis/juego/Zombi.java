package pruebasZombis.juego;

public class Zombi {
	public final static int TAM  = 10;
	private int posX;
	private int posY;
	private int centroX;
	private int centroY;
	private int width;
	private int height;
	
	Zombi(int width, int height) {
		this.width = width;
		this.height = height;
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
	public int getCentroX() {
		return centroX;
	}
	public void setCentroX(int centroX) {
		this.centroX = centroX;
	}
	public int getCentroY() {
		return centroY;
	}
	public void setCentroY(int centroY) {
		this.centroY = centroY;
	}
}

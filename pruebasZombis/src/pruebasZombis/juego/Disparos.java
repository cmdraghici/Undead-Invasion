package pruebasZombis.juego;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Disparos {
	public final static int TAM = 5;
	
	private ArrayList<Disparo> disparos;
	private int width;
	private int height;
	private boolean disparo;

	Disparos(int w, int h) {
		width = w;
		height = h;
		disparos = new ArrayList<Disparo>();
		disparo = false;
	}
	
	public void add(double centroX, double centroY, double x2, double y2) {
		if (this.disparo) {
			double angulo = Operaciones.angulo(centroX, centroY, x2, y2);
			Disparo d = new Disparo((int) centroX, (int) centroY, angulo, 10D, 1D);
			disparos.add(d);
			this.disparo = false;
		}
	}

	public void actualizar(Graphics g, ArrayList<Zombi> zombis, int x, int y) {
		if (disparos.size() > 0) {
			g.setColor(Color.green);
			for (int i = 0; i < disparos.size(); i++) {
				disparos.get(i).actualizar();
				disparos.get(i).colisionZombi(zombis);
				if ((disparos.get(i).getPosX() > width) || (disparos.get(i).getPosX() < 0)
						|| (disparos.get(i).getPosY() > height) || 
						(disparos.get(i).getPosY() < 0)) {
					disparos.remove(i);
				}
			}
			for (int i = 0; i < disparos.size(); i++) {
				g.fillOval(disparos.get(i).getPosX() - x, disparos.get(i).getPosY() - y, TAM, TAM);
			}
		}
	}

	public void setDisparo(boolean b) {
		disparo = b;
	}
}

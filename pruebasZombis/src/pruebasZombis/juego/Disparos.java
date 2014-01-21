package pruebasZombis.juego;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Disparos {
	public final static int TAM = 5;
	public final static double DANIO = 1;
	
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
			Disparo d = new Disparo((int) centroX, (int) centroY, angulo, 10D, DANIO);
			disparos.add(d);
			this.disparo = false;
		}
	}

	public void actualizar(Graphics g, ArrayList<Zombi> zombis, int x, int y,
			BufferedImage mapaColision) {
		if (disparos.size() > 0) {
			g.setColor(Color.yellow);
			for (int i = 0; i < disparos.size(); i++) {
				disparos.get(i).actualizar();
				if ((disparos.get(i).getPosX() > width) || (disparos.get(i).getPosX() < 0)
						|| (disparos.get(i).getPosY() > height) || 
						(disparos.get(i).getPosY() < 0)) {
					disparos.remove(i);
				} else {
					if (!colisionMapa(mapaColision, i)) {
						colisionZombi(zombis, i);
					}
				}
			}
			for (int i = 0; i < disparos.size(); i++) {
				g.fillOval(disparos.get(i).getPosX() - x, disparos.get(i).getPosY() - y, TAM, TAM);
			}
		}
	}

	private boolean colisionMapa(BufferedImage mapaColision, int i) {
		int x = disparos.get(i).getPosX();
		int y = disparos.get(i).getPosY();
		if ((x < mapaColision.getWidth()) && (y < mapaColision.getHeight()) 
				&& (x > 0) && (y > 0)) {
			Color color = new Color(mapaColision.getRGB(x, y));
			if (color.getRed() != 255) {
				disparos.remove(i);
				return true;
			} else if (color.getBlue() != 255) {
				disparos.remove(i);
				return true;
			} else if (color.getGreen() != 255) {
				disparos.remove(i);
				return true;
			}
		}
		return false;
	}

	private void colisionZombi(ArrayList<Zombi> zombis, int i) {
		int colision = -1;
		for (int j = 0; j < zombis.size(); j++) {
			if ((disparos.get(i).getPosX() > zombis.get(j).getPosX()) && 
					(disparos.get(i).getPosX() < (zombis.get(j).getPosX() + Zombi.TAM)) && 
					(disparos.get(i).getPosY() > (zombis.get(j).getPosY())) &&
					(disparos.get(i).getPosY() < (zombis.get(j).getPosY() + Zombi.TAM))) {
				colision = j;
			}
		}
		if (colision != -1) {
			if (!zombis.get(colision).disparo(disparos.get(i).getDanio())) {
				zombis.remove(colision);
			}
			disparos.remove(i);
		}
	}

	public void setDisparo(boolean b) {
		disparo = b;
	}
}

package pruebasZombis.juego;

import java.awt.Graphics;
import java.util.ArrayList;

public class Grupo {
	private static final int GRUPO_MAX = 5;
	private ArrayList<Zombi> zombis;
	private int angulo;
	private int actualizarAngulo;
	
	Grupo() {
		setZombis(new ArrayList<Zombi>());
		angulo = 0;
		actualizarAngulo = 0;
	}
	
	public void addMiembro(Zombi zombi) {
		zombis.add(zombi);
	}

	public ArrayList<Zombi> getZombis() {
		return zombis;
	}

	public void setZombis(ArrayList<Zombi> zombis) {
		this.zombis = zombis;
	}

	public void mover(Graphics g) {
		//if (zombis.size() > 0) {
			//angulo = zombis.get(0).getAngulo();
		//}
		angulo = getAngulo();
		for (int i = 0; i < zombis.size(); i++) {
			zombis.get(i).mover(g, angulo);
		}
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
			if (actualizarAngulo == Zombi.ACTUALIZA_ANGULO + 1) {
				actualizarAngulo = 0;
			}
		}
		return angulo;
	}

	public boolean pedirIngreso() {
		if (zombis.size() >= GRUPO_MAX) {
			return false;
		}
		return true;
	}
}

package pruebasZombis.juego;

import java.util.ArrayList;

public class Grupo {
	private static final int GRUPO_MAX = 10;
	private static final double ALINEACION = 90;
	private static final double SEPARACION = 20;
	private static final double COHERENCIA = 20;
	private ArrayList<Zombi> zombis;
	
	Grupo() {
		setZombis(new ArrayList<Zombi>());
	}
	
	public void addMiembro(Zombi zombi) {
		zombis.add(zombi);
	}

	public void mover() {
		for (int i = 0; i < zombis.size(); i++) {
			Velocidad vel = new Velocidad();
			Velocidad vel2 = new Velocidad();
			Velocidad vel3 = new Velocidad();
			vel = alineamiento(i);
			vel2 = separacion(i);
			vel3 = coherencia(i);
			vel.suma(vel2);
			vel.suma(vel3);
			zombis.get(i).setVelx(zombis.get(i).getVelx() + vel.getVelx());
			zombis.get(i).setVely(zombis.get(i).getVely() + vel.getVely());
			zombis.get(i).moverV();
		}
	}
	
	public Velocidad alineamiento(int i) {
		Velocidad vel = new Velocidad();
		vel.setVelx(0);
		vel.setVely(0);
		
		for (int j = 0; j < zombis.size(); j++) {
			if (i != j) {
				vel.addX(zombis.get(j).getPosX());
				vel.addY(zombis.get(j).getPosY());
			}
		}
		vel.normalizaX(zombis.size() - 1);
		vel.normalizaY(zombis.size() - 1);
		vel.setVelx((vel.getVelx() - zombis.get(i).getPosX()) / ALINEACION);
		vel.setVely((vel.getVely() - zombis.get(i).getPosY()) / ALINEACION);
		
		return vel;
	}
	
	public Velocidad separacion(int i) {
		Velocidad vel = new Velocidad();
		vel.setVelx(0);
		vel.setVely(0);
		
		for (int j = 0; j < zombis.size(); j++) {
			if (i != j) {
				if ((Math.abs(zombis.get(j).getPosX() - zombis.get(i).getPosX())) < SEPARACION) {
					vel.subX(zombis.get(j).getPosX() - zombis.get(i).getPosX());
				}
				if ((Math.abs(zombis.get(j).getPosY() - zombis.get(i).getPosY())) < SEPARACION) {
					vel.subY(zombis.get(j).getPosY() - zombis.get(i).getPosY());
				}
			}
		}
		return vel;
	}
	
	public Velocidad coherencia(int i) {
		Velocidad vel = new Velocidad();
		vel.setVelx(0);
		vel.setVely(0);
		
		for (int j = 0; j < zombis.size(); j++) {
			if (i != j) {
				vel.addX(zombis.get(j).getVelx());
				vel.addY(zombis.get(j).getVely());
			}
		}
		vel.normalizaX(zombis.size() - 1);
		vel.normalizaY(zombis.size() - 1);
		vel.setVelx((vel.getVelx() - zombis.get(i).getVelx()) / COHERENCIA);
		vel.setVely((vel.getVely() - zombis.get(i).getVely()) / COHERENCIA);
		
		return vel;
	}
	
	public boolean pedirIngreso() {
		if (zombis.size() >= GRUPO_MAX) {
			return false;
		}
		return true;
	}
	
	public ArrayList<Zombi> getZombis() {
		return zombis;
	}

	public void setZombis(ArrayList<Zombi> zombis) {
		this.zombis = zombis;
	}
}

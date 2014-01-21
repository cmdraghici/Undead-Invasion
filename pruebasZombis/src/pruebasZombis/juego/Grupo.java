package pruebasZombis.juego;

import java.util.ArrayList;

public class Grupo {
	private static final int GRUPO_MAX = 5;
	private static final double ALINEACION = 90;
	private static final double SEPARACION = 10;
	private static final double COHERENCIA = 80;
	private static final double TENDENCIA = 10;
	private ArrayList<Zombi> zombis;
	private int objetivoX;
	private int objetivoY;
	private boolean buscarHumano;
	
	Grupo() {
		setZombis(new ArrayList<Zombi>());
	}
	
	public void addMiembro(Zombi zombi) {
		zombis.add(zombi);
	}

	public void mover() {
		buscarHumano = false;
		for (int i = 0; i < zombis.size(); i++) {
			if (zombis.get(i).detectarHumano()) {
				buscarHumano = true;
			} else {
				Velocidad vel = new Velocidad();
				Velocidad vel2 = new Velocidad();
				Velocidad vel3 = new Velocidad();
			
				vel = alineamiento(i);
				vel2 = separacion(i);
				vel3 = coherencia(i);
				if (zombis.get(i).getObjetivoX() != -1) {
					Velocidad vel4 = new Velocidad();
					objetivoX = (int) Math.round(zombis.get(i).getObjetivoX());
					objetivoY = (int) Math.round(zombis.get(i).getObjetivoY());
					if (Operaciones.distancia(objetivoX, objetivoY, 
							zombis.get(i).getCentroX(), zombis.get(i).getCentroY()) < 
							Zombi.TAM) {
						zombis.get(i).setObjetivoX(-1);
						zombis.get(i).setObjetivoY(-1);
					}
					vel4 = objetivo(i);
					vel.suma(vel4);
				}
				vel.suma(vel2);
				vel.suma(vel3);
				zombis.get(i).setVelx(zombis.get(i).getVelx() + vel.getVelx());
				zombis.get(i).setVely(zombis.get(i).getVely() + vel.getVely());
				zombis.get(i).moverV();
			}
		}
		if (buscarHumano) {
			for (int i = 0; i < zombis.size(); i++) {
				zombis.get(i).moverHumano();
			}
		}
	}
	
	public Velocidad objetivo(int i) {
		Velocidad vel = new Velocidad();
		vel.setVelx(0);
		vel.setVely(0);
		vel.setVelx((objetivoX - zombis.get(i).getPosX()) / TENDENCIA);
		vel.setVely((objetivoY - zombis.get(i).getPosY()) / TENDENCIA);
		return vel;
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

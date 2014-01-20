package pruebasZombis.juego;

import java.util.ArrayList;

public class Grupos {
	
	private ArrayList<Zombi> zombis;
	private int contadorGrupo;
	private ArrayList<Grupo> grupos;
	
	Grupos(ArrayList<Zombi> zombi) {
		grupos = new ArrayList<Grupo>();
		contadorGrupo = 0;
		setZombis(zombi);
	}
	// sirve para la formacion de nuevos grupos y el ingreso de zombies
	// en los grupos ya formados
	public void controlGrupos() {
		for (int i = 0; i < zombis.size(); i++) {
			for (int j = 0; j < zombis.size(); j++) {
				if (i != j) {
					double x = zombis.get(j).getCentroX();
					double y = zombis.get(j).getCentroY();
					if (zombis.get(i).detectarZombi(x, y)) {
						if ((zombis.get(i).getGrupo() == -1) && 
								(zombis.get(j).getGrupo() == -1)) {
							Grupo grupo = new Grupo();
							zombis.get(i).setLider(true);
							zombis.get(i).setGrupo(contadorGrupo);
							zombis.get(j).setGrupo(contadorGrupo);
							grupo.addMiembro(zombis.get(i));
							grupo.addMiembro(zombis.get(j));
							grupos.add(grupo);
							contadorGrupo++;
						} else if (zombis.get(j).getGrupo() == -1) {
							int grupoZombi = zombis.get(i).getGrupo();
							if (grupos.get(grupoZombi).pedirIngreso()) {
								zombis.get(j).setGrupo(grupoZombi);
								grupos.get(grupoZombi).addMiembro(zombis.get(j));
							}
						}
					}
				}
			}
		}
	}

	public ArrayList<Zombi> getZombis() {
		return zombis;
	}

	public void setZombis(ArrayList<Zombi> zombis) {
		this.zombis = zombis;
	}

	public void mover() {
		for (int i = 0; i < grupos.size(); i++) {
			grupos.get(i).mover();
		}
	}
}

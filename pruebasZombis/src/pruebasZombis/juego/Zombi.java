package pruebasZombis.juego;

public class Zombi {
	public final static int TAM  = 10; // el tamanio del zombi
	public final static int VISION = 100; // el diametro de vision del zombi
	public final static int ACTUALIZA_ANGULO = 50; // cada cuantos movimientos se actualiza la direccion del zombi
	private static final double VEL_MAX = 1.5;
	private double agresividad;
	private double posX;
	private double posY;
	private double centroX;
	private double centroY;
	private int width;
	private int height;
	private int angulo;
	private double velx;
	private double vely;
	private int actualizarAngulo;
	private boolean lider;
	private int grupo;
	
	Zombi(int width, int height) {
		agresividad = 1;
		actualizarAngulo = 0;
		setGrupo(-1);
		setLider(false);
		this.setWidth(width);
		this.setHeight(height);
		posX = (int) Math.round(Math.random() * width);
		posY = (int) Math.round(Math.random() * height);
		centroX = (posX + TAM) / 2D;
		centroY = (posY + TAM) / 2D;
	}
	
	public void mover() {
		if (grupo == -1) {
			if (actualizarAngulo == 50) {
				angulo = (int) Math.round(360 * Math.random());
				actualizarAngulo = 0;
			} else {
				actualizarAngulo++;
			}
			double seno = Math.sin(angulo);
			double coseno = Math.cos(angulo);
			velx = coseno * agresividad;
			vely = seno * agresividad;
			posX += velx;
			posY += vely;
			centroX = posX + (TAM / 2D);
			centroY = posY + (TAM / 2D);
			colisionBordes();
		}
	}
	
	public void mover(int angulo) {
		if (grupo != -1) {
			double seno = Math.sin(angulo);
			double coseno = Math.cos(angulo);
			velx = coseno * agresividad;
			vely = seno * agresividad;
			posX += velx;
			posY += vely;
			centroX = posX + (TAM / 2D);
			centroY = posY + (TAM / 2D);
		}
		colisionBordes();
	}
	
	public void moverV() {
		double realVelx = velx;
		double realVely = vely;
		while ((Math.abs(realVelx) + Math.abs(realVely)) > VEL_MAX) {
			realVelx /= 1.02D;
			realVely /= 1.02D;
		}
		posX = posX + (realVelx * agresividad);
		posY = posY + (realVely * agresividad);
		centroX = posX + (TAM / 2D);
		centroY = posY + (TAM / 2D);
		colisionBordes();
	}
	
	public double distancia(Zombi z) {
		double catetoX = Math.abs(z.getCentroX() - centroX);
		double catetoY = Math.abs(z.getCentroY() - centroY);
		return Math.sqrt((catetoX * catetoX) + (catetoY * catetoY));
	}
	
	public double distancia(int x, int y) {
		double catetoX = Math.abs(x - centroX);
		double catetoY = Math.abs(y - centroY);
		return Math.sqrt((catetoX * catetoX) + (catetoY * catetoY));
	}
	
	public boolean detectarZombi(double x, double y) {
		double catetoX = Math.abs(x - centroX);
		double catetoY = Math.abs(y - centroY);
		double distancia = Math.sqrt((catetoX * catetoX) + (catetoY * catetoY));
		int detectado = (VISION / 2) + (TAM / 2);
		if (distancia < detectado) {
			return true;
		}
		return false;
	}
	
	public double getAngulo() {
		double x = posX + velx;
		double y = posY + vely;
		return Operaciones.angulo(posX, posY, x, y);
	}
	
	public void colisionBordes() {
		if (posX < 0) {
			velx = Math.abs(velx);
		}
		if ((posX + TAM) > width) {
			velx = - Math.abs(velx);
		}
		if (posY < 0) {
			vely = Math.abs(vely);
		}
		if ((posY + TAM) > height) {
			vely = - Math.abs(vely);
		}
	}
	
	public int getPosX() {
		return (int) Math.round(posX);
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return (int) Math.round(posY);
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public double getCentroX() {
		return centroX;
	}
	public void setCentroX(int centroX) {
		this.centroX = centroX;
	}
	public double getCentroY() {
		return centroY;
	}
	public void setCentroY(int centroY) {
		this.centroY = centroY;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isLider() {
		return lider;
	}

	public void setLider(boolean lider) {
		this.lider = lider;
	}

	public int getGrupo() {
		return grupo;
	}

	public void setGrupo(int grupo) {
		this.grupo = grupo;
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

	public double getAgresividad() {
		return agresividad;
	}

	public void setAgresividad(double agresividad) {
		this.agresividad = agresividad;
	}
}

package pruebasZombis.juego;

public class Operaciones {
	
	public static double angulo(double centroX, double centroY, double x2, double y2) {
		double angulo = 0;
		double catcontiguo = (x2 - centroX);
		if (catcontiguo < 0)
			catcontiguo = 0 - catcontiguo;
		double catopuesto = y2 - centroY;
		if (catopuesto < 0)
			catopuesto = 0 - catopuesto;
		angulo = (Math.atan((catopuesto / catcontiguo)));
		if (x2 < centroX) {
			angulo = ((Math.toRadians(90) - angulo) + Math.toRadians(90));
		}
		if (y2 > centroY) {
			double var = (Math.toRadians(180) - angulo);
			angulo = Math.toRadians(180) + var;
		}
		angulo = (Math.toDegrees(angulo));
		return angulo;
	}
	
	public static double distancia(double x1, double y1, double x2, double y2) {
		double catetoX = Math.abs(x1 - x2);
		double catetoY = Math.abs(y1 - y2);
		return Math.sqrt((catetoX * catetoX) + (catetoY * catetoY));
	}
}

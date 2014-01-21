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
}

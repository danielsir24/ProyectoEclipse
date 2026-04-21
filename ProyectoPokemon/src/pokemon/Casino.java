package pokemon;

public class Casino {

	// ----- 1. MECÁNICA DE LA RULETA -----
	public int jugarRuleta(int apuesta, Integer numeroElegido, String colorElegido) {
		
		// El casino genera el número (1 al 37) y el color (rojo/negro)
		int numeroRuleta = (int) (Math.random() * 37) + 1; 
		String colorRuleta = (Math.random() > 0.5) ? "rojo" : "negro"; 

		System.out.println(" RULETA GIRA... Cae en: " + numeroRuleta + " " + colorRuleta.toUpperCase());

		boolean aciertoNumero = (numeroElegido != null && numeroElegido == numeroRuleta);
		boolean aciertoColor = (colorElegido != null && colorElegido.equalsIgnoreCase(colorRuleta));

		int premio = 0;

		if (aciertoNumero) {
			System.out.println(" ¡Acertaste el número!");
			premio += (apuesta * 10); 
		}

		if (aciertoColor) {
			System.out.println(" ¡Acertaste el color!");
			premio += (apuesta * 2); 
		}

		
		if (!aciertoNumero && !aciertoColor) {
			System.out.println(" No acertaste nada. Pierdes la apuesta.");
		}

		return premio; 
	}

	// Cara o cruz
	public int jugarCaraCruz(int apuesta, String eleccion) {
		
		
		String resultadoMoneda = (Math.random() > 0.5) ? "cara" : "cruz";
		
		System.out.println(" La moneda gira en el aire... ¡Y sale " + resultadoMoneda.toUpperCase() + "!");

		int premio = 0;

		
		if (eleccion != null && eleccion.equalsIgnoreCase(resultadoMoneda)) {
			System.out.println(" ¡Has ganado! Elegiste " + eleccion + " y acertaste.");
			premio = apuesta * 2; 
		} else {
			
			System.out.println(" Has perdido. Elegiste " + eleccion + ".");
		}

		return premio; 
	}

	// Adivinar numero
	
	public int generarNumeroSecreto() {
		return (int) (Math.random() * 20) + 1; 
	}

	public int comprobarPremioAdivinar(int intento) {
		int premio = 0;
		
		if (intento == 1) {
			premio = 1000;
		} else if (intento == 2) {
			premio = 750;
		} else if (intento == 3) {
			premio = 500;
		} else if (intento == 4) {
			premio = 250;
		} else {
			premio = 0; 
		}
		
		return premio;
	}

}
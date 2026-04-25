package pokemon;

public class Casino {

	// ----- 1. MECÁNICA DE LA RULETA -----
	// Este método sirve para el juego de la ruleta. Recibe la apuesta, el número y el color que elige el usuario.
	public int jugarRuleta(int apuesta, Integer numeroElegido, String colorElegido) {
		
		// Aquí el casino genera un número aleatorio entre 1 y 37
		int numeroRuleta = (int) (Math.random() * 37) + 1; 
		// Aquí decide si sale rojo o negro de forma aleatoria (50% de probabilidad)
		String colorRuleta = (Math.random() > 0.5) ? "rojo" : "negro"; 

		// Imprimimos por consola lo que ha salido para que el jugador lo vea
		System.out.println(" RULETA GIRA... Cae en: " + numeroRuleta + " " + colorRuleta.toUpperCase());

		// Comprobamos si el usuario ha acertado el número o el color
		boolean aciertoNumero = (numeroElegido != null && numeroElegido == numeroRuleta);
		boolean aciertoColor = (colorElegido != null && colorElegido.equalsIgnoreCase(colorRuleta));

		int premio = 0;

		// Si acierta el número, le damos 10 veces lo que apostó
		if (aciertoNumero) {
			System.out.println(" ¡Acertaste el número!");
			premio += (apuesta * 10); 
		}

		// Si acierta el color, le damos el doble de su apuesta
		if (aciertoColor) {
			System.out.println(" ¡Acertaste el color!");
			premio += (apuesta * 2); 
		}

		// Si no acierta nada de nada, avisamos por consola que pierde
		if (!aciertoNumero && !aciertoColor) {
			System.out.println(" No acertaste nada. Pierdes la apuesta.");
		}

		// Devolvemos el premio total conseguido
		return premio; 
	}

	// Método para el minijuego de tirar la moneda (Cara o Cruz)
	public int jugarCaraCruz(int apuesta, String eleccion) {
		
		// Generamos el resultado de la moneda (cara o cruz) al azar
		String resultadoMoneda = (Math.random() > 0.5) ? "cara" : "cruz";
		
		System.out.println(" La moneda gira en el aire... ¡Y sale " + resultadoMoneda.toUpperCase() + "!");

		int premio = 0;

		// Comparamos lo que eligió el usuario con lo que salió (sin importar mayúsculas)
		if (eleccion != null && eleccion.equalsIgnoreCase(resultadoMoneda)) {
			System.out.println(" ¡Has ganado! Elegiste " + eleccion + " y acertaste.");
			premio = apuesta * 2; // Gana el doble
		} else {
			// Si falla, el premio se queda en 0
			System.out.println(" Has perdido. Elegiste " + eleccion + ".");
		}

		return premio; 
	}

	// Métodos para el juego de adivinar el número secreto
	
	// Genera un número al azar entre 1 y 20
	public int generarNumeroSecreto() {
		return (int) (Math.random() * 20) + 1; 
	}

	// Este método calcula el premio según el número de intentos que haya gastado el jugador
	public int comprobarPremioAdivinar(int intento) {
		int premio = 0;
		
		// Cuantos menos intentos use, más dinero gana
		if (intento == 1) {
			premio = 1000;
		} else if (intento == 2) {
			premio = 750;
		} else if (intento == 3) {
			premio = 500;
		} else if (intento == 4) {
			premio = 250;
		} else {
			// Si tarda más de 4 intentos, no gana nada
			premio = 0; 
		}
		
		return premio;
	}

}
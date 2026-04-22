package pokemon.Controller;

import pokemon.Casino;
import pokemon.Entrenador;
import pokemon.EntrenadorDAO;
import java.util.Scanner;

public class CasinoController {

    public void iniciarCasino(Entrenador entrenadorActual) {
        Scanner scanner = new Scanner(System.in);
        Casino miCasino = new Casino();
        EntrenadorDAO dao = new EntrenadorDAO();
        boolean salir = false;

        System.out.println("BIENVENIDO AL CASINO POKÉMON");

        while (!salir) {
            System.out.println("Saldo actual: " + entrenadorActual.getPokedollars() + " Pokedollars.");
            System.out.println("1. Jugar a Cara o Cruz");
            System.out.println("2. Jugar a la Ruleta");
            System.out.println("3. Salir del Casino");
            
            int opcion = scanner.nextInt();

            if (opcion == 1) {
                System.out.print("¿Cuánto quieres apostar?: ");
                int apuesta = scanner.nextInt();
                
                System.out.print("¿Cara o Cruz?: ");
                String eleccion = scanner.next();

                // Aquí usamos la lógica de pago
                if (entrenadorActual.gastarPokedollars(apuesta)) {
                    int premio = miCasino.jugarCaraCruz(apuesta, eleccion);
                    
                    if (premio > 0) {
                        entrenadorActual.ganarPokedollars(premio);
                    }
                    
                    dao.actualizarPokedollars(entrenadorActual);
                } else {
                    System.out.println("¡No tienes suficientes Pokedollars para esa apuesta!");
                }
                
            } else if (opcion == 2) {
                // Aquí podrías copiar una estructura similar para pedirle al usuario su número y color para la ruleta
                System.out.println("Ruleta en construcción...");
                
            } else if (opcion == 3) {
                System.out.println("Saliendo del casino... ¡Vuelve pronto!");
                salir = true;
            } else {
                System.out.println("Opción no válida.");
            }
        }
    }
}
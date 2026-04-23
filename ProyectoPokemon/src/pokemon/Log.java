package pokemon;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Log {
    private String nombreFichero;
    private List<String> lineasLog;

    public Log() {
        this.lineasLog = new ArrayList<>();
        crearFicheroLog();
    }

    private void crearFicheroLog() {
        // Formato exigido para el nombre del archivo: YYYYMMDDhhmmss.log
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fecha = sdf.format(new Date());
        this.nombreFichero = "logs/" + fecha + ".log";
        
        File directorio = new File("logs");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
    }

    // Registra la línea con el formato exacto de la documentación
    public void registrarEvento(String tipoEvento, Pokemon pJugador, Pokemon pRival, String accionJugador, String accionRival, int turno) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String fecha = sdf.format(new Date());
        
        String estadoJugador = pJugador.estaDebilitado() ? "KO" : "OK";
        String estadoRival = pRival.estaDebilitado() ? "KO" : "OK";
        
        String nomEntrenador1 = Main.entrenadorLogueado != null ? Main.entrenadorLogueado.getNom_Entrenador() : "Jugador";
        
        // Estructura exigida en el documento de Logs
        String linea = String.format("%s INFO %s pokemon={\"%s\", %d, %s, %s}, pokemonRival={\"%s\", %d, Rival, %s}, turno=%d",
            fecha, tipoEvento, 
            pJugador.getMote() != null ? pJugador.getMote() : pJugador.getNombre(), pJugador.getNivel(), nomEntrenador1, estadoJugador,
            pRival.getNombre(), pRival.getNivel(), estadoRival, turno);
        
        lineasLog.add(linea);
        
        if (accionJugador != null) lineasLog.add("Entrenador: " + accionJugador);
        if (accionRival != null) lineasLog.add("Rival: " + accionRival);
    }

    // Crea el fichero físico al terminar el combate
    public void guardarEnFichero() {
        try (FileWriter fw = new FileWriter(nombreFichero)) {
            for (String linea : lineasLog) {
                fw.write(linea + "\n");
            }
            System.out.println("Log guardado correctamente en: " + nombreFichero);
        } catch (IOException e) {
            System.err.println("Error crítico al guardar el log: " + e.getMessage());
        }
    }
}
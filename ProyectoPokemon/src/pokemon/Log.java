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
        // Unificamos a "logs"
        File directorio = new File("logs");
        if(!directorio.exists()) {
            directorio.mkdirs(); 
        }
        crearFicheroLog();
    }

    private void crearFicheroLog() {
        // Formato exigido: YYYYMMDDhhmmss.log
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fechaActual = sdf.format(new Date());
        this.nombreFichero = "logs/" + fechaActual + ".log";
    }

    public void registrarEvento(String tipoEvento, Pokemon pJugador, Pokemon pRival, String accionJugador, String accionRival, int turno) {
       
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String fechaHora = sdf.format(new Date());
        
        String estadoJugador = pJugador.estaDebilitado() ? "KO" : "OK";
        String estadoRival = pRival.estaDebilitado() ? "KO" : "OK";
        
        String nomEntrenador1 = (Main.entrenadorLogueado != null) ? Main.entrenadorLogueado.getNom_Entrenador() : "Entrenador1";
        
        // Formato 
        String lineaInfo = String.format("%s INFO %s pokemon = {\"%s\", %d, %s, %s}, pokemonRival={\"%s\", %d, Rival, %s}, turno=%d",
                fechaHora, tipoEvento, 
                pJugador.getMote() != null ? pJugador.getMote() : pJugador.getNombre(), 
                pJugador.getNivel(), nomEntrenador1, estadoJugador,
                pRival.getNombre(), pRival.getNivel(), estadoRival, turno);
            
            lineasLog.add(lineaInfo);
        
        // Formato de turnos
        if (accionJugador != null || accionRival != null) {
            lineasLog.add("Turno " + turno + ":");
            if (accionJugador != null) lineasLog.add("Entrenador: " + accionJugador);
            if (accionRival != null) lineasLog.add("Rival: " + accionRival);
        }
    }

    public void guardarEnFichero() {
        
        try (FileWriter fw = new FileWriter(nombreFichero)) {
            for (String linea : lineasLog) {
                fw.write(linea + System.lineSeparator());
            }
            System.out.println("Log guardado correctamente en: " + nombreFichero);
        } catch (IOException e) {
            System.err.println("Error: No se ha podido guardar el archivo de log. Verifique permisos en la carpeta /logs.");
            e.printStackTrace();
        }
    }
}
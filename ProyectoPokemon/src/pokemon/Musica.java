package pokemon;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Musica {

    // Variable estática para controlar el reproductor en toda la aplicación
    private static MediaPlayer mediaPlayer;

    // Método para empezar a reproducir una canción dándole la ruta del archivo
    public static void iniciar(String ruta) {
        // Solo la iniciamos si no hay otra sonando ya
        if (mediaPlayer == null) {
            // Cargamos el archivo de sonido desde los recursos del proyecto
            Media media = new Media(Musica.class.getResource(ruta).toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            
            // Hacemos que la música no pare nunca (bucle infinito)
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            // Le damos al play
            mediaPlayer.play();
            // Ajustamos el volumen bajito (5%) para que no moleste al usuario
            mediaPlayer.setVolume(0.05);
        }
    }

    // Este método sirve para el botón de silenciar: si hay sonido lo quita, y si no, lo pone
    public static void toggleMute() {
        if (mediaPlayer != null) {
            // Cambiamos el estado de "Mute" al contrario del que tenga ahora
            mediaPlayer.setMute(!mediaPlayer.isMute());
        }
    }

    // Función rápida para saber si la música está silenciada o no actualmente
    public static boolean isMuted() {
        return mediaPlayer != null && mediaPlayer.isMute();
    }
}
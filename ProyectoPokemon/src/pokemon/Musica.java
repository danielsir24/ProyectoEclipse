package pokemon;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Musica {

    private static MediaPlayer mediaPlayer;

    public static void iniciar(String ruta) {
        if (mediaPlayer == null) {
            Media media = new Media(Musica.class.getResource(ruta).toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
            mediaPlayer.setVolume(0.1);
        }
    }

    public static void toggleMute() {
        if (mediaPlayer != null) {
            mediaPlayer.setMute(!mediaPlayer.isMute());
        }
    }

    public static boolean isMuted() {
        return mediaPlayer != null && mediaPlayer.isMute();
    }
}
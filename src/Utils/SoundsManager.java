package Utils;
import java.net.URL;
import javax.sound.sampled.*;
public class SoundsManager {
	public static class Sound{
        public static final String CAPTURE_PATH = "/Chess/Sounds/capture.wav";
        public static final String CASTLE_PATH = "/Chess/Sounds/castle.wav";
        public static final String CHECK_PATH = "/Chess/Sounds/move-check.wav";
        public static final String MOVE_PATH = "/Chess/Sounds/move-self.wav";
        public static final String PROMOTE_PATH = "/Chess/Sounds/promote.wav";
        public static final String GAMEOVER_PATH = "/Chess/Sounds/gameOver.wav";

        public static final int CAPTURE = 0;
        public static final int CASTLE = 1;
        public static final int CHECK = 2;
        public static final int MOVE = 3;
        public static final int PROMOTE = 4;
        public static final int GAMEOVER = 5;
    }
    public static URL sounds[] = new URL[6];
    public SoundsManager(){
        sounds[Sound.CAPTURE] = SoundsManager.class.getResource(Sound.CAPTURE_PATH);
        sounds[Sound.CASTLE] = SoundsManager.class.getResource(Sound.CASTLE_PATH);
        sounds[Sound.CHECK] = SoundsManager.class.getResource(Sound.CHECK_PATH);
        sounds[Sound.MOVE] = SoundsManager.class.getResource(Sound.MOVE_PATH);
        sounds[Sound.PROMOTE] = SoundsManager.class.getResource(Sound.PROMOTE_PATH);
        sounds[Sound.GAMEOVER] = SoundsManager.class.getResource(Sound.GAMEOVER_PATH);
    }
    public static void playSound(int index){
        if(sounds[index] == null){
            return;
        }
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(sounds[index]);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

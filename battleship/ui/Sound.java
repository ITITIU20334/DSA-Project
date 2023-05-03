package battleship.ui;
import java.net.URL;
import javax.sound.sampled.*;


public class Sound{
	Clip clip;
	URL soundURL[] = new URL[5];
	
	public Sound() {
		soundURL[0] = getClass().getResource("/res/sounds/sinking.wav");
		soundURL[1] = getClass().getResource("/res/sounds/strike.wav");
		soundURL[2] = getClass().getResource("/res/sounds/water_splash.wav");
	}
	
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
		}
	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		
	}
	
	public void stop() {
		
	}
}























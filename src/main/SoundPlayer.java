package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * The SoundPlayer a mostly AI-generated class is used to play sound effects and
 * background music
 */
public class SoundPlayer {
    private static boolean musicIsMuted = false;
    private static boolean soundEffectsAreMuted = false;

    private static Clip clip;

    /**
     * Plays a sound. Can only play one sound at a time
     * 
     * @param fileName The name of the sound file
     */
    public static void playSound(String fileName) {
        if (musicIsMuted) {
            return;
        }
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(fileName).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays a sound a specified number of times. Can only play one sound at a time
     * 
     * @param fileName The name of the sound file
     * @param loop   The number of times to loop the sound, -1 for infinite
     */
    public static void playSound(String fileName, int loop) {
        if (musicIsMuted) {
            return;
        }
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(fileName).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(loop);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays a sound at a specified volume a specified number of times. Can only 
     * play one sound at a time
     * 
     * @param fileName The name of the sound file
     * @param volume   The volume of the sound
     * @param loop   The number of times to loop the sound, -1 for infinite
     */
    public static void playSound(String fileName, float volume, int loop) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(fileName).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
    
            // Get the volume control and set the volume
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float db = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(db);
    
            clip.loop(loop);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    

    /**
     * Plays a sound effect. Can play multiple sound effects at a time
     * 
     * @param fileName The name of the sound effect file
     */
    public static void playSoundEffect(String fileName, float volume) {
        if (soundEffectsAreMuted) {
            return;
        }
        try {
            // Load the sound effect file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(fileName).getAbsoluteFile());
            AudioFormat format = audioInputStream.getFormat();
            byte[] audioData = new byte[(int) audioInputStream.getFrameLength() * format.getFrameSize()];
            audioInputStream.read(audioData);
    
            // Create a SourceDataLine to play the sound effect
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            
            // Set the volume of the line
            FloatControl gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
    
            line.start();
    
            // Write the sound effect data in a separate thread
            new Thread(() -> {
    
                line.write(audioData, 0, audioData.length);
    
                // Wait for the sound effect to finish playing
                line.drain();
                line.stop();
                line.close();
            }).start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Toggle the music on or off
     */
    public static void toggleMusic() {
        if (musicIsMuted) {
            musicIsMuted = false;
            clip.loop(-1);
        } else {
            musicIsMuted = true;
            clip.stop();
        }
    }

    /**
     * Toggle the sound effects on or off
     */
    public static void toggleSoundEffects() {
        if (soundEffectsAreMuted) {
            soundEffectsAreMuted = false;
        } else {
            soundEffectsAreMuted = true;
        }
    }

    public static boolean isMusicMuted() {
        return musicIsMuted;
    }

    public static boolean isSoundEffectsMuted() {
        return soundEffectsAreMuted;
    }

}

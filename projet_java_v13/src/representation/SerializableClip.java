package representation;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

@SuppressWarnings("serial")
public class SerializableClip implements Serializable {
    private transient Clip son;  // Utilisation de transient pour exclure la sérialisation de ce champ
    private String soundName;
    
    public SerializableClip(String soundName) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    	this.soundName = soundName;
    	initClip();
    }

    public void start() {
    	son.start();
    }

    public void stop() {
    	son.stop();
    }

    public void close() {
    	son.close();
    }

    public void loop(int count) {
    	son.loop(count);
    }

    public FloatControl getControl(FloatControl.Type control) {
        return (FloatControl) son.getControl(control);
    }

    // Méthode pour gérer la sérialisation
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        // Ne pas sérialiser le champ clip
    }

    // Méthode pour gérer la désérialisation
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException, UnsupportedAudioFileException, LineUnavailableException {
        in.defaultReadObject();
        // Réinitialiser le champ clip après la désérialisation
        initClip();
    }

    // Méthode pour réinitialiser le champ clip
    private void initClip() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (son == null) {  // Vérifier si le clip est déjà initialisé
            String projectPath = System.getProperty("user.dir");
            projectPath += File.separator + "src" + File.separator + "Sound" + File.separator;
            File file = new File(projectPath + soundName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            son = AudioSystem.getClip();
            son.open(audioStream);
        }
    }

}

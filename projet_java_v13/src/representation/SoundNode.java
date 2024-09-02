/**
 * Classe du decorateur concret du decorateur abstrait decoratorA, qui permet de rajouter des sons aux nodes
 */
package representation;

import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


@SuppressWarnings("serial")
public class SoundNode extends DecorateurA{
	public static float volumeGlobal = 1.0f;
	private SerializableClip[] sonList ;
	private float[] volume;
	private boolean[] boucle;
	
	public SoundNode(Event e, String[] s, float[] v, boolean[] b) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		super(e);
		if (!(s.length == b.length && s.length == v.length )) {
			System.out.println("\u001B[0m\n\t\u001B[41mtaille des tableaux différentes\u001B[0m");
			System.exit(1);
		}
		sonList = new SerializableClip[s.length];
		volume = new float[v.length];
		boucle = new boolean[b.length];
		
		
		for (int i = 0 ; i< s.length ; i++) {
			sonList[i] = new SerializableClip(s[i]);
			FloatControl gainControl = (FloatControl) sonList[i].getControl(FloatControl.Type.MASTER_GAIN);
    		float minVolume = gainControl.getMinimum();
    		float maxVolume = gainControl.getMaximum();
    		volume[i] = minVolume + volumeGlobal*v[i]*(maxVolume-minVolume);
		}
		boucle = b;
	}	
	public SoundNode(Event e, String soundName, float v, boolean b) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		super(e);
		sonList = new SerializableClip[1];
		volume = new float[1];
		boucle = new boolean[1];
			
		SerializableClip son = new SerializableClip(soundName);
			
		FloatControl gainControl = (FloatControl) son.getControl(FloatControl.Type.MASTER_GAIN);
		float minVolume = gainControl.getMinimum();
		float maxVolume = gainControl.getMaximum();
		volume[0] = minVolume + v*(maxVolume-minVolume);
		sonList[0] = son;
		boucle[0] = b;
	}
	
	public void display() {
		node.display();
	}
	
    public Event chooseNext(Scanner s) {
    	lancerSon();
    	Event e = super.chooseNext(s);
    	arreterSon();
    	return e;
    }
    
    public void lancerSon() {
    	for (int i=0 ; i<sonList.length ; i++) {
    		FloatControl gainControl = (FloatControl) sonList[i].getControl(FloatControl.Type.MASTER_GAIN);
    		gainControl.setValue(volume[i]);
    		if (boucle[i]) {
    			sonList[i].loop(Clip.LOOP_CONTINUOUSLY);
    		}else {
    			sonList[i].start();
    		}
    	}
    }
    
    public void arreterSon() {
    	for (int i=0 ; i<sonList.length ; i++) {
    		sonList[i].stop();
    		sonList[i].close();
    	}
    }
    
    public void setVolumeGlobal(float v) {
    	volumeGlobal = v;
    	for (int i=0 ; i<sonList.length ; i++) {
    		FloatControl gainControl = (FloatControl) sonList[i].getControl(FloatControl.Type.MASTER_GAIN);
    		float minVolume = gainControl.getMinimum();
    		gainControl.setValue( (volume[i] - minVolume)*volumeGlobal + minVolume );
    	}
    }
    
    
}


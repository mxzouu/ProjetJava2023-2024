/**
 * Classe decorator concret du decorateur abstrait decoratorA, pour rajouter des images aux nodes 
 */
package representation;

import java.awt.Image;
import java.io.File;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import univers.Joueur;
import univers.JoueurCharismatique;

@SuppressWarnings("serial")
public class ImageNode extends DecorateurA{
	private String imagePath;
	JFrame f ;
	
    public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public ImageNode(Event e, String i) {
    	super(e);
    	this.imagePath = "src" + File.separator + "Images" + File.separator +i;
    }

	public void display() {
	    showImage();
	    node.display();
	}
   /**
    * Afficher une image 
    */
	private void showImage() {
		try {
	    	f = new JFrame("Ajouter une image dans JFrame");
	    	f.toFront();
	        f.add(createImageLabel(imagePath, 500));
	        f.pack();
	        f.setVisible(true);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	private static JLabel createImageLabel(String imagePath, int maxWidth) {
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image originalImage = originalIcon.getImage();

        // Calculer les dimensions ajustées en maintenant le rapport hauteur/largeur
        int originalWidth = originalImage.getWidth(null);
        int originalHeight = originalImage.getHeight(null);

        int newWidth = Math.min(originalWidth, maxWidth);
        int newHeight = (int) ((double) newWidth / originalWidth * originalHeight);

        // Redimensionner l'image
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Créer le JLabel avec l'image redimensionnée
        JLabel label = new JLabel(scaledIcon);

        return label;
    }
	
	@Override
    public Event chooseNext(Scanner s) {
		showImage();
    	Event e = super.chooseNext(s);
    	if (f!=null) f.dispose();
    	return e;
	}
	
	
	
	
     
}







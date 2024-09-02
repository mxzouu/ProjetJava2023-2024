/**
 * Classe qui gere l'ouverture et fermeture d'une nouvelle fenetre dans l interface graphique 
 */
package representation.Interface;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class SecondWindow extends JFrame {
	
    public SecondWindow(final JFrame parent) {

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	
            	actionsAvantFermeture();
            	
            	// Activer à nouveau la fenêtre principale lors de la fermeture de la deuxième fenêtre
                if (parent != null) {
                	parent.setEnabled(true);
                }
            	
                // Fermer la deuxième fenêtre
                dispose();
            }
        });
        
    }
    
    // Méthode dédiée pour effectuer des actions spécifiques
    public void actionsAvantFermeture() {
    }
    
}

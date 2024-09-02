/**
 * Classe qui implémente la fenêtre de l'interface de l'ecran d'accueil
 */
package representation.Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import execution.Tree;
import univers.*;

@SuppressWarnings("serial")
public class InterfaceEcranAccueil extends JFrame {

	private int hauteurFenetre = 700;
	private int largeurFenetre = 875;
	private File[] save;
	private String[] saveName;
	private Font customFont;
	
	private JButton retour;
	private int panelCourant;
	
	private JLabel backgroundImage;
	private JPanel panel1;	// choix entre nouvelle et ancienne partie
	private JButton nouvellePartie;
	private JButton chargerPartie;
	
	private JPanel panel2;	//Nouvelle partie : choix du nom
	private JTextField saisirNom;
	private JButton entree;
	
	private JPanel panel3;
	private JEditorPane text;
	private JPanel panel3_2;
	private JButton[] classeJoueur = new JButton[3];
	
	
	private JPanel panel4;
	private JComboBox<String> comboBox;
	private JButton entree2;
	
	public InterfaceEcranAccueil() {
		try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
        }
		save = new File("src" + File.separator + "Sauvegarde").listFiles();
    	saveName = new String[save.length];
		for (int i=0 ; i<save.length ; i++) {
			saveName[i] = save[i].getName();
		}
		
		
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize(); //recupere les dimensions de l'ecran
        int height = (int)dimension.getHeight();
        int width  = (int)dimension.getWidth();
        hauteurFenetre = (int) Math.round( (700f/1080f)*height );
        largeurFenetre =  (int) Math.round( (875f/1920f)*width );
        customFont = new Font("Britannic Bold", Font.PLAIN, (int) Math.round( (20f/875f)*largeurFenetre));
        
        // Configuration de la fenêtre principale
        setTitle("Mission Codex Académique");
        setIconImage(new ImageIcon("src" + File.separator + "Icons" + File.separator + "logo.png").getImage());
        getContentPane().setBackground(Color.BLACK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(largeurFenetre, hauteurFenetre);
        setResizable(false);
        setLocationRelativeTo(null);
        toFront();
        // Rendre la fenêtre visible
        
        panelCourant = 1;
        
        createComponents();
        configureActionButton();
        addComponentsToFrame();
        
        setVisible(true);
	}
	
	public void createComponents() {
		
		
		backgroundImage = new JLabel(
				InterfaceTree.redimensionne(
						"src"+File.separator+"Icons"+File.separator+"ecran d'accueil.png" ,
						largeurFenetre-10 , 
						hauteurFenetre-40 , 
						hauteurFenetre-40 
						)
				);
				
		LineBorder redBorder = new LineBorder(Color.RED, 3);
		retour = new JButton(InterfaceTree.redimensionne("src"+File.separator+"Icons"+File.separator+"Return.png", 26, 26, hauteurFenetre));
		retour.setBounds(10, 10, 50, 50);
		retour.setFocusable(false);
		retour.setBorder(redBorder);
		retour.setBackground(Color.WHITE);
        InterfaceTree.changementCouleurBouton(retour, new Color(150, 150, 150), Color.WHITE);
        retour.setVisible(false);
        
        ////////////////////////////////////////////////////////////////////////////////////////
		
        panel1 = createPanel();
        nouvellePartie = createButton("Nouvelle partie"); //, 87.5f, 400f 
        chargerPartie = createButton("Charger une partie"); //, 87.5f, 510f
        
        ////////////////////////////////////////////////////////////////////////////////////////
        
        panel2 = createPanel();
        panel2.setVisible(false);
        saisirNom = new JTextField(20);
        saisirNom.setFont(customFont);
        int leftMargin = 10;
        int rightMargin = 10;
        saisirNom.setBorder(new EmptyBorder(0, leftMargin, 0, rightMargin));
        saisirNom.setText("Agent 47");
        saisirNom.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (saisirNom.getText().equals("Agent 47")) {
                	saisirNom.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (saisirNom.getText().isEmpty()) {
                	saisirNom.setText("Agent 47");
                }
            }
        });
        
        entree = createButton("Valider");
        
        ///////////////////////////////////////////////////////////////////////////////////////
        
        panel3 = createPanel();
        panel3.setVisible(false);
        String s ="<html><div style='text-align: center;'>" 
        		+"Vous allez incarner un agent secret, veuillez selectionner le type d'agent secret que vous serez.<br>"
        		+ "<font color=\"red\"> ATTENTION : Ce choix est définitif et influencera votre partie.</font>"
        		+"</div></html>";
        text = new JEditorPane("text/html", s);
        text.setEditable(false);
		text.setFont(customFont);
		
		panel3_2 = createPanel();
		panel3_2.setLayout(new GridLayout(1,3,0,10));
		classeJoueur[0]=createButton("Informaticien");
		classeJoueur[1]=createButton("Chimiste");
		classeJoueur[2]=createButton("Charismatique");
        
		///////////////////////////////////////////////////////////////////////////////////////
		
		panel4 = createPanel();
		panel4.setVisible(false);
		comboBox = new JComboBox<>(saveName);
		entree2 = createButton("Valider");
		
	}

	public JPanel createPanel() {
		JPanel panel = new JPanel();
		panel.setBounds((int) Math.round( (87.5f/875f)*largeurFenetre),
				(int) Math.round( (400f/700f)*hauteurFenetre ),
				(int) Math.round( (700f/875f)*largeurFenetre), 
				(int) Math.round( (210f/700f)*hauteurFenetre ) );
		panel.setLayout(new GridLayout(2,1,0,10));
		panel.setBackground(Color.BLACK);
		return panel;
	}
	
	public JButton createButton(String txt) {
        LineBorder redBorder = new LineBorder(Color.RED, 5);
        JButton button = new JButton(txt);
        button.setFocusable(false);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setBorder(redBorder);
        button.setFont(customFont);
        InterfaceTree.changementCouleurBouton(button, new Color(70, 70, 70), Color.BLACK);
        return button;
    }

	private void configureActionButton() {
		
		nouvellePartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	panel1.setVisible(false);
            	panel2.setVisible(true);
            	panelCourant = 2;
            	retour.setVisible(true);
            }
        });
		
		entree.addActionListener(new ActionListener() {
            int answer;
            @Override
            public void actionPerformed(ActionEvent e) {
            	String texteSaisi = saisirNom.getText() ;
            	saisirNom.setText(
			                		(texteSaisi==null) ? 
			                				"Agent 47" : 
					                		(texteSaisi.length() == 0) ? 
					                				"Agent 47" :
					                				texteSaisi
		                		);
            	texteSaisi = saisirNom.getText() ;
                if (in(saveName, texteSaisi + ".txt")) {
                	do {
                		answer = JOptionPane.showConfirmDialog(InterfaceEcranAccueil.this, 
                		"il existe déja un joueur (et donc une sauvegarde) portant le nom \""+texteSaisi+"\", si vous utilisez ce nom cela supprimera la sauvegarde existante !\nVoulez vous toujours utiliser ce nom ?",
                		"Nom déjà utilisé", JOptionPane.YES_NO_OPTION);
                	}while( answer == -1);
                	if (answer == 0) {
                		panel2.setVisible(false);
                    	panel3.setVisible(true);
                    	panelCourant = 3;
                    	retour.setVisible(true);
                	}
                }else {
                	panel2.setVisible(false);
                	panel3.setVisible(true);
                	panelCourant = 3;
                	retour.setVisible(true);
                }
                
            }
        });
		
		saisirNom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                entree.doClick();
            }
        });
		
		classeJoueur[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	Tree t = null;
        		try {
        			t = new Tree(new JoueurInformaticien(saisirNom.getText()));
        		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
            	lancerPartie(t);
            	
            }
        });
		classeJoueur[1].addActionListener(new ActionListener() {
			
            @Override
            public void actionPerformed(ActionEvent e) {
            	Tree t = null;
        		try {
        			t = new Tree(new JoueurChimiste(saisirNom.getText()));
        		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
            	lancerPartie(t);
            	
            }
        });
		classeJoueur[2].addActionListener(new ActionListener() {
			
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	Tree t = null;
        		try {
        			t = new Tree(new JoueurCharismatique(saisirNom.getText()));
        		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
            	lancerPartie(t);
		    	
		    }
		});

		
		chargerPartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	panel1.setVisible(false);
            	panel4.setVisible(true);
            	panelCourant = 4;
            	retour.setVisible(true);
            }
        });
				
		entree2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = comboBox.getSelectedIndex();
                ObjectInputStream ois = null;
                Tree t = null;
				try {
					ois = new ObjectInputStream(new FileInputStream(save[i]));
					t = (Tree) ois.readObject();
					ois.close();
				}catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                lancerPartie(t);
                
            }
        });
		
		retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (panelCourant == 2) {
                	panel2.setVisible(false);
                	panel1.setVisible(true);
                	panelCourant = 1;
                	retour.setVisible(false);
                } else if (panelCourant == 3) {
                	panel3.setVisible(false);
                	panel2.setVisible(true);
                	panelCourant = 2;
                } else if (panelCourant == 4) {
                	panel4.setVisible(false);
                	panel1.setVisible(true);
                	panelCourant = 1;
                	retour.setVisible(false);
                }
            }
        });
	}
	
	public void addComponentsToFrame() {
		panel4.add(comboBox);
		panel4.add(entree2);
		add(panel4);
		
		panel3.add(text);
		for (JButton b : classeJoueur) {
			panel3_2.add(b);
		}
		panel3.add(panel3_2);
		
		add(panel3);
		
		panel2.add(saisirNom);
		panel2.add(entree);
		
		add(panel2);
		panel1.add(nouvellePartie);
        panel1.add(chargerPartie);
        add(panel1);
        
        add(retour);
        
        add(backgroundImage);
	}
	
	
	public void lancerPartie(Tree t) {
    	setVisible(false);
    	dispose();
    	new InterfaceTree(t,InterfaceEcranAccueil.this);
	}
	
	
	public static boolean in(String[] array, String target) {
        for (String s : array) {
            if (s.equals(target)) {
                return true;
            }
        }
        return false;
    }
	
	/*public static void main(String[] args) {
        new InterfaceEcranAccueil();
    }*/
}
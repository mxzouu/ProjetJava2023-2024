/**
 * Classe qui implémente la fenêtre principale de l'interface graphique, la fenêtre principael
 */
package representation.Interface;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import execution.Tree;
import representation.Event;
import representation.TerminalNode;
import univers.PNJ;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("serial")
public class InterfaceTree extends SecondWindow {

	private int hauteurFenetre = 900;
	private int largeurFenetre = 1000;
	private Tree t;
	private Event node;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private Box verticalBox;
    private JButton menuButton;
    private JButton inventaireButton;
    private JButton saveButton;
    private JButton soundButton;
    private JSlider volumeSlider;
    private JLabel imageLabel;
    private JTextArea description;
    private ArrayList<JButton> buttonList = new ArrayList<>();
    private JButton chanceButton;
    private JEditorPane fin;
    private Timer animatedTextTimer;
    private int selectedButtonIndex = -1;
    
    private boolean[] boutonSorti = {false}; // boutonSorti[0] designe si le menu est sorti ou non
    												// boutonSorti[1] designe si l'inventaire est sorti ou non
    
    
    public int getSelectedButtonIndex() {
    	return selectedButtonIndex;
    }
    
    	@Override
    public void actionsAvantFermeture() {
    	save();
        t.arreterMusiqueAmbiance();
        node.arreterSon();
        new InterfaceEcranAccueil();
    }
    
    public void save() {
    	try {
    		t.setRacine(node);
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(
							new File(
									"src" + File.separator + "Sauvegarde" + File.separator + t.getPlayer().getIdentite()+".txt"
									)
							)
					
					);
			oos.writeObject(t);
			oos.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    public InterfaceTree(Tree t, final JFrame parent) {
    	super(parent);
    	this.t = t;
    	this.node = t.getRacine();
    	String imageName= node.getImagePath();
    	String descriptionNode = node.getDescription();
    	ArrayList<String> titreNode = node.getTitreNode();
    	ArrayList<Event> SuccNode = node.getSuccNode();
    	
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize(); //recupere les dimensions de l'ecran
        int height = (int)dimension.getHeight();
        int width  = (int)dimension.getWidth();
        hauteurFenetre = (int) Math.round( (900f/1080f)*height );
        largeurFenetre =  (int) Math.round( (1000f/1920f)*width );
        
        // Configuration de la fenêtre principale
        setTitle("Mission Codex Académique");
        setIconImage(new ImageIcon("src" + File.separator + "Icons" + File.separator + "logo.png").getImage());
        getContentPane().setBackground(Color.BLACK);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(largeurFenetre, hauteurFenetre);
        setResizable(false);
        setLocationRelativeTo(null);
        toFront();
        // Rendre la fenêtre visible
        
        for (PNJ pnj : t.getPlayer().getFichePerso()) {
    		if (pnj.getSoupcon() >= 10) {
    			node = new TerminalNode("Les soupcons de "+ pnj.getIdentite() +" ont dépassé 10, vous vous êtes fait repérer.\n"
    					+ "\tMISSION ÉCHOUÉE");
    		}
    	}
        
        // Création des composants
        createComponents(imageName, descriptionNode, titreNode, SuccNode);
        
        // Ajout des composants à la fenêtre
        addComponentsToFrame(titreNode);

        
        if ( node.getNodeType().equals("TerminalNode") ) {
        	animatedText(description, node.getDescription());
        } else {
        	animatedText(descriptionNode, titreNode);
        }
        
        
        setVisible(true);
        t.lancerMusiqueAmbiance();
        node.lancerSon(); //lancer le son
        
        
        
    }

    private void createComponentsPanel1(String imageName) {
    	// Création des boutons
        menuButton = createButton("src" + File.separator + "Icons" + File.separator +"Menu.png", Color.BLACK, 10, 10, 26, 26, true);
        inventaireButton = createButton("src" + File.separator + "Icons" + File.separator +"inventaire.png", Color.WHITE, 10, 10, 26, 26, true);
        saveButton = createButton("src" + File.separator + "Icons" + File.separator +"Save.png", Color.WHITE, 10, 10, 26, 26, true);
        soundButton = createButton("src" + File.separator + "Icons" + File.separator +"Sound_Speaker_4.png", Color.WHITE, 10, 10, 26, 26, true);

        // Configuration du changement de couleurs des boutons lorsqu'ils sont survolés
        changementCouleurBouton(menuButton, new Color(70, 70, 70), Color.BLACK);
        changementCouleurBouton(inventaireButton, new Color(150, 150, 150), Color.WHITE);
        changementCouleurBouton(saveButton, new Color(150, 150, 150), Color.WHITE);
        changementCouleurBouton(soundButton, new Color(150, 150, 150), Color.WHITE);

        // Création du slider de volume
        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (100*t.getVolumeGlobal()) );
        volumeSlider.setBounds(15, 190, 40, 50);
        volumeSlider.setBackground(Color.BLACK);
        volumeSlider.setForeground(Color.WHITE);
        volumeSlider.setVisible(false); // Le rend invisible au début
        
        // Création du label pour l'image
        imageLabel = new JLabel(redimensionne(imageName , largeurFenetre, (int) Math.round( (6f/9f)*hauteurFenetre ), hauteurFenetre ));

    }
    private void addActionListenerPanel1() {
    	
    	int vitesseAnimation = 5;
    	
    	// Action du menuButton
    	menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Timer timer;
                if (boutonSorti[0]) {
                    timer = new Timer(vitesseAnimation, new ActionListener() {
                        int y = soundButton.getY();

                        @Override
                        public void actionPerformed(ActionEvent e) {
                        	
                            // Déplacer le bouton2 progressivement vers la nouvelle position
                        	if (y > 130) {
                                y = y-7<=130 ? 130 : y-7;
                                soundButton.setLocation(10, y);
                            } else if (y > 70) {
                                y = y-7<=70 ? 70 : y-7;
                                saveButton.setLocation(10, y);
                                soundButton.setLocation(10, 10);
                            } else if (y > 10) {
                            	y = y-7<=10 ? 10 : y-7 ;
                            	inventaireButton.setLocation(10, y);
                            	saveButton.setLocation(10,10);
                            } else {
                                // Arrêter le Timer une fois que la nouvelle position est atteinte
                                ((Timer) e.getSource()).stop();
                                boutonSorti[0] = !boutonSorti[0];
                            }
                        }
                    });
                } else {
                    timer = new Timer(vitesseAnimation, new ActionListener() {
                        int y = inventaireButton.getY();

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Déplacer le bouton2 progressivement vers la nouvelle position
                            if (y < 70) {
                                y = y+7>=70 ? 70 : y+7 ;
                                inventaireButton.setLocation(10, y);
                            } else if (y < 130) {
                            	y = y+7>=130 ? 130 : y+7 ;
                            	saveButton.setLocation(10, y);
                            } else if (y < 190) {
                            	y = y+7>=190 ? 190 : y+7 ;
                            	soundButton.setLocation(10, y);
                            } else {
                                // Arrêter le Timer une fois que la nouvelle position est atteinte
                                ((Timer) e.getSource()).stop();
                                boutonSorti[0] = !boutonSorti[0];
                            }
                        }
                    });
                }

                // Démarrer le Timer
                int delai = 0;
                if ( volumeSlider.isVisible() ) {
                	soundButton.doClick();
                	delai = 200;
                }
                timer.setInitialDelay(delai);
                timer.start();
                
            }
        });

    	
        // Action du inventaireButton
        inventaireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	setEnabled(false);
            	
            	try {
            	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            	} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
            	    e1.printStackTrace();
            	}
            	
                // Créer et afficher la deuxième fenêtre
            	new InterfaceInventaire(InterfaceTree.this, t.getPlayer(),1);
            	
            	try {
                    UIManager.setLookAndFeel(new NimbusLookAndFeel());
                } catch (UnsupportedLookAndFeelException e1) {
                    e1.printStackTrace();
                }
                
            }
        });
        
        
        // Action du saveButton
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	save();
            	JOptionPane.showMessageDialog(null, "Partie sauvegardée !");
            }
        });
        
        
        // Action du soundButton
        soundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Timer timer;
                if (volumeSlider.isVisible()) {
                    // Le JSlider est actuellement visible, le cacher
                    
                    timer = new Timer(vitesseAnimation, new ActionListener() {
                    	int x = volumeSlider.getX();
                        int w = volumeSlider.getWidth();

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Déplacer le JSlider progressivement vers la droite
                            if (x > 15) {
                                x = x - 7 <= 15 ? 15 : x - 7;
                                w = w - 8 <= 40 ? 40 : w - 8;
                                volumeSlider.setLocation(x, soundButton.getY());
                                volumeSlider.setBounds(x,190,w,50);
                            } else {
                                // Arrêter le Timer une fois que la nouvelle position est atteinte
                                ((Timer) e.getSource()).stop();
                                volumeSlider.setVisible(false);
                            }
                        }
                    });
                    
                } else {
                    // Le JSlider est actuellement caché, le faire glisser depuis derrière le soundButton
                    //volumeSlider.setLocation(soundButton.getX(), soundButton.getY());
                    volumeSlider.setVisible(true);

                    timer = new Timer(vitesseAnimation, new ActionListener() {
                    	int x = volumeSlider.getX();
                        int w = volumeSlider.getWidth();

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Déplacer le JSlider progressivement vers la droite
                            if (x < 70) {
                                x = x + 7 >= 70 ? 70 : x + 7;
                                w = w + 8 >= 100 ? 100 : w + 8;
                                volumeSlider.setLocation(x, soundButton.getY());
                                volumeSlider.setBounds(x,190,w,50);
                            } else {
                                // Arrêter le Timer une fois que la nouvelle position est atteinte
                                ((Timer) e.getSource()).stop();
                            }
                        }
                    });
  
                    // Démarrer le Timer
                   
                }
                timer.start();
            }
        });
    	
    	
    	// Changer l'icone du soundButton lors des changements d'état du slider
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                
                int sliderValue = volumeSlider.getValue();
                node.setVolumeGlobal((float)sliderValue/100);
                t.setVolumeGlobal((float)sliderValue/100);
                ImageIcon icon = null;
                
                if (sliderValue >= 66) {
                	icon = redimensionne("src/Icons/Sound_Speaker_4.png",26,26, hauteurFenetre);
                }else if (sliderValue >= 36) {
                	icon = redimensionne("src/Icons/Sound_Speaker_3.png",26,26, hauteurFenetre);
                }else if (sliderValue > 0) {
                	icon = redimensionne("src/Icons/Sound_Speaker_2.png",26,26, hauteurFenetre);
                }else {
                	icon = redimensionne("src/Icons/Sound_Speaker_0.png",26,26, hauteurFenetre);
                }
                soundButton.setIcon(icon);
            }
        });
        
    }
    private void addComponentsPanel1ToFrame() {
    	// Ajout des composants au panel1
        panel1.setLayout(new BorderLayout());
        panel1.add(menuButton);
        panel1.add(inventaireButton);
        panel1.add(saveButton);
        panel1.add(soundButton);
        panel1.add(volumeSlider);
        panel1.add(imageLabel);
    }
    
    
    private void createComponentsPanel2() {
    	//Choix de la police 
        Font customFont = new Font("Britannic Bold", Font.PLAIN, (int) Math.round( (16f/900f)*hauteurFenetre) );
        
        // Création de la zone de texte
        description = new JTextArea();
        description.setEditable(false);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setMargin(new Insets(10, 10, 10, 10));
        description.setFont(customFont);
        description.setForeground(Color.WHITE);
        description.setBackground(Color.BLACK);

        // Création du JScrollPane
        //JScrollPane scrollPane = new JScrollPane(description);
        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
    }
    private void addComponentsPanel2ToFrame() {
    	// Ajout du composant scrollPane à panel2
        panel2.add(new JScrollPane(description), BorderLayout.CENTER);
    }
    
    
    private void createComponentsPanel3(ArrayList<String> titreNode) {
    	if ( node.getNodeType().equals("TerminalNode") ) {
    		
    		fin = new JEditorPane("text/html", "");
    		fin.setEditable(false);
    		fin.setOpaque(false);
    		fin.setBackground(Color.BLACK);
    		
    	} else if ( node.getNodeType().equals("ChanceNode") ) {
    		
    		Color couleurBouton = new Color(40, 70, 255); //bleu
	    	Color couleurSurvolee = new Color(100, 150, 255); //bleu un peu plus clair
	    	
	    	
	    	//Choix de la police 
	        Font customFont = new Font("Britannic Bold", Font.PLAIN, 16);
	        
	    	// Création des choiceButton
	        for (int i=0 ; i<titreNode.size() ; i++) {
	            JButton choiceButton = new JButton();
	            choiceButton.setLayout(new BorderLayout());
	            choiceButton.setFocusable(false);
	            choiceButton.setBackground(couleurBouton);
	            changementCouleurBouton(choiceButton, couleurSurvolee, couleurBouton);
				choiceButton.setFont(customFont); // On modifie la police et la taille du texte dans le bouton
				choiceButton.setEnabled(false);
				
	            buttonList.add(choiceButton);
	        }
	        
    	} else {
    		
    		Color couleurBouton = Color.WHITE;
	    	Color couleurSurvolee = new Color(150, 150, 150); //gris
	    		    	
	    	//Choix de la police 
	        Font customFont = new Font("Britannic Bold", Font.PLAIN, (int) Math.round( (16f/900f)*hauteurFenetre) );
	        
	    	// Création des choiceButton
	        for (int i=0 ; i<titreNode.size() ; i++) {
	            JButton choiceButton = new JButton();
	            choiceButton.setLayout(new BorderLayout());
	            choiceButton.setFocusable(false);
	            choiceButton.setBackground(couleurBouton);
	            changementCouleurBouton(choiceButton, couleurSurvolee, couleurBouton);
				choiceButton.setFont(customFont); // On modifie la police et la taille du texte dans le bouton
				
	            buttonList.add(choiceButton);
	        }
	        
    	}
    	
    	
    	
    	
    }
    private void addActionListenerPanel3(ArrayList<Event> SuccNode) {
        for( int i=0 ; i<buttonList.size() ; i++ ) {
        	final int index = i;
        	buttonList.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	node.arreterSon();		// On arrete le son courant
                	selectedButtonIndex = index;
                	for ( JButton choiceButton : buttonList ) {
                		choiceButton.setEnabled(false);
                	}
                	animatedTextTimer.stop();
                	node = SuccNode.get(selectedButtonIndex);
                	node.getFunction().execute();	// On fait les changements sur le personnage ou l'arbre 
                	
                	for (PNJ pnj : t.getPlayer().getFichePerso()) {
                		if (pnj.getSoupcon() >= 10) {
                			node = new TerminalNode("Les soupcons de "+ pnj.getIdentite() +" ont dépassé 10, vous vous êtes fait repérer.\n"
                					+ "\tMISSION ÉCHOUÉE");
                			changeToTerminalNode();
                		}
                	}
                	
                	
                	node.lancerSon();		// On lance le son du nouveau node courant
                	node.setVolumeGlobal((float)volumeSlider.getValue()/100);
                	
                	save();
                	
                	if (node.getNodeType().equalsIgnoreCase("DecisionNode") ) {
                    	changeToDecisionNode();
                    }else if (node.getNodeType().equalsIgnoreCase("ChanceNode") ) {
                    	changeToChanceNode();
                    }else if (node.getNodeType().equalsIgnoreCase("TerminalNode") ) {
                    	changeToTerminalNode();
                    }
                	
                	
                	
                	/*
                	Joueur p = new Joueur();
                	Event poserQuestionsSurLeCours= new DecisionNode("Il vous explique les points que vous lui avez demande.\nPendant ce temps, vous observez son bureau : une photo de lui avec une femme, \n\t\t\tune tasse \"meilleur papa du monde\", \n\t\t\tune mini maquette du systeme solaire, \n\t\t\tplusieurs dossiers poses dans un coin du bureau et pleins de feuilles de brouillons \n\t\t\teparpillees partout.", p);
                	Event DemanderEmploiDuTemps = new DecisionNode("M. Dupont : Vous pouvez juste m'envoyer un mail avant de passer et je vous repondrais si je suis disponible",p);
                	Event parlerRecherche = new DecisionNode("M. Dupont :Ce sont des recherches confidentielles donc je ne peux pas vous en parler.",p);
                	 poserQuestionsSurLeCours.ajoutNodeList(DemanderEmploiDuTemps, "lui demander son emploi du temps pour voir quand il est disponible");
                     poserQuestionsSurLeCours.ajoutNodeList(parlerRecherche, "lui parler de ses recherches");
                    changeToDecisionNode(poserQuestionsSurLeCours);
                	*/
                	//changeToTerminalNode(new TerminalNode("Vous pensiez vraiment pouvoir le tuer comme ca? \nVous vous faites arreter !\n\t---JEU FINI---"));
                }
            });
        }      
    }
    private void addComponentsPanel3ToFrame(ArrayList<String> titreNode) {
    	if ( node.getNodeType().equals("TerminalNode") ) {
    		panel3.add(fin);
    	} else {
    		//TODO remplacer titreNode par buttonList
	    	// Création des boutons pour choisir le nœud suivant
	        for (int i=0 ; i<titreNode.size() ; i++) {
	            panel3.add(buttonList.get(i), BorderLayout.CENTER);
	        }
    	}
    }
    
    
    private void createComponentsPanel4() {
    	Font customFont = new Font("Britannic Bold", Font.PLAIN, (int) Math.round( (16f/900f)*hauteurFenetre) );
        chanceButton = new JButton();
        chanceButton.setLayout(new BorderLayout());
        chanceButton.setFocusable(false);
        chanceButton.setBackground(Color.WHITE);
        changementCouleurBouton(chanceButton, new Color(150, 150, 150), Color.WHITE);
        chanceButton.setFont(customFont);
    }
    private void addActionListenerPanel4(ArrayList<Event> SuccNode) {
    	
    	chanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	chanceButton.setEnabled(false);
            	int vitesseAnimation = 150;
				int nbIteration = 3;
					Timer timer = new Timer(vitesseAnimation, new ActionListener() {
						
						int n= buttonList.size();
		                Random random= new Random();
		                int r = random.nextInt(n);
		                
		                int enabledButton = 0;
		                int index = 0;
		                
	                    @Override
	                    public void actionPerformed(ActionEvent e) {
	                    	
	                    	buttonList.get(enabledButton).setEnabled(false);
                			
	                    	buttonList.get(index % buttonList.size()).setEnabled(true);
	                    	
	                    	enabledButton = index % buttonList.size();
	                    	index++;
	                    	if ( ((Timer) e.getSource()).getDelay() < 500 ) {
	                    		((Timer) e.getSource()).setDelay( ((Timer) e.getSource()).getDelay() + (int) Math.exp(5-buttonList.size() + (float)index*2.0f / ( (float)buttonList.size() ) ) ) ;
	                    		//TODO trouver vrai fonction pour gérer les setDelay
	                    	}
	                    	
	                        if ( ( index >= nbIteration * buttonList.size() ) && ( enabledButton == r ) ) {
	                        	node = SuccNode.get(enabledButton);
	                        	node.getFunction().execute();	// On fait les changements sur le personnage ou l'arbre 
	                        	save();
	                        	
	                        	addActionListenerPanel3(SuccNode);
	                        	//LineBorder blueBorder = new LineBorder(Color.BLUE, 5);
	                            
	                            // Appliquer la bordure au bouton
	                        	//buttonList.get(enabledButton).setBorder(blueBorder);
	                        	chanceButton.setText("");
	                        	animatedText(chanceButton, "Cliquer sur le bouton bleu ! (Si vous quittez la fenetre maintenant le bouton sera automatiquement pressé)");
	                        	((Timer) e.getSource()).stop();
	                        	
	                        }
	                        
	                    }
	                });
					timer.start();
				
                
            }
        });	
		
    }
    private void addComponentsPanel4ToFrame() {
    	panel4.add(chanceButton);
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private void createComponents(String imageName, String descriptionNode, ArrayList<String> titreNode, ArrayList<Event> SuccNode) {
    	if ( node.getNodeType().equals("TerminalNode") ) {
    		
    		panel1 = createPanelWithHeight(Color.BLACK, (int) Math.round( (6f/9f)*hauteurFenetre ) );
	        panel2 = createPanelWithHeight(Color.BLACK, (int) Math.round( (1f/9f)*hauteurFenetre ) );
	        panel3 = createPanelWithHeight(Color.BLACK, (int) Math.round( (2f/9f)*hauteurFenetre ) );
	        panel4 = createPanelWithHeight(Color.BLUE, 0);
	        panel4.setVisible(false);
	        
	        panel1.setLayout(new BorderLayout());
	        panel2.setLayout(new BorderLayout());
	        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
	        panel4.setLayout(new BorderLayout());
	        
	        // Utilisation de BoxLayout pour les empiler
	        verticalBox = Box.createVerticalBox();
	        
	        createComponentsPanel1(imageName);
	        createComponentsPanel2();
	        createComponentsPanel3(titreNode);
	        
	        // Configuration des actions du boutons et du JSlider
	        configureActionButton(SuccNode);
	        
    	} else if ( node.getNodeType().equals("ChanceNode") ) {
    		
    		panel1 = createPanelWithHeight(Color.BLACK, (int) Math.round( (550f/900f)*hauteurFenetre ) );
	        panel2 = createPanelWithHeight(Color.BLACK, (int) Math.round( (1f/9f)*hauteurFenetre ) );
	        panel3 = createPanelWithHeight(Color.BLACK, (int) Math.round( (2f/9f)*hauteurFenetre ) );
	        panel4 = createPanelWithHeight(Color.BLUE,  (int) Math.round( (50f/900f)*hauteurFenetre ) );
	        
	        panel1.setLayout(new BorderLayout());
	        panel2.setLayout(new BorderLayout());
	        panel3.setLayout(new GridLayout(2, 2));
	        panel4.setLayout(new BorderLayout());
	        
	        // Utilisation de BoxLayout pour les empiler
	        verticalBox = Box.createVerticalBox();
	        
	        createComponentsPanel1(imageName);
	        createComponentsPanel2();
	        createComponentsPanel3(titreNode);
	        createComponentsPanel4();
	        
	        // Configuration des actions du boutons et du JSlider
	        configureActionButton(SuccNode);
	        
    	} else {
    		
    		panel1 = createPanelWithHeight(Color.BLACK, (int) Math.round( (6f/9f)*hauteurFenetre ) );
    		panel2 = createPanelWithHeight(Color.BLACK, (int) Math.round( (1f/9f)*hauteurFenetre ) );
	        panel3 = createPanelWithHeight(Color.BLACK, (int) Math.round( (2f/9f)*hauteurFenetre ) );
	        panel4 = createPanelWithHeight(Color.BLUE, 0);
	        panel4.setVisible(false);
    		if (node.getSuccNode().size() == 1) {
    			panel2 = createPanelWithHeight(Color.BLACK, (int) Math.round( (2f/9f)*hauteurFenetre ) );
    	        panel3 = createPanelWithHeight(Color.BLACK, (int) Math.round( (1f/9f)*hauteurFenetre ) );
    		}
    		
	        panel1.setLayout(new BorderLayout());
	        panel2.setLayout(new BorderLayout());
	        panel3.setLayout(new GridLayout(2, 2));
	        panel4.setLayout(new BorderLayout());
	        if (node.getSuccNode().size() == 1) {
	        	panel3.setLayout(new BorderLayout());
	        }
	        // Utilisation de BoxLayout pour les empiler
	        verticalBox = Box.createVerticalBox();
	        
	        createComponentsPanel1(imageName);
	        createComponentsPanel2();
	        createComponentsPanel3(titreNode);
	        
	        // Configuration des actions du boutons et du JSlider
	        configureActionButton(SuccNode);
	        
    	}
    }

    private void configureActionButton(ArrayList<Event> SuccNode) {
    	if ( node.getNodeType().equals("TerminalNode") ) {
    		
    		addActionListenerPanel1();
    		
    	} else if ( node.getNodeType().equals("ChanceNode") ) {

    		addActionListenerPanel1();
            //addActionListenerPanel3(SuccNode);
            addActionListenerPanel4(SuccNode);
            
    	} else {

    		addActionListenerPanel1();
            addActionListenerPanel3(SuccNode);
            
    	} 
    }
    
    private void addComponentsToFrame(ArrayList<String> titreNode) {
    	    	
        verticalBox.add(panel1);
        verticalBox.add(panel2);
        verticalBox.add(panel3);
        verticalBox.add(panel4);

        // Ajout du conteneur Box à la JFrame
        add(verticalBox);

        addComponentsPanel1ToFrame();

        addComponentsPanel2ToFrame();
        
        addComponentsPanel3ToFrame(titreNode);
        
        if ( node.getNodeType().equals("ChanceNode") ) {
        	addComponentsPanel4ToFrame();
        }
        

    }

    public static JPanel createPanelWithHeight(Color color, int height) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setPreferredSize(new Dimension(0, height)); // 0 pour occuper toute la largeur disponible
        return panel;
    }

    public static ImageIcon redimensionne(String imagePath, int newWidth, int newHeight, int hauteurFenetre) {
    	String path;
    	int nW, nH;
    	if (imagePath == null) {
    		path = "src" + File.separator + "Icons" + File.separator + "logo.png";
    		nW= (int) Math.round( (6f/9f)*hauteurFenetre ) ;
    		nH = nW;
    	}else {
    		path = imagePath;
    		nW = newWidth;
    		nH = newHeight;
    	}
    	
    	BufferedImage originalImage = null;
        try {
            originalImage = javax.imageio.ImageIO.read(new File( path ));
        } catch (IOException e) {
        	throw new IllegalArgumentException(
    				"\nErreur avec le fichier :> " + path +" <");
            
        }
        Image resizedImage = originalImage.getScaledInstance(nW, nH, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public JButton createButton(String imagePath, Color BackgroundColor , int x, int y, int newWidth, int newHeight, boolean visible) {
        JButton button = new JButton(redimensionne(imagePath, newWidth, newHeight, hauteurFenetre));
        button.setFocusable(false);
        button.setBackground(BackgroundColor);
        button.setBounds(x, y, 50, 50);
        button.setVisible(visible);
        return button;
    }

    public static void changementCouleurBouton(JButton button, Color couleurSurvolee, Color couleurOriginale) {
        button.getModel().addChangeListener(e -> {
            ButtonModel model = (ButtonModel) e.getSource();
            if (model.isRollover()) {
                button.setBackground(couleurSurvolee);
            } else {
                button.setBackground(couleurOriginale);
            }
        });
    }

    //TODO essayer de fuisionner les méthodes animatedText
    public void animatedText(String descriptionNode,  ArrayList<String> titreNode) {
    	description.setText("");
    	for (JButton b : buttonList) {
    		b.setText("");
    	}
    	if ( node.getNodeType().equals("ChanceNode") ) {
    		chanceButton.setText("");
    	}
    	
    	
    	animatedTextTimer = new Timer(1, new ActionListener() {
    		int index = 0;
    		int longestString = node.getNodeType().equals("ChanceNode") ?
    				findLongestString(descriptionNode, titreNode, "Lancer l'évènement aléatoire !")
    				: findLongestString(descriptionNode, titreNode, null);
    		@Override
            public void actionPerformed(ActionEvent e) {
    			if (( descriptionNode != null ) && ( index <descriptionNode.length() )) {
    				description.setText(description.getText() + descriptionNode.charAt(index));
    			}
    			for (int i=0 ; i<titreNode.size() ; i++) {
    				if ( index < titreNode.get(i).length() ) {
    					buttonList.get(i).setText(buttonList.get(i).getText() +titreNode.get(i).charAt(index));
    				}

    			}
    			if (( index <"Lancer l'évènement aléatoire !".length() )&&( node.getNodeType().equals("ChanceNode") )) {
    				chanceButton.setText(chanceButton.getText() + "Lancer l'évènement aléatoire !".charAt(index));
    			}
    			
    			index++;
    			if ( index == longestString ) {
    				((Timer) e.getSource()).stop();
    			}
    		}
    	});
    	animatedTextTimer.start();
    }
    public void animatedText(JTextArea component, String txt) {
    	
    	String messageDeFin ="<html><div style='text-align: center;'><br>" +
                "<span style='font-family: Britannic Bold; font-size: "+(int) Math.round( (30f/900f)*hauteurFenetre) +"px; color: red;'>FIN</span><br/>" +
                "<span style='font-family: Britannic Bold; font-size: "+(int) Math.round( (20f/900f)*hauteurFenetre) +"px; color: red;'>--- Un jeu créé par Tamazouzt Ait Eldjoudi et Mendel Souffir ---</span>" +
                "</div></html>";
    	fin.setBackground(Color.BLACK);
    	fin.setForeground(Color.RED);
    	animatedTextTimer = new Timer(1, new ActionListener() {
    		int index = 0;
    		int longestString = txt.length() > messageDeFin.length() ?
    				txt.length()
    				: messageDeFin.length();
    		
    		@Override
            public void actionPerformed(ActionEvent e) {
    			if (( txt != null ) && ( index <txt.length() )) {
    				component.setText(component.getText() + txt.charAt(index));
    			}
    			if (index <= messageDeFin.length()) {
                    fin.setText("<html><div style='text-align: center;'>" +
                    		messageDeFin.substring(0, index) +
                            "<span style='color: white;'>_</span></div></html>");
    			}
    			
    			index++;
    			if ( index == longestString ) {
    				((Timer) e.getSource()).stop();
    			}
    		}
    	});
    	animatedTextTimer.start();
    }
    public void animatedText(JButton component, String txt) {
    	animatedTextTimer = new Timer(1, new ActionListener() {
    		int index = 0;
    		@Override
            public void actionPerformed(ActionEvent e) {
    			if (( txt != null ) && ( index <txt.length() )) {
    				component.setText(component.getText() + txt.charAt(index));
    			}
    			index++;
    			if ( index == txt.length() ) {
    				((Timer) e.getSource()).stop();
    			}
    		}
    	});
    	animatedTextTimer.start();
    }
    
    /*
    private void animatedText(String descriptionNode,  ArrayList<String> titreNode) {
    	Timer timerDescription = new Timer(15, new ActionListener() {
            int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Ajouter le caractère actuel au JTextArea
            	description.append(String.valueOf(descriptionNode.charAt(index)));

                // Incrémenter l'index
                index++;

                // Arrêter le Timer une fois que tout le texte est affiché
                if (index == descriptionNode.length()) {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
    	// Démarrer le Timer
    	timerDescription.start();
    	
    	
    	
    	for (int i=0 ; i<titreNode.size() ; i++) {
         // Créer une animation de machine à écrire pour chaque bouton
            animateTextButton(buttonList.get(i), titreNode.get(i));
        } 
    }
    
    private void animateTextButton(JButton button, String text) {
        Timer timer = new Timer(15, new ActionListener() {
            int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < text.length()) {
                    button.setText(button.getText() + text.charAt(index));
                    index++;
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        // Démarrer le Timer
        timer.start();
    }
    */

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public void changeToDecisionNode() {
    	panel1.setPreferredSize(new Dimension(0, (int) Math.round( (6f/9f)*hauteurFenetre ))); 
        panel2.setPreferredSize(new Dimension(0, (int) Math.round( (1f/9f)*hauteurFenetre ))); 
        panel3.setPreferredSize(new Dimension(0, (int) Math.round( (2f/9f)*hauteurFenetre )));
        panel3.setLayout(new GridLayout(2, 2));
        if (node.getSuccNode().size() == 1) {
        	panel2.setPreferredSize(new Dimension(0, (int) Math.round( (2f/9f)*hauteurFenetre ))); 
            panel3.setPreferredSize(new Dimension(0, (int) Math.round( (1f/9f)*hauteurFenetre )));
	        panel3.setLayout(new BorderLayout());
		}
        panel4.setPreferredSize(new Dimension(0, 0)); 
    	panel4.setVisible(false);
    	
    	
    	imageLabel.setIcon(redimensionne(node.getImagePath(), largeurFenetre, (int) Math.round( (6f/9f)*hauteurFenetre ), hauteurFenetre )) ;
    	
    	description.setText("");
    	
    	panel3.removeAll();
    	panel3.revalidate();
        panel3.repaint();
        buttonList = new ArrayList<>();
        createComponentsPanel3(node.getTitreNode());
        addActionListenerPanel3( node.getSuccNode() );
        addComponentsPanel3ToFrame(node.getTitreNode());
        
        animatedText(node.getDescription(), node.getTitreNode());
        /*
        description.setText(node.getDescription());
        int i = 0;
        for(JButton b : buttonList) {
        	b.setText(node.getTitreNode().get(i++));
        }
        */
        
    }
    
    public void changeToChanceNode() {
    	panel1.setPreferredSize(new Dimension(0, (int) Math.round( (550f/900f)*hauteurFenetre ))); 
        panel2.setPreferredSize(new Dimension(0, (int) Math.round( (1f/9f)*hauteurFenetre ))); 
        panel3.setPreferredSize(new Dimension(0, (int) Math.round( (2f/9f)*hauteurFenetre )));
        panel3.setLayout(new GridLayout(2, 2));
        panel4.setPreferredSize(new Dimension(0, (int) Math.round( (50f/900f)*hauteurFenetre ))); 
        
    	
    	imageLabel.setIcon(redimensionne(node.getImagePath(), largeurFenetre, (int) Math.round( (6f/9f)*hauteurFenetre ), hauteurFenetre )) ;

    	description.setText("");
    	
    	panel3.removeAll();
    	panel3.revalidate();
        panel3.repaint();
        buttonList = new ArrayList<>();
        createComponentsPanel3(node.getTitreNode());
        addComponentsPanel3ToFrame(node.getTitreNode());
        
        panel4.removeAll();
    	panel4.revalidate();
        panel4.repaint();
        panel4.setVisible(true);
        createComponentsPanel4();
        addActionListenerPanel4(node.getSuccNode());
        addComponentsPanel4ToFrame();
        
        //chanceButton.setText(""); //tester si c'est vraiment utile
        animatedText(node.getDescription(), node.getTitreNode());
    }
    
    public void changeToTerminalNode() {
    	panel1.setPreferredSize(new Dimension(0, (int) Math.round( (6f/9f)*hauteurFenetre ))); 
        panel2.setPreferredSize(new Dimension(0, (int) Math.round( (1f/9f)*hauteurFenetre ))); 
        panel3.setPreferredSize(new Dimension(0, (int) Math.round( (2f/9f)*hauteurFenetre )));
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
        panel4.setPreferredSize(new Dimension(0, 0)); 
    	panel4.setVisible(false);

    	imageLabel.setIcon(redimensionne(node.getImagePath(), largeurFenetre, (int) Math.round( (6f/9f)*hauteurFenetre ), hauteurFenetre )) ;
    	
    	description.setText("");
    	
    	panel3.removeAll();
        panel3.revalidate();
        panel3.repaint();
        buttonList = new ArrayList<>();
        createComponentsPanel3(node.getTitreNode());
        addComponentsPanel3ToFrame(node.getTitreNode());
    	
		animatedText(description, node.getDescription());

    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public int findLongestString(String s1, ArrayList<String> s2, String s3) {
    	ArrayList<String> copie = new ArrayList<>(s2);
    	copie.add(s3);
        int longestString = s1 == null ? 0 : s1.length();
        for (String str : copie) {
            if ( (str==null ? 0 : str.length()) > longestString) {
                longestString = str==null ? 0 : str.length();
            }
        }

        return longestString;
    }
    
    
    /*
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    	Joueur p = new JoueurChimiste("Mendel");
    	Tree t = new Tree(p);
    	new Interface(t,null);
    }
    */	
}

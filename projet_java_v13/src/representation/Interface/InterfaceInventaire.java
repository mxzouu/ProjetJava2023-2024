/**
 * Classe qui implemente la fenêtre de l'inventaire dans l'interface graphique
 */
package representation.Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import univers.*;

@SuppressWarnings("serial")
public class InterfaceInventaire extends SecondWindow {
	private Joueur player;
	private int onglet;
	private int hauteurFenetre = 980;
	private int largeurFenetre = 735;
	private JLabel backgroundImage;
	private JButton gadgetButton;
	private JButton personnageButton;
	private JButton infoButton;
	
	private JPanel gadgetPanel;
	private ArrayList<JLabel> gadgetList = new ArrayList<>();
	//private JScrollPane armeScrollPane;
	
	private JPanel personnagePanel;
	private ArrayList<JButton> pnjList = new ArrayList<>();
	private ArrayList<Tuple<Integer,Integer>> pnjPosition = new ArrayList<>();
	private ArrayList<Tuple<Integer,Integer>> pnjDimension = new ArrayList<>();
	private int[] personnageAffiche = {-1};
	private ArrayList<JEditorPane> pnjInformation= new ArrayList<>();
	//private JScrollPane personnageScrollPane;
	
	private JPanel infoPanel;
	private ArrayList<JLabel> infoList = new ArrayList<>();
	//private JScrollPane infoScrollPane;
	
	private JToolTip toolTip = new JToolTip();
	private Popup currentPopup;
	

	public InterfaceInventaire(final JFrame parent, Joueur p, int onglet) {
		super(parent);
		player=p;
		this.onglet=onglet;
		
		
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize(); //recupere les dimensions de l'ecran
        int height = (int)dimension.getHeight();
        int width  = (int)dimension.getWidth();
        hauteurFenetre = (int) Math.round( (735f/1080f)*height );
        largeurFenetre =  (int) Math.round( (980f/1920f)*width );
        
        // Configuration de la fenêtre principale
        setTitle("Inventaire");
        setIconImage(InterfaceTree.redimensionne("src" + File.separator + "Icons" + File.separator + "inventaire.png",40,40,40).getImage());
        getContentPane().setBackground(Color.BLACK);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(largeurFenetre, hauteurFenetre);
        setResizable(false);
        setLocationRelativeTo(null);
        toFront();
        //setUndecorated(true);
        //setBackground(new Color(0, 0, 0, 0));
        
       
        createComponents();
        
        addComponentsToFrame();
        
        if (onglet == 1) {
        	gadgetPanel.setVisible(true);
        	personnagePanel.setVisible(false);
        	infoPanel.setVisible(false);
        } else if(onglet == 2) {
        	gadgetPanel.setVisible(false);
        	personnagePanel.setVisible(true);
        	infoPanel.setVisible(false);
        } else if(onglet == 3){
        	gadgetPanel.setVisible(false);
        	personnagePanel.setVisible(false);
        	infoPanel.setVisible(true);
        } else {
        	throw new IllegalArgumentException(
    				"\nle 3eme argument ne peut prendre que les valeurs 1,2 ou 3 \nil est actuellement égale à " + onglet);
        }
        
        setVisible(true);
        
        for (JButton b : pnjList) {
			pnjPosition.add( new Tuple<>(b.getX(), b.getY()) );
			pnjDimension.add(new Tuple<>(b.getWidth(), b.getHeight()));
		}
        configureActionButton();
	}
	
	public void createComponents() {
		
		String imageName = onglet == 1 ? "dossiers_noir_onglet_1_sur_3.png" : 
			onglet == 2 ? "dossiers_noir_onglet_2_sur_3.png" : "dossiers_noir_onglet_3_sur_3.png";
		backgroundImage = new JLabel(
				InterfaceTree.redimensionne(
						"src"+File.separator+"Icons"+File.separator+"dossierimage"+File.separator+imageName ,
						largeurFenetre-10 , 
						hauteurFenetre-40 , 
						hauteurFenetre-40 
						)
				);

		gadgetButton = createButton("src" + File.separator + "Icons" + File.separator +"Gadget2.png", "Gadgets", (int) Math.round( (20f/980f)*largeurFenetre ),(int) Math.round( (10f/735f)*hauteurFenetre) );
		personnageButton = createButton("src" + File.separator + "Icons" + File.separator +"fiche_personnage.png", "Personnages", (int) Math.round( (327f/980f)*largeurFenetre ), (int) Math.round( (10f/735f)*hauteurFenetre) ) ;
		infoButton = createButton("src" + File.separator + "Icons" + File.separator +"Info.png", "Informations découvertes", (int) Math.round( (645f/980f)*largeurFenetre ), (int) Math.round( (10f/735f)*hauteurFenetre) ) ;
		
		LineBorder blackBorder = new LineBorder(Color.BLACK, 5);
		LineBorder blackBorder2 = new LineBorder(Color.BLACK, 2);
		LineBorder grayBorder = new LineBorder(Color.DARK_GRAY, 5);
		
		gadgetPanel = createPanel();
		int index = 0;
		for (Gadget g : player.getGadget() ) {
			JLabel tempLabel = new JLabel(InterfaceTree.redimensionne(
									"src"+File.separator+"Icons"+File.separator+"Gadgets"+File.separator+g+".png" ,
									150 , 
									150 , 
									150 
									)
								);
			tempLabel.setBackground(Color.WHITE);
			tempLabel.setBorder(blackBorder);
			tempLabel.setPreferredSize(new Dimension( (int) Math.round( (160f/980f)*largeurFenetre ) , (int) Math.round( (160f/980f)*largeurFenetre ) ));
			
			tempLabel.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    showTooltip(tempLabel, g.toString(), e.getX(), e.getY());
                }
            });
            
			tempLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                    hideTooltip();
                }
            });
			
			gadgetList.add( tempLabel );
			index++;
		}
		if (index == 0) {
			gadgetPanel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weighty = 1.0; // Permet au composant de prendre toute la hauteur disponible
			gbc.anchor = GridBagConstraints.CENTER;

			JLabel tempLabel = new JLabel("Vous ne possédez aucun gadget pour le moment");
			gadgetPanel.add(tempLabel, gbc);
		}
		
		
		personnagePanel = createPanel();
		index = 0;
		for (PNJ pnj : player.getFichePerso() ) {
			JButton tempButton = new JButton(InterfaceTree.redimensionne(
										//"src"+File.separator+"Icons"+File.separator+"Personnages"+File.separator+pnj.getIdentite()+".png" ,
										"src" + File.separator + "Icons" + File.separator + "logo.png",
										150 , 
										150 , 
										150 
										)
									);
			tempButton.setPreferredSize(new Dimension( (int) Math.round( (160f/980f)*largeurFenetre ) , (int) Math.round( (160f/980f)*largeurFenetre ) ));
			tempButton.setBorder(blackBorder);
			tempButton.getModel().addChangeListener(e -> {
	            ButtonModel model = (ButtonModel) e.getSource();
	            if (model.isRollover()) {
	            	tempButton.setBorder(grayBorder);
	            } else {
	            	tempButton.setBorder(blackBorder);
	            }
	        });
			
			
			tempButton.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    showTooltip(tempButton, pnj.getIdentite(), e.getX(), e.getY());
                }
            });
            
			tempButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                    hideTooltip();
                }
            });
			pnjList.add( tempButton );
			
			pnjInformation.add(createTextArea(pnj.toString_Interface( (int) Math.round( (15f/735f)*hauteurFenetre) )));
			
			index++;
		}
		if (index == 0) {
			personnagePanel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weighty = 1.0; // Permet au composant de prendre toute la hauteur disponible
			gbc.anchor = GridBagConstraints.CENTER;

			JLabel tempLabel = new JLabel("Vous ne possédez aucune information sur les personnages pour le moment");
			personnagePanel.add(tempLabel, gbc);
		}
		
		infoPanel = createPanel();
		index = 0;
		for (String s : player.getInformationDecouverte() ) {
			JLabel tempLabel = new JLabel("  "+s);
		    tempLabel.setPreferredSize(new Dimension(infoPanel.getWidth(), (int) Math.round( (50f/735f)*hauteurFenetre))); 
		    //tempLabel.setHorizontalAlignment(JLabel.CENTER);
		    tempLabel.setBackground(Color.WHITE);
		    tempLabel.setBorder(blackBorder2);

		    infoList.add(tempLabel);
		    index++;
		}
		Collections.reverse(infoList);
		if (index == 0) {
			infoPanel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weighty = 1.0; // Permet au composant de prendre toute la hauteur disponible
			gbc.anchor = GridBagConstraints.CENTER;

			JLabel tempLabel = new JLabel("Vous n'avez découvert aucune information pour le moment");
			infoPanel.add(tempLabel, gbc);
		}
		
	}
	
	private void configureActionButton() {
		gadgetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (onglet != 1) {
            		changeTogadgetPanel();
            		
                }
            }
        });
		
		personnageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (onglet != 2) {
            		changeToPersonnagePanel();
                } 
            }
        });
		
		infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (onglet != 3) {
            		changeToInfoPanel();
                } 
            }
        });
		
		
		for (int i=0; i<pnjList.size() ; i ++) {
			int index = i;
			pnjList.get(i).addActionListener(new ActionListener() {
	            
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	personnagePanel.setLayout(null);
	            	
	            	
	            	
	            	boolean[] finTimer = {true};
	            	
	            	Timer timer ;
	            	
	            	if (personnageAffiche[0] == -1) {
	            		pnjInformation.get(index).setBounds(personnagePanel.getWidth(),5,1000,1000);
	            		pnjInformation.get(index).setOpaque(false);
	            		pnjInformation.get(index).setVisible(true);
	            		int[] x_text = {pnjInformation.get(index).getX()};
	            		int[] x_text_goal = {pnjList.get(index).getWidth() + 5 + 5};
	            		if (pnjDimension.get(index).getVal1() != pnjList.get(index).getWidth()) {
	            			System.out.println("bite");
	            			System.exit(1);
	            		}
	            		
	            		ArrayList<Tuple<Integer,Integer>> position_current = new ArrayList<>();
		            	for (int j=0;j<pnjList.size();j++) {
	                		position_current.add(new Tuple<>( pnjList.get(j).getX(),pnjList.get(j).getY() ));
	                	}
	            		
	            		ArrayList<Tuple<Integer,Integer>> position_goal = new ArrayList<>();
		            	for (int j=0;j<pnjList.size();j++) {
	                		if (j != index) {
	                			position_goal.add(new Tuple<>( pnjList.get(j).getX(), -( pnjList.get(j).getY()+ pnjList.get(j).getHeight() ) ));
	                			if (pnjDimension.get(j).getVal2() != pnjList.get(j).getHeight() ) {
	    	            			System.out.println("bite2");
	    	            			System.exit(1);
	    	            		}
	                		}else {
	                			position_goal.add( new Tuple<>(5,5) );
	                		}
	                	}
	            		
	            		
	            		timer = new Timer(1, new ActionListener() {
	                        @Override
	                        public void actionPerformed(ActionEvent e) {
	                        	finTimer[0] = true;
	                        	for (int j=0;j<pnjList.size();j++) {
	                        		if ( position_current.get(j).getVal1() > position_goal.get(j).getVal1() ) {
	                        			position_current.get(j).setVal1( 
	                        					position_current.get(j).getVal1() - 20 <= position_goal.get(j).getVal1() ?
	                        							position_goal.get(j).getVal1() :
	                        							position_current.get(j).getVal1() - 20
	                        					);
	                        			finTimer[0] = false;
	                        		}
	                        		if ( position_current.get(j).getVal2() > position_goal.get(j).getVal2() ) {
	                        			position_current.get(j).setVal2( 
	                        					position_current.get(j).getVal2() - 20 <= position_goal.get(j).getVal2() ?
	                        							position_goal.get(j).getVal2() :
	                        							position_current.get(j).getVal2() - 20
	                        					);
	                        			finTimer[0] = false;
	                        		}
	                        		pnjList.get(j).setLocation(position_current.get(j).getVal1() , position_current.get(j).getVal2() );
	                        	}
	                        	
	                        	if ( x_text[0] > x_text_goal[0] ) {
	                        		x_text[0] = x_text[0]-25 <= x_text_goal[0] ? x_text_goal[0] : x_text[0]-25;
	                        		pnjInformation.get(index).setLocation(x_text[0], 5);
	                        		finTimer[0] = false;
	                        	}
	                        	
	                        	
	                        	if ( finTimer[0] ) {
	                        		((Timer) e.getSource()).stop();
	                                personnageAffiche[0] = index;
	                        	}
	                        }
	                    });
	            	} else  {
	            		
	            		//pnjInformation.get(index).setBounds(personnagePanel.getWidth(),5,1000,1000);
	            		
	            		int[] x_text = {pnjInformation.get(index).getX()}; // ie pnjDimension.get(index).getVal1() + 5 + 5
	            		int[] x_text_goal = {personnagePanel.getWidth()};
	            		
	            		ArrayList<Tuple<Integer,Integer>> position_current = new ArrayList<>();
		            	for (int j=0;j<pnjList.size();j++) {
	                		position_current.add(new Tuple<>( pnjList.get(j).getX(),pnjList.get(j).getY() ));
	                	}
	            		
	            		ArrayList<Tuple<Integer,Integer>> position_goal = new ArrayList<>(pnjPosition);
	            		
	            		timer = new Timer(1, new ActionListener() {
	            			
	                        @Override
	                        public void actionPerformed(ActionEvent e) {
	                        	finTimer[0] = true;
	                        	for (int j=0;j<pnjList.size();j++) {
	                        		if ( position_current.get(j).getVal1() < position_goal.get(j).getVal1() ) {
                        			position_current.get(j).setVal1( 
                        					position_current.get(j).getVal1() + 20 >= position_goal.get(j).getVal1() ?
                        							position_goal.get(j).getVal1() :
                        							position_current.get(j).getVal1() + 20
                        					);
                        			finTimer[0] = false;
	                        		}
	                        		if ( position_current.get(j).getVal2() < position_goal.get(j).getVal2() ) {
	                        			position_current.get(j).setVal2( 
	                        					position_current.get(j).getVal2() + 20 >= position_goal.get(j).getVal2() ?
	                        							position_goal.get(j).getVal2() :
	                        							position_current.get(j).getVal2() + 20
	                        					);
	                        			finTimer[0] = false;
	                        		}
	                        		pnjList.get(j).setLocation(position_current.get(j).getVal1() , position_current.get(j).getVal2() );
	                        	}
	                        	
	                        	if ( x_text[0] < x_text_goal[0] ) {
	                        		x_text[0] = x_text[0]+25 >= x_text_goal[0] ? x_text_goal[0] : x_text[0]+25;
	                        		pnjInformation.get(index).setLocation(x_text[0], 5);
	                        		finTimer[0] = false;
	                        	}
	                        	
	                        	if ( finTimer[0] ) {
                                    ((Timer) e.getSource()).stop();
                                    personnageAffiche[0] = -1;
                                    pnjInformation.get(index).setVisible(false);
                        		}
	                        }
	                    });
	            	}
	            	
	            	timer.start();
	            }
	        });
		}
		
	}
	
	public void addComponentsToFrame() {

		for (JLabel l : infoList) {
			infoPanel.add(l);
		}
		
		add(infoPanel);
		
		for (int i=0 ; i<pnjList.size() ; i++) {
			personnagePanel.add(pnjList.get(i));
			personnagePanel.add(pnjInformation.get(i));
		}
		add(personnagePanel);
		
		
		
		for (JLabel l : gadgetList) {
			gadgetPanel.add(l);
		}
		add(gadgetPanel);
		
		add(gadgetButton);
		add(personnageButton);
		add(infoButton);
		
		add(backgroundImage);
		
	}
	
	public JButton createButton(String imagePath, String txt, int x, int y) {
        JButton button = new JButton(InterfaceTree.redimensionne(imagePath, 15, 15, hauteurFenetre));
        button.setText(txt);
        button.setFocusable(false);
        button.setOpaque(false); // Rend le bouton transparent
        button.setContentAreaFilled(false); // Supprime le remplissage du contenu
        button.setBorderPainted(false); // Supprime la bordure peinte
        button.setFont(button.getFont().deriveFont(Font.PLAIN));
        
        button.setBounds(x, y, (int) Math.round( (270f/980f)*largeurFenetre ), (int) Math.round( (30f/735f)*hauteurFenetre ));
        button.getModel().addChangeListener(e -> {
            ButtonModel model = (ButtonModel) e.getSource();
            if (model.isRollover()) {
            	button.setFont(button.getFont().deriveFont(Font.BOLD));
            } else {
            	button.setFont(button.getFont().deriveFont(Font.PLAIN));
            }
        });
        return button;
    }

	public JPanel createPanel() {
		JPanel panel = new JPanel();
		panel.setBounds(15, 100, largeurFenetre-50, hauteurFenetre-160);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.setOpaque(false);
		return panel;
	}
	
	public JEditorPane createTextArea(String s) {
		JEditorPane text = new JEditorPane("text/html", s);
		text.setEditable(false);
		text.setOpaque(false);
		text.setVisible(false);
		
		return text;
	}
	
	public void showTooltip(JComponent component, String txt, int x, int y) {
	    hideTooltip();

	    toolTip = component.createToolTip();
	    toolTip.setTipText(txt);

	    PopupFactory popupFactory = PopupFactory.getSharedInstance();
	    Point location = component.getLocationOnScreen();
	    int screenX = location.x + x + 10;
	    int screenY = location.y + y +10;

	    currentPopup = popupFactory.getPopup(component, toolTip, screenX, screenY);
	    currentPopup.show();
	}

	public void hideTooltip() {
	    if (currentPopup != null) {
	        currentPopup.hide();
	        currentPopup = null;
	    }
	}

	
	public void changeTogadgetPanel() {
		onglet = 1;
		backgroundImage.setIcon(InterfaceTree.redimensionne(
				"src"+File.separator+"Icons"+File.separator+"dossierimage"+File.separator+"dossiers_noir_onglet_1_sur_3.png" ,
				largeurFenetre-10 , 
				hauteurFenetre-40 , 
				hauteurFenetre-40 
				));
		gadgetPanel.setVisible(true);
    	personnagePanel.setVisible(false);
    	infoPanel.setVisible(false);
    	
    	revalidate();
    	repaint();
	}
	public void changeToPersonnagePanel() {
		onglet = 2;
		backgroundImage.setIcon(InterfaceTree.redimensionne(
				"src"+File.separator+"Icons"+File.separator+"dossierimage"+File.separator+"dossiers_noir_onglet_2_sur_3.png" ,
				largeurFenetre-10 , 
				hauteurFenetre-40 , 
				hauteurFenetre-40 
				));
		gadgetPanel.setVisible(false);
    	personnagePanel.setVisible(true);
    	infoPanel.setVisible(false);
    	
    	revalidate();
    	repaint();
	}
	public void changeToInfoPanel() {
		onglet = 3;
		backgroundImage.setIcon(InterfaceTree.redimensionne(
				"src"+File.separator+"Icons"+File.separator+"dossierimage"+File.separator+"dossiers_noir_onglet_3_sur_3.png" ,
				largeurFenetre-10 , 
				hauteurFenetre-40 , 
				hauteurFenetre-40 
				));
		gadgetPanel.setVisible(false);
    	personnagePanel.setVisible(false);
    	infoPanel.setVisible(true);
    	
    	revalidate();
    	repaint();
	}
	/*
	public static void main(String[] args) {
		//ArrayList<Arme> a = new ArrayList<>(Arrays.asList(Arme.ACCIDENT_DE_LABORATOIRE,Arme.ALLERGIE, Arme.BOMBE,Arme.CHANTAGE, Arme.COUTEAU_DE_CUISINE, Arme.DEFENESTRATION, Arme.ELECTROCUTION, Arme.ELECTROCUTION));
		ArrayList<Gadget> g = new ArrayList<>(Arrays.asList(Gadget.Cape_invisibilite,
		Gadget.Micro_espion,
		Gadget.Lunette,
		Gadget.Virus_informatique,
		Gadget.Brouilleur,
		Gadget.Stylo_Somnifere,
		Gadget.Thermite,
		Gadget.Poison,
		Gadget.Bombe,
		Gadget.Deguisement,
		Gadget.Parfum_hypnotiseur,
		Gadget.Blanchisseur_de_dents));
		ArrayList<String> info = new ArrayList<>();
		
		for (int i=0 ; i<15 ; i++) {
			info.add( "info"+i);
		}
		
		Joueur p = new JoueurInformaticien();
		p.setGadget(g);
		p.setInformationDecouverte(info);
		
		InterfaceInventaire ii = new InterfaceInventaire(null,p, 1);
	}
	*/
}

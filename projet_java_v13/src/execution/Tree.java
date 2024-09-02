/**
 * Classe ou est construit le graphe de l'histoire
 */
package execution;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import representation.*;
import univers.Gadget;
import univers.Hobbie;
import univers.Joueur;
import univers.JoueurCharismatique;
import univers.JoueurChimiste;
import univers.JoueurInformaticien;
import univers.PNJ;

@SuppressWarnings("serial")
public class Tree implements Serializable{
	private Joueur player;
	private Event racine;// on enverra par la suite que la racine de l'arbre 
	private SerializableClip musiqueAmbiance;
	private float volumeGlobal = 1.0f;
	private Number heure; 

	
	public Joueur getPlayer() {
		return player;
	}

	public void setPlayer(Joueur p) {
		this.player = p;
	}

	public Event getRacine() {
		return racine;
	}

	public void setRacine(Event racine) {
		this.racine = racine;
	}
	/**
	 * permet d'incrémenter le niveau de méfiance de tous les PNJ, si ce niveau dépasse un certain nombre, c'est Game Over
	 */
	public void augmenterMefianceTous() {
		for (int i=0; i<player.getFichePerso().size(); i++) {
				if ( ! player.getAllie().contains(player.getFichePerso().get(i))) {
					player.getFichePerso().get(i).setSoupcon( player.getFichePerso().get(i).getSoupcon() + 1 );
				}
    			
    		}
	}
	/**
	 * Diminuer le niveau de soupcon des PNJ de 1
	 */
	public void diminuerMefianceTous() {
		for (int i=0; i<player.getFichePerso().size(); i++) {
			if ( ! player.getAllie().contains(player.getFichePerso().get(i))) {
				player.getFichePerso().get(i).setSoupcon( player.getFichePerso().get(i).getSoupcon() - 1 );
			}
    		}
	}
	/**
	 * gère un certain cas dans l'implementation du graphe
	 */
	public void Function_EntrerBureau() {
		try {
			changerMusiqueAmbiance("musique_d'ambiance1.wav");
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		PNJ cible = player.getFichePerso().get(0);
		ArrayList<String> s = cible.getInfoComplementaire();
		s.add("Partage son bureau avec quelqu'un");
		cible.setInfoComplementaire(s);
	}
	/**
	 * permet de mettre à jour les attributs de certains PNJ selon le choix du joueur
	 */
	public void Function_ParlerMaquette() {
		PNJ cible = player.getFichePerso().get(0);
		cible.setHobbie( new ArrayList<Hobbie>(Arrays.asList(Hobbie.ASTRONOMIE)) );
	}
	/**
	 * permet de mettre à jour les attributs de certains PNJ selon le choix du joueur
	 */
	public void Function_ParlerTasse() {
		PNJ cible = player.getFichePerso().get(0);
		ArrayList<String> s = cible.getFaiblesse();
		s.add("fille morte ?");
		cible.setFaiblesse( s );
	}
	/**
	 * permet de mettre à jour les attributs de certains PNJ selon le choix du joueur
	 */
	public void Function_Suivrelecours() {
		player.setLvlCompetence(player.getLvlCompetence()+1);
	}
	/**
	 * permet de mettre à jour les attributs de certains PNJ selon le choix du joueur
	 */
	public void Function_Parlerauxautreseleves() {
		player.setLvlSociabilite(player.getLvlSociabilite() +1);
	}
	/**
	 * permet de mettre à jour les attributs de certains PNJ selon le choix du joueur
	 */
	public MAJAjoutNode Function_Continuervosrecherchespro1(Event e1, Event e2, String s) {
		return new MAJAjoutNode(e1, e2, s);
	}
	

	/**
	 * permet de mettre à jour les attributs de certains PNJ selon le choix du joueur
	 */
	public void Function_ContinuerAChercher() {
		PNJ cible = player.getFichePerso().get(0);
		ArrayList<String> s = cible.getFaiblesse();
		s.add("Accident de voiture ?");
		cible.setFaiblesse( s );
	}
	
	/**
	 * augmenter le soupcon du PNJ Nikolai de 1
	 */
    public void augmenterMefianceNikolai() {
    	PNJ nik=player.getFichePerso().get(4);
    	nik.setSoupcon(nik.getSoupcon()+1);        
    }
  
   /**
    * ajouter un gadget 
    * @param g: le gadget à ajouter
    */
    public void ajoutGadget(Gadget g) {
    	player.addGadget(g);
    }
    
    public static boolean in(ArrayList<String> liste,String s) {
    	for (String element: liste) {
    		if (element.equals(s)){
    			return true;
    		}
    	}
    	return false;
    }
    /**
     * Création du graphe
     * @param player
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
	public Tree(Joueur player) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		changerMusiqueAmbiance("musique_d'ambiance2.wav");

		
		/////////////////////////////////////////////////// CREATION DES NODES ////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		Event bienvenue = new DecisionNode("Bienvenue à l'université Agent 47\n"
				+ "Votre mission, si vous l'acceptez, infiltrer l'université où le professeur Kessler un éminent scientifique donne cours et poursuit ses recherches.\n"
				+ "Ses recherches sont très dangereuse et menacent le pays tout entier.\n"
				+ "Le 1er objectif de votre mission est de voler ses recherches, le second est de mettre un terme au agissement du professeur en l'enliminant.\n"
				+ "Cette mission demande finesse, subtilité et une maîtrise totale de l'infiltration, vous disposez de 2 jours pour accomplir votre mission. \n"
				, player);
			bienvenue = new SoundNode(bienvenue, "intro.wav", 1.0f, false);/// pour mettre un son particulier a un node
		Event explication = new DecisionNode("Votre couverture est celle d'un étudiant en doctorat, nouvellement arrivé à Paris pour vos études. "
				+ "Vous étiez précedemment à l'université \"Massey\" en Nouvelle-Zélande.\n"
				+ "Vous disposez, pour le moment, de 3 gadgets et d'informations basiques sur cetains membres de l'université."
				+ "Vous avez acces a votre inventaire en haut a gauche de l'écran.\n"
				+ "Bonne chance Agent 47.", player);
		
		Event infiltrerSalleDeControle = new DecisionNode("Vous allez à la salle de contrôle et tombez face à face avec un garde qui surveille les caméras de sécurité. Il vous demande ce que vous faites là. ", player);
		Event EndormirGarde = new DecisionNode("Vous avez maintenant le champs libre pour fouiller la salle mais faites vite vous entendez des bruits à l'exterieur de la salle", player, () -> {
				if (player.getGadget().contains(Gadget.Stylo_Somnifere)) {
					player.removeGadget(Gadget.Stylo_Somnifere);
				}

			});
		
		Event debut = new DecisionNode("Des votre arrivée vous commencez à explorer l'université. Vous apercevez plusieurs membres de la sécurité se diriger vers un même endroit il s'agit très certainement d'une réunion de sécurité. ", player,
				()-> {
					if (player.getGadget().contains(Gadget.Stylo_Somnifere)) {
						infiltrerSalleDeControle.ajoutNodeList(EndormirGarde, "Utiliser le Gadget Stylo-somnifère sur le garde");
					}
				});
		
		Event fouillerSalleDeControle = new ChanceNode("Vous trouvez une liste de code permettant d'acceder à certaines zones sous acces réglémenté",player,
				() -> {player.addInformationDecouverte("liste de codes");});
		
		Event fouillerSalleDeControle2 = new DecisionNode("Vous ne trouvez rien d'autres suceptible de vous aider dans votre mission vous sorter donc de la salle.\n"
				+ "Vous vous rendez compte que vous avez un cours, pour préserver votre couverture vous décidez d'y aller\n"
				+ "Sur le chemin, vous vous trouvez nez à nez avec un autre étudiant, nommé \"Nikolai Steele\". "
	    		+ "Il vous scrute avec méfiance.",player);
		
		Event QuelquunRentre = new DecisionNode("Un agent de sécurite rentre de la salle, vous tenter de fuir immédiatement mais il essaye de vous suivre",player);
		
		
		Event mentirSalleDeControle = new DecisionNode("Il trouve cela très étonnant car il n'avait pas été prevenu qu'un nouveau membre arrivait aujourd'hui. Il vous demande de patienter pendant qu'il vérifie cette information.",player);
		
		Event FuirSalleDeControle = new DecisionNode("Vous arrivez a fuir mais les membres de la sécurité seront maintenant un peu plus sur leur garde", player, ()->augmenterMefianceTous());
		
		
        Event RejoindreReunion= new DecisionNode("vous observez des membres de la sécurité discuter de mesures renforcées pour protéger "
        		+ "les recherches du Professeur Kessler. Tout à coup, vous repérez un document confidentiel sur la table, mais la"
        		+ " sécurité est vigilante.", player);
	    	RejoindreReunion = new SoundNode(RejoindreReunion, new String[]{"bureau_silence.wav"}, new float[]{1.0f}, new boolean[]{true});
	    Event glisserDoc= new DecisionNode("Vous réussissez à glisser le document dans votre poche, mais un agent de"
	    		+ " sécurite vous surprend et essaye de vous suivre", player, ()->{player.addInformationDecouverte("Document confidentiel de sécurité");});
	    Event netraliserCape= new DecisionNode("Vous ne disposez plus de la cape d'invisibilité, mais vous avez réussi à éviter l'agent avec succès \n"
	    		+ "Vous vous dirigez vers un cours",player,()->{player.removeGadget(Gadget.Cape_invisibilite); System.out.println("gadgets: "+player.getGadget().get(0));});
	    Event courir= new DecisionNode("Vous avez réussi à éviter l'agent avec succès \n vous vous dirigez vers un cours", player, ()->augmenterMefianceTous());
	    Event suivantSecu= new DecisionNode("Vous rencontrez un étudiant nommé Nikolai Steele, il vous accompagne mais il a vu le document",player,()->augmenterMefianceNikolai());
	    
	    Event rencontreNikolai = new DecisionNode("Vous vous rendez compte que vous avez un cours, pour préserver votre couverture vous décidez d'y aller\n"
				+ "Sur le chemin, vous vous trouvez nez à nez avec un autre étudiant, nommé \"Nikolai Steele\". "
	    		+ "Il vous scrute avec méfiance."
				+ "Nikolai Steele : \"Que fais-tu ici ? Je ne t'ai jamais vu ici"
	    		+ " avant. T'es nouveau, c'est ça ?\"\n Agent 47 : \"Euh, oui, je suis nouveau ici. Tu sais où se trouve le cours de sociologie du professeur Blackwell?\" ."
				+ "Nikolai Steele vous dévisage, mais après un moment de réflexion, il semble légèrement moins méfiant.\r\n"
	    		+ "\r\n"
	    		+ "Nikolai Steele : \"Bien, les nouveaux ont souvent du mal à s'orienter ici. Tu as de la chance j'assiste aussi a ce cours. Viens, je vais te montrer où est le cours. "
	    		+ "On ne voudrait pas être en retard.\"",
	    		player);	//()->augmenterMefianceNikolai()
	 	Event SuivantExplorer3 = new DecisionNode("En allant en cours, vous surprenez une discussion entre professeurs",player);
	 	Event rejoindreDiscussionProf= new DecisionNode("Au cours de la conversation, un professeur de mathématiques Mr.Dupont vous propose de le rejoindre dans "
	 			+ "son bureau, en B506, après votre cours, pour une discussion plus approfondie sur le fonctionnement de votre doctorat",player);
	 	Event sortirDuCours= new DecisionNode("Vous quittez le cours et Nikolai",player);
	 	Event allerAuBureauDuProf= new DecisionNode("Ayant accepté la proposition du prof, vous vous dirigez vers son bureau",player);
	 	Event allerBureauKessler= new DecisionNode("Vous vous dirigez en sortant vers le bureau du professeur Kessler",player);
	    Event accepter= new DecisionNode("Vous dites au professeur Dupont que vous irez le voir après votre cours, vous le quittez et allez en cours.\nVous êtes maintenant en cours, accompagné de Nikolai. ",player,()-> {player.addInformationDecouverte("Bureau de Dupont: B506");sortirDuCours.ajoutNodeList(allerAuBureauDuProf, "Aller au bureau du professeur Dupont");});
	    Event decliner= new DecisionNode("Après avoir décliné la proposition du professeur Dupont vous lui dites que vous avez cours et vous le quitter.\nVous êtes maintenant en cours, accompagé de Nikolai",player,()->sortirDuCours.ajoutNodeList(allerBureauKessler, "Se diriger vers le Bureau de M.Kessler"));
	    
	    Event coursNikolai = new DecisionNode("Une fois en cours, Nikolai vous présente deux amis à lui, Anas et Tom", player);
	    
	    Event parlerAnas = new DecisionNode("Anas semble parfaitement manipiuler des les outils informatique.",player);
	    Event demanderEDT= new DecisionNode("Anas vous apprends comment récupérer les emplois du temps des profs et des étudiants \n le cours commence",player,()->{augmenterMefianceTous(); player.addInformationDecouverte("EDT");});
	    Event demanderCarte= new DecisionNode("Anas vous apprends comment avoir acces à une carte détaillée du batiment de l'université \n le cours commence",player,()->{augmenterMefianceTous();player.addInformationDecouverte("CARTE");});
	    Event demanderAnnales= new DecisionNode("Anas vous donne un moyen d'avoir les annales d'examens des années précedentes \n le cours commence",player,()->{player.addInformationDecouverte("ANNALES");});
	    
	    Event parlerTom= new DecisionNode("Tom semble être bon en mathématiques",player);
	    Event poserQstCours= new DecisionNode("Tom vous explique une preuve mathématique \n Le cours commence ",player, ()->{player.addInformationDecouverte("PREUVE MATHEMATIQUE");});
	    Event poserQstProf= new DecisionNode("TOM: Je n'ai jamais eu de cours avec le professeur Kessler, mais d'après les dires, ses recherches sont "
	    		+ "respectables \n le cours commence",player,()->augmenterMefianceTous());
	    Event suivreCours1= new DecisionNode("Vous suivez tout le cours ce qui fait baisser la mefiance de tous les personnages\nC'est la fin du cours",player, ()->diminuerMefianceTous());
	    
	    Event ecouterB = new DecisionNode("M.Dupont: Merci M.Kessler, c'est noté, Rendez-vous au laboratoire pour en discuter plus calmement",player,()->player.addInformationDecouverte("Existence d'un laboratoire secret"));
	    Event toquerPorte = new DecisionNode("M.Dupont vous ouvre la porte en disant au revoir à son collègue le professeur Kessler",player);
	    Event suivantB = new DecisionNode("Le professeur Kessler sort brusquement du bureau et vous surprends entrain d'écouter aux portes \n vous entrez dans le bureau ",player,()->augmenterMefianceTous());
	    Event dansLeBureau= new DecisionNode("M.Dupont: Vous êtes finalement venu, je vous prie de vous assoir. Rappelez-moi dans quelle université vous venez",player);
	    Event Massy= new DecisionNode("\"M.Dupont: Très bonne université \n Vous apercevez un document signé M.Kessler sur le bureau, vous souhaitez vous en emparer",player);
	    Event Mazy= new DecisionNode("Je n'ai jamais entendu parler de cette université. \n Au même moment, vous appercevez un document"
	    		+ " signé M.Kessler sur le bureau, vous souhaitez vous en emparer",player, ()->augmenterMefianceTous());
	    Event Marta= new DecisionNode("Je n'ai jamais entendu parler de cette université. \n Au même moment, vous appercevez un document"
	    		+ " signé M.Kessler sur le bureau, vous souhaitez vous en emparer",player, ()->augmenterMefianceTous());
	  
	    Event DemandeEau= new DecisionNode("Vous: Je souhaiterais avoir un verre d'eau s'il vous plait \n M.Dupont: Bien evidemment, je vous cherche ça"
	    		+ "de suite",player);
	    Event DemandeMedaille= new DecisionNode("Vous: Elle est à vous la médaille fields? je n'en ai jamais vu une d'aussi près \n M.Dupont: Oui, ne bougez pas, je vous la ramène"
	    		+ "de suite",player);
	    Event DemanderTote= new DecisionNode("Vous: J'ai vu que plusieurs personnes avaient des tote-Bag de la fac, sav"
	    		+ "ez-vous où je pourrai m'en procurer un \n M.Dupont: Bien evidemment, je vous cherche ça"
	    		+ "de suite",player);
	    Event glisserDoc2= new DecisionNode("Vous réussissez à glisser le document dans votre sac \n Le prof"
	    		+ " commence à vous parler de vos cours, vous l'écoutez attentivement. Il termine en vous demandant si vous avez des questions", player, ()->player.addInformationDecouverte("Document signé Kessler"));
	    Event demanderEDTB= new DecisionNode("Vous: J'aimerais avoir accès aux EDT de M.Kessler, j'aurais des questions à lui poser",player,()->{augmenterMefianceTous(); if(!(player.getInformationDecouverte().contains("EDT"))) {player.addInformationDecouverte("EDT");}});
	    Event EDTB= new DecisionNode("M.Dupont: Vous avez un stylo sur vous? Je vous lis ça",player);
	    Event leRemercier= new DecisionNode("Vous: Je vous remercie, tout était clair. A bientôt",player);
	    
	    
        Event ecouterAuxPortes= new DecisionNode("Vous rapprochez votre tête de la porte pour écouter, vous n'entendez rien. Il doit etre seul", player);
	    	ecouterAuxPortes = new SoundNode(ecouterAuxPortes, new String[]{"bureau_silence.wav"}, new float[]{1.0f}, new boolean[]{true});
	    
	    Event continuerEcouterAuxPortes1 = new DecisionNode("Toujours pas bruit.", player);
	    	continuerEcouterAuxPortes1 = new SoundNode(continuerEcouterAuxPortes1, new String[]{"bureau_silence.wav","bureau_sonnerie_telephone.wav"},new float[]{1f, 0.7f}, new boolean[] {true,true});
	    
	    
	    Event continuerEcouterAuxPortes2 = new DecisionNode("Vous entendez une sonnerie de telephone.", player);
	    	continuerEcouterAuxPortes2 = new SoundNode(continuerEcouterAuxPortes2, new String[]{"bureau_sonnerie_telephone.wav"}, new float[]{1f}, new boolean[]{true});
		
	    
	    
	    Event continuerEcouterAuxPortes3 = new DecisionNode("Vous l'entendez parler avec quelqu'un au telephone. Il hausse vite le ton et a l'air de se disputer. \nVous n'arrivez pas a tout entendre :\nM. Dupont :Comment .. .... .. faire ca ?! .. ..... ........ .... partager ... recherches mais .......... ... .. n'as .... .. besoin de moi .. .. ......\n\tCa fait longtemps que vous etes en train d'ecouter aux portes, les gens commencent a se poser des questions, vous devez bouger. \n", player, 
	    		() -> augmenterMefianceTous()
	    		);
		Event continuerEcouterAuxPortes4 = new DecisionNode("M.Kessler: Bon, ........ .. manger ensemble .. ....... .. .. .. ....., demain .. .. va ?\n", player, 
				() -> augmenterMefianceTous()
				);
		Event continuerEcouterAuxPortes5 = new DecisionNode("M.Kessler raccroche et vous n'entendez plus de bruits.\n", player, 
				() -> augmenterMefianceTous()
				);
		Event continuerEcouterAuxPortes6 = new DecisionNode("Pas de bruit\n", player, 
				() -> augmenterMefianceTous()
				);
		
        Event entrerBureau= new DecisionNode("NOUVELLE INFORMATION : un autre prof partage avec lui le bureau, il n'est donc pas tout seul\nIl vous demande comment il pourrait vous aider",player, 
        		() -> Function_EntrerBureau()
        		);
        	entrerBureau = new ImageNode(entrerBureau,"Cible_bureau.jpg");
		Event sortirBureau = new DecisionNode("vous etes maintenant sorti du bureau.",player);
        Event poserQuestionsSurLeCours= new DecisionNode("Il vous explique les points que vous lui avez demande.\nPendant ce temps, vous observez son bureau :\n"
        		+ " - une photo de lui avec une femme, \n"
        		+ " - une tasse \"meilleur papa du monde\", \n"
        		+ " - une mini maquette du systeme solaire, \n"
        		+ " - plusieurs dossiers poses dans un coin du bureau et pleins de feuilles de brouillons eparpillees partout.",player);
        Event DemanderEmploiDuTemps = new DecisionNode("M. Kessler : Vous pouvez juste m'envoyer un mail avant de passer et je vous repondrais si je suis disponible",player);
		Event parlerRecherche = new DecisionNode("M. Kessler :Ce sont des recherches confidentielles donc je ne peux pas vous en parler.",player);
        Event parlerMaquette = new DecisionNode("M. Kessler : Oh je suis un grand fan d'astronomie. L'immensite du cosmos, l'infini complexite de notre univers ... je trouve ça passionnant ! ", player, 
        		() -> {Function_ParlerMaquette(); player.addInformationDecouverte("M.Kessler est fan d'astronomie");}
        		);
        Event parlerTasse = new DecisionNode("M. Kessler : Oh, c'etait un cadeau de ma fille...\n\tJe crois que vous avez cours n'est ce pas? Je m'en voudrais si vous arriviez en retard.", player, 
        		() -> {Function_ParlerTasse(); player.addInformationDecouverte("Monsieur Kessler a une fille");}
        		);
        Event parlerPhoto = new DecisionNode("M. Kessler : C'est ma femme mais je ne suis pas tres a l'aise avec le fait d'en parler avec un de mes etudiants.\n\tJe crois que vous avez cours n'est ce pas? Je m'en voudrais si vous arriviez en retard.",player);
        Event aimeAstronomie = new DecisionNode("M. Kessler : Si vous etes interesse sachez que l'association d'astronomie cherche toujours des nouveaux membres. Je participe moi-meme a certains evenements.\nJoueur : je vais essayer de me renseigner merci du conseil.\nM. Dupont : Je serais heureux de vous compter parmi les membres de l'association, mais pour le moment je crois que vous avez cours, n'est ce pas ?",player);
        Event aimePasAstronomie = new DecisionNode("M.Kessler: C'est bien malheureux. Desole de vous avoir ennuyé avec mes histoires, je vous laisse aller en cours.",player);
	    
        
        ///////////////////////////////////// COURS ELENA///////////////////////////////////////////////////////////////
        
        Event assisterCoursElena= new DecisionNode("Vous vous installez dans un amphi rempli d'étudiant, le sujet du cours est"
        		+ " le piratage",player);
        Event suivantE= new DecisionNode("Au cours de la pause, une étudiante nommée Elena Cat s'interesse à avous et engage une "
        		+ " discussion avec vous",player);
        Event suivantE2= new DecisionNode("Elena: Dans quel pays vivais-tu avant de venir en Suisse",player);
        Event Allemagne= new ChanceNode("Ich wusste, dass Sie einen Übersetzer einsetzen würden",player, ()->augmenterMefianceTous());
        Event NewZeland= new ChanceNode("I mohio ahau ka whakamahi koe i te kaiwhakamaori",player);
        Event Norvege= new ChanceNode("Jeg visste at du skulle bruke en oversetter",player, ()->augmenterMefianceTous());
	    
        Event rep1Allemagne= new DecisionNode("Elena vous parle de tout et n'importe quoi. Elle commence par évoquer"
        		+ " une association intitulée LES OMBRES DU NUMERIQUE en précisant qu'ils organisent un bal masqué le lendemain ",player);
        Event rep2Allemagne= new DecisionNode("Elena vous parle de tout et n'importe quoi. Elle commence par évoquer"
        		+ " une association intitulée LES OMBRES DU NUMERIQUE en précisant qu'ils organisent un bal masqué le lendemain ",player,()-> augmenterMefianceTous());
        Event rep3Allemagne= new DecisionNode("Elena vous parle de tout et n'importe quoi. Elle commence par évoquer"
        		+ " une association intitulée LES OMBRES DU NUMERIQUE en précisant qu'ils organisent un bal masqué le lendemain ",player);
        
        Event rep1NewZeland= new DecisionNode("Elena vous parle de tout et n'importe quoi. Elle commence par évoquer"
        		+ " une association intitulée LES OMBRES DU NUMERIQUE en précisant qu'ils organisent un bal masqué le lendemain ",player);
        Event rep2NewZeland= new DecisionNode("Elena vous parle de tout et n'importe quoi. Elle commence par évoquer"
        		+ " une association intitulée LES OMBRES DU NUMERIQUE en précisant qu'ils organisent un bal masqué le lendemain ",player);
        Event rep3NewZeland= new DecisionNode("Elena vous parle de tout et n'importe quoi. Elle commence par évoquer"
        		+ " une association intitulée LES OMBRES DU NUMERIQUE en précisant qu'ils organisent un bal masqué le lendemain ",player,()-> augmenterMefianceTous());
        
        Event rep1Norvege= new DecisionNode("Elena vous parle de tout et n'importe quoi. Elle commence par évoquer"
        		+ " une association intitulée LES OMBRES DU NUMERIQUE en précisant qu'ils organisent un bal masqué le lendemain ",player, ()->augmenterMefianceTous());
        Event rep2Norvege= new DecisionNode("Elena vous parle de tout et n'importe quoi. Elle commence par évoquer"
        		+ " une association intitulée LES OMBRES DU NUMERIQUE en précisant qu'ils organisent un bal masqué le lendemain ",player);
        Event rep3Norvege= new DecisionNode("Elena vous parle de tout et n'importe quoi. Elle commence par évoquer"
        		+ " une association intitulée LES OMBRES DU NUMERIQUE en précisant qu'ils organisent un bal masqué le lendemain ",player);
        Event ombreNum= new DecisionNode("Elena: L'association organise un projet de piratage éthique, souhaitez-vous nous rejoindre",player);
        Event oui= new DecisionNode("Elena vous partage certaines ressources avec vous, le projet vous donne accès à une fiche contenant les codes secrets de certaines pièces"
        		+ " mais aussi aux caméras de surveillance",player);
        Event codeSecret= new DecisionNode("Vous avez obtenu une liste contenant des codes pourvant être très utiles pour la suite "
        		+ "de votre mission", player,()->player.addInformationDecouverte("liste de codes"));
        Event cameras= new DecisionNode("Vous apercevez une personne  avec M.Kessler dans son bureau"
        		+ ", Vous zoomez et reconnaissez la personne: Nikolai",player,()->player.addInformationDecouverte("Nikolai cache quelque chose"));
        /// a voirr
        Event suivreCoursElena = new DecisionNode("Vous suivez tout le cours ce qui fait baisser la mefiance de tous les personnages\nC'est la fin du cours",player, ()->diminuerMefianceTous());
        
        Event empoisonnerPomme = new DecisionNode("Vous injectez le poison sur la pomme et la reposer là où elle était.\nQue voulez vous fouiller désormais ?",player, ()->{player.removeGadget(Gadget.Poison);player.addInformationDecouverte("Pomme empoisonnée sur le bureau du professeur Kessler");});
        Event nePasEmpoisonnerPomme = new DecisionNode("Vous décidez de ne pas injecter le poin dans la pomme\nQue voulez vous fouiller désormais ?",player, ()->player.removeGadget(Gadget.Poison));
        
        
         
        
        
        
       
        Event itineraireLabo= new DecisionNode("En rentrant, Vous croisez le prof de maths Dupont parlant à Nikolai, vous vous approchez, ils parlent d'un laboratoire secret",player,()-> {
        	player.addInformationDecouverte("Nikolai et Dupont sont complices");
        	player.addInformationDecouverte("Existence d'un laboratoire secret");
        });
        Event micro= new DecisionNode("Vous ne pouvez plus utiliser le gadget micro. Vous avez à votre disposition un clip vocal détaillant le chemin pour accéder à un laboratoire secret",player,
        		()->{player.removeGadget(Gadget.Micro_espion); player.addInformationDecouverte("Chemin menant au laboratoire");});
        
        
        
        Event brouillerCamera = new DecisionNode("Vous remarquez la présence de caméra au alentour du bureau",player);
        
        Event utiliserBrouilleur = new DecisionNode("Vous utilisez votre brouilleur pour mettre hors service les caméras pendant un court laps de temps",player,()->{player.removeGadget(Gadget.Brouilleur);});
        Event nePasUtiliserBrouilleur = new DecisionNode("Vous essayez de cacher le plus possible votre visage, vous arrivez à entrer dans le bureau mais faites vite quelqu'un vous a peut-etre vu",player,()->{augmenterMefianceTous();});
        boolean[] bureauVisite = {false};
        
 
        
        Event fouillerOrdinateurKessler = new DecisionNode("L'ordinateur est évidemment bloqué par un mot de passe et se bloque complètement après 3essais",player);
        
        Event taper3essais = new ChanceNode("J'espère que vous aurez de la chance !",player);
        Event mdpNonTrouve = new TerminalNode("Après avoir essayé 3 mots de passe différents l'ordinateur s'est bloqué et a envoyé une alerte au professeur vous faisant ainsi repérer.\n\tMISSION ÉCHOUÉE ");
        
        Event utiliserCleUSB = new DecisionNode("Vous avez maintenant acces à son ordinateur en fouillant à l'intérieur vous trouvez certaines informations sur ces recherches mais ce n'est visiblement qu'une petite partie.\n"
        		+ "En continuant de fouiller et notamment en lisant ses mails, vous aprennez plusieurs choses : \n"
        		+ "- Il compte renvoyer son assistante car elle en sait trop sur le projet\n"
        		+ "- L'essentiel des recherches est gardé dans un labo secret\n"
        		+ "- Vous trouvez comment vous rendre au labo",player,()-> {
        			if (player.getGadget().contains(Gadget.Virus_informatique)) {
        				player.removeGadget(Gadget.Virus_informatique);
                	}
        			
        			player.addInformationDecouverte("Kessler compte renvoyer son assistante car elle en sait trop sur le projet");
        			player.addInformationDecouverte("Existence d'un laboratoire secret");
        			player.addInformationDecouverte("Chemin menant au laboratoire");
        		});
        
        Event fouillerBureauKessler = new DecisionNode("Après avoir crocheté la porte, vous avez désormais le champs libre pour explorer le bureau.",player,()->{
        	if (player.getGadget().contains(Gadget.Virus_informatique)) {
        		fouillerOrdinateurKessler.ajoutNodeList(utiliserCleUSB, "Utiliser le Gadget virus informatique");
        	}
        });
        
        Event proposerFouillerBureau = new DecisionNode("En jetant un oeil à l'emploi du temps du professeur Kessler vous vous rendez compte qu'il a actuellement un cours, c'est une occasion en or pour fouiller son bureau sans être dérangé",player,()->{
        	if (player.getGadget().contains(Gadget.Brouilleur)) {
        		brouillerCamera.ajoutNodeList(utiliserBrouilleur, "Utiliser votre brouilleur");
        	}
        	if (player.getGadget().contains(Gadget.Poison)) {
        		fouillerBureauKessler.setDescription(fouillerBureauKessler.getDescription()+"Dès votre arrivé, vous apercevez une pomme posée sur le bureau.");
        		fouillerBureauKessler.setNodeList(new ArrayList<>(), new ArrayList<>());
        		fouillerBureauKessler.ajoutNodeList(empoisonnerPomme, "Empoisonner la pomme");
        		fouillerBureauKessler.ajoutNodeList(nePasEmpoisonnerPomme, "ne pas empoisonner la pomme");
        	}
        });
        
        
       
        
        Event ContinuerfouillerBureauDeTravailKessler = new DecisionNode("Vous trouvez son carnet de santé et prenez connaissance d'une allergie au cacahuète qui peut lui être mortelle",player);
        Event placerMicro = new DecisionNode("Vous décidez de placer un micro dans le bureau",player, ()-> {player.removeGadget(Gadget.Micro_espion);});
        
        Event fouillerBureauDeTravailKessler = new DecisionNode("Vous commencez à fouiller le bureau, malgré la présence de dizaines de documents vous ne trouvez rien de très intéressant",player, ()->{
        	if (player.getGadget().contains(Gadget.Micro_espion)) {
        		ContinuerfouillerBureauDeTravailKessler.ajoutNodeList(placerMicro, "placer un micro-espion");
        	}
        });
        
       
        
        
        
        
        
        
        
        Event InfiltrerUniversite = new DecisionNode("Plus tard dans la soirée, vous décidez d'infiltrer l'université durant la nuit. Lorsque vous arrivez vous remarquez que la sécurité est plus élevée que durant la journée. Voici les possibilités dont vous disposez pour rentrer dans l'université",player);
        
        
        
        
        Event ouAller = new DecisionNode("Ou voulez vous aller ?",player);
        
        Event SalleDeBal = new DecisionNode("Vous vous rendez à la salle de bal, d'après les informations que vous avez entendu le professeur Kessler fera un discours pendant la soirée. \n"
        		+ "Heureusement pour vous et malheureusement pour lui un lustre se trouve juste au dessus de la scène où il fera son discours\n"
        		+ "Voulez vous placer votre gadget bombe sur le lustre pour le faire détonner lors du discours ?",player);
        
        
        Event NePasPlacerBombe = new DecisionNode("Vous sortez de la salle de bal.",player, ()->{
			ouAller.deleteNodeList(SalleDeBal, "Se rendre à la salle de bal");
		});
        
        Event capeSalleDeBalle = new DecisionNode("Vous utilisez votre cape d'invisibilité, la personne qui arrivait, un homme d'entretien, ne vous voit pas ce qui vous permet de vous eclipser discrètement.",player,()->{
        	player.removeGadget(Gadget.Cape_invisibilite);
        });
        
        Event dentSalleDeBalle = new DecisionNode("Vous utilisez votre blanchisseur dentaire, la personne qui arrivait, un homme d'entretien, est ébloui par votre sourire irresistible ce qui vous permet de vous eclipser discrètement.",player,()->{
        	player.removeGadget(Gadget.Blanchisseur_dentaire);
        });
        
        Event styloSalleDeBalle = new DecisionNode("Vous utilisez votre stylo_somnifère, la personne qui arrivait, un homme d'entretien, tombe dans les bras de Morphée ce qui vous permet de vous eclipser discrètement.",player,()->{
        	player.removeGadget(Gadget.Stylo_Somnifere);
        });
        
        
        Event quelqunArrive = new DecisionNode("Vous entendez des bruits de pas venir vers vous, vous avez manifestement fait suffisement de bruit pour attirer quelqu'un",player);
         
        Event PlacerBombe = new DecisionNode("Vous arrivez tant bien que mal à placer la charge explosive sur le lustre mais vous avez fait suffisement de bruit pour attirer quelqu'un, vous l'entendez arriver.",player,()->{
			ouAller.deleteNodeList(SalleDeBal, "Se rendre à la salle de bal");
			
			if ( player.getGadget().contains(Gadget.Cape_invisibilite) ) {
				quelqunArrive.ajoutNodeList(capeSalleDeBalle, "Utiliser gadget cape d'invisibilité");
			}
			if ( player.getGadget().contains(Gadget.Blanchisseur_dentaire) ) {
				quelqunArrive.ajoutNodeList(dentSalleDeBalle, "Utiliser gadget blanchisseur de dent d'invisibilité");
			}
			if ( player.getGadget().contains(Gadget.Stylo_Somnifere) ) {
				quelqunArrive.ajoutNodeList(styloSalleDeBalle, "Utiliser gadget stylo-somnifère d'invisibilité");
			}
		});
        
        Event SalleDeBal1 = new DecisionNode("Vous analysez la salle de bal et chercher des plans de fuite optimaux si jamais vous devez vous echapper durant le bal",player,()->player.addInformationDecouverte("plan de fuite depuis le bal"));
        Event continuerFouillerSalleDeBal = new DecisionNode("Vous ne trouvez rien d'autres dans cette salle c'est pourquoi vous sortez", player, ()->ouAller.deleteNodeList(SalleDeBal1, "Se rendre à la salle de bal"));
        
        Event seCacher = new ChanceNode("En entendant les bruits de pas vous allez vous cacher vous allez vous cacher quelque part dans la salle. Les bruits de pas appartiennent à un homme d'entretien, il inspectent rapidement la salle",player);
        
        Event trouve = new DecisionNode("L'homme d'entretien vous trouve et vous demande qui vous êtes\n"
        		+ "Vous dites appartenir aux membres de la sécurité et que vous faisiez une ronde. L'homme vous laisse partir mais n'est pas vraiment convaincu.\n la méfiance des personnages envers vous augmente", player, ()->{
        			augmenterMefianceTous();
        		});
        
        Event pasTrouve = new DecisionNode("Après sa rapide inspection il ne vous trouve pas et repart. Vous attendez un peu pour être sûr qu'il soit bien partie puis vous sortez de la salle vous aussi",player);
        
        
        Event ChercherLabo = new DecisionNode("Vous cherchez le laboratoire sans relache mais vous ne trouvez rien, vous comprenez le sens du mot SECRET dans laboratoire secret",player);
        
        //Event Laboratoire = new DecisionNode("Vous vous dirigez vers le laboratoire", player);
        
        
        
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        
        
        
        
        Event sortirLabo = new DecisionNode("Vous décidez de faire marche arriere de quitter le sous sol",player);
        Event ClePorte = new DecisionNode("Vous utilisé la carte d'acces que vous avez volé à Bouin pour déverouiller la porte\nVous êtes maintenant dans le labo, vous rassemblez toutes les documents papiers et copiez les documents informatiques avant de supprimer les originaux.",player, ()->{
        	player.addInformationDecouverte("Ensemble des recherches récupérer");
        });
         Event GadgetPorte = new DecisionNode("votre thermite fais fondre la porte\nVous êtes maintenant dans le labo, vous rassemblez toutes les documents papiers et copiez les documents informatiques avant de supprimer les originaux.",player, ()->{
        	player.addInformationDecouverte("Ensemble des recherches récupérer");
        });
        Event AriaPorte = new DecisionNode("Aria utilise une substance chimique qui fait fondre la porte \nVous êtes maintenant dans le labo, vous rassemblez toutes les documents papiers et copiez les documents informatiques avant de supprimer les originaux.",player, ()->{
        	player.addInformationDecouverte("Ensemble des recherches récupérer");
        });
        Event CodeLaser = new DecisionNode("Vous rentrer le code d'acces que vous avez obtenu plus tot \nVous etes maintenant devant une porte blindée il semblerait que pour l'ouvrir on besoin d'une carte d'acces",player,()->{
        	player.addInformationDecouverte("3eme obstacle du labo : porte blindé");
        });
        Event GadgetLaser = new DecisionNode("Vous utilisez votre gadget pour désactiver les lasers\nVous etes maintenant devant une porte blindée il semblerait que pour l'ouvrir on besoin d'une carte d'acces",player,()->{
        	player.addInformationDecouverte("3eme obstacle du labo : porte blindé");
        });
        Event ElenaLaser = new DecisionNode("Elena pirate le systeme de sécurité pour désactiver les lasers\nVous etes maintenant devant une porte blindée il semblerait que pour l'ouvrir on besoin d'une carte d'acces",player,()->{
        	player.addInformationDecouverte("3eme obstacle du labo : porte blindé");
        });
        Event gadgetGarde = new DecisionNode("Votre gadget vous permet de passer sans probleme les gardes\nVous arrivez dans un couloir blanc assez long et vide, "
        		+ "vous comprenez instinctivement qu'il y a des lasers dans tous le couloirs\nIl y a un boitier a coté de vous permettant de désactiver les lasers avec un code",player,()->{
                	player.addInformationDecouverte("2eme obstacle du labo : laser");

                	boolean b = false;
                	if (player.getAllie().contains(player.getFichePerso().get(3))){//TODO a changer
                		ElenaLaser.ajoutNodeList(AriaPorte, "Demander à Aria si elle peut aider avec la porte");
                		GadgetLaser.ajoutNodeList(AriaPorte, "Demander à Aria si elle peut aider avec la porte");
                		CodeLaser.ajoutNodeList(AriaPorte, "Demander à Aria si elle peut aider avec la porte");
                		b=true;
                	}
                	if (player.getGadget().contains(Gadget.Thermite)) {
                		ElenaLaser.ajoutNodeList(GadgetPorte, "Utiliser de la thermite pour faire fondre la porte");
                		GadgetLaser.ajoutNodeList(AriaPorte, "Utiliser de la thermite pour faire fondre la porte");
                		CodeLaser.ajoutNodeList(AriaPorte, "Utiliser de la thermite pour faire fondre la porte");
                		player.removeGadget(Gadget.Thermite);
                		b=true;
                	}
                	if (player.getInformationDecouverte().contains("Clé d'acces au laboratoire")) {
                		ElenaLaser.ajoutNodeList(ClePorte, "Utiliser la carte d'acces volé plus tot");
                		GadgetLaser.ajoutNodeList(ClePorte, "Utiliser la carte d'acces volé plus tot");
                		CodeLaser.ajoutNodeList(ClePorte, "Utiliser la carte d'acces volé plus tot");
                		b=true;
                	}
                	if (!b) {
                		ElenaLaser.ajoutNodeList(sortirLabo, "Partir et revenir quand aurez de quoi passez l'obstacle");
                		GadgetLaser.ajoutNodeList(sortirLabo, "Partir et revenir quand aurez de quoi passez l'obstacle");
                		CodeLaser.ajoutNodeList(sortirLabo, "Partir et revenir quand aurez de quoi passez l'obstacle");
                	}
                });
        Event nikolaiGarde = new DecisionNode("Vous envoyez Nikolaï distraire les gardes grace a ça vous pouvez avancer discrètement\nVous arrivez maintenant dans un couloir blanc assez long et vide, "
        		+ "vous comprenez instinctivement qu'il y a des lasers dans tous le couloirs\\nIl y a un boitier a coté de vous permettant de désactiver les lasers avec un code",player,()->{
        	player.addInformationDecouverte("2eme obstacle du labo : laser");
        	
        	boolean b = false;
        	if (player.getAllie().contains(player.getFichePerso().get(3))){//TODO a changer
        		ElenaLaser.ajoutNodeList(AriaPorte, "Demander à Aria si elle peut aider avec la porte");
        		GadgetLaser.ajoutNodeList(AriaPorte, "Demander à Aria si elle peut aider avec la porte");
        		CodeLaser.ajoutNodeList(AriaPorte, "Demander à Aria si elle peut aider avec la porte");
        		b=true;
        	}
        	if (player.getGadget().contains(Gadget.Thermite)) {
        		ElenaLaser.ajoutNodeList(GadgetPorte, "Utiliser de la thermite pour faire fondre la porte");
        		GadgetLaser.ajoutNodeList(AriaPorte, "Utiliser de la thermite pour faire fondre la porte");
        		CodeLaser.ajoutNodeList(AriaPorte, "Utiliser de la thermite pour faire fondre la porte");
        		player.removeGadget(Gadget.Thermite);
        		b=true;
        	}
        	if (player.getInformationDecouverte().contains("Clé d'acces au laboratoire")) {
        		ElenaLaser.ajoutNodeList(ClePorte, "Utiliser la carte d'acces volé plus tot");
        		GadgetLaser.ajoutNodeList(ClePorte, "Utiliser la carte d'acces volé plus tot");
        		CodeLaser.ajoutNodeList(ClePorte, "Utiliser la carte d'acces volé plus tot");
        		b=true;
        	}
        	if (!b) {
        		ElenaLaser.ajoutNodeList(sortirLabo, "Partir et revenir quand aurez de quoi passez l'obstacle");
        		GadgetLaser.ajoutNodeList(sortirLabo, "Partir et revenir quand aurez de quoi passez l'obstacle");
        		CodeLaser.ajoutNodeList(sortirLabo, "Partir et revenir quand aurez de quoi passez l'obstacle");
        	}
        	
        });
        
        Event gardeLabo = new DecisionNode("En arrivant devant le bureau vous apercevez des gardes.",player,()->{
        	player.addInformationDecouverte("1er obstacle du labo : garde");
        	boolean b = false;
        	if (player.getAllie().contains(player.getFichePerso().get(2))){//TODO a changer
        		nikolaiGarde.ajoutNodeList(ElenaLaser, "Demander à Elena de pirater le systeme de sécurité");
        		gadgetGarde.ajoutNodeList(ElenaLaser, "Demander à Elena de pirater le systeme de sécurité");
        		b=true;
        	}
        	if (player.getGadget().contains(Gadget.Brouilleur)) {
        		nikolaiGarde.ajoutNodeList(gadgetGarde, "Utiliser le brouilleur pour neutraliser le systeme de securite");
        		gadgetGarde.ajoutNodeList(ElenaLaser, "Demander à Elena de pirater le systeme de sécurité");
        		player.removeGadget(Gadget.Brouilleur);
        		b=true;
        	}
        	if (player.getInformationDecouverte().contains("liste de codes")) {
        		nikolaiGarde.ajoutNodeList(CodeLaser, "Utiliser les codes d'acces obtenus plus tot pour désactiver les lasers");
        		gadgetGarde.ajoutNodeList(ElenaLaser, "Demander à Elena de pirater le systeme de sécurité");
        		b=true;
        	}
        	if (!b) {
        		nikolaiGarde.ajoutNodeList(sortirLabo, "Partir et revenir quand aurez de quoi passez l'obstacle");
        		gadgetGarde.ajoutNodeList(sortirLabo, "Partir et revenir quand aurez de quoi passez l'obstacle");
        	}
        	
        });
        Event Laboratoire = new DecisionNode("Vous vous dirigez vers le laboratoire", player, ()->{
        	boolean b=false;
        	if (player.getAllie().contains(player.getFichePerso().get(1))){//TODO a changer
        		gardeLabo.ajoutNodeList(nikolaiGarde, "Demander à Nikolai de distraire les gardes");
        		b=true;
        	}
        	if (player.getGadget().contains(Gadget.Cape_invisibilite)) {
        		gardeLabo.ajoutNodeList(gadgetGarde, "Utiliser la cape invisible pour passer les gardes");
        		player.removeGadget(Gadget.Cape_invisibilite);
        		b=true;
        	}
        	if (player.getGadget().contains(Gadget.Deguisement)) {
        		gardeLabo.ajoutNodeList(gadgetGarde, "Se deguiser en Kesller pour duper les gardes");
        		player.removeGadget(Gadget.Deguisement);
        		b=true;
        	}
        	if (player.getGadget().contains(Gadget.Parfum_hypnotiseur)) {
        		gardeLabo.ajoutNodeList(gadgetGarde, "Utiliser le parfum hypnotisant pour manipuler les gardes");
        		player.removeGadget(Gadget.Parfum_hypnotiseur);
        		b=true;
        	}
        	if (player.getGadget().contains(Gadget.Blanchisseur_dentaire)) {
        		gardeLabo.ajoutNodeList(gadgetGarde, "Utiliser le blanchisseur de dents pour éblouir les gardes");
        		player.removeGadget(Gadget.Blanchisseur_dentaire);
        		b=true;
        	}
        	if (!b) {
        		gardeLabo.ajoutNodeList(sortirLabo, "Partir et revenir quand aurez de quoi passez l'obstacle");
        		
        	}
        	
        });
        
        Laboratoire.ajoutNodeList(gardeLabo, "Suite");
        
        
        
        
        
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        
        Event finDeJournee= new DecisionNode("Agent 47, ces dernières découvertes marquent la fin de votre première "
        		+ "journée d'infiltration. Vous pouvez vérifier l'onglet Information ainsi que Gadget pour faire le point"
        		+ " sur les gadgets qui vous restent et les informations récoltées",player);
        Event suivantFinJournee= new TerminalNode("Demain sera une journée chargée pour vous (TO BE CONTINUED...");
        Event rentrer1= new DecisionNode("Dring Dring... il est l'heure de se réveiller, Agent 47",player);
        
        Event SortirBureauKessler = new DecisionNode("Vous sortez du bureau et quitter l'université pour le moment",player);
        
        Event seDeguiser = new DecisionNode("Vous vous déguiser en votre cible le professeur Kessler puis vous vous diriger vers l'entrée gardée par les gardes\n"
        		+ "Garde : Oh, Monsieur Kessler ... C'est une surprise de vous voir je pensais que vous etiez au sous sol dans votre laboratoire\n"
        		+ "Agent 47 : euh...non j'étais parti un peu plus tot\n\n"
        		+ "Vous réussisez à passer la sécurité et entrer dans le batiment, une fois dans le batiment votre déguisement se désactive.", player, ()->{
        	player.removeGadget(Gadget.Deguisement);
        	player.addInformationDecouverte("Existence d'un laboratoire secret");
        	player.addInformationDecouverte("Chemin menant au laboratoire");
        	if ( player.getGadget().contains(Gadget.Bombe)) {
        		ouAller.ajoutNodeList(SalleDeBal, "Se rendre à la salle de bal");
        	} else {
        		ouAller.ajoutNodeList(SalleDeBal1, "Se rendre à la salle de bal");
        	}
        	if ( player.getInformationDecouverte().contains("Chemin menant au laboratoire")) {
        		ouAller.ajoutNodeList(Laboratoire, "Explorer le Laboratoire");
        		ouAller.deleteNodeList(ChercherLabo, "Chercher le laboratoire secrer");
        	}else if ( player.getInformationDecouverte().contains("Existence d'un laboratoire secret")){
        		ouAller.ajoutNodeList(ChercherLabo, "Chercher le laboratoire secrer");
        	}
        	if ( ! bureauVisite[0]) {
        		ouAller.ajoutNodeList(brouillerCamera, "Aller fouiller le bureau du professeur Kessler");
        		SortirBureauKessler.setNodeList(new ArrayList<>(), new ArrayList<>());
        		SortirBureauKessler.ajoutNodeList(ouAller, "Suite");
        		SortirBureauKessler.setDescription("Vous sortez du bureau");
        		brouillerCamera.setFunction(()->{ouAller.deleteNodeList(brouillerCamera, "Aller fouiller le bureau du professeur Kessler");;});
        	}
        });
        
        Event thermite = new DecisionNode("Vous utilisez la thermite que vous avez fabriqué pour faire fondre une fenetre et vous infiltrer en toute discretion.\n"
        		+ "Vous réussisez à passer la sécurité et entrer dans le batiment", player,()->{
                	player.removeGadget(Gadget.Thermite);
                	player.addInformationDecouverte("Existence d'un laboratoire secret");
                	if ( player.getGadget().contains(Gadget.Bombe)) {
                		ouAller.ajoutNodeList(SalleDeBal, "Se rendre à la salle de bal");
                	} else {
                		ouAller.ajoutNodeList(SalleDeBal1, "Se rendre à la salle de bal");
                	}
                	if ( player.getInformationDecouverte().contains("Chemin menant au laboratoire")) {
                		ouAller.ajoutNodeList(Laboratoire, "Explorer le Laboratoire");
                	}
                	if ( ! bureauVisite[0]) {
                		ouAller.ajoutNodeList(brouillerCamera, "Aller fouiller le bureau du professeur Kessler");
                		SortirBureauKessler.setNodeList(new ArrayList<>(), new ArrayList<>());
                		SortirBureauKessler.ajoutNodeList(ouAller, "Suite");
                		SortirBureauKessler.setDescription("Vous sortez du bureau");
                		brouillerCamera.setFunction(()->{ouAller.deleteNodeList(brouillerCamera, "Aller fouiller le bureau du professeur Kessler");;});
                	}
                } );
        
        Event blanchirDent = new DecisionNode("Vous utilisez votre blanchisseur dentaire. Vous allez face à face avec les gardes et faites le plus grand sourire possible, les gardes sont éblouis et désorientés par votre hygiène bucale irréprochable ce qui vous permet de vous infiltrer en toute DISCRETION",player,()->{
        	player.removeGadget(Gadget.Blanchisseur_dentaire);
        	player.addInformationDecouverte("Existence d'un laboratoire secret");
        	if ( player.getGadget().contains(Gadget.Bombe)) {
        		ouAller.ajoutNodeList(SalleDeBal, "Se rendre à la salle de bal");
        	} else {
        		ouAller.ajoutNodeList(SalleDeBal1, "Se rendre à la salle de bal");
        	}
        	if ( player.getInformationDecouverte().contains("Chemin menant au laboratoire")) {
        		ouAller.ajoutNodeList(Laboratoire, "Explorer le Laboratoire");
        	}
        	if ( ! bureauVisite[0]) {
        		ouAller.ajoutNodeList(brouillerCamera, "Aller fouiller le bureau du professeur Kessler");
        		SortirBureauKessler.setNodeList(new ArrayList<>(), new ArrayList<>());
        		SortirBureauKessler.ajoutNodeList(ouAller, "Suite");
        		SortirBureauKessler.setDescription("Vous sortez du bureau");
        		
        		brouillerCamera.setFunction(()->{ouAller.deleteNodeList(brouillerCamera, "Aller fouiller le bureau du professeur Kessler");;});
        	}
        });
        
        Event RentrerChezVous = new DecisionNode("Pour le moment vous rentrez chez vous pour mettre de l'idée dans vos découverte", player, ()->{
        	if (player.getGadget().contains(Gadget.Cape_invisibilite)){
        		InfiltrerUniversite.ajoutNodeList(seDeguiser, "Utiliser le gadget Deguisement");
        	}
        	if (player.getGadget().contains(Gadget.Thermite)){
        		InfiltrerUniversite.ajoutNodeList(thermite, "Utiliser le gadget Thermite");
        	}
        	if (player.getGadget().contains(Gadget.Blanchisseur_dentaire)){
        		InfiltrerUniversite.ajoutNodeList(blanchirDent, "Utiliser le gadget Blanchisseur dentaire");
        	}
        });
        
        Event QuoiFouiller = new DecisionNode("Que voulez vous fouiller désormais ?",player,()->{
        	bureauVisite[0] = true;
        	if (player.getGadget().contains(Gadget.Cape_invisibilite) || player.getGadget().contains(Gadget.Thermite) || player.getGadget().contains(Gadget.Blanchisseur_dentaire)) {
        		RentrerChezVous.deleteNodeList(finDeJournee, "Suivant") ;
        		RentrerChezVous.ajoutNodeList(InfiltrerUniversite, "Suivant");
        	}
        });
        
        Event LaisserTomberOrdinateur = new DecisionNode("Vous décidez d'arreter de fouiller l'ordinateur",player, ()->{QuoiFouiller.deleteNodeList(fouillerOrdinateurKessler, "Fouiller l'ordinateur");});
        Event ArreterFouillerBureauDeTravailKessler = new DecisionNode("Après une rapide inspection vous ne trouvez rien d'autre à fouiller",player, ()->{
        	QuoiFouiller.deleteNodeList(fouillerBureauDeTravailKessler, "Fouiller le bureau(le meuble pas la piece)");
        });
        
        
        Event partir= new DecisionNode("Il commence à faire nuit, votre première journée d'infiltration s'apprête à prendre fin",player, ()->{
        	if (player.getGadget().contains(Gadget.Cape_invisibilite) || player.getGadget().contains(Gadget.Thermite) || player.getGadget().contains(Gadget.Blanchisseur_dentaire)) {
        		RentrerChezVous.deleteNodeList(finDeJournee, "Suivant") ;
        		RentrerChezVous.ajoutNodeList(InfiltrerUniversite, "Suivant");
        	}
        });
        
        Event non= new DecisionNode("En refusant, vous laissez place à un situation malaisante entre vous et Elena",player, ()->{
        	if (player.getInformationDecouverte().contains("EDT")) {
        		partir.setNodeList(new ArrayList<>(), new ArrayList<>());
        		partir.ajoutNodeList(proposerFouillerBureau, "Suivant");
        	}
        });
        Event bal= new DecisionNode("Vous: A quelle heure se trouve le bal et en quelle salle? \n Elena: 20h en Amphi 8",player, ()->{
        	player.addInformationDecouverte("Bal à 20h Amphi 8");
        	if (player.getInformationDecouverte().contains("EDT")) {
        		partir.setNodeList(new ArrayList<>(), new ArrayList<>());
        		partir.ajoutNodeList(proposerFouillerBureau, "Suivant");
        	}
        	});
        
        Event FinDeTout= new TerminalNode("TO BE CONTINUED...");
        sortirLabo.ajoutNodeList(FinDeTout, "FIN");        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
       
        
       
        
        
        
        
        
        
        
        
        
        
        
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////2 eme journée////////////////////////////////////////////////////////
        
        
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////// CREATION DES SUCCESSEURS /////////////////////////////////////////////////////////////////////
        
        bienvenue.ajoutNodeList(explication, "Accepter la mission");

        explication.ajoutNodeList(debut, "Se rendre sur le lieu de la mission");
        
        debut.ajoutNodeList(RejoindreReunion, "Rejoindre la réunion");
        debut.ajoutNodeList(infiltrerSalleDeControle , "Profiter de l'attention détourner des gardes pour infiltrer la salle de contrôle");
        
        infiltrerSalleDeControle.ajoutNodeList(mentirSalleDeControle , "Prétendre être un nouveau membre de la sécurité");
        infiltrerSalleDeControle.ajoutNodeList(rencontreNikolai , "prétendre s'être trompé, s'excuser et partir");
        //TODO
        EndormirGarde.ajoutNodeList(fouillerSalleDeControle, "Fouiller la salle");
		EndormirGarde.ajoutNodeList(rencontreNikolai , "Quitter la salle");
        
		fouillerSalleDeControle.ajoutNodeList(QuelquunRentre , "quelqu'un rentre dans la salle");
		fouillerSalleDeControle.ajoutNodeList(fouillerSalleDeControle2 , "personne ne rentre dans la salle"); 
		
		fouillerSalleDeControle2.ajoutNodeList(SuivantExplorer3, "Suivant");
		
		QuelquunRentre.ajoutNodeList(netraliserCape, "Utiliser le Gadget: Cape d'invisibilité");
		QuelquunRentre.ajoutNodeList(courir,"prendre la fuite sans utiliser le gadget");

		mentirSalleDeControle.ajoutNodeList(FuirSalleDeControle , "Profiter du fait qu'il a son attention détournée pour quitter la pièce discrètement");
		mentirSalleDeControle.ajoutNodeList(EndormirGarde , "Utiliser le Gadget Stylo-somnifère sur le garde");
		FuirSalleDeControle.ajoutNodeList(rencontreNikolai, "suivant");
		
        RejoindreReunion.ajoutNodeList(glisserDoc, "Glisser le document dans votre poche");
        RejoindreReunion.ajoutNodeList(rencontreNikolai, "Sortir de la salle car c'est risqué");
        
        glisserDoc.ajoutNodeList(netraliserCape, "Uiliser le Gadget: Cape d'invisibilité");
        glisserDoc.ajoutNodeList(courir,"prendre la fuite");
        
        Event s= new DecisionNode("Vous rencontrez un étudiant nommé Nikolai Steele",player);
        netraliserCape.ajoutNodeList(s, "Suivant");// y avait suivantSecu au lieu de s
        courir.ajoutNodeList(s, "Suivant");
        s.ajoutNodeList(SuivantExplorer3, "Suivant");
        
        rencontreNikolai.ajoutNodeList(SuivantExplorer3, "Suivant");
        
        SuivantExplorer3.ajoutNodeList(rejoindreDiscussionProf, "rejoindre la discussion entre les professeurs");
        
        rejoindreDiscussionProf.ajoutNodeList(accepter, "Accepter la proposition");
        rejoindreDiscussionProf.ajoutNodeList(decliner, "Refuser la proposition");
        
        accepter.ajoutNodeList(coursNikolai, "Aller en cours avec Nikolai");
        decliner.ajoutNodeList(coursNikolai,"Aller en cours avec Nikolai");
        
        coursNikolai.ajoutNodeList(parlerAnas, "Parler à anas");
        coursNikolai.ajoutNodeList(parlerTom, "Parler à Tom");
        
        parlerAnas.ajoutNodeList(demanderEDT, "Demander l'acces aux EDT des profs");
        parlerAnas.ajoutNodeList(demanderCarte, "Demander une carte détaillée de l'université");
        parlerAnas.ajoutNodeList(demanderAnnales,"demander des Annales");
        
        parlerTom .ajoutNodeList(poserQstCours, "poser des questions sur le cours");
        parlerTom.ajoutNodeList(poserQstProf, "poser des questions sur la cible");
        
        demanderEDT.ajoutNodeList(sortirDuCours, "Quitter le cours");
        player.creerGadget(demanderEDT, player);
        demanderEDT.ajoutNodeList(suivreCours1, "Suivre le cours");
        
        demanderCarte.ajoutNodeList(sortirDuCours, "Quitter le cours");
        player.creerGadget(demanderCarte, player);
        demanderCarte.ajoutNodeList(suivreCours1, "Suivre le cours");
        
        demanderAnnales.ajoutNodeList(sortirDuCours, "Quitter le cours");
        player.creerGadget(demanderAnnales, player);
        demanderAnnales.ajoutNodeList(suivreCours1, "Suivre le cours");
        
        poserQstCours.ajoutNodeList(sortirDuCours, "Quitter le cours");
        player.creerGadget(poserQstCours, player);
        poserQstCours.ajoutNodeList(suivreCours1, "Suivre le cours");
        
        poserQstProf.ajoutNodeList(sortirDuCours, "Quitter le cours");
        player.creerGadget(poserQstProf, player);
        poserQstProf.ajoutNodeList(suivreCours1, "Suivre le cours");
        suivreCours1.ajoutNodeList(sortirDuCours,"Quitter le cours");//TODO suspicion -1
        
        
        allerAuBureauDuProf.ajoutNodeList(ecouterB, "Ecouter la discussion aux portes");
        allerAuBureauDuProf.ajoutNodeList(toquerPorte, "Toquer à la porte");
        
        ecouterB.ajoutNodeList(suivantB, "suivant");
        
        suivantB.ajoutNodeList(dansLeBureau, "entrer dans le bureau");
        
        toquerPorte.ajoutNodeList(dansLeBureau, "entrer dans le bureau");
        
        dansLeBureau.ajoutNodeList(Massy, "Massy");
        dansLeBureau.ajoutNodeList(Mazy, "Mazy");
        dansLeBureau.ajoutNodeList(Marta, "Marta");
        
        Massy.ajoutNodeList(DemandeEau, "Demander un verre d'eau");
        Massy.ajoutNodeList(DemandeMedaille, "Demander de montrer une médaille fields");
        Massy.ajoutNodeList(DemanderTote,"Demander un tote_bag de bienvenue");
        
        Mazy.ajoutNodeList(DemandeEau, "Demander un verre d'eau");
        Mazy.ajoutNodeList(DemandeMedaille, "Demander de voir de plus près une médaille fields");
        Mazy.ajoutNodeList(DemanderTote,"Demander un tote_bag de bienvenue");
        
        Marta.ajoutNodeList(DemandeEau, "Demander un verre d'eau");
        Marta.ajoutNodeList(DemandeMedaille, "Demander de voir de plus près une médaille fields");
        Marta.ajoutNodeList(DemanderTote,"Demander un tote_bag de bienvenue");
        
        DemandeEau.ajoutNodeList(glisserDoc2, "glisser le document dans votre poche");
        DemandeMedaille.ajoutNodeList(glisserDoc2, "glisser le document dans votre poche ");
        DemanderTote.ajoutNodeList(glisserDoc2,"glisser le document dans votre poche");
        demanderEDTB.ajoutNodeList(EDTB, "Suivant");
        glisserDoc2.ajoutNodeList(demanderEDTB,"Demander l'EDT de M.Kessler");
        glisserDoc2.ajoutNodeList(leRemercier, "Le remercier et quitter le bureau");
        
        allerBureauKessler.ajoutNodeList(entrerBureau, "Toquer a la porte et entrer");
        allerBureauKessler.ajoutNodeList(ecouterAuxPortes, "Ecouter aux portes");
        
        ecouterAuxPortes.ajoutNodeList(entrerBureau,"Toquer a la porte et entrer");
        ecouterAuxPortes.ajoutNodeList(continuerEcouterAuxPortes1, "Rester dehors et continuer d'ecouter");
        
        continuerEcouterAuxPortes1.ajoutNodeList(entrerBureau,"Toquer a la porte et entrer");
        continuerEcouterAuxPortes1.ajoutNodeList(continuerEcouterAuxPortes2, "Rester dehors et continuer d'ecouter");
        
        continuerEcouterAuxPortes2.ajoutNodeList(entrerBureau,"Toquer a la porte et entrer");
        continuerEcouterAuxPortes2.ajoutNodeList(continuerEcouterAuxPortes3, "Rester dehors et continuer d'ecouter");
        
        continuerEcouterAuxPortes3.ajoutNodeList(entrerBureau,"Toquer a la porte et entrer");
        continuerEcouterAuxPortes3.ajoutNodeList(continuerEcouterAuxPortes4, "Rester dehors et continuer d'ecouter");
        
        continuerEcouterAuxPortes4.ajoutNodeList(entrerBureau,"Toquer a la porte et entrer");
        continuerEcouterAuxPortes4.ajoutNodeList(continuerEcouterAuxPortes5, "Rester dehors et continuer d'ecouter");
        
        continuerEcouterAuxPortes5.ajoutNodeList(entrerBureau,"Toquer a la porte et entrer");
        continuerEcouterAuxPortes5.ajoutNodeList(continuerEcouterAuxPortes6, "Rester dehors et continuer d'ecouter");
        
        continuerEcouterAuxPortes6.ajoutNodeList(entrerBureau,"Toquer a la porte et entrer");
        continuerEcouterAuxPortes6.ajoutNodeList(continuerEcouterAuxPortes6, "Rester dehors et continuer d'ecouter");
        
        
       
        entrerBureau.ajoutNodeList(sortirBureau, "Dire que l'on s'est trompe de bureau, s'excuser et partir");
        entrerBureau.ajoutNodeList(poserQuestionsSurLeCours,"Demander des precisions sur le cours qu'il donne");
       
        poserQuestionsSurLeCours.ajoutNodeList(parlerPhoto, "lui parler de la photo");
        poserQuestionsSurLeCours.ajoutNodeList(parlerTasse, "lui parler de la tasse");
        poserQuestionsSurLeCours.ajoutNodeList(DemanderEmploiDuTemps, "demander son EDT");
        poserQuestionsSurLeCours.ajoutNodeList(parlerRecherche, "lui parler de ses recherches");
        poserQuestionsSurLeCours.ajoutNodeList(sortirBureau, "le remercier et sortir du bureau");
        
        DemanderEmploiDuTemps.ajoutNodeList(sortirBureau, "le remercier et sortir du bureau");
        
        parlerPhoto.ajoutNodeList(sortirBureau, "s'excuser et partir");
        
        parlerTasse.ajoutNodeList(sortirBureau, "s'excuser et partir");
        
        parlerRecherche.ajoutNodeList(sortirBureau, "s'excuser et partir");
        
        parlerMaquette.ajoutNodeList(aimeAstronomie, "lui dire que vous etes un fan d'astronomie aussi");
        parlerMaquette.ajoutNodeList(aimePasAstronomie, "lui dire que vous n'êtes pas fan d'astronomie");
        
        aimeAstronomie.ajoutNodeList(sortirBureau, "Suivant");
        aimePasAstronomie.ajoutNodeList(sortirBureau, "Suivant");
          
        /// rencontre avec Elena
        sortirBureau.ajoutNodeList(assisterCoursElena,"Assister à un cours magistral" );
        leRemercier.ajoutNodeList(assisterCoursElena, "Assister à un cours magistral");
        EDTB.ajoutNodeList(assisterCoursElena, "Assister à un cours magistral");
        
        assisterCoursElena.ajoutNodeList(suivantE, "Suivant");
        suivantE.ajoutNodeList(suivantE2, "Suivant");
        
        suivantE2.ajoutNodeList(Allemagne, "Allemagne");
        suivantE2.ajoutNodeList(NewZeland, "Nouvelle-Zélande");
        suivantE2.ajoutNodeList(Norvege, "Norvège");
        
        Allemagne.ajoutNodeList(rep1Allemagne, "Sonne");
        Allemagne.ajoutNodeList(rep2Allemagne, "Regen");
        Allemagne.ajoutNodeList(rep3Allemagne, "Feuer");
        
        NewZeland.ajoutNodeList(rep1NewZeland, "Ra");
        NewZeland.ajoutNodeList(rep2NewZeland, "Ua");
        NewZeland.ajoutNodeList(rep3NewZeland, "Ahi");
        
        Norvege.ajoutNodeList(rep1Norvege, "Sol");
        Norvege.ajoutNodeList(rep2Norvege, "Regn");
        Norvege.ajoutNodeList(rep3Norvege, "Brann");
        
        rep1Allemagne.ajoutNodeList(ombreNum, "Parler des ombres du numérique");
        rep1Allemagne.ajoutNodeList(bal, "Parler du bal");
        
        rep2Allemagne.ajoutNodeList(ombreNum, "Parler des ombres du numérique");
        rep2Allemagne.ajoutNodeList(bal, "Parler du bal");
         
        rep3Allemagne.ajoutNodeList(ombreNum, "Parler des ombres du numérique");
        rep3Allemagne.ajoutNodeList(bal, "Parler du bal");
        
        rep1NewZeland.ajoutNodeList(ombreNum, "Parler des ombres du numérique");
        rep1NewZeland.ajoutNodeList(bal, "Parler du bal");
        
        rep2NewZeland.ajoutNodeList(ombreNum, "Parler des ombres du numérique");
        rep2NewZeland.ajoutNodeList(bal, "Parler du bal");
        
        rep3NewZeland.ajoutNodeList(ombreNum, "Parler des ombres du numérique");
        rep3NewZeland.ajoutNodeList(bal, "Parler du bal");
        
        rep1Norvege.ajoutNodeList(ombreNum, "Parler des ombres du numérique");
        rep1Norvege.ajoutNodeList(bal, "Parler du bal");
        
        rep2Norvege.ajoutNodeList(ombreNum, "Parler des ombres du numérique");
        rep2Norvege.ajoutNodeList(bal, "Parler du bal");
        
        rep3Norvege.ajoutNodeList(ombreNum, "Parler des ombres du numérique");
        rep3Norvege.ajoutNodeList(bal, "Parler du bal");
        
        ombreNum.ajoutNodeList(oui, "accepter");
        ombreNum.ajoutNodeList(non, "decliner");
        
        oui.ajoutNodeList(codeSecret, "récuperer les codes");
        oui.ajoutNodeList(cameras, "regarder les caméras de surveillance");
        
        
        codeSecret.ajoutNodeList(partir, "Quitter le cours");
        player.creerGadget(codeSecret, player);
        codeSecret.ajoutNodeList(bal, "parler à Elena du Bal");
        codeSecret.ajoutNodeList(suivreCoursElena, "Suivre le cours");
        
        cameras.ajoutNodeList(partir, "Quitter le cours");
        player.creerGadget(cameras, player);
        cameras.ajoutNodeList(bal, "parler à Elena du Bal");
        cameras.ajoutNodeList(suivreCoursElena, "Suivre le cours");
        
        non.ajoutNodeList(partir, "Quitter le cours");
        player.creerGadget(non, player);
        non.ajoutNodeList(bal, "parler du Bal");
        non.ajoutNodeList(suivreCoursElena, "Suivre le cours");
        
        bal.ajoutNodeList(partir, "Quitter le cours");
        player.creerGadget(bal, player);
        bal.ajoutNodeList(suivreCoursElena, "Suivre le cours");
        
        suivreCoursElena.ajoutNodeList(partir, "Quitter le cours");
        
        proposerFouillerBureau.ajoutNodeList(brouillerCamera, "Aller fouiller le bureau du professeur Kessler");
        proposerFouillerBureau.ajoutNodeList(itineraireLabo, "Quitter l'université");
        
        brouillerCamera.ajoutNodeList(nePasUtiliserBrouilleur, "Essayer de cacher au mieux son visage en entrant");
        
        utiliserBrouilleur.ajoutNodeList(fouillerBureauKessler, "Suivant");
        nePasUtiliserBrouilleur.ajoutNodeList(fouillerBureauKessler, "Suivant");
        
        fouillerBureauKessler.ajoutNodeList(QuoiFouiller, "Suivant");
        
        empoisonnerPomme.ajoutNodeList(QuoiFouiller, "Suivant");
        nePasEmpoisonnerPomme.ajoutNodeList(QuoiFouiller, "Suivant");
        
        QuoiFouiller.ajoutNodeList(fouillerBureauDeTravailKessler, "Fouiller le bureau(le meuble pas la piece)");
        QuoiFouiller.ajoutNodeList(fouillerOrdinateurKessler, "Fouiller l'ordinateur");
        QuoiFouiller.ajoutNodeList(SortirBureauKessler, "Sortir");
        
        fouillerBureauDeTravailKessler.ajoutNodeList(ContinuerfouillerBureauDeTravailKessler, "Continuer à fouiller");
        fouillerBureauDeTravailKessler.ajoutNodeList(ArreterFouillerBureauDeTravailKessler, "Arreter de fouiller le bureau(toujours le meuble)");
        
        
        ContinuerfouillerBureauDeTravailKessler.ajoutNodeList(ArreterFouillerBureauDeTravailKessler, "Arreter de fouiller le bureau(toujours le meuble)");
        
        placerMicro.ajoutNodeList(ArreterFouillerBureauDeTravailKessler, "Arreter de fouiller le bureau(toujours le meuble)");
        
        ArreterFouillerBureauDeTravailKessler.ajoutNodeList(QuoiFouiller, "Suivant");
        
        fouillerOrdinateurKessler.ajoutNodeList(taper3essais, "utiliser les 3 essais en testant des mots de passe");
        fouillerOrdinateurKessler.ajoutNodeList(LaisserTomberOrdinateur, "Arreter de fouiller l'ordinateur");
        
        taper3essais.ajoutNodeList(utiliserCleUSB, "Vous trouvez le mot de passe");
        taper3essais.ajoutNodeList(mdpNonTrouve, "Vous ne trouvez pas le mot de passe");
        
        utiliserCleUSB.ajoutNodeList(LaisserTomberOrdinateur, "Arreter de fouiller l'ordinateur");
        
        LaisserTomberOrdinateur.ajoutNodeList(QuoiFouiller, "Suivant");
        
        SortirBureauKessler.ajoutNodeList(RentrerChezVous, "Suivant");
        
        
        
        
        partir.ajoutNodeList(itineraireLabo, "Quitter l'université pour le moment");
        
        itineraireLabo.ajoutNodeList(micro,"enregistrer la discussion à l'aide d'un micro");
        itineraireLabo.ajoutNodeList(RentrerChezVous, "s'en aller");
        
        micro.ajoutNodeList(RentrerChezVous, "rentrer chez vous pour le moment");
  
        RentrerChezVous.ajoutNodeList(finDeJournee, "Suivant") ;
        
        //infiltrer l'ecole
        seDeguiser.ajoutNodeList(ouAller, "Suivant");
        thermite.ajoutNodeList(ouAller, "Suivant");
        blanchirDent.ajoutNodeList(ouAller, "Suivant");
        
        
        ouAller.ajoutNodeList(finDeJournee, "Quitter l'université");
        
        SalleDeBal1.ajoutNodeList(continuerFouillerSalleDeBal, "Continuer a fouiller la salle de bal");
        continuerFouillerSalleDeBal.ajoutNodeList(ouAller, "Suivant");
        
        SalleDeBal.ajoutNodeList(PlacerBombe, "Placer la bombe");
        SalleDeBal.ajoutNodeList(NePasPlacerBombe, "Ne pas placer la bombe et sortir de la salle");
        
        PlacerBombe.ajoutNodeList(quelqunArrive, "Suite");
        
        NePasPlacerBombe.ajoutNodeList(ouAller, "Suivant");
        quelqunArrive.ajoutNodeList(seCacher, "Se cacher");
        
        seCacher.ajoutNodeList(trouve, "L'homme d'entretien vous trouve");
        seCacher.ajoutNodeList(pasTrouve, "L'homme d'entretien ne vous trouve pas");
        
        trouve.ajoutNodeList(ouAller, "Suivant");
        pasTrouve.ajoutNodeList(ouAller, "Suivant");
        
        capeSalleDeBalle.ajoutNodeList(ouAller, "Suite");
        dentSalleDeBalle.ajoutNodeList(ouAller, "Suite");
        styloSalleDeBalle.ajoutNodeList(ouAller, "Suite");
        
        
        
        AriaPorte.ajoutNodeList(sortirLabo, "Sortir du labo");
        GadgetPorte.ajoutNodeList(sortirLabo, "Sortir du labo");
        ClePorte.ajoutNodeList(sortirLabo, "Sortir du labo");
        
        
        finDeJournee.ajoutNodeList(suivantFinJournee,"Suivant");
        suivantFinJournee.ajoutNodeList(rentrer1, "rentrer chez vous");
        
        
  //////////////////////// EXECUTIONNN ////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        this.player = player;
        this.racine = bienvenue;
	}
	
	
	public void changerMusiqueAmbiance(String name) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (this.musiqueAmbiance != null) {
        	musiqueAmbiance.stop();
        	musiqueAmbiance.close();
        }
        musiqueAmbiance = new SerializableClip(name);
        lancerMusiqueAmbiance();
	}
	
	public void lancerMusiqueAmbiance() {
        if (this.musiqueAmbiance != null) {
        	musiqueAmbiance.stop();
        	//musiqueAmbiance.close();
        }
        FloatControl gainControl = (FloatControl) musiqueAmbiance.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(gainControl.getMinimum() + volumeGlobal * 0.6f * (gainControl.getMaximum() - gainControl.getMinimum()));
        musiqueAmbiance.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void arreterMusiqueAmbiance() {
		if (this.musiqueAmbiance != null) {
        	musiqueAmbiance.stop();
        	//musiqueAmbiance.close();
        }
	}
	
	public void setVolumeGlobal(float v) {
		volumeGlobal = v;
    	FloatControl gainControl = (FloatControl) musiqueAmbiance.getControl(FloatControl.Type.MASTER_GAIN);
    	gainControl.setValue(gainControl.getMinimum() + volumeGlobal * 0.6f * (gainControl.getMaximum() - gainControl.getMinimum()));
	}

	public float getVolumeGlobal() {
		return volumeGlobal;
	}	
	
}

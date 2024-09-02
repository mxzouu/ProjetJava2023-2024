package univers;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import representation.Event;

@SuppressWarnings("serial")
public abstract class Joueur implements Serializable {
	
	private String identite = "Agent 47";
	protected ArrayList<Gadget> gadget = new ArrayList<Gadget>();// les armes que le joueur a a sa disposition
	private ArrayList<PNJ> fichePerso= new ArrayList<PNJ>();// est censee contenir les personnages disponnibles dans notre monde
	private ArrayList<PNJ> allie = new ArrayList<PNJ>(); // les allies du joueur durant l aventure
	private ArrayList<String> informationDecouverte = new ArrayList<>();
	private int lvlSociabilite = 1;// va se developper en fonction de la filière du joueur etc...
	private int lvlCompetence=1;// on peut acquerir des competences en fonction de nos choix
	
	public String getIdentite() {
		return identite;
	}
	public void setIdentite(String identite) {
		this.identite = identite;
	}

	public ArrayList<Gadget> getGadget() {
		return gadget;
	}
	public void setGadget(ArrayList<Gadget> gadget) {
		this.gadget = gadget;
	}
	public void addGadget(Gadget g) {
		gadget.add(g);
	}
	public void removeGadget(Gadget g) {
			gadget.remove(g);
		
	}
	
	public ArrayList<PNJ> getFichePerso() {
		return fichePerso;
	}
	public void setFichePerso(ArrayList<PNJ> fichePerso) {
		this.fichePerso = fichePerso;
	}

	public ArrayList<PNJ> getAllie() {
		return this.allie;
	}
	public void setAllie(ArrayList<PNJ> allie) {
		this.allie = allie;
	}
	
	public ArrayList<String> getInformationDecouverte() {
		return informationDecouverte;
	}
	public void setInformationDecouverte(ArrayList<String> informationDecouverte) {
		this.informationDecouverte = informationDecouverte;
	}
	public void addInformationDecouverte(String s) {
		if ( ! informationDecouverte.contains(s)){
			informationDecouverte.add(s);
		}
	}
	
	public int getLvlSociabilite() {
		return lvlSociabilite;
	}
	public void setLvlSociabilite(int lvlSociabilite) {
		this.lvlSociabilite = lvlSociabilite;
	}

	public int getLvlCompetence() {
		return lvlCompetence;
	}
	public void setLvlCompetence(int lvlCompetence) {
		this.lvlCompetence = lvlCompetence;
	}
	
	public void initialiseAttribut() {
		gadget.add(Gadget.Cape_invisibilite);
		gadget.add(Gadget.Micro_espion);
		
		PNJ cible = new PNJ("Professeur Sekkler",
				Domaine.INFORMATIQUE,
				new ArrayList<Caractere>(Arrays.asList(Caractere.INCONNU)),
				new ArrayList<Hobbie>(Arrays.asList(Hobbie.INCONNU)),
				Association.AUCUNE,
				new ArrayList<PNJ>(),
				0, 0, 10,
				new LinkedHashMap<>(),
				new ArrayList<String>(),
				new ArrayList<String>(),
				new ArrayList<String>(),
				new ArrayList<String>(),
				0,10);
		Map<Tuple<Number, Number>, String> routineNikolai = new LinkedHashMap<>();
		routineNikolai.put(new Tuple<>(10,11), "Cours de Sociologie");
		routineNikolai.put(new Tuple<>(15,17), "Cours de Sociologie");
	PNJ Nikolai = new PNJ("Nikolai",
			Domaine.SOCIOLOGIE,
			new ArrayList<Caractere>(Arrays.asList(Caractere.MEFIANT, Caractere.CHARISMATIQUE)),
			new ArrayList<Hobbie>(Arrays.asList(Hobbie.SPORT)),
			Association.AUCUNE,
			new ArrayList<PNJ>(),
			1, 3, 3,
			routineNikolai,
			new ArrayList<String>(),
			new ArrayList<String>(),
			new ArrayList<String>(),
			new ArrayList<String>(),
			8,
			2);
		
		
		
        Map<Tuple<Number, Number>, String> routineElena = new LinkedHashMap<>();
        routineElena.put(new Tuple<>(8,12), "Cours de Java");
        routineElena.put(new Tuple<>(13.75,15.25), "Cours de Stat");
        routineElena.put(new Tuple<>(15.5,17), "Cours de cybersecurité");
		PNJ Elena = new PNJ( "Elena", 
				Domaine.INFORMATIQUE, 
				new ArrayList<Caractere>(Arrays.asList(Caractere.GENTIL, Caractere.NAIF)),
				new ArrayList<Hobbie>(),
				Association.GAMEPHINE,
				new ArrayList<PNJ>() , 
				5, 0, 2, 
				routineElena, 
				new ArrayList<String>(Arrays.asList("bracelet")),
				new ArrayList<String>(Arrays.asList("secret1","secret2")),
				new ArrayList<String>(Arrays.asList("faiblesse1","faiblesse2")),
				new ArrayList<String>(Arrays.asList("info1","info2")),
				1,
				10);
		Map<Tuple<Number, Number>, String> routineAria = new LinkedHashMap<>();
		routineAria.put(new Tuple<>(8,12), "Cours de Chimie");
		routineAria.put(new Tuple<>(15.5,17), "Cours de Chimie");
		PNJ Aria = new PNJ( "Aria", 
				Domaine.MATHEMATIQUES, 
				new ArrayList<Caractere>(Arrays.asList(Caractere.STRESSE, Caractere.SOCIABLE)),
				new ArrayList<Hobbie>(Arrays.asList(Hobbie.LECTURE)),
				Association.AUCUNE, 
				new ArrayList<PNJ>() , 
				4, 0, 0, 
				routineAria,
				new ArrayList<String>(Arrays.asList("collier","montre")),
				new ArrayList<String>(Arrays.asList("secret1","secret2")),
				new ArrayList<String>(Arrays.asList("faiblesse1","faiblesse2","faiblesse3","faiblesse4")),
				new ArrayList<String>(Arrays.asList("info1","info2","info3")),
				1,
				10);
		
	
		ArrayList<PNJ> amisT= new ArrayList<>();
		amisT.add(Aria);
		Elena.ajouterAmis(amisT);
		fichePerso.add(cible);
		fichePerso.add(Nikolai);
		fichePerso.add(Elena);
		fichePerso.add(Aria);
		
	}
	
	public Joueur() {
		initialiseAttribut();
	}
	
	public Joueur(String i) {
		if ((i!="")&&(i!=null)) {
			identite = i;
		}
		
		initialiseAttribut();
	}

	public void inventaire(Scanner scanner) {
	   
	    System.out.println(" -> Pour afficher la liste des armes tapez \"arme\"\n"
	            + " -> Pour afficher la liste des personnages tapez \"personnage\"\n"
	            + " -> Pour afficher les informations d'un personnage en particulier tapez son nom");
	    String s = scanner.nextLine(); 
	    inventaire(s,scanner);
	    
	}
	
	public void inventaire(String s, Scanner scanner) {
		ArrayList<String> nomPNJ= new ArrayList<>();
    	for (PNJ p : fichePerso) {
    		nomPNJ.add(p.getIdentite());
    	}
		if (s.equals("arme")) { 
	        System.out.println("\n\u001B[4mListe des gadget :\u001B[0m " + gadget); 
	    } else if (s.equals("personnage")) { 
	        System.out.println("\n\u001B[4mListe des personnages :\u001B[0m " + nomPNJ);
	        
	       
		    System.out.println("\nTapez le nom du personnage dont vous voulez les informations : ");
		    String t = scanner.nextLine(); 
		    int i = nomPNJ.indexOf(t);
		    if (i != -1) {
	            System.out.println("\u001B[1mInformation sur " + t  + " :\u001B[0m\n" +fichePerso.get(i));
	        } else {
	            System.out.println("\n\u001B[31mERROR --- \"" + t + "\" ne fait pas parti des personnages\u001B[0m");
	        }
		    	
		    
	    } else if(nomPNJ.indexOf(s) != -1) {
	    	System.out.println("\u001B[1mInformation sur " + s  + " :\u001B[0m" +fichePerso.get(nomPNJ.indexOf(s)));
	    } else {
	        System.out.println("\n\u001B[31mERROR --- La chaine de caractere n'est pas valide\u001B[0m"
	        		+ "\n---"+s+"---");
	    }
	}

	
	public abstract void creerGadget(Event node, Joueur player);
	
	
}




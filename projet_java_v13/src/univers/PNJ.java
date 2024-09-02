/**
 * classe des personnages non-joueurs
 */
package univers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Iterator;

@SuppressWarnings("serial")
public class PNJ implements Serializable{

	private final int id;
	private static int nbPNJ = 0;
	private String identite;
	private Domaine domaine = Domaine.INCONNU;
	private ArrayList<Caractere> caractere = new ArrayList<Caractere>(Arrays.asList(Caractere.INCONNU));
	private ArrayList<Hobbie> hobbie =new ArrayList<Hobbie>(Arrays.asList(Hobbie.INCONNU)); 
	private Association association= Association.INCONNUE; 
	private ArrayList<PNJ> amis= new ArrayList<PNJ>();										// liste des amis du PNJ
	private int confiance=0;																// de 0 a 10
																							// Degre de confiance du PNJ envers le joueur
	private int soupcon=0;																	// de 0 a 10
																							// Degre de soupcon du PNJ envers le joueur
	private int affectionCible=0;															// de 0 a 10
																							// Degre d'affection du PNJ envers la cible
	private Map<Tuple<Number, Number>, String> routine = new LinkedHashMap<>();					// routine du personnage
	private ArrayList<String> preuve = new ArrayList<String>(); 							// Tous les objets que l'on pourra utiliser pour incriminer le PNJ
	private ArrayList<String>  secret = new ArrayList<String>();							// Tous les secret du PNJ que l'on pourra utiliser pour le faire chanter
	private ArrayList<String>  faiblesse = new ArrayList<String>();
	private ArrayList<String>  infoComplementaire = new ArrayList<String>();
	private int lvlFragilite = 0; 															// De 0 a 10
																							// permet de savoir si un PNJ est facilement brisable psychologiquement
																							// Plus la valeur est eleve plus il est simple de le briser
	private int santeMentale = 10;															// De 0 a 10
																							// Permet de connaitre l'etat mental actuel du PNJ
																							// Quelqu'un de completement stable mentalement est a 10
	private int vie = 3;																	// Nombre de "vie" du PNJ (i.e. nombre de preuve a fournir pour l'incriminer)

	
	
	
	public static int getNbPNJ() {
		return nbPNJ;
	}
	public static void setNbPNJ(int nbPNJ) {
		PNJ.nbPNJ = nbPNJ;
	}

	public String getIdentite() {
		return identite;
	}
	public void setIdentite(String identite) {
		this.identite = identite;
	}

	public Domaine getDomaine() {
		return domaine;
	}
	public void setDomaine(Domaine domaine) {
		this.domaine = domaine;
	}

	public ArrayList<Caractere> getCaractere() {
		return caractere;
	}
	public void setCaractere(ArrayList<Caractere> caractere) {
		this.caractere = caractere;
	}
	
	public ArrayList<Hobbie> getHobbie() {
		return hobbie;
	}
	public void setHobbie(ArrayList<Hobbie> hobbie) {
		this.hobbie = hobbie;
	}
	
	public Association getAssociation() {
		return association;
	}
	public void setAssociation(Association association) {
		this.association = association;
	}

	public ArrayList<PNJ> getAmis() {
		return amis;
	}
	public void setAmis(ArrayList<PNJ> amis) {
		this.amis = amis;
	}

	public int getConfiance() {
		return confiance;
	}
	public void setConfiance(int confiance) {
		this.confiance = confiance;
	}

	public int getSoupcon() {
		return soupcon;
	}
	public void setSoupcon(int soupcon) {
		this.soupcon = soupcon;
	}

	public int getAffectionCible() {
		return affectionCible;
	}
	public void setAffectionCible(int affectionCible) {
		this.affectionCible = affectionCible;
	}

	public Map<Tuple<Number, Number>, String> getRoutine() {
		return routine;
	}
	public void setRoutine(Map<Tuple<Number, Number>, String> routine) {
		this.routine = routine;
	}
	
	public ArrayList<String> getPreuve() {
		return preuve;
	}
	public void setPreuve(ArrayList<String> preuve) {
		this.preuve = preuve;
	}
	
	public ArrayList<String> getSecret() {
		return secret;
	}
	public void setSecret(ArrayList<String> secret) {
		this.secret = secret;
	}
	
	public ArrayList<String> getFaiblesse() {
		return faiblesse;
	}
	public void setFaiblesse(ArrayList<String> faiblesse) {
		this.faiblesse = faiblesse;
	}
	
	public ArrayList<String> getInfoComplementaire() {
		return infoComplementaire;
	}
	public void setInfoComplementaire(ArrayList<String> infoComplementaire) {
		this.infoComplementaire = infoComplementaire;
	}
	
	public int getLvlFragilite() {
		return lvlFragilite;
	}
	public void setLvlFragilite(int lvlFragilite) {
		this.lvlFragilite = lvlFragilite;
	}

	public int getSanteMentale() {
		return santeMentale;
	}
	public void setSanteMentale(int santeMentale) {
		this.santeMentale = santeMentale;
	}

	public int getVie() {
		return vie;
	}
	public void setVie(int vie) {
		this.vie = vie;
	}

	public PNJ() {
		identite="INCONNU";
		id = ++nbPNJ;
	}
	
	public PNJ(String i, Domaine d, ArrayList<Caractere> ca, ArrayList<Hobbie> h, Association as, ArrayList<PNJ> am, int c, int so, int a, Map<Tuple<Number, Number>, String> r, ArrayList<String> p, ArrayList<String> se, ArrayList<String> f, ArrayList<String> info, int lvlF, int sM) {
		if ( (c>10)||(c<0)||(so>10)||(so<0)||(a>10)||(a<0)||(lvlFragilite>10)||(lvlFragilite<0)||(santeMentale>10)||(santeMentale<0) ) {
			System.out.println("\u001B[41mERREUR --- Probleme avec les attributs confiance, soupcon ou affectionCible du PNJ "+i+"\u001B[0m\n\t\u001B[41mCes attributs doivent etre compris entre 0 et 10\u001B[0m");
			System.exit(1);
		}
		if (!(this.routineVerif(r))) {
			System.out.println("\u001B[41mERREUR --- Probleme avec la routine du PNJ "+i+"\u001B[0m\n\t\u001B[41mLe PNJ ne peut pas faire 2 activitÃ©s en meme temps\u001B[0m");
			System.exit(1);
		}
		
		id = ++nbPNJ;
		identite = i;
		domaine = d;
		caractere = ca;
		hobbie = h;
		association = as;
		amis = am;
		confiance = c;
		soupcon = so;
		affectionCible = a;
		routine =r;
		preuve = p;
		secret=se;
		faiblesse=f;
		infoComplementaire = info;
		lvlFragilite = lvlF;
		santeMentale = sM;
		
	}

	/**
	 * fonction qui verifie si une routine est dela bonne forme, ie le premier nombre est inferieur au deuxieme
	 * @param r: routine
	 * @return boolean
	 */
	public boolean routineVerif(Map<Tuple<Number, Number>, String> r) {
		for (Tuple<Number, Number> t : r.keySet()) {
			if (t.getVal2().doubleValue() < t.getVal1().doubleValue() ) {
				return false ;
			}
		}
		return true;
	}
	
	public String aUnAlibi(Number h) {
	    for (Tuple<Number, Number> t : routine.keySet()) {
	        if (t.getVal1().doubleValue() <= h.doubleValue() && t.getVal2().doubleValue() >= h.doubleValue()) {
	            return routine.get(t);
	        }
	    }
	    return null;
	}

	public void ajouterAmis(PNJ a) {
		amis.add(a);
		a.amis.add(this);
	}
	public void ajouterAmis(ArrayList<PNJ> a) {
		for ( PNJ p : a) {
			if (!amis.contains(p)) {
				ajouterAmis(p);
			}
		}
	}
	

		@Override
	public String toString() {			
		String c = "";
		if (caractere.size() == 0) {
			c = "INCONNU";
		}else {
			for (int i = 0; i < caractere.size(); i++) {
			    c = c + caractere.get(i);
			    if (i < caractere.size() - 1) {
			        c = c + ", ";
			    }
			}
		}
			
		String h = "";
		if (hobbie.size() == 0) {
			h="INCONNU";
		}else {
			for (int i = 0; i < hobbie.size(); i++) {
			    h = h + hobbie.get(i);
			    if (i < hobbie.size() - 1) {
			        h = h + ", ";
			    }
			}
		}
		
		String a = "";
		if (amis.size() == 0) {
			a = "INCONNU";
		}else {
			for (int i = 0; i < amis.size(); i++) {
			    a = a + amis.get(i).identite;
			    if (i < amis.size() - 1) {
			        a = a + ", ";
			    }
			}
		}
		
		
		String r = "";
		if (routine.isEmpty()) {
			r = "INCONNU";
		}else {
			Iterator<Map.Entry<Tuple<Number, Number>, String>> iterator = routine.entrySet().iterator();
			
			while (iterator.hasNext()) {
				Map.Entry<Tuple<Number, Number>, String> entry = iterator.next();
				int d = entry.getKey().getVal1().intValue();
				int f = entry.getKey().getVal2().intValue();
				int minuteD = (int) ((entry.getKey().getVal1().doubleValue() - d)*60);
				int minuteF = (int) ((entry.getKey().getVal2().doubleValue() - f)*60);
				String zeroD = (minuteD < 10) ? "0" : "" ;
				String zeroF = (minuteF < 10) ? "0" : "" ;
				r = r + "[ " + d + "h" + minuteD + zeroD
						+ " - " + f + "h" + minuteF + zeroF
						+ " ] " + entry.getValue();
			    if (iterator.hasNext()) {
			       r = r +"\n          ";
			    }
			}
		}
		
		String p = "";
		if (preuve.size() == 0) {
			p="INCONNU";
		}else {
			for (int i = 0; i < preuve.size(); i++) {
			    p = p + preuve.get(i);
			    if (i < preuve.size() - 1) {
			        p = p + ", ";
			    }
			}
		}
		
		String s = "";
		if (secret.size() == 0) {
			s = "INCONNU";
		}else {
			for (int i = 0; i < secret.size(); i++) {
			    s = s + secret.get(i);
			    if (i < secret.size() - 1) {
			        s = s + ", ";
			    }
			}
		}
		
		String f = "";
		if (faiblesse.size() == 0) {
			f = "INCONNU";
		}else {
			for (int i = 0; i < faiblesse.size(); i++) {
			    f = f + faiblesse.get(i);
			    if (i < faiblesse.size() - 1) {
			        f = f + ", ";
			    }
			}
		}

		String info = "";
		if (infoComplementaire.size() == 0) {
			info = "INCONNU";
		}else {
			for (int i = 0; i < infoComplementaire.size(); i++) {
				info = info + infoComplementaire.get(i);
			    if (i < infoComplementaire.size() - 1) {
			    	info = info + ", ";
			    }
			}
		}

		
		return "\n[ id = " + id + " ]\n"
				+ "\u001B[4mIdentite :\u001B[0m " + identite + "\n"
				+ "\u001B[4mDomaine :\u001B[0m " + domaine.toString() + "\n"
				+ "\u001B[4mCaractere :\u001B[0m " + c + "\n"
				+ "\u001B[4mHobbie(s) :\u001B[0m " + h + "\n"
				+ "\u001B[4mAssociation :\u001B[0m " + association + "\n"
				+ "\u001B[4mAmi(s) :\u001B[0m " + a + "\n"
				+ "\u001B[4mDegre de confiance envers vous :\u001B[0m " + confiance + "\n"
				+ "\u001B[4mDegre de soupcon envers vous :\u001B[0m " + soupcon + "\n"
				+ "\u001B[4mDegre d'affection envers la cible :\u001B[0m " + affectionCible + "\n"
				+ "\u001B[4mRoutine :\u001B[0m " + r + "\n"
				+ "\u001B[4mObjet pouvant etre utilise pour l'incriminer :\u001B[0m " + p + "\n"
				+ "\u001B[4mInformation pouvant etre utiliser pour le faire chanter :\u001B[0m " + s + "\n"
				+ "\u001B[4mFaiblesse/Allergie :\u001B[0m " + f + "\n"
				+ "\u001B[4mInformations complémentaires :\u001B[0m " + info + "\n"
				+ "\u001B[4mNiveau de fragilite :\u001B[0m " + lvlFragilite + "\n"
				+ "\u001B[4mNiveau de sante mentale :\u001B[0m " + santeMentale + "\n"
				+ "\u001B[4mNombre de vie :\u001B[0m " + vie + "\n";
					
	}	
	public String toString_Interface(int police) {			
		String c = "";
		if (caractere.size() == 0) {
			c = "INCONNU";
		}else {
			for (int i = 0; i < caractere.size(); i++) {
			    c = c + caractere.get(i);
			    if (i < caractere.size() - 1) {
			        c = c + ", ";
			    }
			}
		}
			
		String h = "";
		if (hobbie.size() == 0) {
			h="INCONNU";
		}else {
			for (int i = 0; i < hobbie.size(); i++) {
			    h = h + hobbie.get(i);
			    if (i < hobbie.size() - 1) {
			        h = h + ", ";
			    }
			}
		}
		
		String a = "";
		if (amis.size() == 0) {
			a = "INCONNU";
		}else {
			for (int i = 0; i < amis.size(); i++) {
			    a = a + amis.get(i).identite;
			    if (i < amis.size() - 1) {
			        a = a + ", ";
			    }
			}
		}
		
		
		String r = "";
		if (routine.isEmpty()) {
			r = "INCONNU";
		}else {
			Iterator<Map.Entry<Tuple<Number, Number>, String>> iterator = routine.entrySet().iterator();
			
			while (iterator.hasNext()) {
				Map.Entry<Tuple<Number, Number>, String> entry = iterator.next();
				int d = entry.getKey().getVal1().intValue();
				int f = entry.getKey().getVal2().intValue();
				int minuteD = (int) ((entry.getKey().getVal1().doubleValue() - d)*60);
				int minuteF = (int) ((entry.getKey().getVal2().doubleValue() - f)*60);
				String zeroD = (minuteD < 10) ? "0" : "" ;
				String zeroF = (minuteF < 10) ? "0" : "" ;
				r = r + "[ " + d + "h" + minuteD + zeroD
					+ " - " + f + "h" + minuteF + zeroF
					+ " ] " + entry.getValue();
			    if (iterator.hasNext()) {
			       r = r +"<br>";
			    }
			}
		}
		
		String p = "";
		if (preuve.size() == 0) {
			p="INCONNU";
		}else {
			for (int i = 0; i < preuve.size(); i++) {
			    p = p + preuve.get(i);
			    if (i < preuve.size() - 1) {
			        p = p + ", ";
			    }
			}
		}
		
		String s = "";
		if (secret.size() == 0) {
			s = "INCONNU";
		}else {
			for (int i = 0; i < secret.size(); i++) {
			    s = s + secret.get(i);
			    if (i < secret.size() - 1) {
			        s = s + ", ";
			    }
			}
		}
		
		String f = "";
		if (faiblesse.size() == 0) {
			f = "INCONNU";
		}else {
			for (int i = 0; i < faiblesse.size(); i++) {
			    f = f + faiblesse.get(i);
			    if (i < faiblesse.size() - 1) {
			        f = f + ", ";
			    }
			}
		}

		String info = "";
		if (infoComplementaire.size() == 0) {
			info = "INCONNU";
		}else {
			for (int i = 0; i < infoComplementaire.size(); i++) {
				info = info + infoComplementaire.get(i);
			    if (i < infoComplementaire.size() - 1) {
			    	info = info + ", ";
			    }
			}
		}

		
		return "<html><span style=\"font-family: 'Times New Roman'; font-size: "+police+"px;\">"
				+ "[ id = " + id + " ]<br>"
				+ "<b>Identite :</b> " + identite + "<br>"
				+ "<b>Domaine :</b> " + domaine.toString() + "<br>"
				+ "<b>Caractere :</b> " + c + "<br>"
				+ "<b>Hobbie(s) :</b> " + h + "<br>"
				+ "<b>Association :</b> " + association + "<br>"
				+ "<b>Ami(s) :</b> " + a + "<br>"
				+ "<b>Degre de confiance envers vous :</b> " + confiance + "<br>"
				+ "<b>Degre de soupcon envers vous :</b> " + soupcon + "<br>"
				+ "<b>Degre d'affection envers la cible :</b> " + affectionCible + "<br>"
				+ "<b>Routine :</b> " + r + "<br>"
				+ "<b>Objet pouvant etre utilise pour l'incriminer :</b> " + p + "<br>"
				+ "<b>Information pouvant etre utiliser pour le faire chanter :</b> " + s + "<br>"
				+ "<b>Faiblesse/Allergie :</b> " + f + "<br>"
				+ "<b>Informations complémentaires :</b> " + info + "<br>"
				+ "<b>Niveau de fragilite :</b> " + lvlFragilite + "<br>"
				+ "<b>Niveau de sante mentale :</b> " + santeMentale + "<br>"
				+ "<b>Nombre de vie :</b> " + vie 
				+ "</span></html>";
		
		}
		
		@Override
	public Object clone()  {
			return (new PNJ(new String(identite),
					Domaine.copy(domaine),
					new ArrayList<Caractere>(caractere),
					new ArrayList<Hobbie>(hobbie),
					Association.copy(association),
					new ArrayList<PNJ>(amis),
					confiance,soupcon,affectionCible,
					new LinkedHashMap<>(routine),
					new ArrayList<String>(preuve),
					new ArrayList<String>(secret),
					new ArrayList<String>(faiblesse),
					new ArrayList<String>(infoComplementaire),
					lvlFragilite,
					santeMentale));
	}	
		
		@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    PNJ otherPNJ = (PNJ) obj;
	    
	    return Objects.equals(identite, otherPNJ.identite) &&
	           Objects.equals(domaine, otherPNJ.domaine) &&
	           Objects.equals(caractere, otherPNJ.caractere) &&
	           Objects.equals(hobbie, otherPNJ.hobbie) &&
	           Objects.equals(association, otherPNJ.association) &&
	           Objects.equals(amis, otherPNJ.amis) &&
	           confiance == otherPNJ.confiance &&
	           soupcon == otherPNJ.soupcon &&
	           affectionCible == otherPNJ.affectionCible &&
	           Objects.equals(routine, otherPNJ.routine) &&
	           Objects.equals(preuve, otherPNJ.preuve) &&
	           Objects.equals(secret, otherPNJ.secret) &&
	           Objects.equals(faiblesse, otherPNJ.faiblesse) &&
	           Objects.equals(infoComplementaire, otherPNJ.infoComplementaire) &&
	           lvlFragilite == otherPNJ.lvlFragilite &&
	           santeMentale == otherPNJ.santeMentale &&
	           vie == otherPNJ.vie;
		}
		
}

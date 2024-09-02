package univers;

import java.io.Serializable;

public enum Caractere implements Serializable{
	INCONNU,
	LACHE,
	DEBROUILLARD, 
	SOCIABLE,
	STRESSE,
	INFLUENCABLE,
	BIZARRE,
	GENTIL,
	NAIF,
	MEFIANT,
	CHARISMATIQUE;

	
		@Override
	public String toString() {
        return this.name();
    }
	public static String toStringTab(Caractere[] cTab) {
		String caract = "";
		if (cTab.length == 0) {
			caract = "INCONNU";
		}else {
			for (int i = 0; i < cTab.length; i++) {
			    caract = caract + cTab[i];
			    if (i < cTab.length - 1) {
			        caract = caract + ", ";
			    }
			}
		}
		return caract;
	}
	
	public static Caractere copy(Caractere c) {
        return c; 
    }
	public static Caractere[] copy(Caractere[] cTab) {
		Caractere[] newC = new Caractere[cTab.length];
		int i =0;
		for (Caractere c : cTab) {
			newC[i++]=Caractere.copy(c);
		}
        return newC; 
    }

}

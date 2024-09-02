package univers;

import java.io.Serializable;

public enum Hobbie implements Serializable{
	INCONNU,
	LECTURE,
	SPORT,
	CUISINE,
	ASTRONOMIE,
	JEUX_VIDEO,
	CINEMA,
	COMICS;
	
		@Override
	public String toString() {
        return this.name(); 
    }
	public static String toStringTab(Hobbie[] hTab) {
		String h = "";
		if (hTab.length == 0) {
			h = "INCONNU";
		}else {
			for (int i = 0; i < hTab.length; i++) {
				h = h + hTab[i];
				if (i < hTab.length - 1) {
					h = h + ", ";
				}
			}
		}
		return h;
	}
	
	public static Hobbie copy(Hobbie h) {
        return h; 
    }
	public static Hobbie[] copy(Hobbie[] hTab) {
		Hobbie[] newH = new Hobbie[hTab.length];
		int i =0;
		for (Hobbie h : hTab) {
			newH[i++]=Hobbie.copy(h);
		}
        return newH; 
    }
	
}

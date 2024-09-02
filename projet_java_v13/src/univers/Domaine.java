package univers;

import java.io.Serializable;

public enum Domaine implements Serializable{
	INCONNU,
	INFORMATIQUE,
	MATHEMATIQUES,
	CHIMIE,
	SOCIOLOGIE,
	SPORT;

	
		@Override
	public String toString() {
        return this.name(); 
    }
	
	public static Domaine copy(Domaine d) {
        return d; 
    }
	
	
	
}

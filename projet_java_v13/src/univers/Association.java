package univers;

import java.io.Serializable;

public enum Association implements Serializable{
	INCONNUE,
	AUCUNE,
	GAMEPHINE,
	ASTROLOGIE,
	AMD,
	RP;
	
		@Override
	public String toString() {
        return this.name(); 
    }
	
	public static Association copy(Association a) {
        return a;
    }
	
}

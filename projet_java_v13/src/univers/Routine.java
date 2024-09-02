package univers;

public class Routine {
	private double heureDebut;
	private double heureFin;
	private String alibi;
	
	
	public double getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(double heureDebut) {
		this.heureDebut = heureDebut;
	}

	public double getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(double heureFin) {
		this.heureFin = heureFin;
	}

	public String getAlibi() {
		return alibi;
	}

	public void setAlibi(String alibi) {
		this.alibi = alibi;
	}

	public Routine(Number d, Number f, String a) {
        double debut = d.doubleValue();
        double fin = f.doubleValue();
        
        if (debut > fin) {
        	System.out.println("\u001B[41mERREUR --- Probleme avec l'alibi : " + a 
        			+ "\u001B[0m\n\t\u001B[41ml'alibi se finit avant d'avoir commencé\u001B[0m");
            System.exit(1);
        }
        
        heureDebut = debut;
        heureFin = fin;
        alibi = a;
    }
	
		@Override
	public String toString() {
		int d = (int) heureDebut;
		int f = (int) heureFin;
		int minuteD = (int) ((heureDebut - d)*60);
		int minuteF = (int) ((heureFin - f)*60);
		String zeroD = (minuteD < 10) ? "0" : "" ;
		String zeroF = (minuteF < 10) ? "0" : "" ;
			
		return "[ " + d + "h" + minuteD + zeroD
				+ " - " + f + "h" + minuteF + zeroF
				+ " ] " + alibi;
	}
	
		@Override
	public Object clone() {
		return new Routine(this.heureDebut, this.heureFin, this.alibi);
	}
	
	public static Routine[] copy(Routine[] rTab) {
		Routine[] newR = new Routine[rTab.length];
		int i = 0;
		for (Routine r : rTab) {
			newR[i++] = (Routine) r.clone();
		}
	       return newR; 
	}
		
}

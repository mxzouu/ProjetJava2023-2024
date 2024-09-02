/**
 * Classe des nodes pour lesquels le joueur n'a pas le choix de choisir un successeur à emprunter
 */
package representation;
import java.util.Scanner;

import univers.Joueur;

import java.util.Random;

@SuppressWarnings("serial")
public class ChanceNode extends InnerNode {
	
	public ChanceNode(String s, Joueur p, MAJNode f) {
		super(s, p, f);
	}
	
	public ChanceNode(String s, Joueur p, SerializableRunnable r) {
		super(s, p, r);
	}
	
	public ChanceNode(String s, Joueur p) {
		super(s, p);
	}
	/**
	 * fonction qui permet de choisir le successeur, se fait aléatoirement avec random
	 */
	@Override
	public Event chooseNext(Scanner s) {
		getFunction().execute();
		
		int n=this.getSuccNode().size();
		Random random= new Random();
		int r = random.nextInt(n);
		return getSuccNode().get(r);
		
	}

	@Override
	public String getNodeType() {
		return "ChanceNode";
	}

}

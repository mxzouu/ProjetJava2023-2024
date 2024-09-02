/**
 * Classe des nodes pour lesquels le joueur a le droit 
 */
package representation;

import java.util.ArrayList;
import java.util.Scanner;

import univers.*;

@SuppressWarnings("serial")
public class DecisionNode extends InnerNode implements Event {
	
	public DecisionNode(String s, Joueur p, MAJNode f) {
		super(s,p,f);
	}
	public DecisionNode(String s, Joueur p, SerializableRunnable r) {
		super(s,p,r);
	}
	public DecisionNode(String s, Joueur p) {
		super(s,p);
	}
	
	public Event chooseNext(Scanner scanner) {
		getFunction().execute();
		
		//System.out.println("\n"+this.getDescription());
		System.out.println("\n");
		this.display();
		
		
		if (getSuccNode().size() == 1 ) {
			return getSuccNode().get(0);
		}
		
		ArrayList<String> l = getTitreNode();
		for (int i=1; i<=l.size();i++) {
			System.out.println("Choix " +i+" : " + l.get(i-1));
		}
		
		
		System.out.print("choose a situation : ");
		String nextId= scanner.nextLine();
		if (nextId.equals("inventaire")){
			System.out.println("inventaire a ete tape");
			getPlayer().inventaire(scanner);
			return this.chooseNext(scanner);
		}else {
			int n = Integer.valueOf(nextId) - 1;
			if (n >= 0 && n < getSuccNode().size()) {
			    return getSuccNode().get(n);
			} else {
			    System.out.println("Please enter a valid number");
			    return this.chooseNext(scanner);
			}
		}
	}

	@Override
	public String getNodeType() {
		return "DecisionNode";
	}
}

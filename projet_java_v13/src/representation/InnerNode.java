/**
 * Classe mère des Nodes qui seront divisés en ChanceNode et DecisionNode
 */
package representation;

import java.util.ArrayList;

import univers.Joueur;

@SuppressWarnings("serial")
public abstract class InnerNode extends Node implements Event {
	
	private ArrayList<Event> succNode= new ArrayList<Event>();
	private ArrayList<String> titreNode = new ArrayList<String>(); 
	private Joueur player;
    private MAJNode function;
    
	public ArrayList<Event> getSuccNode() {
		return succNode;
	}
	public ArrayList<String> getTitreNode() {
		return titreNode;
	}

	public void setNodeList(ArrayList<Event> l1, ArrayList<String> l2) {
		succNode = l1;
		titreNode = l2;
	}
	public void ajoutNodeList(Event n, String t) {
		succNode.add(n);
		titreNode.add(t);
	}
	public void deleteNodeList(Event n, String t) {
		succNode.remove(n);
		titreNode.remove(t);
	}
	
	public Joueur getPlayer() {
		return player;
	}
	
	public MAJNode getFunction() {
		return function;
	}
	public void setFunction(MAJNode function) {
		this.function = function;
	}
	public void setFunction(SerializableRunnable r) {
		this.function = new MAJNode(r);
	}
	
	public InnerNode(String s, Joueur p, MAJNode f) {
		super(s);
		player = p;
		function = f;
	}
	public InnerNode(String s, Joueur p, SerializableRunnable r) {
		super(s);
		player = p;
		function = new MAJNode(r);
	}
	public InnerNode(String s, Joueur p) {
		super(s);
		player = p;
		function = new MAJNode();
	}
	
	
	
	
	
	public void displayNodes() {
	    for (Event n : succNode) {
	        n.display();
	    }
	}



}

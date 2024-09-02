/**
 * Classe d'un decorateur abstrait qui permettra de rajouter des fonctionnalités aux noeuds 
 */
package representation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;


@SuppressWarnings("serial")
public abstract class DecorateurA implements Event, Serializable {
	    // cest une classe qui aura comme attribut des nodes et qui les fera vivre 
	   // au lieu de creer une sous classe et lui faire faire des choses, on cree un decorateur et on y met un attribut du type de la classe mere 
	// les decorateurs concrets sont censes rajouter de l information aux decorateurs abstraits 
	protected Event node;
	
	public DecorateurA(Event e) {// dans e, on peut prendre nimporte quel objet d un type qui implemente l interface
		node=e;
	}
	public String getNodeType() {
		return node.getNodeType();
	}
	
	public String getImagePath() {
		return null;
	}
	// il faut redefinir les methodes de linterface

	public abstract void display();
	
    @Override
    public Event chooseNext(Scanner s) {
    	return node.chooseNext(s);
    }
    
    @Override
    public  void ajoutNodeList(Event n, String t) {
    	node.ajoutNodeList(n,t);
    }
    public void deleteNodeList(Event n, String t) {
    	node.deleteNodeList(n, t);
    }
	
    public String getDescription() {
		return node.getDescription();
	}
    public void setDescription(String description) {
    	node.setDescription(description);
	}
    public MAJNode getFunction() {
    	return node.getFunction();
    }
    public void setFunction(MAJNode function) {
    	node.setFunction(function);
    }
	public void setFunction(SerializableRunnable r) {
		node.setFunction(r);
	}
	

	public ArrayList<Event> getSuccNode(){
		return node.getSuccNode();
	}
	public ArrayList<String> getTitreNode(){
		return node.getTitreNode();
	}
	
	public void setNodeList(ArrayList<Event> l1, ArrayList<String> l2) {
		node.setNodeList(l1, l2);
	}
	
	public void lancerSon() {
		node.lancerSon();
    }
    
    public void arreterSon() {
    	node.arreterSon();
    }
    
    public void setVolumeGlobal(float v) {
    	node.setVolumeGlobal(v);
    }
}
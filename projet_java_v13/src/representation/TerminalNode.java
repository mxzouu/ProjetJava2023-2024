/**
 * Classe des nodes qui n'ont plus de successeurs dans le graphe
 */
package representation;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("serial")
public class TerminalNode extends Node{
	
	public TerminalNode (String s) {
		super(s);
	}
    
	@Override
    public Event chooseNext(Scanner s) {
		System.out.println("\n");
		this.display();
    	return this;
    }
	
	public void ajoutNodeList(Event n, String t) {
	}
	public void deleteNodeList(Event n, String t) {
	}

	public ArrayList<Event> getSuccNode(){
		return new ArrayList<>() ;
	}
	public ArrayList<String> getTitreNode(){
		return new ArrayList<>() ;
	}

	public void setNodeList(ArrayList<Event> l1, ArrayList<String> l2) {
		
	}
	
	
	
	
	@Override
	public String getNodeType() {
		// TODO Auto-generated method stub
		return "TerminalNode";
	}
	public MAJNode getFunction() {
		return new MAJNode();
	}
	public void setFunction(MAJNode function) {
		
	};
	public void setFunction(SerializableRunnable r) {
		
	};
}

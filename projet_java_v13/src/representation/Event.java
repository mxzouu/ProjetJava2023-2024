/**
 * Interface qui implémente les méthodes via lesquelles on rajoute des fonctionnalités aux nodes
 */
package representation;
import java.util.ArrayList;
import java.util.Scanner;


// interface des objects qu'on peut decorer 
public interface Event {
	
	public void display();
	public Event chooseNext(Scanner scanner);
	
	// Pour moi(Mendel) a enlevé 
	public abstract void ajoutNodeList(Event n, String t) ;
	public void deleteNodeList(Event n, String t) ;
	public String getDescription();
	public void setDescription(String description);
	public String getImagePath();
	public ArrayList<Event> getSuccNode();
	public ArrayList<String> getTitreNode();
	public void setNodeList(ArrayList<Event> l1, ArrayList<String> l2);
	public String getNodeType();
	public MAJNode getFunction();
	public void setFunction(MAJNode function);
	public void setFunction(SerializableRunnable r);
	
	public void lancerSon();
    public void arreterSon();
    public void setVolumeGlobal(float v);
}

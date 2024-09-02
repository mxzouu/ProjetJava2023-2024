package representation;
import java.io.Serializable;
import java.util.Scanner;

@SuppressWarnings("serial")
public abstract class Node implements Event, Serializable {
	private  String description;
	private final int ID;
	private static int CPT;

	public String getImagePath() {
		return null;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public static int getCPT() {
		return CPT;
	}
	
	public int getID() {
		return ID;
	}

	public Node(String s) {
		description=s;
		CPT++;
		ID=CPT;
	}
	
	public void display() {
		//System.out.println(ID + ": "+ description); 
		char[] descriptionChar = description.toCharArray();
		for (char c : descriptionChar) {
			System.out.print(c);
			try {
				Thread.sleep(15);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println();
	}
	
	public abstract Event chooseNext(Scanner scanner);
	//public abstract void ajoutNodeList(Node n, String t) ;
		
	public void lancerSon() {
		
    }
    
    public void arreterSon() {
    	
    }
    
    public void setVolumeGlobal(float v) {
    	
    }
	
}

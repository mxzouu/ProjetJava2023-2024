/**
 * sous classe du type du joueur Chimiste
 */
package univers;

import java.util.ArrayList;
import java.util.Arrays;

import representation.DecisionNode;
import representation.Event;

@SuppressWarnings("serial")
public class JoueurChimiste extends Joueur {

	public JoueurChimiste() {
		super();
		ArrayList<Gadget> g = getGadget();
		g.addAll(Arrays.asList(Gadget.Stylo_Somnifere));
		setGadget(g);
	}
	
	public JoueurChimiste(String nom) {
		super(nom);
		
		ArrayList<Gadget> g = getGadget();
		g.addAll(Arrays.asList(Gadget.Stylo_Somnifere));
		setGadget(g);
		
	}
	
	/**
	 * fonction qui permet de creer un nouveau gadget selon le type du joueur 
	 */
	public void creerGadget(Event node, Joueur player) {
		ArrayList<Event> l = new ArrayList<>();

		Event construireGadjet = new DecisionNode("Choisissez le gadjet que vous souhaitez construire",player);
		
		Event construireCape = new DecisionNode("NOUVEAU GADGET: Cape d'invisibilité (permet de devenir invisible pendant un cours laps de temps)\n"
				+ "Attention, les gadgets ont une utilisation unique \n"
				+ "C'est la fin du cours",
				player, ()->player.addGadget(Gadget.Cape_invisibilite));
		Event construireMicro = new DecisionNode("NOUVEAU GADGET: Micro espion (permet d'enregistrer et d'écouter des conversations)\n"
				+ "Attention, les gadgets ont une utilisation unique \n"
				+ "C'est la fin du cours",
				player, ()->player.addGadget(Gadget.Micro_espion));
		Event construireStylo = new DecisionNode("NOUVEAU GADGET: Stylo-somnifère (permet d'endormir une cible)\n"
	    		+ "Attention, les gadgets ont une utilisation unique \n"
	    		+ "C'est la fin du cours",
	    		player,()->player.addGadget(Gadget.Stylo_Somnifere));
	    Event construireThermite = new DecisionNode("NOUVEAU GADGET: Thermite (permet de faire fondre des fenetres ou des murs)\n"
	    		+ "Attention, les gadgets ont une utilisation unique \n"
	    		+ "C'est la fin du cours",
	    		player,()->player.addGadget(Gadget.Thermite));
	    Event construirePoison = new DecisionNode("NOUVEAU GADGET: Poison \n"
	    		+ "Attention, les gadgets ont une utilisation unique \n"
	    		+ "C'est la fin du cours",
	    		player, ()->player.addGadget(Gadget.Poison));
	    Event construireBombe = new DecisionNode("NOUVEAU GADGET: Bombe \n"
	    		+ "Attention, les gadgets ont une utilisation unique \n"
	    		+ "C'est la fin du cours",
	    		player,()->player.addGadget(Gadget.Bombe));
	    
	    l.add(construireCape);
	    l.add(construireMicro);
	    l.add(construireStylo);
	    l.add(construireThermite);
	    l.add(construirePoison);
	    l.add(construireBombe);
	    
	    construireGadjet.ajoutNodeList(construireCape, "Fabriquer une cape d'invisibilité");
	    construireGadjet.ajoutNodeList(construireMicro, "Fabriquer un micro espion");
	    construireGadjet.ajoutNodeList(construireStylo, "Fabriquer un stylo-somnifère");
	    construireGadjet.ajoutNodeList(construireThermite, "Fabriquer de la thermite");
	    construireGadjet.ajoutNodeList(construirePoison, "Fabriquer un poison");
	    construireGadjet.ajoutNodeList(construireBombe, "Fabriquer une bombe");
	    
	    for (Event e : l) {
	    	e.setNodeList(node.getSuccNode(), node.getTitreNode());
	    }
	    
		node.setNodeList(new ArrayList<>(), new ArrayList<>());
		node.ajoutNodeList(construireGadjet, "Construire un Gadget");
		
	}
	
}

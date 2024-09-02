/**
 * sous classe du type du joueur charismatique
 */
package univers;

import java.util.ArrayList;
import java.util.Arrays;

import representation.DecisionNode;
import representation.Event;

@SuppressWarnings("serial")
public class JoueurCharismatique extends Joueur{

	public JoueurCharismatique() {
		super();
		
		ArrayList<Gadget> g = getGadget();
		g.addAll(Arrays.asList(Gadget.Deguisement));
		setGadget(g);
		
		setLvlSociabilite(5);
	}
	
	public JoueurCharismatique(String nom) {
		super(nom);
		
		ArrayList<Gadget> g = getGadget();
		g.addAll(Arrays.asList(Gadget.Deguisement));
		setGadget(g);
		
		setLvlSociabilite(5);
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
	    Event construireDeguisement = new DecisionNode("NOUVEAU GADGET: Deguisement (permet de prendre l'apparence de quelqu'un pendant un cours laps de temps) \n"
	    		+ "Attention, les gadgets ont une utilisation unique \n"
	    		+ "C'est la fin du cours",
	    		player, ()->player.addGadget(Gadget.Parfum_hypnotiseur));
	    Event construireParfum = new DecisionNode("NOUVEAU GADGET: Parfum hypnotisant (permet de manipuler quelqu'un pour obtenir des informations) \n"
	    		+ "Attention, les gadgets ont une utilisation unique \n"
	    		+ "C'est la fin du cours",
	    		player, ()->player.addGadget(Gadget.Parfum_hypnotiseur));
	    Event construireBlanchisseur = new DecisionNode("NOUVEAU GADGET: Blanchisseur dentaire (rend vos dents éblouissantes ... l'hygienne dentaire est importante même pour agent secret)\n"
	    		+ "Attention, les gadgets ont une utilisation unique \n"
	    		+ "C'est la fin du cours",player,()->player.addGadget(Gadget.Blanchisseur_dentaire)); 
	   
	    l.add(construireCape);
	    l.add(construireMicro);
	    l.add(construireDeguisement);
	    l.add(construireParfum);
	    l.add(construireBlanchisseur);
	    
	    construireGadjet.ajoutNodeList(construireCape, "Fabriquer une cape d'invisibilité");
	    construireGadjet.ajoutNodeList(construireMicro, "Fabriquer un micro espion");
	    construireGadjet.ajoutNodeList(construireDeguisement, "Fabriquer un déguisement");
	    construireGadjet.ajoutNodeList(construireParfum, "Fabriquer un parfum hynotiseur");
	    construireGadjet.ajoutNodeList(construireBlanchisseur, "Fabriquer un blanchisseur dentaire");
	    
	    for (Event e : l) {
	    	e.setNodeList(node.getSuccNode(), node.getTitreNode());
	    }
	    
		node.setNodeList(new ArrayList<>(), new ArrayList<>());
		node.ajoutNodeList(construireGadjet, "Construire un Gadget");
		
	}
}

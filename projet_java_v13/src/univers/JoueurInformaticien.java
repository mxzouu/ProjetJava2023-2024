/**
 * Classe du type du joueur informaticien
 */
package univers;

import java.util.ArrayList;
import java.util.Arrays;

import representation.DecisionNode;
import representation.Event;

@SuppressWarnings("serial")
public class JoueurInformaticien extends Joueur{

	public JoueurInformaticien() {
		super();
		ArrayList<Gadget> g = getGadget();
		g.addAll(Arrays.asList(Gadget.Lunette));
		setGadget(g);
	}
	
	public JoueurInformaticien(String nom) {
		super(nom);
		
		ArrayList<Gadget> g = getGadget();
		g.addAll(Arrays.asList(Gadget.Lunette));
		setGadget(g);
		
	}
	
	/**
	 * fonction qui permet de creer un gagdet selon le type du joueur
	 */
	public void creerGadget(Event node, Joueur player) {
		ArrayList<Event> l = new ArrayList<>();

		Event construireGadjet = new DecisionNode("Choisissez le gadjet que vous souhaitez construire",player);
		
		Event construireCape = new DecisionNode("NOUVEAU GADGET: Cape d'invisibilit� (permet de devenir invisible pendant un cours laps de temps)\n"
				+ "Attention, les gadgets ont une utilisation unique \n"
				+ "C'est la fin du cours",
				player, ()->player.addGadget(Gadget.Cape_invisibilite));
		Event construireMicro = new DecisionNode("NOUVEAU GADGET: Micro espion (permet d'enregistrer et d'�couter des conversations)\n"
				+ "Attention, les gadgets ont une utilisation unique \n"
				+ "C'est la fin du cours",
				player, ()->player.addGadget(Gadget.Micro_espion));
		Event construireLunette = new DecisionNode("NOUVEAU GADGET: Lunette d'agent secret (permet de rep�rer les syst�mes de s�curit� comme des lasers ou des cam�ras de suveillance)\n"
				+ "Attention, les gadgets ont une utilisation unique \n"
				+ "C'est la fin du cours",
				player, ()->player.addGadget(Gadget.Lunette));		
		Event construireVirus = new DecisionNode("NOUVEAU GADGET: Virus sous cl� USB (permet d'hacker des ordinateurs) \n"
				+ "Attention, les gadgets ont une utilisation unique \n"
				+ "C'est la fin du cours",
				player, ()->player.addGadget(Gadget.Virus_informatique));
		Event construireBrouilleur = new DecisionNode("NOUVEAU GADGET: Brouilleur (permet de d�sactiver les syst�mes de s�curit� aux alentours) \n"
				+ "Attention, les gadgets ont une utilisation unique \n"
				+ "C'est la fin du cours",
				player,()->player.addGadget(Gadget.Brouilleur));
		
	    l.add(construireCape);
	    l.add(construireMicro);
	    l.add(construireLunette);
	    l.add(construireVirus);
	    l.add(construireBrouilleur);
	    
	    construireGadjet.ajoutNodeList(construireCape, "Fabriquer une cape d'invisibilit�");
	    construireGadjet.ajoutNodeList(construireMicro, "Fabriquer un micro espion");
	    construireGadjet.ajoutNodeList(construireLunette, "Fabriquer des lunette d'agent secret");
	    construireGadjet.ajoutNodeList(construireVirus, "Fabriquer un Virus sous cl� USB");
	    construireGadjet.ajoutNodeList(construireBrouilleur, "Fabriquer un brouilleur");
	    
		for (Event e : l) {
	    	e.setNodeList(node.getSuccNode(), node.getTitreNode());
	    }
		
		node.setNodeList(new ArrayList<>(), new ArrayList<>());
		node.ajoutNodeList(construireGadjet, "Construire un Gadget");
	}
	
}

package representation;


@SuppressWarnings("serial")
public class MAJAjoutNode extends MAJNode {
	private TriFunction<Event, Event, String, Void> functionWithArguments;
	private Event e1;
	private Event e2;
	private String s;
	
	
	public MAJAjoutNode (SerializableRunnable f) {
		super(f);
		functionWithArguments = (event1, event2, string) -> {return null;} ;
	}
	public MAJAjoutNode (Event e1, Event e2, String s) {
		super();
		if ((e1 == null)||(e2==null)||(s==null)) {
			throw new IllegalArgumentException("un des arguments est nul, cela peut provenir du fait que vous faites référence à un node par encore créé");
		}
			
		functionWithArguments = (event1, event2, string) -> {
			e1.ajoutNodeList(e2, s);
			return null;} ;
	}
	
	public void execute() {
		super.execute();
		functionWithArguments.apply(e1, e2, s);
	}
}

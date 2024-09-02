package representation;


@SuppressWarnings("serial")
public class MAJDeleteNode extends MAJNode {
	
	private TriFunction<Event, Event, String, Void> functionWithArguments;
	private Event e1;
	private Event e2;
	private String s;
	
	
	public MAJDeleteNode (SerializableRunnable f) {
		super(f);
		functionWithArguments = (event1, event2, string) -> {return null;} ;
	}
	public MAJDeleteNode (Event e1, Event e2, String s) {
		super();
		functionWithArguments = (event1, event2, string) -> {
			e1.deleteNodeList(e2, s);
			return null;} ;
	}
	
	public void execute() {
		super.execute();
		functionWithArguments.apply(e1, e2, s);
	}
}

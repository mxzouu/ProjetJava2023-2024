package representation;
import java.io.*;


    
@SuppressWarnings("serial")
public class MAJNode implements Serializable {
    private SerializableRunnable functionWithoutArgument;

    public MAJNode(SerializableRunnable f) {
        functionWithoutArgument = f;
    }

    public MAJNode() {
        functionWithoutArgument = () -> {
        };
    }

    public SerializableRunnable getFunctionWithoutArgument() {
        return functionWithoutArgument;
    }

    public void setFunctionWithoutArgument(SerializableRunnable functionWithoutArgument) {
        this.functionWithoutArgument = functionWithoutArgument;
    }

    public void execute() {
        functionWithoutArgument.run();
    }

   

    
}

package univers;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class Tuple<T, S> implements Serializable{
    private T val1;
    private S val2;

    public T getVal1() {
		return val1;
	}
    public void setVal1(T val1) {
		this.val1 = val1;
	}

	public S getVal2() {
		return val2;
	}
	public void setVal2(S val2) {
		this.val2 = val2;
	}

	public Tuple(T t, S s) {
        val1 = t;
        val2 = s;
    }

    @Override
    public String toString() {
        return "(" + val1 + "," + val2 + ")";
    }

	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) obj;
        return Objects.equals(val1, tuple.val1) && Objects.equals(val2, tuple.val2);
    }

		@Override
	public Object clone() {
		return new Tuple<T,S>(val1,val2);
	}
	
}

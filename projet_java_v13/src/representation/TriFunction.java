package representation;

import java.io.Serializable;

@FunctionalInterface
interface TriFunction<A, B, C, R> extends Serializable {
	R apply(A a, B b, C c);
}


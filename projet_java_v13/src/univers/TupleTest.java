package univers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class TupleTest {
	 private Tuple<Integer, String> tuple;
	
	 @BeforeEach
	 void CreationTuple() {
	       tuple = new Tuple<>(1, "One");   
	    }

	 @AfterEach
	 void depointerTuple() {
	        tuple = null;
	    }


	 @ParameterizedTest
	 @CsvSource({ "1, 'One', 1, 'One'", "1, 'One', 2, 'Two'" })
	 void testEquals(int val1_1, String val2_1, int val1_2, String val2_2) {
	        Tuple<Integer, String> tuple1 = new Tuple<>(val1_1, val2_1);
	        Tuple<Integer, String> tuple2 = new Tuple<>(val1_2, val2_2);

	        if (val1_1 == val1_2 && val2_1.equals(val2_2)) {
	            assertTrue(tuple2.equals(tuple1) );
	        } else {
	            assertFalse(tuple2.equals(tuple1));
	        }
	    }
     
	 /// Fail explicite déclenché, on s'attend donc à deux failures dans JUnit
	 @ParameterizedTest
	 @ValueSource(strings = {"(1,One)", "(2,Two)", "(0,Zero)"})
	 void testToString(String expectedToString) {
	      String actualToString = tuple.toString();

	      if (!expectedToString.equals(actualToString)) {
	          fail("Expected: " + expectedToString + ", but got: " + actualToString);
	        }
	    }
     
    @Test
    void testClone() {
        @SuppressWarnings("unchecked")
		Tuple<Integer, String> clone = (Tuple<Integer, String>) tuple.clone();
		Tuple<Integer, String> clonedTuple = clone;

        assertEquals(tuple, clonedTuple);
        assertNotSame(tuple, clonedTuple);
    } 

    @Test
    void testGettersAndSetters() {

        assertEquals(1, tuple.getVal1());
        assertEquals("One", tuple.getVal2());

        tuple.setVal1(2);
        tuple.setVal2("Two");

        assertEquals(2, tuple.getVal1());
        assertEquals("Two", tuple.getVal2());
    }
}
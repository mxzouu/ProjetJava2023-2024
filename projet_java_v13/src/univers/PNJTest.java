package univers;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap; 
import java.util.Map;

public class PNJTest {
	private PNJ pnj = new PNJ();

    @Test
    public void testAjouterAmis() {
        PNJ pnj1 = new PNJ("Ami1", Domaine.INCONNU, new ArrayList<>(Arrays.asList(Caractere.INCONNU)),
                new ArrayList<>(Arrays.asList(Hobbie.INCONNU)), Association.INCONNUE, new ArrayList<>(), 5, 5, 5,
                new HashMap<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 5, 5);
        PNJ pnj2 = new PNJ("Ami2", Domaine.INCONNU, new ArrayList<>(Arrays.asList(Caractere.INCONNU)),
                new ArrayList<>(Arrays.asList(Hobbie.INCONNU)), Association.INCONNUE, new ArrayList<>(), 5, 5, 5,
                new HashMap<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 5, 5);

        pnj1.ajouterAmis(pnj2);

        assertEquals(1, pnj1.getAmis().size());
        assertEquals(1, pnj2.getAmis().size());
        assertEquals("Ami2", pnj1.getAmis().get(0).getIdentite());
        assertEquals("Ami1", pnj2.getAmis().get(0).getIdentite());
    }

    @Test
    public void testRoutineVerif() {
    	Map<Tuple<Number, Number>, String> routineAvecChevauchement = new LinkedHashMap<>();
        routineAvecChevauchement.put(new Tuple<>(8, 10), "Activité 1");
        routineAvecChevauchement.put(new Tuple<>(12, 9), "Activité 2");

       
        assertFalse(pnj.routineVerif(routineAvecChevauchement));

        Map<Tuple<Number, Number>, String> routineSansChevauchement = new LinkedHashMap<>();
        routineSansChevauchement.put(new Tuple<>(8, 10), "Activité 1");
        routineSansChevauchement.put(new Tuple<>(11, 14), "Activité 2");
        
        assertTrue(pnj.routineVerif(routineSansChevauchement));
    }
    
    @Test
    public void testClone() { 
        PNJ pnjTest= new PNJ();
        PNJ clonedPNJ =  (PNJ) pnjTest.clone();
        assertTrue(pnj.equals(clonedPNJ));
       assertNotSame(pnj, clonedPNJ);
    }
    
    @ParameterizedTest
    @ValueSource(doubles = { 8.30, 13.00})
    public void testAUnAlibi(double heure) {
        Map<Tuple<Number, Number>, String> routine = new LinkedHashMap<>();
        routine.put(new Tuple<>(8, 9), "Alibi au café");
        routine.put(new Tuple<>(12, 14), "Alibi à la bibliothèque");
        pnj.setRoutine(routine);
        String alibi = pnj.aUnAlibi(heure);
        assertNotNull(alibi); 
        System.out.println("Alibi à " + heure + " : " + alibi);
    }
    
    @Test
    void testEquals() {
        PNJ pnj1 = new PNJ("Test", Domaine.INCONNU, new ArrayList<>(Arrays.asList(Caractere.INCONNU)),
                new ArrayList<>(Arrays.asList(Hobbie.INCONNU)), Association.INCONNUE, new ArrayList<>(), 5, 5, 5,
                new HashMap<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 5, 5);

        PNJ pnj2 = new PNJ("Test", Domaine.INCONNU, new ArrayList<>(Arrays.asList(Caractere.INCONNU)),
                new ArrayList<>(Arrays.asList(Hobbie.INCONNU)), Association.INCONNUE, new ArrayList<>(), 5, 5, 5,
                new HashMap<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 5, 5);

        assertTrue(pnj1.equals(pnj2));
        assertTrue(pnj2.equals(pnj1));

       
        pnj2.setConfiance(8);

     
        assertFalse(pnj1.equals(pnj2));
        assertFalse(pnj2.equals(pnj1));
    }

}

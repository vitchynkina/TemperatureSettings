package ua.edu.ucu.tempseries;

import org.junit.Test;

import ua.edu.ucu.apps.tempseries.TempSummaryStatistics;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TempSummaryStatisticsTest {
    @Test
    public void constructorAndGetters() {
        TempSummaryStatistics s = new TempSummaryStatistics(1.0, 2.0, -3.0, 4.0);
        assertEquals(1.0, s.getAvgTemp(), 1e-9);
        assertEquals(2.0, s.getDevTemp(), 1e-9);
        assertEquals(-3.0, s.getMinTemp(), 1e-9);
        assertEquals(4.0, s.getMaxTemp(), 1e-9);
    }

    @Test
    public void equalsAndHashCode() {
        TempSummaryStatistics a = new TempSummaryStatistics(1.0, 2.0, -3.0, 4.0);
        TempSummaryStatistics b = new TempSummaryStatistics(1.0, 2.0, -3.0, 4.0);
        TempSummaryStatistics c = new TempSummaryStatistics(1.0, 2.1, -3.0, 4.0);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }

    @Test
    public void toStringContainsValues() {
        TempSummaryStatistics s = new TempSummaryStatistics(1.5, 0.0, -7.2, 9.9);
        String str = s.toString();
        assertTrue(str.contains("1.5"));
        assertTrue(str.contains("0.0"));
        assertTrue(str.contains("-7.2"));
        assertTrue(str.contains("9.9"));
    }

    @Test
    public void noSettersPresent() {
        Method[] methods = TempSummaryStatistics.class.getMethods();
        Set<String> setters = new HashSet<>();
        for (Method m : methods) {
            if (m.getName().startsWith("set") && m.getParameterCount() == 1) {
                setters.add(m.getName());
            }
        }
        assertTrue("Class should be immutable and have no setters, found: " + setters, setters.isEmpty());
    }
}

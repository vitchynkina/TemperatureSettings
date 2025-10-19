package ua.edu.ucu.tempseries;

import static org.junit.Assert.*;

import java.util.InputMismatchException;

import org.junit.Test;
import ua.edu.ucu.apps.tempseries.TemperatureSeriesAnalysis;

public class TemperatureSeriesAnalysisTest {

    @Test
    public void test() {
        double[] temperatureSeries = {-1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = -1.0;

        double actualResult = seriesAnalysis.average();
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test
    public void testAverageWithOneElementArray() {
       // setup input data and expected result
        double[] temperatureSeries = {-1.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = -1.0;

       // call tested method
        double actualResult = seriesAnalysis.average();

       // compare expected result with actual result
        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAverageWithEmptyArray() {
        double[] temperatureSeries = {};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);

       // expect exception here
        seriesAnalysis.average();
    }

    @Test
    public void testAverage() {
        double[] temperatureSeries = {3.0, -5.0, 1.0, 5.0};
        TemperatureSeriesAnalysis seriesAnalysis = new TemperatureSeriesAnalysis(temperatureSeries);
        double expResult = 1.0;

        double actualResult = seriesAnalysis.average();

        assertEquals(expResult, actualResult, 0.00001);
    }

    @Test(expected = InputMismatchException.class)
    public void constructorShouldRejectBelowAbsoluteZero() {
        double[] bad = {-274.0, 0.0};
        new TemperatureSeriesAnalysis(bad);
    }

    @Test
    public void deviationShouldBeZeroForConstantSeries() {
        TemperatureSeriesAnalysis a = new TemperatureSeriesAnalysis(new double[]{2.0, 2.0, 2.0, 2.0});
        assertEquals(0.0, a.deviation(), 1e-9);
    }

    @Test
    public void minMaxBasic() {
        TemperatureSeriesAnalysis a = new TemperatureSeriesAnalysis(new double[]{3.0, -5.0, 1.0, 5.0});
        assertEquals(-5.0, a.min(), 1e-9);
        assertEquals(5.0, a.max(), 1e-9);
    }

    @Test
    public void closestToZeroPrefersPositiveOnTie() {
        TemperatureSeriesAnalysis a = new TemperatureSeriesAnalysis(new double[]{-0.2, 0.2});
        assertEquals(0.2, a.findTempClosestToZero(), 1e-9);
    }

    @Test
    public void closestToValuePrefersHigherOnTie() {
        TemperatureSeriesAnalysis a = new TemperatureSeriesAnalysis(new double[]{1.0, 3.0});
        assertEquals(3.0, a.findTempClosestToValue(2.0), 1e-9);
    }

    @Test
    public void findTempsLessThenStrictlyLessAndTrimmed() {
        TemperatureSeriesAnalysis a = new TemperatureSeriesAnalysis(new double[]{1.0, -2.0, 3.0, -4.0});
        double[] got = a.findTempsLessThen(0.0);
        assertArrayEquals(new double[]{-2.0, -4.0}, got, 1e-9);
    }

    @Test
    public void findTempsGreaterThenIsGreater() {
        TemperatureSeriesAnalysis a = new TemperatureSeriesAnalysis(new double[]{0.0, -1.0, 1.0});
        double[] got = a.findTempsGreaterThen(0.0);
        assertArrayEquals(new double[]{1.0}, got, 1e-9);
    }

    @Test
    public void addTempsAppendsAllAndReturnsNewSize() {
        TemperatureSeriesAnalysis a = new TemperatureSeriesAnalysis(new double[]{1.0, 2.0});
        int newSize = a.addTemps(3.0, 4.0, 5.0);
        assertEquals(5, newSize);
        assertArrayEquals(new double[]{1.0, 2.0, 3.0, 4.0, 5.0}, a.sortTemps(), 1e-9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void resetMakesSeriesEmpty() {
        TemperatureSeriesAnalysis a = new TemperatureSeriesAnalysis(new double[]{1.0});
        a.reset();
        a.average();
    }

    @Test
    public void sortTempsReturnsSortedCopy() {
        TemperatureSeriesAnalysis a = new TemperatureSeriesAnalysis(new double[]{3.0, 1.0, 2.0});
        double[] sorted = a.sortTemps();
        assertArrayEquals(new double[]{1.0, 2.0, 3.0}, sorted, 1e-9);

        assertEquals(1.0, a.min(), 1e-9);
        assertEquals(3.0, a.max(), 1e-9);
    }
}

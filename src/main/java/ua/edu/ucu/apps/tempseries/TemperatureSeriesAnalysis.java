package ua.edu.ucu.apps.tempseries;

import java.security.DrbgParameters.Capability;
import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    private static final int CAPACITY = 20;
    private static final double MIN_TEMP = -273.0;

    private double[] data;
    private int size;

    public TemperatureSeriesAnalysis() {
        this.data = new double[CAPACITY];
        this.size = 0;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        if (temperatureSeries.length == 0) {
            this.data = new double[CAPACITY];
            this.size = 0;
        } else {
            for (double t : temperatureSeries) {
                if (t < MIN_TEMP) {
                    throw new InputMismatchException("Low temperature");
                }
            }
            this.data = new double[Math.max(CAPACITY, temperatureSeries.length)];
            System.arraycopy(temperatureSeries, 0, this.data, 0, temperatureSeries.length);
            this.size = temperatureSeries.length;
        }
    }

    public double average() {
        checkNotEmpty();
        double sum = 0.0;
        for (int i = 0; i < size; i++) {
            sum += data[i];
        }
        return sum / size;
    }

    public double deviation() {
        checkNotEmpty();
        double aver = average();
        double sumSqaures = 0.0;

        for (int i = 0; i < size; i++) {
            sumSqaures += Math.pow(data[i] - aver, 2);
        }
        return Math.pow(sumSqaures/size, 0.5);
    }

    public double min() {
        checkNotEmpty();
        double min = data[0];
        for (int i = 1; i < size; i++) {
            if (data[i] < min) {
                min = data[i];
            }
        }
        return min;
    }

    public double max() {
        checkNotEmpty();
        double max = data[0];
        for (int i = 1; i < size; i++) {
            if (data[i] > max) {
                max = data[i];
            }
        }
        return max;
    }

    public double findTempClosestToZero() {
        checkNotEmpty();
        double closest = Math.abs(data[1]);
        double closestValue = data[1];
        for (int i = 0; i < size; i++) {
            if (Math.abs(data[i]) < closest) {
                closest = Math.abs(data[i]);
                closestValue = data[i];
            }
            if (Math.abs(data[i]) == closest) {
                closest = Math.max(closestValue, data[i]);
                closestValue = data[i];
            }
        }
        return closestValue;
    }

    public double findTempClosestToValue(double tempValue) {
        checkNotEmpty();
        double closest = Math.abs(data[1] - tempValue);
        double closestValue = data[1];
        for (int i = 0; i < size; i++) {
            if (Math.abs(data[i] - tempValue) < closest) {
                closest = Math.abs(data[i] - tempValue);
                closestValue = data[i];
            }
            if (Math.abs(data[i]) == closest) {
                closest = Math.max(closestValue, data[i]);
                closestValue = data[i];
            }
        }
        return closestValue;
    }

    public double[] findTempsLessThen(double tempValue) {
        double[] lessValues = new double[size];
        int lessSize = 0;
        for (int i = 0; i < size; i++) {
            if (data[i] < tempValue) {
                lessValues[lessSize] = data[i];
                lessSize ++;
            }
        }
        return Arrays.copyOf(lessValues, lessSize);
    }

    public double[] findTempsGreaterThen(double tempValue) {
        double[] greaterValues = new double[size];
        int greaterSize = 0;
        for (int i = 0; i < size; i++) {
            if (data[i] > tempValue) {
                greaterValues[greaterSize] = data[i];
                greaterSize++;
            }
        }
        return Arrays.copyOf(greaterValues, greaterSize);
    }

    public double[] findTempsInRange(double lowerBound, double upperBound) {
        double[] suitableTemps = new double[size];
        int arrSize = 0;

        for (int i = 0; i < size; i++) {
            if (data[i] >= lowerBound && data[i] <= upperBound) {
                suitableTemps[arrSize] = data[i];
                arrSize ++;
            }
        }
        return suitableTemps;
    }

    public void reset() {
        size = 0;
    }

    public double[] sortTemps() {
        double[] copy = new double[size];
        System.arraycopy(data, 0, copy, 0, size);
        Arrays.sort(copy);
        return copy;
    }

    public TempSummaryStatistics summaryStatistics() {
        checkNotEmpty();
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public int addTemps(double... temps) {
        if (temps.length == 0) {
            return size;
        }
        for (double t : temps) {
                if (t < MIN_TEMP) {
                    throw new InputMismatchException("Low temperature");
                }
                int neededSpace = size + temps.length;
                int newCapacity = CAPACITY;
                while (CAPACITY < neededSpace) {
                    newCapacity *= 2;
                }
                double[] newSeries = new double[newCapacity];
                if (size > 0) {
                    System.arraycopy(data, 0, newSeries, 0, size);
                }
                data = newSeries;
                System.arraycopy(temps, 0, data, size, temps.length);
                size += temps.length;
                return size;


            }
        return 0;
    }

    private void checkNotEmpty() {
        if (size == 0) {
            throw new IllegalArgumentException("Series is empty");
        }
    }
}

package ua.edu.ucu.apps.tempseries;

import lombok.Value;

@Value
public class TempSummaryStatistics {
    private double avgTemp;
    private double devTemp;
    private double minTemp;
    private double maxTemp;
}

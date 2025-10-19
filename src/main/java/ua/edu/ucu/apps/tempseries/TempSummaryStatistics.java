package ua.edu.ucu.apps.tempseries;

import lombok.Value;

@Value
public class TempSummaryStatistics {
    double avgTemp;
    double devTemp;
    double minTemp;
    double maxTemp;
}

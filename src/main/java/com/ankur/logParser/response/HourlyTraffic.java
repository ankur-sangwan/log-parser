package com.ankur.logParser.response;

import com.ankur.logParser.common.HourlyTrafficVisitors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HourlyTraffic {
    public String date;
    public List<HourlyTrafficVisitors> hourlyTraffic = new ArrayList<>();

    public HourlyTraffic(String date, Map<String, Integer> hourlyTrafficMap) {
        this.date = date;
        HourlyTrafficVisitors hourlyTrafficOccurrence;
        for (Map.Entry<String, Integer> entry : hourlyTrafficMap.entrySet()) {
            hourlyTrafficOccurrence = new HourlyTrafficVisitors(entry.getKey(), entry.getValue());
            hourlyTraffic.add(hourlyTrafficOccurrence);
        }
    }


    @Override
    public String toString() {
        return "HourlyTraffic{" +
                "date='" + date + '\'' +
                ", hourlyTraffic=" + hourlyTraffic +
                '}';
    }
}

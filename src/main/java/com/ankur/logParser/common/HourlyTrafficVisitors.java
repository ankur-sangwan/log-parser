package com.ankur.logParser.common;

public class HourlyTrafficVisitors {
    public int hour;
    public int visitors;

    public HourlyTrafficVisitors(String key, Integer value) {
        this.hour= Integer.parseInt(key);
        this.visitors=value;

    }

    @Override
    public String toString() {
        return "HourlyTraffic{" +
                "hour=" + hour +
                ", visitors=" + visitors +
                '}';
    }
}

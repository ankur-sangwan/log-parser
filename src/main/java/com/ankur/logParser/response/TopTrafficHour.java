package com.ankur.logParser.response;

import java.util.List;

public class TopTrafficHour {
    public String date;
    public double percentage;
    public List<String> topTrafficHours;

    public TopTrafficHour(String date, double percentage, List<String> topTrafficHourList) {
        this.date = date;
        this.percentage = percentage;
        this.topTrafficHours = topTrafficHourList;
    }

    @Override
    public String toString() {
        return "TopTrafficHour{" +
                "date='" + date + '\'' +
                ", percentage=" + percentage +
                ", topTrafficHours=" + topTrafficHours +
                '}';
    }
}

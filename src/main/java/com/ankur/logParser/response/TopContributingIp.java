package com.ankur.logParser.response;

import java.util.List;

public class TopContributingIp {
    public String date;
    public double percentage;
    public List<String> topContributingIps;

    public TopContributingIp(String date, double percentage, List<String> topContributingIpList) {
        this.date = date;
        this.percentage = percentage;
        this.topContributingIps = topContributingIpList;
    }

    @Override
    public String toString() {
        return "TopContributingIp{" +
                "date='" + date + '\'' +
                ", percentage=" + percentage +
                ", topContributingIps=" + topContributingIps +
                '}';
    }
}

package com.ankur.logParser.response;

import com.ankur.logParser.common.IpAddressOccurrences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UniqueIp {
    public String date;
    public List<IpAddressOccurrences> uniqueIps = new ArrayList<>();

    public UniqueIp(String date, Map<String, Integer> uniqueIpMap) {
        this.date = date;
        IpAddressOccurrences ipAddressOccurrence;
        for (Map.Entry<String, Integer> entry : uniqueIpMap.entrySet()) {
            ipAddressOccurrence = new IpAddressOccurrences(entry.getKey(), entry.getValue());
            uniqueIps.add(ipAddressOccurrence);
        }
    }

    @Override
    public String toString() {
        return "UniqueIp{" +
                "date='" + date + '\'' +
                ", uniqueIps=" + uniqueIps +
                '}';
    }
}

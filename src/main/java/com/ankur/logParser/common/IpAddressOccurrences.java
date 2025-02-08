package com.ankur.logParser.common;

public class IpAddressOccurrences {
    public String ipAddress;
    public int occurrences;

    public IpAddressOccurrences(String key, Integer value) {
        this.ipAddress=key;
        this.occurrences=value;
    }

    @Override
    public String toString() {
        return "IpAddressOccurrence{" +
                "ipAddress='" + ipAddress + '\'' +
                ", occurrences=" + occurrences +
                '}';
    }
}

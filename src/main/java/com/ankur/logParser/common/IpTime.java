package com.ankur.logParser.common;

public class IpTime {
    private String ip;
    private String time;

    public IpTime(String ip, String time) {
        this.ip=ip;
        this.time=time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "IpTime{" +
                "ip='" + ip + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}

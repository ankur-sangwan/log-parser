package com.ankur.logParser.service;

import com.ankur.logParser.common.IpTime;
import com.ankur.logParser.common.Task;
import com.ankur.logParser.eventListener.FileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LogParserService {
    @Autowired
    private FileReader fileReader;

    public HashMap<String, Integer> getIpAddressOccurrencesAtaGivenDay(String date) {
        HashMap<String, List<IpTime>> hashMap = fileReader.getIpDateMap();
        HashMap<String, Integer> ipOccurrenceMap = new HashMap<>();
        for (Map.Entry<String, List<IpTime>> entry : hashMap.entrySet()) {
            if (date.equals(entry.getKey())) {
                List<IpTime> list = entry.getValue();
                for (IpTime ipTime : list) {
                    ipOccurrenceMap.put(ipTime.getIp(), ipOccurrenceMap.getOrDefault(ipTime.getIp(), 0) + 1);
                }
            }
        }
        return ipOccurrenceMap;
    }

    public TreeMap<String, Integer> getHourlyTraffic(String date) {
        HashMap<String, List<IpTime>> hashMap = fileReader.getIpDateMap();
        TreeMap<String, Integer> hourlyTrafficMap = new TreeMap<>();
        for (Map.Entry<String, List<IpTime>> entry : hashMap.entrySet()) {
            if (date.equals(entry.getKey())) {
                List<IpTime> list = entry.getValue();
                for (IpTime ipTime : list) {
                    String time = ipTime.getTime().substring(0, 2);
                    hourlyTrafficMap.put(time, hourlyTrafficMap.getOrDefault(time, 0) + 1);
                }
            }
        }
        return hourlyTrafficMap;
    }

    public List<String> ipContribution(String date, double percentage) {
        HashMap<String, Integer> hashMap = getIpAddressOccurrencesAtaGivenDay(date);
        List<String> ipList = new ArrayList<>();
        long totalIpRequestCount = 0;
        for (int value : hashMap.values()) {
            totalIpRequestCount += value;
        }
        double eightyFivePercentageOfIpRequest = (percentage * totalIpRequestCount) / 100;
        PriorityQueue<Task> taskQueue = new PriorityQueue<>((t1, t2) -> Integer.compare(t2.occurrence, t1.occurrence));
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            taskQueue.add(new Task(entry.getKey(), entry.getValue()));
        }
        int count = 0;
        while (!taskQueue.isEmpty()) {
            Task task = taskQueue.poll();
            count += task.occurrence;
            if (count <= eightyFivePercentageOfIpRequest) {
                ipList.add(task.key);
            }
        }
        return ipList;
    }

    public List<String> hourlyTraffic(String date, double percentage) {
        TreeMap<String, Integer> treeMap = getHourlyTraffic(date);
        List<String> hourList = new ArrayList<>();
        long totalHourOccurance = 0;
        for (int value : treeMap.values()) {
            totalHourOccurance += value;
        }
        double percentageOccuranceOfHours = (percentage * totalHourOccurance) / 100;
        PriorityQueue<Task> taskQueue = new PriorityQueue<>((t1, t2) -> Integer.compare(t2.occurrence, t1.occurrence));
        for (Map.Entry<String, Integer> entry : treeMap.entrySet()) {
            taskQueue.add(new Task(entry.getKey(), entry.getValue()));
        }
        int count = 0;
        while (!taskQueue.isEmpty()) {
            Task task = taskQueue.poll();
            count += task.occurrence;
            if (count <= percentageOccuranceOfHours) {
                hourList.add(task.key);
            }
        }
        return hourList;
    }
}

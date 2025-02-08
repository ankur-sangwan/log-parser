package com.ankur.logParser.service;

import com.ankur.logParser.common.HourlyTrafficVisitors;
import com.ankur.logParser.common.IpAddressOccurrences;
import com.ankur.logParser.common.IpTime;
import com.ankur.logParser.common.Task;
import com.ankur.logParser.eventListener.FileReader;
import com.ankur.logParser.response.HourlyTraffic;
import com.ankur.logParser.response.UniqueIp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DashboardService {
    @Autowired
    private FileReader fileReader;
    @Autowired
    private LogParserService logParserService;

    public Page<IpAddressOccurrences> getIpAddressOccurrencesAtaGivenDayWithPagination(String date, Pageable pageable) {
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
        return getIpAddressOccurrences(date, pageable, ipOccurrenceMap);
    }

    private static Page<IpAddressOccurrences> getIpAddressOccurrences(String date, Pageable pageable, Map<String, Integer> ipOccurrenceMap) {
        List<IpAddressOccurrences> ipAddressOccurrences = new UniqueIp(date, ipOccurrenceMap).uniqueIps;

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<IpAddressOccurrences> list;

        if (ipAddressOccurrences.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, ipAddressOccurrences.size());
            list = ipAddressOccurrences.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), ipAddressOccurrences.size());
    }

    public Page<HourlyTrafficVisitors> getHourlyTrafficAtaGivenDayWithPagination(String date, Pageable pageable) {
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
        return getHourlyTraffic(date, pageable, hourlyTrafficMap);
    }

    private static Page<HourlyTrafficVisitors> getHourlyTraffic(String date, Pageable pageable, Map<String, Integer> hourlyTrafficMap) {
        List<HourlyTrafficVisitors> hourlyTrafficOccurrences = new HourlyTraffic(date, hourlyTrafficMap).hourlyTraffic;

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<HourlyTrafficVisitors> list;

        if (hourlyTrafficOccurrences.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, hourlyTrafficOccurrences.size());
            list = hourlyTrafficOccurrences.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), hourlyTrafficOccurrences.size());
    }

    public Page<String> getTopContributingIpsAtaGivenDay(String date, Pageable pageable) {
        HashMap<String, Integer> hashMap = logParserService.getIpAddressOccurrencesAtaGivenDay(date);
        List<String> ipList = new ArrayList<>();
        long totalIpRequestCount = 0;
        for (int value : hashMap.values()) {
            totalIpRequestCount += value;
        }
        double eightyFivePercentageOfIpRequest = (double) (85 * totalIpRequestCount) / 100;
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
        return getTopContributingIps(date, pageable, ipList);
    }

    public Page<String> getTopContributingTrafficHoursAtaGivenDay(String date, Pageable pageable) {
        TreeMap<String, Integer> treeMap = logParserService.getHourlyTraffic(date);
        List<String> hourList = new ArrayList<>();
        long totalHourOccurance = 0;
        for (int value : treeMap.values()) {
            totalHourOccurance += value;
        }
        double percentageOccuranceOfHours = (70.0 * totalHourOccurance) / 100;
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
        return getTopContributingIps(date, pageable, hourList);
    }


    private static Page<String> getTopContributingIps(String date, Pageable pageable, List<String> ipList) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<String> list;

        if (ipList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, ipList.size());
            list = ipList.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), ipList.size());
    }
}

package com.ankur.logParser.controller;

import com.ankur.logParser.response.HourlyTraffic;
import com.ankur.logParser.response.TopContributingIp;
import com.ankur.logParser.response.TopTrafficHour;
import com.ankur.logParser.response.UniqueIp;
import com.ankur.logParser.service.LogParserService;
import com.ankur.logParser.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@RestController
@RequestMapping("/log-parser/api")
public class LogParserController {
    @Autowired
    private LogParserService logParserService;

    @GetMapping("/unique-ip")
    public ResponseEntity<Object> getIpAddressOccurrencesAtaGivenDay(@RequestParam String date) {
        if (!DateUtil.isValidDate(date)) {
            return ResponseEntity.badRequest().body("Invalid date format. Use dd/MMM/yyyy (e.g., 30/Jan/2024).");
        }
        HashMap<String, Integer> uniqueIpMap = logParserService.getIpAddressOccurrencesAtaGivenDay(date);
        return new ResponseEntity<>(new UniqueIp(date, uniqueIpMap), HttpStatus.OK);
    }

    @GetMapping("/hourly-traffic")
    public ResponseEntity<Object> getHourlyTraffic(@RequestParam String date) {
        if (!DateUtil.isValidDate(date)) {
            return ResponseEntity.badRequest().body("Invalid date format. Use dd/MMM/yyyy (e.g., 30/Jan/2024).");
        }
        TreeMap<String, Integer> hourlyTrafficMap = logParserService.getHourlyTraffic(date);
        return new ResponseEntity<>(new HourlyTraffic(date, hourlyTrafficMap), HttpStatus.OK);
    }

    @GetMapping("/top-contributing-ips")
    public ResponseEntity<Object> ipContribution(@RequestParam String date, @RequestParam double percentage) {
        if (!DateUtil.isValidDate(date)) {
            return ResponseEntity.badRequest().body("Invalid date format. Use dd/MMM/yyyy (e.g., 30/Jan/2024).");
        }
        List<String> topContributingIpList = logParserService.ipContribution(date, percentage);
        return new ResponseEntity<>(new TopContributingIp(date, 85.0, topContributingIpList), HttpStatus.OK);
    }

    @GetMapping("/top-traffic-hours")
    public ResponseEntity<Object> hourlyTraffic(@RequestParam String date, @RequestParam double percentage) {
        if (!DateUtil.isValidDate(date)) {
            return ResponseEntity.badRequest().body("Invalid date format. Use dd/MMM/yyyy (e.g., 30/Jan/2024).");
        }
        List<String> topTrafficHourList = logParserService.hourlyTraffic(date, percentage);
        return new ResponseEntity<>(new TopTrafficHour(date, percentage, topTrafficHourList), HttpStatus.OK);
    }

}

package com.ankur.logParser.eventListener;

import com.ankur.logParser.common.IpTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class FileReader {

    @Autowired
    private ResourceLoader resourceLoader;

    private HashMap<String, List<IpTime>> ipDateMap = new HashMap<>();

    public void readFileFromResources(String filename) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + filename);
        InputStream inputStream = resource.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" - - ");
            if (parts.length == 2) {
                String ip = parts[0].trim(); // The IP address is the first part
                String[] dateTimePart = parts[1].split("]");
                if (dateTimePart.length > 0) {
                    String dateTime = dateTimePart[0].replace("[", "").trim(); // Remove square brackets
                    String[] dateTimeParts = dateTime.split(":");
                    String date = dateTimeParts[0];
                    String time = dateTimeParts[1] + ":" + dateTimeParts[2] + ":" + dateTimeParts[3];
                    IpTime ipTime = new IpTime(ip, time);
                    List<IpTime> ipTimeArrayList = ipDateMap.getOrDefault(date,new ArrayList<>());
                    ipTimeArrayList.add(ipTime);
                    ipDateMap.put(date, ipTimeArrayList);
                }
            }
            stringBuilder.append(line);
        }
        reader.close();
    }

    public HashMap<String, List<IpTime>> getIpDateMap() {
        return ipDateMap;
    }
}

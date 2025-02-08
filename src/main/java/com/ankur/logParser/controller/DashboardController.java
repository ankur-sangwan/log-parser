package com.ankur.logParser.controller;

import com.ankur.logParser.common.HourlyTrafficVisitors;
import com.ankur.logParser.common.IpAddressOccurrences;
import com.ankur.logParser.service.DashboardService;
import com.ankur.logParser.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/log-parser/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/unique-ip")
    public String getIpAddressOccurrencesAtaGivenDay(@RequestParam String date, Model model, @RequestParam("page") Optional<Integer> page) {
        final int currentPage = page.orElse(1);
        final int pageSize = 10;
        if (!DateUtil.isValidDate(date)) {
            model.addAttribute("message", "Invalid date format. Use dd/MMM/yyyy (e.g., 30/Jan/2024).");
            return "error";
        }

        Page<IpAddressOccurrences> ipAddressOccurrencePage = dashboardService.getIpAddressOccurrencesAtaGivenDayWithPagination(date, PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("ipAddressOccurrencePage", ipAddressOccurrencePage);
        int totalPages = ipAddressOccurrencePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("date", date);
        return "unique-ip";
    }

    @GetMapping("/hourly-traffic")
    public String getHourlyTrafficAtaGivenDay(@RequestParam String date, Model model, @RequestParam("page") Optional<Integer> page) {
        final int currentPage = page.orElse(1);
        final int pageSize = 10;
        if (!DateUtil.isValidDate(date)) {
            model.addAttribute("message", "Invalid date format. Use dd/MMM/yyyy (e.g., 30/Jan/2024).");
            return "error";
        }

        Page<HourlyTrafficVisitors> hourlyTrafficPage = dashboardService.getHourlyTrafficAtaGivenDayWithPagination(date, PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("hourlyTrafficPage", hourlyTrafficPage);

        int totalPages = hourlyTrafficPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("date", date);
        return "hourly-traffic";
    }

    @GetMapping("/top-contributing-ips")
    public String getTopContributingIpsAtaGivenDay(@RequestParam String date, Model model, @RequestParam("page") Optional<Integer> page) {
        final int currentPage = page.orElse(1);
        final int pageSize = 10;
        if (!DateUtil.isValidDate(date)) {
            model.addAttribute("message", "Invalid date format. Use dd/MMM/yyyy (e.g., 30/Jan/2024).");
            return "error";
        }

        Page<String> topContributingIpPage = dashboardService.getTopContributingIpsAtaGivenDay(date, PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("topContributingIpPage", topContributingIpPage);

        int totalPages = topContributingIpPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("date", date);
        return "top-contributing-ip";
    }

    @GetMapping("/top-traffic-hours")
    public String getTopContributingTrafficHoursAtaGivenDay(@RequestParam String date, Model model, @RequestParam("page") Optional<Integer> page) {
        final int currentPage = page.orElse(1);
        final int pageSize = 10;
        if (!DateUtil.isValidDate(date)) {
            model.addAttribute("message", "Invalid date format. Use dd/MMM/yyyy (e.g., 30/Jan/2024).");
            return "error";
        }

        Page<String> topContributingHourPage = dashboardService.getTopContributingTrafficHoursAtaGivenDay(date, PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("topContributingHourPage", topContributingHourPage);

        int totalPages = topContributingHourPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("date", date);
        return "top-traffic-hour";
    }


}

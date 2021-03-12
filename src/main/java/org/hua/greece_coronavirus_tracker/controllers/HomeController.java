package org.hua.greece_coronavirus_tracker.controllers;

import org.hua.greece_coronavirus_tracker.models.LocationStats;
import org.hua.greece_coronavirus_tracker.services.CoronavirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    CoronavirusDataService coronavirusDataService ;

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("locationStats", coronavirusDataService.getAllStats());
        model.addAttribute("totalReportedCases", LocationStats.getSumOfNewCases().toString());
        return "home";
    }


}

package org.hua.greece_coronavirus_tracker.services;

import org.hua.greece_coronavirus_tracker.models.LocationStats;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronavirusDataService {

    private static String virusDataURL = "https://raw.githubusercontent.com/Covid-19-Response-Greece/covid19-greece-api/master/data/greece/regional/regions_daily.csv";

    private List<LocationStats> allStats = new ArrayList<>();

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {

        List<LocationStats> newStats = new ArrayList<>();

        // get the stats from the link
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(virusDataURL)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());




        //System.out.println(httpResponse.body());
    }
}

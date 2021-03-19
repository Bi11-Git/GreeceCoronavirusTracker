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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

@Service
public class CoronavirusDataService {

    private static String virusDataURL = "https://raw.githubusercontent.com/Covid-19-Response-Greece/covid19-greece-api/master/data/greece/regional/regions_daily.csv";

    private List<LocationStats> allStats = new ArrayList<>();
    private static String dateString = null;

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException, ParseException {

        List<LocationStats> newStats = new ArrayList<>();

        // get the stats from the link
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(virusDataURL)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringTokenizer lines = new StringTokenizer(httpResponse.body(), "\n");

        String h = lines.nextToken();
        StringTokenizer head = new StringTokenizer( h, ",");
        int cases = 0, j = 0, region = 0, geoDepartment = 0, date = 0;
        String tmp;

        while (head.hasMoreTokens()) {
            tmp = head.nextToken();
            if(tmp.contentEquals("last_updated_at")) {
                date = j;
            }
            if( tmp.contentEquals("new_cases")){
                cases = j;
            } else if ( tmp.contentEquals("area_gr")) {
                region = j;
            } else if (tmp.contentEquals("geo_department_gr")) {
                geoDepartment = j;
            }

            j++;

        }

        while(lines.hasMoreTokens()) {

            StringTokenizer tokens = new StringTokenizer(lines.nextToken(), ",");

            LocationStats stats = new LocationStats();

            j = 0;
            while (tokens.hasMoreTokens()) {
                tmp = tokens.nextToken();
                if( j == date ) {
                    dateString = tmp;
                }
                if( j == region) {
                    stats.setRegion(tmp);
                } else if ( j == geoDepartment ) {
                    stats.setGeoDepartment(tmp);
                } else if(j == cases) {
                    stats.setNewCases(tmp);
                }
                j++;
            }

            LocationStats.setSumOfNewCases(LocationStats.getSumOfNewCases() + Integer.parseInt(stats.getNewCases()));

            newStats.add(stats);

        }

        allStats = newStats;

        Date dat = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        dateString = dat.toString();

        StringTokenizer tokens = new StringTokenizer(dateString, " ");
        String day = tokens.nextToken();
        String month = tokens.nextToken();
        String dayMonth = tokens.nextToken();

        tokens.nextToken();
        tokens.nextToken();

        String year = tokens.nextToken();

        switch (day) {
            case "Mon":
                day = "Δευτέρα";
                break;
            case "Tue":
                day = "Τρίτη";
            case "Wed":
                day = "Τετάρτη";
                break;
            case "Thu":
                day = "Πέμπτη";
                break;
            case "Fri":
                day = "Παρασκευή";
                break;
            case "Sat":
                day = "Σάββατο";
                break;
            case "Sun":
                day = "Κυριακή";
                break;
        }

        switch (month) {
            case "Jan":
                month = "Ιανουαρίου";
                break;
            case "Feb":
                month = "Φεβρουαρίου";
                break;
            case "Mar":
                month = "Μαρτίου";
                break;
            case "Apr":
                month = "Απριλίου";
                break;
            case "May":
                month = "Μαΐου";
                break;
            case "Jun":
                month = "Ιονίου";
                break;
            case "Jul":
                month = "Ιουλίου";
                break;
            case "Aug":
                month = "Αυγούστου";
                break;
            case "Sep":
                month = "Σεπτεμβρίου";
                break;
            case "Oct":
                month = "Οκτωβρίου";
                break;
            case "Nov":
                month = "Νοεμβρίου";
                break;
            case "Dec":
                month = "Δεκεμβρίου";
                break;

        }

        dateString = day + " " + dayMonth  + " " +  month + " " +  year;

    }

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    public static String getDateString() {

        String d = dateString;
        return d;
    }
}

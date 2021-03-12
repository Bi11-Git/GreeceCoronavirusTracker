package org.hua.greece_coronavirus_tracker.models;

public class LocationStats {

    private String region;
    private String geoDepartment;
    private String newCases;
    private static Integer sumOfNewCases = 0 ;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getGeoDepartment() {
        return geoDepartment;
    }

    public void setGeoDepartment(String geoDepartment) {
        this.geoDepartment = geoDepartment;
    }

    public String getNewCases() {
        return newCases;
    }

    public void setNewCases(String newCases) {
        this.newCases = newCases;
    }

    public static Integer getSumOfNewCases() {
        return sumOfNewCases;
    }

    public static void setSumOfNewCases(int sumOfNewCases) {
        LocationStats.sumOfNewCases = sumOfNewCases;
    }

    @Override
    public String toString() {
        return region + '-' + geoDepartment + '-' + newCases + '\n';
    }
}

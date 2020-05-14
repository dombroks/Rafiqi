package com.dombroks.rafiqi.Model;

public class PrayerTimes {
    private String fajr;
    private String sunrise;
    private String dhuhr;
    private String asr;
    private String maghrib;
    private String isha;
    private String imsak ;
    private String midnight;
    private String sunset;

    public PrayerTimes() {
    }

    public String getImsak() {
        return imsak;
    }

    public void setImsak(String imsak) {
        this.imsak = imsak;
    }

    public String getMidnight() {
        return midnight;
    }

    public void setMidnight(String midnight) {
        this.midnight = midnight;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public PrayerTimes(String fajr, String sunrise, String dhuhr, String asr, String maghrib, String isha, String imsak, String midnight, String sunset) {
        this.fajr = fajr;
        this.sunrise = sunrise;
        this.dhuhr = dhuhr;
        this.asr = asr;
        this.maghrib = maghrib;
        this.isha = isha;
        this.imsak = imsak;
        this.midnight = midnight;
        this.sunset = sunset;
    }

    public String getFajr() {
        return fajr;
    }

    public void setFajr(String fajr) {
        this.fajr = fajr;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getDhuhr() {
        return dhuhr;
    }

    public void setDhuhr(String dhuhr) {
        this.dhuhr = dhuhr;
    }

    public String getAsr() {
        return asr;
    }

    public void setAsr(String asr) {
        this.asr = asr;
    }

    public String getMaghrib() {
        return maghrib;
    }

    public void setMaghrib(String maghrib) {
        this.maghrib = maghrib;
    }

    public String getIsha() {
        return isha;
    }

    public void setIsha(String isha) {
        this.isha = isha;
    }

    @Override
    public String toString() {
        return "PrayerTimes{" +
                "fajr='" + fajr + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", dhuhr='" + dhuhr + '\'' +
                ", asr='" + asr + '\'' +
                ", maghrib='" + maghrib + '\'' +
                ", isha='" + isha + '\'' +
                '}';
    }
}

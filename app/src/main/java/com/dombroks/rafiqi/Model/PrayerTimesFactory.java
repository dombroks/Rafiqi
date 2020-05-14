package com.dombroks.rafiqi.Model;

public class PrayerTimesFactory {
    private String fajr;
    private String sunrise;
    private String dhuhr;
    private String asr;
    private String maghrib;
    private String isha;
    private String imsak;
    private String midnight;
    private String sunset;

    public PrayerTimesFactory() {
    }

    public PrayerTimesFactory fajr(String fajr) {
        this.fajr = fajr;
        return this;
    }
    public PrayerTimesFactory asr(String asr) {
        this.asr = asr;
        return this;
    }
    public PrayerTimesFactory dhuhr(String dhuhr) {
        this.dhuhr = dhuhr;
        return this;
    }
    public PrayerTimesFactory sunset(String sunset) {
        this.sunset = sunset;
        return this;
    }
    public PrayerTimesFactory sunrise(String sunrise) {
        this.sunrise = sunrise;
        return this;
    }
    public PrayerTimesFactory isha(String isha) {
        this.isha = isha;
        return this;
    }
    public PrayerTimesFactory midnight(String midnight) {
        this.midnight = midnight;
        return this;
    }
    public PrayerTimesFactory imsak(String imsak) {
        this.imsak = imsak;
        return this;
    }
    public PrayerTimesFactory maghrib(String maghrib) {
        this.maghrib = maghrib;
        return this;
    }
    public PrayerTimes build(){
        return  new PrayerTimes(fajr, sunrise, dhuhr, asr, sunset, maghrib, isha, imsak, midnight);
    }

}

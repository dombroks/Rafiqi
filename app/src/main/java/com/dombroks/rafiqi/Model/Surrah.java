package com.dombroks.rafiqi.Model;

public class Surrah {
    private int id;
    private String nameAr;
    private String nameEng;
    private String ayaNumber;
    private String type;

    public Surrah() {
    }

    public Surrah(String nameAr, String nameEng, String ayaNumber, String type) {
        this.nameAr = nameAr;
        this.nameEng = nameEng;
        this.ayaNumber = ayaNumber;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getAyaNumber() {
        return ayaNumber;
    }

    public void setAyaNumber(String ayaNumber) {
        this.ayaNumber = ayaNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

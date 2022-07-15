package com.codewithnaveen.JevanKhana.Models;

public class mealType {
    String Name;
    int Url;

    public mealType(String name, int url) {
        Name = name;
        Url = url;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getUrl() {
        return Url;
    }

    public void setUrl(int url) {
        Url = url;
    }
}

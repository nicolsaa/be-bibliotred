package com.example.libreria_app.client.response;

public class GenericKeyDto {

    private String key;

    private String name;

    private String title;

    public GenericKeyDto(String key, String name, String title) {
        this.key = key;
        this.name = name;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

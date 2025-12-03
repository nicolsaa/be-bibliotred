package com.example.libreria_app.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.ArrayList;

public class OpenLibratyEditionDto {

    private String key;

    private String title;

    @JsonProperty("isbn_10")
    private List<String> isbnTen;

    @JsonProperty("isbn_13")
    private List<String> isbnThirteen;

    private String description;

    private List<Integer> covers;

    private List<KeyRefDto> works;

    private List<KeyRefDto> authors;

    public OpenLibratyEditionDto() {
        this.authors = new ArrayList<>();
        this.works = new ArrayList<>();
        this.covers = new ArrayList<>();
        this.description = null;
        this.isbnTen = new ArrayList<>();
        this.isbnThirteen = new ArrayList<>();
        this.title = null;
        this.key = null;
    }

    public OpenLibratyEditionDto(List<KeyRefDto> authors, List<KeyRefDto> works, List<Integer> covers, String description, List<String> isbnThirteen, List<String> isbnTen, String title, String key) {
        this.authors = authors;
        this.works = works;
        this.covers = covers;
        this.description = description;
        this.isbnThirteen = isbnThirteen;
        this.isbnTen = isbnTen;
        this.title = title;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getIsbnTen() {
        return isbnTen;
    }

    public void setIsbnTen(List<String> isbnTen) {
        this.isbnTen = isbnTen;
    }

    public List<String> getIsbnThirteen() {
        return isbnThirteen;
    }

    public void setIsbnThirteen(List<String> isbnThirteen) {
        this.isbnThirteen = isbnThirteen;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getCovers() {
        return covers;
    }

    public void setCovers(List<Integer> covers) {
        this.covers = covers;
    }

    public List<KeyRefDto> getWorks() {
        return works;
    }

    public void setWorks(List<KeyRefDto> works) {
        this.works = works;
    }

    public List<KeyRefDto> getAuthors() {
        return authors;
    }

    public void setAuthors(List<KeyRefDto> authors) {
        this.authors = authors;
    }
}

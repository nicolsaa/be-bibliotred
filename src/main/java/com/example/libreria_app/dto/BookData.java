package com.example.libreria_app.dto;

import java.util.List;

public class BookData {
    private String title;
    private List<String> authorNames;
    private String coverUrl;
    private String descripcion;
    private String barCode;

    public BookData() {
    }

    public BookData(String title, List<String> authorNames, String coverUrl, String descripcion, String barCode) {
        this.title = title;
        this.authorNames = authorNames;
        this.coverUrl = coverUrl;
        this.descripcion = descripcion;
        this.barCode = barCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthorNames() {
        return authorNames;
    }

    public void setAuthorNames(List<String> authorNames) {
        this.authorNames = authorNames;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}

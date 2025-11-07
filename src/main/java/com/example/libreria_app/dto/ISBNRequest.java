package com.example.libreria_app.dto;

public class ISBNRequest {
    private String isbn;
    private String correoPropietario;

    public ISBNRequest() {
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCorreoPropietario() {
        return correoPropietario;
    }

    public void setCorreoPropietario(String correoPropietario) {
        this.correoPropietario = correoPropietario;
    }
    
}

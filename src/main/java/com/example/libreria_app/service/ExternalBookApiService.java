package com.example.libreria_app.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servicio para enriquecer libros a partir de un ISBN utilizando APIs externas
 * (Google Books y Open Library).
 * Este servicio devuelve un BookInfo con los campos básicos necesarios para
 * rellenar un Libro en la biblioteca del usuario.
 */
@Service
public class ExternalBookApiService {

  private static final Logger logger = LoggerFactory.getLogger(ExternalBookApiService.class);
  private final RestTemplate restTemplate;

  public ExternalBookApiService(RestTemplateBuilder builder) {
    this.restTemplate = builder
      .setConnectTimeout(Duration.ofSeconds(2))
      .setReadTimeout(Duration.ofSeconds(5))
      .build();
  }

  public BookInfo enrichFromIsbn(String isbn) {
    if (isbn == null || isbn.trim().isEmpty()) return null;

    BookInfo info = fetchFromGoogleBooks(isbn);
    if (info != null && info.getTitulo() != null && !info.getTitulo().isEmpty()) {
      return info;
    }

    info = fetchFromOpenLibrary(isbn);
    return info;
  }

  private BookInfo fetchFromGoogleBooks(String isbn) {
    String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
    try {
      Map<String, Object> resp = restTemplate.getForObject(url, Map.class);
      return parseGoogleBooks(resp);
    } catch (Exception e) {
      logger.error("Error fetching Google Books for ISBN {}: {}", isbn, e.toString(), e);
      return null;
    }
  }

  private BookInfo fetchFromOpenLibrary(String isbn) {
    String url = "https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data";
    try {
      Map<String, Object> resp = restTemplate.getForObject(url, Map.class);
      return parseOpenLibrary(resp, isbn);
    } catch (Exception e) {
      logger.error("Error fetching Open Library for ISBN {}: {}", isbn, e.toString(), e);
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  private BookInfo parseGoogleBooks(Map<String, Object> resp) {
    if (resp == null) return null;
    List<Map<String, Object>> items = (List<Map<String, Object>>) resp.get("items");
    if (items != null && !items.isEmpty()) {
      Map<String, Object> first = items.get(0);
      Map<String, Object> volumeInfo = (Map<String, Object>) first.get("volumeInfo");
      if (volumeInfo != null) {
        BookInfo bi = new BookInfo();
        bi.setTitulo((String) volumeInfo.get("title"));
        Object authorsObj = volumeInfo.get("authors");
        if (authorsObj instanceof List) {
          List<String> authors = (List<String>) authorsObj;
          if (!authors.isEmpty()) bi.setAutor(authors.get(0));
        }
        bi.setDescripcion((String) volumeInfo.get("description"));
        String publishedDate = (String) volumeInfo.get("publishedDate");
        if (publishedDate != null) {
          int year = extractYear(publishedDate);
          if (year > 0) bi.setAnioPublicacion(Date.valueOf(year + "-01-01"));
        }
        Object imageLinksObj = volumeInfo.get("imageLinks");
        if (imageLinksObj instanceof Map) {
          Map<String, Object> imageLinks = (Map<String, Object>) imageLinksObj;
          Object thumb = imageLinks.get("thumbnail");
          if (thumb != null) bi.setPortadaUrl(thumb.toString());
        }
        return bi;
      }
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  private BookInfo parseOpenLibrary(Map<String, Object> resp, String isbn) {
    String key = "ISBN:" + isbn;
    Map<String, Object> data = (Map<String, Object>) resp.get(key);
    if (data != null) {
      BookInfo bi = new BookInfo();
      bi.setTitulo((String) data.get("title"));

      Object authorsObj = data.get("authors");
      if (authorsObj instanceof List) {
        List<Map<String, Object>> authors = (List<Map<String, Object>>) authorsObj;
        if (!authors.isEmpty()) {
          Object name = authors.get(0).get("name");
          if (name != null) bi.setAutor(name.toString());
        }
      }

      String publishDate = (String) data.get("publish_date");
      if (publishDate != null) {
        int year = extractYear(publishDate);
        if (year > 0) bi.setAnioPublicacion(Date.valueOf(year + "-01-01"));
      }

      Object desc = data.get("description");
      if (desc instanceof String) bi.setDescripcion((String) desc);

      Map<String, Object> image = (Map<String, Object>) data.get("cover");
      if (image != null) {
        Object url = image.get("large");
        if (url != null) bi.setPortadaUrl(url.toString());
      }

      return bi;
    }
    return null;
  }

  private int extractYear(String dateStr) {
    try {
      java.util.regex.Matcher m = java.util.regex.Pattern.compile("(\\d{4})").matcher(dateStr);
      if (m.find()) {
        return Integer.parseInt(m.group(1));
      }
    } catch (Exception ignored) {}
    return -1;
  }

  public static class BookInfo {
    private String titulo;
    private String descripcion;
    private String autor;
    private Date anioPublicacion;
    private String portadaUrl;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public Date getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(Date anioPublicacion) { this.anioPublicacion = anioPublicacion; }

    public String getPortadaUrl() { return portadaUrl; }
    public void setPortadaUrl(String portadaUrl) { this.portadaUrl = portadaUrl; }
  }
}

package com.example.libreria_app.mapper;

import com.example.libreria_app.model.Libro;
import com.example.libreria_app.model.Autor;
import com.example.libreria_app.dto.BookData;

import java.util.List;
import java.util.stream.Collectors;

public class LibroMapper {
    public static BookData toBookData(Libro libro) {
        if (libro == null) {
            return null;
        }

        BookData dto = new BookData();
        // Mapea título
        dto.setTitle(libro.getTitulo());

        // Mapea autores a nombres
        if (libro.getAutores() != null) {
            List<String> authorNames = libro.getAutores().stream()
                    .map(Autor::getNombre)
                    .collect(Collectors.toList());
            dto.setAuthorNames(authorNames);
        }

        // Géneros: aún no se mapea; queda en null si no existe información en Libro
        // dto.setGenreNames(...);

        // Portada y descripción
        dto.setCoverUrl(libro.getPortada_url());
        dto.setDescripcion(libro.getDescripcion());

        return dto;
    }
}

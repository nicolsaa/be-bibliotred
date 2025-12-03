package com.example.libreria_app.service;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.libreria_app.dto.BookData;
import com.example.libreria_app.model.Autor;
import com.example.libreria_app.model.Libro;
import com.example.libreria_app.model.Usuario;
import com.example.libreria_app.repository.AutorRepository;
import com.example.libreria_app.repository.LibroRepository;
import com.example.libreria_app.repository.UsuarioRepository;

@Service
public class LibroService {

    private static final long CACHE_TTL_MS = 24 * 60 * 60 * 1000; // 24h
    private static final Logger logger = LoggerFactory.getLogger(LibroService.class);

    @Autowired
    private OpenLibraryApiService openLibraryApiService;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;


    @Autowired
    private UsuarioRepository usuarioRepository;

    // Nuevo helper para obtener o crear Autor por nombre
    private Autor obtenerOCrearAutorPorNombre(String nombre) {
        if (nombre == null) {
            return null;
        }
        String nombreNormalizado = nombre.trim().replaceAll("\\s+", " ");
        Optional<Autor> existente = autorRepository.findByNombreIgnoreCase(nombreNormalizado);
        if (existente.isPresent()) {
            Autor autorExistente = existente.get();
            System.out.println("LIBRO SERVICE: reutilizando Autor existente '" + nombreNormalizado + "' con id " + autorExistente.getId());
            return autorExistente;
        }
        Autor autorNuevo = new Autor();
        autorNuevo.setNombre(nombreNormalizado);
        autorNuevo = autorRepository.save(autorNuevo);
        System.out.println("LIBRO SERVICE: creado Autor '" + nombreNormalizado + "' con id " + autorNuevo.getId());
        return autorNuevo;
    }

    public List<Libro> listAllLibros() {
        return libroRepository.findAll();
    }

    // Scope: simple scan by ISBN, using OpenLibrary data, reusing existing Authors/Generos
    public Libro scanBarcode(String isbn, String correo, HttpSession session) {
        System.out.println("LIBRO SERVICE: scanBarcode called for ISBN=" + isbn);
        System.out.println("LIBRO SERVICE: inicio de procesamiento para ISBN=" + isbn);

        Map<String, BookData> cache = (Map<String, BookData>) session.getAttribute("OPENLIB_CACHE");
        Map<String, Long> cacheTime = (Map<String, Long>) session.getAttribute("OPENLIB_CACHE_TIME");
        if (cache == null) {
            cache = new HashMap<>();
            session.setAttribute("OPENLIB_CACHE", cache);
        }
        if (cacheTime == null) {
            cacheTime = new HashMap<>();
            session.setAttribute("OPENLIB_CACHE_TIME", cacheTime);
        }

        BookData data = null;
        Long cachedAt = cacheTime.get(isbn);
        long now = new Date().getTime();

        if (cache.containsKey(isbn) && cachedAt != null && (now - cachedAt) < CACHE_TTL_MS) {
            data = cache.get(isbn);
            System.out.println("LIBRO SERVICE: cache HIT for ISBN " + isbn);
        } else {
            data = openLibraryApiService.fetchBookData(isbn);
            cache.put(isbn, data);
            cacheTime.put(isbn, now);
            System.out.println("LIBRO SERVICE: cache MISS for ISBN " + isbn + "; fetched from OpenLibrary");
        }

        // 2) Crear o actualizar Autor
        Set<Autor> autores = new HashSet<>();
        if (data != null && data.getAuthorNames() != null) {
            for (String authorName : data.getAuthorNames()) {
                if (authorName == null || authorName.trim().isEmpty()) continue;
                Autor autor = obtenerOCrearAutorPorNombre(authorName);
                if (autor != null) {
                    autores.add(autor);
                }
            }
        }

        Libro libro = libroRepository.findById(isbn).orElse(null);

        if (Objects.nonNull(libro) 
            && !correo.equals(libro.getPropietario().getCorreo())) {
            libro = new Libro();
            libro.setCodigoBarra(isbn);
            libro.setPortada_url(data.getCoverUrl());
            Usuario usuario = usuarioRepository.findByCorreo(correo);
            if (Objects.nonNull(usuario)) {
                libro.setPropietario(usuario);
            }
            
        } else {
            libro = new Libro();
            libro.setCodigoBarra(isbn);
            libro.setPortada_url(data.getCoverUrl());
            Usuario usuario = usuarioRepository.findByCorreo(correo);
            if (Objects.nonNull(usuario)) {
                libro.setPropietario(usuario);
            }
        }

        if (data != null && data.getTitle() != null && !data.getTitle().isEmpty()) {
            libro.setTitulo(data.getTitle());
        }

        libro.setAutores(autores);

        System.out.println("LIBRO SERVICE: preparando guardar Libro ISBN=" + isbn +
                " con " + autores.size() + " autores.");

        libro = libroRepository.save(libro);
        System.out.println("LIBRO SERVICE: guardado Libro ISBN=" + isbn);

        return libro;
    }

    // Nueva: crear o obtener libro a partir de ISBN (sin caché de sesión)
    @Transactional
    public Libro createOrGetBookFromIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("isbn invalido");
        }
        isbn = isbn.trim();

        Optional<Libro> existing = libroRepository.findById(isbn);
        if (existing.isPresent()) {
            logger.info("LIBRO SERVICE: existing Libro found for ISBN={}", isbn);
            return existing.get();
        }

        BookData data = null;
        try {
            data = openLibraryApiService.fetchBookData(isbn);
        } catch (Exception e) {
            logger.error("LIBRO SERVICE: error fetching data for ISBN={}", isbn, e);
            throw e;
        }

        Libro libro = new Libro();
        libro.setCodigoBarra(isbn);

        Set<Autor> autores = new HashSet<>();
        if (data != null && data.getAuthorNames() != null) {
            for (String authorName : data.getAuthorNames()) {
                if (authorName == null || authorName.trim().isEmpty()) continue;
                Autor autor = obtenerOCrearAutorPorNombre(authorName);
                if (autor != null) {
                    autores.add(autor);
                }
            }
        }

        if (data != null && data.getTitle() != null && !data.getTitle().isEmpty()) {
            libro.setTitulo(data.getTitle());
        }

        libro.setAutores(autores);
        libro = libroRepository.save(libro);
        logger.info("LIBRO SERVICE: guardado Libro ISBN={}", isbn);

        return libro;
    }

    // Nuevo método para borrar un libro registrado por codigoBarra
    public void deleteLibroByCodigoBarra(String codigoBarra) {
        if (codigoBarra == null || codigoBarra.trim().isEmpty()) {
            throw new IllegalArgumentException("codigoBarra invalido");
        }
        String id = codigoBarra.trim();
        Optional<Libro> libroOpt = libroRepository.findById(id);
        if (libroOpt.isPresent()) {
            libroRepository.delete(libroOpt.get());
        } else {
            throw new NoSuchElementException("Libro no encontrado con codigoBarra: " + id);
        }
    }

    public List<BookData> getBookByEmail(String userEmail) {
        return Optional.ofNullable(usuarioRepository.findByCorreo(userEmail))
        .map(usuario -> 
            Optional.ofNullable(libroRepository.findByPropietario(usuario))
                .orElse(Collections.emptyList()) 
                .stream()
                .map(libro -> new BookData(libro.getTitulo(), 
                    Optional.ofNullable(libro.getAutores())
                        .orElse(Collections.emptySet())
                        .stream().map(Autor::getNombre).toList(), 
                    libro.getPortada_url(), 
                    libro.getDescripcion(), 
                    libro.getCodigoBarra()))
                .toList() )
        .orElse(Collections.emptyList());
    }

}

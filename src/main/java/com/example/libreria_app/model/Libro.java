package com.example.libreria_app.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;

@Entity
@Table(name = "libro")
public class Libro implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "codigo_barra")
    private String codigoBarra;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "anio_publicacion")
    private Date anio_publicacion;

    @Column(name = "portada_url")
    private String portada_url;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "propietario_id")
    private Usuario propietario;

    @ManyToMany
    @JoinTable(
        name = "libro_autor",
        joinColumns = @JoinColumn(name = "libro_codigo_barra"),
        inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autores = new HashSet<>();


    public Libro() {
    }

public String getCodigoBarra() {
    return codigoBarra;
}

public void setCodigoBarra(String codigoBarra) {
    this.codigoBarra = codigoBarra;
}

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getAnio_publicacion() {
        return anio_publicacion;
    }

    public void setAnio_publicacion(Date anio_publicacion) {
        this.anio_publicacion = anio_publicacion;
    }

    public String getPortada_url() {
        return portada_url;
    }

    public void setPortada_url(String portada_url) {
        this.portada_url = portada_url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }

    public Set<Autor> getAutores() {
        return autores;
    }

public void setAutores(Set<Autor> autores) {
    this.autores = autores;
}
}

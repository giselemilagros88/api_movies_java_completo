package com.ar.cac.movies;

public class Pelicula {
    private Long idPelicula;
    private String titulo;
    private String duracion;
    private String genero;
    private String imagen;

     // Constructor por defecto
    public Pelicula() {
    }

    // Constructor
    public Pelicula(Long idPelicula, String titulo, String duracion, String genero, String imagen) {
        this.idPelicula = idPelicula;
        this.titulo = titulo;
        this.duracion = duracion;
        this.genero = genero;
        this.imagen = imagen;
    }

    // Getters
    public Long getIdPelicula() {
        return idPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getGenero() {
        return genero;
    }

    public String getImagen() {
        return imagen;
    }

    // Setters
    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    // toString method
    @Override
    public String toString() {
        return "Pelicula{" +
                "idPelicula='" + idPelicula + '\'' +
                ", titulo='" + titulo + '\'' +
                ", duracion='" + duracion + '\'' +
                ", genero='" + genero + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}


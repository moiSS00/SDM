package es.uniovi.eii.favmovies.modelo;


public class RepartoPelicula {

    // Atributos BD
    private int id_actor;
    private int id_pelicula;
    private String nombrePersonaje;


    public RepartoPelicula() {

    }

    public RepartoPelicula(int id_actor, int id_pelicula, String nombrePersonaje) {
        this.id_actor = id_actor;
        this.id_pelicula = id_pelicula;
        this.nombrePersonaje = nombrePersonaje;
    }

    public int getId_actor() {
        return id_actor;
    }

    public void setId_actor(int id_actor) {
        this.id_actor = id_actor;
    }

    public int getId_pelicula() {
        return id_pelicula;
    }

    public void setId_pelicula(int id_pelicula) {
        this.id_pelicula = id_pelicula;
    }

    public String getNombrePersonaje() {
        return nombrePersonaje;
    }

    public void setNombrePersonaje(String nombrePersonaje) {
        this.nombrePersonaje = nombrePersonaje;
    }
}

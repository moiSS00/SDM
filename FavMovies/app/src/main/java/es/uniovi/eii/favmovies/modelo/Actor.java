package es.uniovi.eii.favmovies.modelo;

import android.os.Parcel;
import android.os.Parcelable;

public class Actor implements Parcelable {

    private int id;
    private String nombre;
    private String imagen;
    private String URL_imdb;

    public Actor() {
    }

    public Actor(int id, String nombre, String imagen, String URL_imdb) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.URL_imdb = URL_imdb;
    }

    protected Actor(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        imagen = in.readString();
        URL_imdb = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getURL_imdb() {
        return URL_imdb;
    }

    public void setURL_imdb(String URL_imdb) {
        this.URL_imdb = URL_imdb;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", imagen='" + imagen + '\'' +
                ", URL_imdb='" + URL_imdb + '\'' +
                '}';
    }

    public static final Creator<Actor> CREATOR = new Creator<Actor>() {
        @Override
        public Actor createFromParcel(Parcel in) {
            return new Actor(in);
        }

        @Override
        public Actor[] newArray(int size) {
            return new Actor[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nombre);
        parcel.writeString(imagen);
        parcel.writeString(URL_imdb);
    }
}

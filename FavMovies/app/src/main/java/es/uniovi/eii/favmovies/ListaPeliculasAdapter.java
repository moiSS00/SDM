package es.uniovi.eii.favmovies;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.uniovi.eii.favmovies.modelo.Pelicula;

public class ListaPeliculasAdapter extends RecyclerView.Adapter<ListaPeliculasAdapter.PeliculasWiewHolder> {

    // Atributos auxiliares
    private final OnItemClickListener listener;
    private List<Pelicula> listaPeliculas;

    public ListaPeliculasAdapter(List<Pelicula> listaPeliculas, OnItemClickListener listener) {
        this.listaPeliculas = listaPeliculas;
        this.listener = listener;
    }

    /**
     * Interfaz para menjar el evento click
     */
    public interface OnItemClickListener {
        void onItemClick(Pelicula item);
    }

    // --- Métodos que hay que sobrescribir al heredar de  RecyclerView.Adapter ---

    @NonNull
    @Override
    public PeliculasWiewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linea_recycler_view_pelicula, parent, false);
        return new PeliculasWiewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PeliculasWiewHolder holder, int position) {
        // Extrae de la lista el elemento indicando por posición
        Pelicula pelicula = listaPeliculas.get(position);
        Log.i("Lista", "Visualiza elemento " + pelicula);

        // llama al método de nuestro holder para asignar valores a los componentes
        // además, pasamos el listener del evento onClick
        holder.bindUser(pelicula, listener);
    }

    @Override
    public int getItemCount() {
        return listaPeliculas.size();
    }

    /**
     * Clase interna que define los componentes de la vista
     */
    public static class PeliculasWiewHolder extends RecyclerView.ViewHolder {

        // Atribitos que contendrán una referencia a los componentes usados
        private TextView titulo;
        private TextView fecha;
        private ImageView imagen;

        public PeliculasWiewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.titulopeli);
            fecha = (TextView) itemView.findViewById(R.id.fechaestreno);
            imagen = (ImageView) itemView.findViewById(R.id.imagen);
        }

        public void bindUser(final Pelicula pelicula, final OnItemClickListener listener) {
            titulo.setText(pelicula.getTitulo() + " " + pelicula.getFecha());
            fecha.setText(pelicula.getCategoria().getNombre());
            itemView.setOnClickListener((v) -> {
                listener.onItemClick(pelicula);
            });
        }

    }
}

package es.uniovi.eii.favmovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import es.uniovi.eii.favmovies.R;
import es.uniovi.eii.favmovies.modelo.Actor;

public class ListaActoresAdapter extends RecyclerView.Adapter<ListaActoresAdapter.ActoresWiewHolder>{

    // Atributos auxiliares
    private final OnItemClickListener listener;
    private List<Actor> listaActores;

    public ListaActoresAdapter(List<Actor> listaActores, OnItemClickListener listener) {
        this.listener = listener;
        this.listaActores = listaActores;
    }

    /**
     * Interfaz para menjar el evento click
     */
    public interface OnItemClickListener {
        void onItemClick(Actor item);
    }

    @NonNull
    @Override
    public ActoresWiewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linea_recycler_view_actor, parent, false);
        return new ListaActoresAdapter.ActoresWiewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ActoresWiewHolder holder, int position) {
        // Extrae de la lista el elemento indicando por posición
        Actor actor = listaActores.get(position);

        // llama al método de nuestro holder para asignar valores a los componentes
        // además, pasamos el listener del evento onClick
        holder.bindUser(actor, listener);
    }

    @Override
    public int getItemCount() {
        return listaActores.size();
    }

    /**
     * Clase interna que define los componentes de la vista
     */
    public static class ActoresWiewHolder extends RecyclerView.ViewHolder {

        // Atribitos que contendrán una referencia a los componentes usados
        private TextView nombre;
        private TextView personaje;
        private ImageView imagen;

        public ActoresWiewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.nombre_actor);
            personaje = (TextView) itemView.findViewById(R.id.nombre_personaje);
            imagen = (ImageView) itemView.findViewById(R.id.imagen_actor);
        }

        public void bindUser(final Actor actor, final OnItemClickListener listener) {
            nombre.setText(actor.getNombre());
            personaje.setText(actor.getPersonaje());
            Picasso.get().load(actor.getImagen()).into(imagen);
            itemView.setOnClickListener((v) -> {
                listener.onItemClick(actor);
            });
        }

    }
}

package es.uniovi.eii.favmovies.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import es.uniovi.eii.favmovies.R;
import es.uniovi.eii.favmovies.adapters.ListaActoresAdapter;
import es.uniovi.eii.favmovies.datos.ActoresDataSource;
import es.uniovi.eii.favmovies.modelo.Actor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActoresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActoresFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ID_PELICULA = "id_pelicula";

    // TODO: Rename and change types of parameters
    private int id_pelicula;

    public ActoresFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id_pelicula Parameter 1.
     * @return A new instance of fragment ActoresFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActoresFragment newInstance(int id_pelicula) {
        ActoresFragment fragment = new ActoresFragment();
        Bundle args = new Bundle();
        args.putInt(ID_PELICULA, id_pelicula);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id_pelicula = getArguments().getInt(ID_PELICULA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_actores, container, false);

        // Obtenemos los actores
        ActoresDataSource actoresDataSource = new ActoresDataSource(getContext());
        actoresDataSource.open();
        List<Actor> actores = actoresDataSource.actoresParticipantes(id_pelicula);
        actoresDataSource.close();

        // Tratamos recyclerView
        RecyclerView listaActoresView = (RecyclerView) root.findViewById(R.id.actoresRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        listaActoresView.setHasFixedSize(true);
        listaActoresView.setLayoutManager(layoutManager);

        // Creamos el adapter
        ListaActoresAdapter laAdater = new ListaActoresAdapter(actores,
                new ListaActoresAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Actor actor) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(actor.getURL_imdb())));
                    }
                });

        // Asignamos el adapter creado
        listaActoresView.setAdapter(laAdater);

        return root;
    }
}
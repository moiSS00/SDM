package es.uniovi.eii.favmovies.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import es.uniovi.eii.favmovies.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PELICULA_ESTRENO = "pelicula_estreno";
    private static final String PELICULA_DURACION = "pelicula_duracion";
    private static final String PELICULA_CARATULA= "pelicula_caratula";

    // TODO: Rename and change types of parameters
    private String estreno;
    private String duracion;
    private String caratula;

    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param estreno Parameter 1.
     * @param duracion Parameter 2.
     * @param caratula Parameter 3.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String estreno, String duracion, String caratula) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putString(PELICULA_ESTRENO, estreno);
        args.putString(PELICULA_DURACION, duracion);
        args.putString(PELICULA_CARATULA, caratula);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            estreno = getArguments().getString(PELICULA_ESTRENO);
            duracion = getArguments().getString(PELICULA_DURACION);
            caratula = getArguments().getString(PELICULA_CARATULA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        // Recogemos elementos del fragmento
        View root = inflater.inflate(R.layout.fragment_info, container, false);
        TextView testreno = (TextView) root.findViewById(R.id.estrenoPelicula);
        TextView tduracion = (TextView) root.findViewById(R.id.duracionPelicula);
        ImageView caratulaimg = (ImageView) root.findViewById(R.id.caratulaPelicula);

        // Asignamos valores
        testreno.setText(estreno);
        tduracion.setText(duracion);
        Picasso.get()
                .load(caratula).into(caratulaimg);

        return root;
    }
}
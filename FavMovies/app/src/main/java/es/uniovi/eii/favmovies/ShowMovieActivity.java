package es.uniovi.eii.favmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import es.uniovi.eii.favmovies.databinding.ActivityShowMovieBinding;
import es.uniovi.eii.favmovies.modelo.Categoria;
import es.uniovi.eii.favmovies.modelo.Pelicula;

public class ShowMovieActivity extends AppCompatActivity {

    // Atribitos que contendrán una referencia a los componentes usados
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private CollapsingToolbarLayout toolBarLayout;

    private TextView categoria;
    private TextView estreno;
    private TextView duracion;
    private TextView argumento;
    private ImageView imagenCaratula;
    private ImageView imagenFondo;

    // Atributos auxiliares
    private ActivityShowMovieBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // -- Código de configuración autogenerado por Android Studio al crear una ScrollActivity--
        binding = ActivityShowMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtenemos referencias a los componentes
        categoria = (TextView) findViewById(R.id.categoria);
        estreno = (TextView) findViewById(R.id.estreno);
        duracion = (TextView) findViewById(R.id.duracion);
        argumento = (TextView) findViewById(R.id.argumento);
        imagenCaratula = (ImageView) findViewById(R.id.caratula);
        imagenFondo = (ImageView) findViewById(R.id.imagenFondo);

        toolBarLayout = binding.toolbarLayout;
        toolbar = binding.toolbar;
        fab = binding.fab;

        // -- Código de configuración autogenerado por Android Studio al crear una ScrollActivity--
        setSupportActionBar(toolbar);

        // Recepción de datos
        Intent intentPeli = getIntent();
        Pelicula pelicula = intentPeli.getParcelableExtra(MainRecycler.PELICULA_SELECCIONADA);
        if (pelicula != null)
            abrirModoConsulta(pelicula);

        // Asignamos listeners

        // --- Acción para el botón fab (muestra el trailer de la película actual) ---
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pelicula != null) // Mostramos el trailer de la película
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(pelicula.getUrlTrailer())));;
            }
        });
    }

    /**
     * Muestra la información de una película en concreto
     * @param pelicula Película concreta de la que queremos saber
     */
    private void abrirModoConsulta(Pelicula pelicula) {
        if (!pelicula.getTitulo().isEmpty()) {

            // Cargamos las imagenes de la película usando la librería Picasso
            Picasso.get().load(pelicula.getUrlCaratula()).into(imagenCaratula);
            Picasso.get().load(pelicula.getUrlFondo()).into(imagenFondo);

            // Actualizar componentes con valores de la película específica
            toolBarLayout.setTitle(pelicula.getTitulo() + " (" + pelicula.getFecha() + ")");
            categoria.setText(pelicula.getCategoria().getNombre());
            estreno.setText(pelicula.getFecha());
            duracion.setText(pelicula.getDuracion());
            argumento.setText(pelicula.getArgumento());
        }
    }
}
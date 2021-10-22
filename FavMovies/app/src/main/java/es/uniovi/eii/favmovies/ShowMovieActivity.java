package es.uniovi.eii.favmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import es.uniovi.eii.favmovies.databinding.ActivityShowMovieBinding;
import es.uniovi.eii.favmovies.modelo.Pelicula;
import es.uniovi.eii.favmovies.ui.InfoFragment;
import es.uniovi.eii.favmovies.util.Conexion;

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
    private Pelicula pelicula;

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

        // Gestión de la botonera
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // -- Código de configuración autogenerado por Android Studio al crear una ScrollActivity--
        setSupportActionBar(toolbar);

        // Recepción de datos
        Intent intentPeli = getIntent();
        pelicula = intentPeli.getParcelableExtra(MainRecycler.PELICULA_SELECCIONADA);
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

        // --- Añado un listener creado aparte a la botonera ---
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * Listener creado aparte para asignar información en función de la pestaña seleccionada
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (pelicula != null) {
                switch (item.getItemId()) {
                    case R.id.navigation_info:
                        // Creamos el fragmento de información
                        InfoFragment info = new InfoFragment() // Paso de información
                                .newInstance(pelicula.getFecha(), pelicula.getDuracion(), pelicula.getUrlCaratula());
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, info).commit();
                        return true;
                }
            }
            return false;
        }
    };

//    /**
//     * Muestra la información de una película en concreto
//     * @param pelicula Película concreta de la que queremos saber
//     */
//    private void abrirModoConsulta(Pelicula pelicula) {
//        if (!pelicula.getTitulo().isEmpty()) {
//
//            // Cargamos las imagenes de la película usando la librería Picasso
//            Picasso.get().load(pelicula.getUrlCaratula()).into(imagenCaratula);
//            Picasso.get().load(pelicula.getUrlFondo()).into(imagenFondo);
//
//            // Actualizar componentes con valores de la película específica
//            toolBarLayout.setTitle(pelicula.getTitulo() + " (" + pelicula.getFecha() + ")");
//            categoria.setText(pelicula.getCategoria().getNombre());
//            estreno.setText(pelicula.getFecha());
//            duracion.setText(pelicula.getDuracion());
//            argumento.setText(pelicula.getArgumento());
//        }
//    }

    /**
     * Muestra la información de una película en concreto
     * @param pelicula Película concreta de la que queremos saber
     */
    private void abrirModoConsulta(Pelicula pelicula) {
        if (!pelicula.getTitulo().isEmpty()) {

            // Información base
            toolBarLayout.setTitle(pelicula.getTitulo() + " (" + pelicula.getFecha() + ")");
            Picasso.get().load(pelicula.getUrlFondo()).into(imagenFondo);

            // Cargar el fragment de INFO por defecto
            InfoFragment info = new InfoFragment()
                    .newInstance(pelicula.getFecha(), pelicula.getDuracion(), pelicula.getUrlCaratula());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, info).commit();
        }
    }

    // Métodos necesarios para trabajar con el menú que permite compartir la película. El
    // activity los ejecuta automáticamente

    // ------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share_movie, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.compartir) { // Asigamos acción al botón de compartir
            Conexion conexion = new Conexion(getApplicationContext());
            if (conexion.compruebaConexion()) {
                compartirPeli();
            }
            else {
                Toast.makeText(getApplicationContext(), R.string.error_conexion, Toast.LENGTH_LONG).show();
            }
        }

        return  super.onOptionsItemSelected(item);
    }

    // ------------------------------------------------------------------------------------

    /**
     * Método que permite compartir una película
     */
    private void compartirPeli() {
        // es necesario un intent con la cte
        Intent itSend = new Intent(Intent.ACTION_SEND);

        // Vamos a enviar texto plano
        itSend.setType("text/plain");

        itSend.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.subject_compartir) + ": " + pelicula.getTitulo());
        itSend.putExtra(Intent.EXTRA_TEXT, getString(R.string.titulo)
                + ": " + pelicula.getTitulo() + "\n"
                + ": " + pelicula.getArgumento());

        // Iniciamos la actividad
        /* Puede haber mas de una aplicacion a la que hacer un ACTION_SEND,
         * nos sale una venana que nos permite elegir una */
        Intent shareIntent = Intent.createChooser(itSend, null);
        startActivity(shareIntent);
    }

}
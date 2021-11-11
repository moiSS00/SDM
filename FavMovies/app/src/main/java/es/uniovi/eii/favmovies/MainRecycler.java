package es.uniovi.eii.favmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.favmovies.adapters.ListaPeliculasAdapter;
import es.uniovi.eii.favmovies.datos.ActoresDataSource;
import es.uniovi.eii.favmovies.datos.PeliculasDataSource;
import es.uniovi.eii.favmovies.datos.RepartoPeliculaDataSource;
import es.uniovi.eii.favmovies.modelo.Actor;
import es.uniovi.eii.favmovies.modelo.Categoria;
import es.uniovi.eii.favmovies.modelo.Pelicula;
import es.uniovi.eii.favmovies.modelo.RepartoPelicula;

public class MainRecycler extends AppCompatActivity {

    // Constantes para la navegación
    public static final String PELICULA_SELECCIONADA = "pelicula_seleccionada";

    // Atribitos que contendrán una referencia a los componentes usados
    private RecyclerView listaPeliView;

    // Atributos auxiliares
    private List<Pelicula> listaPeli;
    public static String filtroCategoria = null;
    private boolean filtrado = false;

    // Para interactuar con base de datos
    PeliculasDataSource peliculasDataSource = new PeliculasDataSource(this);
    ActoresDataSource actoresDataSource = new ActoresDataSource(this);
    RepartoPeliculaDataSource repartoPeliculaDataSource = new RepartoPeliculaDataSource(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycler);

        // Recogemos la SharedPreference para hacer el filtro establecido
        SharedPreferences sharedPreferencesMainRecycler =
                PreferenceManager.getDefaultSharedPreferences(this);
        filtroCategoria = sharedPreferencesMainRecycler.getString("keyCategoria", "");
    }

    /**
     * Cuando se inicia / vuelve a la activity se cargaran las películas con
     * el filtro aplicado
     */
    @Override
    protected void onResume() {
        super.onResume();

        // --- Inicializa el modelo de datos ---

        // Cargamos las películas con o son filtro
        //if (filtroCategoria == null || filtroCategoria == "") {
        //    rellenarLista();
        //} else {
        //    rellenarLista(filtroCategoria);
        //}


        // Cargamos la base de datos
        cargarPeliculas();
        cargarActores();
        cargarRepartos();

        // Obtenemos todas las películas
        peliculasDataSource.open();
        listaPeli = peliculasDataSource.getAllValorations();
        peliculasDataSource.close();

        // Obtenemos referencias a los componentes
        listaPeliView = (RecyclerView) findViewById(R.id.peliculasRecyclerView);

        // Configuramos el RecyclerView con la lista de películas
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listaPeliView.setHasFixedSize(true);
        listaPeliView.setLayoutManager(layoutManager);

        // Creamos el adapter
        ListaPeliculasAdapter lpAdater = new ListaPeliculasAdapter(listaPeli,
                new ListaPeliculasAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Pelicula peli) {
                        clickonItem(peli);
                    }
                });

        // Asignamos el adapter creado
        listaPeliView.setAdapter(lpAdater);
    }

    // --- Métodos para obtener datos directamente desde un archivo local ---

    /**
     * Rellenamos la lista de películas a partir de un fichero con datos de ejemplo
     * aplicando un filtro
     */
    private void rellenarLista(String filtro) {
        listaPeli = new ArrayList<Pelicula>();
        Pelicula peli = null;

        InputStream file = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;

        try {
            // Abrimos directamente el fichero tomando como referencia la carpeta "assets"
            file = getAssets().open("lista_peliculas_url_utf8.csv");

            reader = new InputStreamReader(file);
            bufferedReader = new BufferedReader(reader);

            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(";"); // Separamos los campos de cada línea
                if (data != null && data.length >= 5) {
                    if (data[2].equals(filtro)) {
                        if (data.length == 8) {
                            peli = new Pelicula(data[0], data[1], new Categoria(data[2], ""),
                                    data[3], data[4], data[5], data[6], data[7]);
                        } else {
                            peli = new Pelicula(data[0], data[1], new Categoria(data[2], ""),
                                    data[3], data[4], "", "", ""); // Se deben poner urls por defecto
                        }
                        Log.d("cargarPeliculas", peli.toString());
                        listaPeli.add(peli);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Rellenamos la lista de películas a partir de un fichero con datos de ejemplo
     */
    private void rellenarLista() {
        listaPeli = new ArrayList<Pelicula>();
        Pelicula peli = null;

        InputStream file = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;

        try {
            // Abrimos directamente el fichero tomando como referencia la carpeta "assets"
            file = getAssets().open("lista_peliculas_url_utf8.csv");

            reader = new InputStreamReader(file);
            bufferedReader = new BufferedReader(reader);

            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(";"); // Separamos los campos de cada línea
                if (data != null && data.length >= 5) {
                    if (data.length == 8) {
                        peli = new Pelicula(data[0], data[1], new Categoria(data[2], ""),
                                data[3], data[4], data[5], data[6], data[7]);
                    } else {
                        peli = new Pelicula(data[0], data[1], new Categoria(data[2], ""),
                                data[3], data[4], null, null, null); // Se deben poner urls por defecto
                    }
                }
                Log.d("cargarPeliculas", peli.toString());
                listaPeli.add(peli);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    /**
//     * Rellenamos la lista de películas de forma directa con datos de ejemplo
//     */
//    private void rellenarLista() {
//        listaPeli = new ArrayList<Pelicula>();
//        Categoria catAccion = new Categoria("Acción", "Películas de acción");
//        Categoria catDrama = new Categoria("Dramáticas", "Películas dramáticas");
//        Pelicula peli1 = new Pelicula("Tenet", "Una acción épica que gira en torno...",
//                catAccion, "150", "7/08/2020");
//        Pelicula peli2 = new Pelicula("Shreck", "Película de acción...",
//                catDrama, "150", "12/08/2020");
//        listaPeli.add(peli1);
//        listaPeli.add(peli2);
//    }

    // --- Métodos para cargar datos en la base de datos ---

    private void cargarPeliculas() {
        Pelicula peli = null;

        InputStream file = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;

        try {
            // Abrimos directamente el fichero tomando como referencia la carpeta "assets"
            file = getAssets().open("peliculas.csv");

            reader = new InputStreamReader(file);
            bufferedReader = new BufferedReader(reader);

            String line = null;

            peliculasDataSource.open();

            // Esquivamos línea inicial
            bufferedReader.readLine();

            while((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(";"); // Separamos los campos de cada línea
                if (data != null && data.length == 9) {
                    peli = new Pelicula(
                            Integer.parseInt(data[0]),
                            data[1],
                            data[2],
                            new Categoria(data[3], ""),
                            data[4],
                            data[5],
                            data[6],
                            data[7],
                            data[8]);

                    Log.d("cargarPeliculas", peli.toString());
                    peliculasDataSource.createpelicula(peli);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                    peliculasDataSource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void cargarActores() {
        Actor actor = null;

        InputStream file = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;

        try {
            // Abrimos directamente el fichero tomando como referencia la carpeta "assets"
            file = getAssets().open("reparto.csv");

            reader = new InputStreamReader(file);
            bufferedReader = new BufferedReader(reader);

            String line = null;

            actoresDataSource.open();

            // Esquivamos línea inicial
            bufferedReader.readLine();

            while((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(";"); // Separamos los campos de cada línea
                if (data != null && data.length == 4) {
                    actor = new Actor(
                            Integer.parseInt(data[0]),
                            data[1],
                            data[2],
                            data[3]);

                    Log.d("cargarActores", actor.toString());
                    actoresDataSource.createactor(actor);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                    actoresDataSource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void cargarRepartos() {
        RepartoPelicula repartoPelicula = null;

        InputStream file = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;

        try {
            // Abrimos directamente el fichero tomando como referencia la carpeta "assets"
            file = getAssets().open("peliculas-reparto.csv");

            reader = new InputStreamReader(file);
            bufferedReader = new BufferedReader(reader);

            String line = null;

            repartoPeliculaDataSource.open();

            // Esquivamos línea inicial
            bufferedReader.readLine();

            while((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(";"); // Separamos los campos de cada línea
                if (data != null && data.length == 9) {
                    repartoPelicula = new RepartoPelicula(
                            Integer.parseInt(data[0]),
                            Integer.parseInt(data[1]),
                            data[2]);

                    Log.d("cargarRepartoPeliculas", repartoPelicula.toString());
                    repartoPeliculaDataSource.createrepartoPelicula(repartoPelicula);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                    repartoPeliculaDataSource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Click del item del adapter
     * @param peli Película a la que se ha dado click
     */
    public void clickonItem(Pelicula peli) {
        Log.i("Click adpater", "Item clicked " + peli.getCategoria().getNombre());
        // Paso al modo de apertura
        Intent intent = new Intent(MainRecycler.this, ShowMovieActivity.class);
        intent.putExtra(PELICULA_SELECCIONADA, peli);

        // Poner transiciones
        // startActivity(intent);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    // --- Gestión del menú ---

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            Intent intentSettingsActivity = new Intent(MainRecycler.this, SettingsActivity.class);
            startActivity(intentSettingsActivity);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
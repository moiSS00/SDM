package es.uniovi.eii.favmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.favmovies.modelo.Categoria;
import es.uniovi.eii.favmovies.modelo.Pelicula;

public class MainRecycler extends AppCompatActivity {

    // Constantes para la navegación
    public static final String PELICULA_SELECCIONADA = "pelicula_seleccionada";

    // Atribitos que contendrán una referencia a los componentes usados
    private RecyclerView listaPeliView;

    // Atributos auxiliares
    private List<Pelicula> listaPeli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycler);

        // Inicializa el modelo de datos
        rellenarLista();

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

    /**
     * Rellenamos la lista de películas de forma directa con datos de ejemplo
     */
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

    /**
     * Click del item del adapter
     * @param peli Película a la que se ha dado click
     */
    public void clickonItem(Pelicula peli) {
        Log.i("Click adpater", "Item clicked " + peli.getCategoria().getNombre());
        // Paso al modo de apertura
        Intent intent = new Intent(MainRecycler.this, MainActivity.class);
        intent.putExtra(PELICULA_SELECCIONADA, peli);
        startActivity(intent);
    }

}
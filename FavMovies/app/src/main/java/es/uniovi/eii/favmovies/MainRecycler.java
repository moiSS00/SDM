package es.uniovi.eii.favmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
     * Rellenamos la lista de películas con datos de ejemplo
     */
    private void rellenarLista() {
        listaPeli = new ArrayList<Pelicula>();
        Categoria catAccion = new Categoria("Acción", "Películas de acción");
        Categoria catDrama = new Categoria("Dramáticas", "Películas dramáticas");
        Pelicula peli1 = new Pelicula("Tenet", "Una acción épica que gira en torno...",
                catAccion, "150", "7/08/2020");
        Pelicula peli2 = new Pelicula("Shreck", "Película de acción...",
                catDrama, "150", "12/08/2020");
        listaPeli.add(peli1);
        listaPeli.add(peli2);
    }

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
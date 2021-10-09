package es.uniovi.eii.favmovies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.favmovies.modelo.Categoria;

public class MainActivity extends AppCompatActivity {

    // Constantes para la navegación entre categorías
    public static final String POS_CATEGORIA_SELECCIONADA = "pos_categoria_seleccionada";
    public static final String CATEGORIA_SELECCIONADA = "categoria_seleccionada";
    public static final String CATEGORIA_MODIFICADA = "categoria_modificada";
    public static final int GESTION_CATEGORIA = 1;

    // Atribitos que contendrán una referencia a los componentes usados
    private Button guardarCategoriaBtn;
    private ImageButton editarCategoriaBtn;
    private Spinner categoriaSpinner;
    private EditText editTitulo;
    private EditText editArgumento;
    private EditText editDuracion;
    private EditText editFecha;

    // Atributos auxiliares
    private boolean creandoCategoria;
    private List<Categoria> listaCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa el modelo de datos
        listaCategorias = new ArrayList<Categoria>();
        listaCategorias.add(new Categoria("Acción", "Películas de acción"));
        listaCategorias.add(new Categoria("Aventuras", "Películas de aventuras"));
        listaCategorias.add(new Categoria("Dramáticas", "Películas dramáticas"));
        listaCategorias.add(new Categoria("De terror", "Películas de terror"));
        listaCategorias.add(new Categoria("Musicales", "Películas de música"));
        listaCategorias.add(new Categoria("Ciencia Ficción", "Películas de ciencia ficción"));
        listaCategorias.add(new Categoria("De guerra", "Películas de guerra"));
        listaCategorias.add(new Categoria("Comedias", "Películas de comedia"));
        listaCategorias.add(new Categoria("Románticas", "Películas de romance"));

        // Obtenemos referencias a los componentes
        guardarCategoriaBtn = (Button) findViewById(R.id.guardarCategoriaBtn);
        editarCategoriaBtn = (ImageButton) findViewById(R.id.editarCategoriaBtn);
        categoriaSpinner = (Spinner) findViewById(R.id.categoriaSpinner);
        editTitulo = (EditText) findViewById(R.id.tituloEdit);
        editArgumento = (EditText) findViewById(R.id.argumentoEdit);
        editDuracion = (EditText) findViewById(R.id.duracionEdit);
        editFecha = (EditText) findViewById(R.id.fechaEdit);

        // Damos valores al spinner que muestra las categorías
        introListaSpinner(categoriaSpinner, listaCategorias);

        // Asignamos listeners

        // --- Se validan los campos al dar al botón de guardar película ---
        guardarCategoriaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validamos los campos
                if (editTitulo.getText().toString().isEmpty()) {
                    Snackbar.make(findViewById(R.id.layoutPrincipal), R.string.msg_titulo_vacio,
                            Snackbar.LENGTH_SHORT)
                            .show(); // Para mostrar mensajes emergentes
                }
                else if (editArgumento.getText().toString().isEmpty()) {
                    Snackbar.make(findViewById(R.id.layoutPrincipal), R.string.msg_argumento_vacio,
                            Snackbar.LENGTH_SHORT)
                            .show();
                }
                else if (editDuracion.getText().toString().isEmpty()) {
                    Snackbar.make(findViewById(R.id.layoutPrincipal), R.string.msg_duracion_vacio,
                            Snackbar.LENGTH_SHORT)
                            .show();
                }
                else if (editFecha.getText().toString().isEmpty()) {
                    Snackbar.make(findViewById(R.id.layoutPrincipal), R.string.msg_fecha_vacio,
                            Snackbar.LENGTH_SHORT)
                            .show();
                }
                else {
                    Snackbar.make(findViewById(R.id.layoutPrincipal), R.string.msg_guardado,
                            Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });

        // --- Al pulsar el botón de editar, cambiamos de activity ---
        editarCategoriaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // En función de lo seleccionado en el spinner, se muestra un mensaje u otro
                Snackbar mensaje;
                if (categoriaSpinner.getSelectedItemPosition()==0) { // La categoría es "Sin definir"
                    mensaje = Snackbar.make(findViewById(R.id.layoutPrincipal), R.string.msg_crear_nueva_categoria,
                            Snackbar.LENGTH_SHORT);
                } else {
                    mensaje = Snackbar.make(findViewById(R.id.layoutPrincipal), R.string.msg_modif_categoria,
                            Snackbar.LENGTH_SHORT);
                }

                // Hacemos que el mensaje tenga la opción de aceptar
                mensaje.setAction(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(findViewById(R.id.layoutPrincipal), R.string.msg_accion_ok,
                                Snackbar.LENGTH_SHORT).show();
                        modificarCategoria();
                    }
                });

                // Hacemos que el mensaje tenga la opción de cancelación
//                mensaje.setAction(android.R.string.cancel, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Snackbar.make(findViewById(R.id.layoutPrincipal), R.string.msg_accion_cancelada,
//                                Snackbar.LENGTH_SHORT).show();
//
//                    }
//                });

                // Mostramos el mensaje
                mensaje.show();
            }
        });
    }

    /**
     * Método auxiliar para navegar hasta la activity de creación / modificación de categorías
     */
    private void modificarCategoria() {
        Intent categoriaIntent = new Intent(MainActivity.this, CategoriaActivity.class);

        // Lanza activity categoria sin pasarle ninguna categoria
        // startActivity(categoriaIntent);

        // Preparamos los valores para pasar a la activity de categoria
        // -- Metemos el índice de la categoría seleccionada --
        categoriaIntent.putExtra(POS_CATEGORIA_SELECCIONADA, categoriaSpinner.getSelectedItemPosition());

        // -- Si se seleccionó "Sin definir", no se pasan los datos de la categoría --
        creandoCategoria = true;

        // -- Si no se seleccionó "Sin definir", se pasan los datos de la categoría --
        if (categoriaSpinner.getSelectedItemPosition() > 0) {
            creandoCategoria = false;
            categoriaIntent.putExtra(CATEGORIA_SELECCIONADA,listaCategorias.get(categoriaSpinner.getSelectedItemPosition() - 1));
        }

        // lanzamos activity para gestionar categoria esperando por un resultado
        startActivityForResult(categoriaIntent, GESTION_CATEGORIA);
    }

    /**
     * Introduce los nombres de una lista de categorías dada en un spinner dado
     */
    private void introListaSpinner(Spinner spinner, List<Categoria> listaCategorias) {
        // -- Creamos un array sólo con los nombres de las categorías --
        ArrayList<String> nombres = new ArrayList<String>();
        nombres.add("Sin definir");
        for (Categoria elemento : listaCategorias) {
            nombres.add(elemento.getNombre());
        }

        // -- Crea un ArrayAdapter usando el array de strings de nombres y el layout por defecto del spinner --
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, nombres);

        // -- Especifica el layout para usar cuando aparece la lista de elecciones --
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // -- Asignamos el adapter al spinner --
        spinner.setAdapter(adapter);
    }

    /**
     * Cuando se vuelve a esta activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Comprobamos que peticion se esta respondiendo
        if (requestCode == GESTION_CATEGORIA) {

            // Nos aseguramos que el resultado fue OK
            if (resultCode == RESULT_OK) {
                Categoria cateAux = data.getParcelableExtra(CATEGORIA_MODIFICADA);
                Log.d("FavMovies.MainActivity", cateAux.toString());
                if (creandoCategoria) {  // añadimos categoria a la lista (Categoría nueva)
                    listaCategorias.add(cateAux);
                    introListaSpinner(categoriaSpinner, listaCategorias);
                }
                else { // Modificando una categoría existente
                    // busca la categoria del mismo nombre en la lista y cambia la descripcion
                    for (Categoria cat : listaCategorias) {
                        if (cat.getNombre().equals(cateAux.getNombre())) {
                            cat.setDescripcion(cateAux.getDescripcion());
                            Log.d("FavMovie.MainActivity", "Modificada la descripción de: " + cat.getNombre());
                            break;
                        }
                    }
                }
            }
            else if (resultCode == RESULT_CANCELED) { // El resultado es cancelado
                Log.d("FavMovie.MainActivity", "CategoriaActivity cancelada");
            }
        }
    }
}
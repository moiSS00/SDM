package es.uniovi.eii.favmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import es.uniovi.eii.favmovies.modelo.Categoria;

public class CategoriaActivity extends AppCompatActivity {

    // Atribitos que contendrán una referencia a los componentes usados
    private Button btnOK;
    private Button btnCancel;
    private EditText editNombreCategoria;
    private EditText editDescripcionCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        // Obtenemos referencias a los componentes
        btnOK = (Button) findViewById(R.id.okCategoriaBtn);
        btnCancel = (Button) findViewById(R.id.cancelCategoriaBtn);
        editNombreCategoria = (EditText) findViewById(R.id.nombreCategoriaEdit);
        editDescripcionCategoria = (EditText) findViewById(R.id.descripcionCategoriaEdit);

        // Recepción de datos
        Intent intent = getIntent();
        int posCategoria = intent.getIntExtra(MainActivity.POS_CATEGORIA_SELECCIONADA, 0);
        Categoria categEntrada = null;
        if (posCategoria > 0) {
            categEntrada = intent.getParcelableExtra(MainActivity.CATEGORIA_SELECCIONADA);
            // Si la categoría ya existía, ponemos la información base
            editNombreCategoria.setText(categEntrada.getNombre());
            editDescripcionCategoria.setText(categEntrada.getDescripcion());
            editNombreCategoria.setEnabled(false); // No se modifica nombre de categoría existente
        }

        // Asignamos listeners

        // --- Crea / modifica la categoría ---
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creamos la categoría
                Categoria categoria = new Categoria(editNombreCategoria.getText().toString(),
                        editDescripcionCategoria.getText().toString());

                // Pasamos la categoría a la activity principal
                Intent mainIntent = new Intent(CategoriaActivity.this, MainActivity.class);
                mainIntent.putExtra(MainActivity.CATEGORIA_MODIFICADA, categoria);

                // Volvemos a la activity principal con éxito
                setResult(RESULT_OK, mainIntent);
                finish();
            }
        });

        // --- Cancela la operación ---
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Volvemos a la activity principal sin éxito
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
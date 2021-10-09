package es.uniovi.eii.favmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    // Atribitos que contendrán una referencia a los componentes usados
    private Button guardarCategoriaBtn;
    private EditText editTitulo;
    private EditText editArgumento;
    private EditText editDuracion;
    private EditText editFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtenemos referencias a los componentes
        guardarCategoriaBtn = (Button) findViewById(R.id.guardarCategoriaBtn);
        editTitulo = (EditText) findViewById(R.id.tituloEdit);
        editArgumento = (EditText) findViewById(R.id.argumentoEdit);
        editDuracion = (EditText) findViewById(R.id.duracionEdit);
        editFecha = (EditText) findViewById(R.id.fechaEdit);

        // Asignamos listeners
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

    }
}
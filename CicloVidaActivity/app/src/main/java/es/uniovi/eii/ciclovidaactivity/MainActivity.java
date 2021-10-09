package es.uniovi.eii.ciclovidaactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(), getString(R.string.OnCreate), Toast.LENGTH_SHORT).show(); // mensaje emergente
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), getString(R.string.OnStart), Toast.LENGTH_SHORT).show(); // mensaje emergente
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), getString(R.string.OnStop), Toast.LENGTH_SHORT).show(); // mensaje emergente
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), getString(R.string.OnDestroy), Toast.LENGTH_SHORT).show(); // mensaje emergente
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), getString(R.string.OnPause), Toast.LENGTH_SHORT).show(); // mensaje emergente
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), getString(R.string.OnResume), Toast.LENGTH_SHORT).show(); // mensaje emergente
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(), getString(R.string.OnRestart), Toast.LENGTH_SHORT).show(); // mensaje emergente
    }

}
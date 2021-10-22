package es.uniovi.eii.favmovies.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Conexion {

    private Context nContexto;

    public Conexion(Context nContext) {
        nContexto = nContext;
    }

    /**
     * Método que combrueba si el móvil está actualmente o no conectado a Internet.
     * @return Booleano indicando si se esta o no conectado a Internet.
     */
    public boolean compruebaConexion() {
        boolean conectado = false;

        ConnectivityManager connectivityManager = (ConnectivityManager)
                nContexto.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        conectado = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return conectado;
    }

}

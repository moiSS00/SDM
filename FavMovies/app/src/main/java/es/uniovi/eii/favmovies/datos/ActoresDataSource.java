package es.uniovi.eii.favmovies.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.favmovies.modelo.Actor;

public class ActoresDataSource {

    /**
     * Referencia para manejar la base de datos. Este objeto lo obtenemos a partir de MyDBHelper
     * y nos proporciona metodos para hacer operaciones
     * CRUD (create, read, update and delete)
     */
    private SQLiteDatabase database;
    /**
     * Referencia al helper que se encarga de crear y actualizar la base de datos.
     */
    private MyDBHelper dbHelper;
    /**
     * Columnas de la tabla
     */
    private final String[] allColumns = {MyDBHelper.COLUMNA_ID_PELICULAS, MyDBHelper.COLUMNA_NOMBRE_ACTOR,
            MyDBHelper.COLUMNA_IMAGEN_ACTOR, MyDBHelper.COLUMNA_URL_imdb};

    /**
     * Constructor.
     *
     * @param context
     */
    public ActoresDataSource(Context context) {
        //el último parámetro es la versión
        dbHelper = new MyDBHelper(context, null, null, 1);
    }

    /**
     * Abre una conexion para escritura con la base de datos.
     * Esto lo hace a traves del helper con la llamada a getWritableDatabase. Si la base de
     * datos no esta creada, el helper se encargara de llamar a onCreate
     *
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();

    }

    /**
     * Cierra la conexion con la base de datos
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Recibe el actor y crea el registro en la base de datos.
     * @param actorToInsert
     * @return
     */
    public long createactor(Actor actorToInsert) {
        // Establecemos los valores que se insertaran
        ContentValues values = new ContentValues();

        values.put(MyDBHelper.COLUMNA_ID_PELICULAS, actorToInsert.getId());
        values.put(MyDBHelper.COLUMNA_NOMBRE_ACTOR, actorToInsert.getNombre());
        values.put(MyDBHelper.COLUMNA_IMAGEN_ACTOR, actorToInsert.getImagen());
        values.put(MyDBHelper.COLUMNA_URL_imdb, actorToInsert.getURL_imdb());

        // Insertamos la valoracion
        long insertId =
                database.insert(MyDBHelper.TABLA_PELICULAS, null, values);

        return insertId;
    }

    /**
     * Obtiene todas las valoraciones andadidas por los usuarios.
     *
     * @return Lista de objetos de tipo Actor
     */
    public List<Actor> getAllValorations() {
        // Lista que almacenara el resultado
        List<Actor> actorList = new ArrayList<Actor>();
        //hacemos una query porque queremos devolver un cursor

        Cursor cursor = database.query(MyDBHelper.TABLA_PELICULAS, allColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final Actor actor = new Actor();
            cursor.getInt(0);
            actor.setId(cursor.getInt(0));
            actor.setNombre(cursor.getString(1));
            actor.setImagen(cursor.getString(2));
            actor.setURL_imdb(cursor.getString(3));

            actorList.add(actor);
            cursor.moveToNext();
        }

        cursor.close();
        // Una vez obtenidos todos los datos y cerrado el cursor, devolvemos la
        // lista.

        return actorList;
    }



}

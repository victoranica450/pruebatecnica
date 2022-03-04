package com.vian.prueba_tecnica.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.vian.prueba_tecnica.Models.Pelicula;

import java.util.ArrayList;

public class DataBaseHandler extends SQLiteOpenHelper {

    //DB version
    private static final int DATABASE_VERSION = 1;

    //DB nombre
    private static final String DATABASE_NOMBRE = "PruebaTecnica";


    //DB tablas
    private static final String TABLE_PELICULA = "tbl_pelicula";
    private static final String TABLE_UBICACION = "tbl_ubicacion";
    private static final String TABLE_IMAGEN = "tbl_imagen";



    //tbl_pelicula

    private static final String ID = "id";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String OVERVIEW = "overview";
    private static final String POPULARITY = "popularity";
    private static final String RELEASE_DATE = "release_date";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String VOTE_COUNT = "vote_count";
    private static final String POSTER_PATH = "poster_path";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String ORIGINAL_LENGUAJE = "original_lenguaje";
    private static final String CATEGORY = "category";

    //tbl_ubicacion
    private static final String LATITUD = "latitud";
    private static final String LONGITUD = "longitud";
    private static final String FECHA_ALMACENAMIENTO = "fecha_almacenamiento";

    //tbl_imagen


    private static final String IMAGEN_GUARDADA_PATH = "imagen_guardada_path";

    public DataBaseHandler(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_PELICULA = "CREATE TABLE " + TABLE_PELICULA + " ("
                + ID + " TEXT,"
                + ORIGINAL_TITLE + " TEXT,"
                + OVERVIEW + " TEXT,"
                + POPULARITY + " TEXT,"
                + RELEASE_DATE + " TEXT,"
                + VOTE_AVERAGE + " TEXT,"
                + VOTE_COUNT + " TEXT,"
                + POSTER_PATH + " TEXT,"
                + BACKDROP_PATH + " TEXT,"
                + ORIGINAL_LENGUAJE + " TEXT,"
                + CATEGORY + " TEXT)";
        db.execSQL(CREATE_PELICULA);

        String CREATE_UBICACION = "CREATE TABLE " + TABLE_UBICACION + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + LATITUD + " TEXT,"
                + LONGITUD + " TEXT,"
                + FECHA_ALMACENAMIENTO + " TEXT)";
        db.execSQL(CREATE_UBICACION);

        String CREATE_IMAGEN = "CREATE TABLE " + TABLE_IMAGEN + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + IMAGEN_GUARDADA_PATH + " TEXT)";
        db.execSQL(CREATE_IMAGEN);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PELICULA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UBICACION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGEN);
    }

    public void addPelicula(Pelicula pelicula){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(ID, pelicula.getId());

        values.put(ORIGINAL_TITLE, pelicula.getOriginal_title());
        values.put(OVERVIEW, pelicula.getOverview());
        values.put(POPULARITY, pelicula.getPopularity());
        values.put(RELEASE_DATE, pelicula.getRelease_data());
        values.put(VOTE_AVERAGE, pelicula.getVote_average());
        values.put(VOTE_COUNT, pelicula.getVote_count());
        values.put(POSTER_PATH, pelicula.getPoster_path());
        values.put(BACKDROP_PATH, pelicula.getBackdrop_path());
        values.put(ORIGINAL_LENGUAJE, pelicula.getOriginal_lenguaje());
        db.insert(TABLE_PELICULA, null, values);
        db.close();
    }

    public void addImagen(String path){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(IMAGEN_GUARDADA_PATH, path);
        db.insert(TABLE_IMAGEN, null, values);
        db.close();
    }

    public void addUbicacion(double lat, double lon, String fecha){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(LATITUD, String.valueOf(lat));
        values.put(LONGITUD, String.valueOf(lat));
        values.put(FECHA_ALMACENAMIENTO, fecha);
        db.insert(TABLE_UBICACION, null, values);
        db.close();
    }

    public ArrayList<Pelicula> getAllPeliculas (){
        ArrayList<Pelicula> arrayListPelicula = new ArrayList<Pelicula>();

        String selectQuery = "SELECT * FROM " + TABLE_PELICULA;

        Log.e("query_peliculas", selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{

                Pelicula pelicula = new Pelicula();
                pelicula.setId(cursor.getString(0));
                pelicula.setOriginal_title(cursor.getString(1));
                pelicula.setOverview(cursor.getString(2));
                pelicula.setPopularity(cursor.getString(3));
                pelicula.setRelease_data(cursor.getString(4));
                pelicula.setVote_average(cursor.getString(5));
                pelicula.setVote_count(cursor.getString(6));
                pelicula.setPoster_path(cursor.getString(7));
                pelicula.setBackdrop_path(cursor.getString(8));
                pelicula.setOriginal_lenguaje(cursor.getString(9));
                arrayListPelicula.add(pelicula);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return arrayListPelicula;
    }

    public void deletePeliculas(){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = " DELETE FROM " + TABLE_PELICULA;
        db.execSQL(selectQuery);
        db.close();
    }
}

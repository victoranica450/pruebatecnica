package com.vian.prueba_tecnica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.vian.prueba_tecnica.DB.DataBaseHandler;
import com.vian.prueba_tecnica.Fragments.CamaraFragment;
import com.vian.prueba_tecnica.Fragments.InicioFragment;
import com.vian.prueba_tecnica.Fragments.MapaFragment;
import com.vian.prueba_tecnica.Globals.AsyncTaskLoopJ;
import com.vian.prueba_tecnica.Globals.Connectivity;
import com.vian.prueba_tecnica.Globals.GPSLocation;
import com.vian.prueba_tecnica.Globals.Globals;
import com.vian.prueba_tecnica.Globals.LoopInterface;
import com.vian.prueba_tecnica.Models.Pelicula;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static String API_URL_POPULAR = Globals.URL_BASE + "popular?api_key=" + Globals.API_KEY;

    DataBaseHandler db;

    FragmentTransaction transaction;

    Fragment fragmentInicio, fragmentCamara, fragmentUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = new DataBaseHandler(this);

        NetworkInfo infor = Connectivity.getNetworkInfo(this);
        if (isNetworkConnected() && Connectivity.isConnectionFast(infor.getType(), infor.getSubtype())) {

            RequestParams postData = new RequestParams();

            postData.put("ex", "example");
            AsyncTaskLoopJ myLoopJTaskPopular = new AsyncTaskLoopJ(this, API_URL_POPULAR, "TEST", new LoopInterface() {
                @Override
                public void tareaFinalizada(String results) {
                    decodeResult(results);
                }
            });
            myLoopJTaskPopular.executeLoopjCall(postData);

        }

        if (solicitarPermisoUbicacion(this)){
            getUbication(this);
        }

        fragmentInicio = new InicioFragment();
        fragmentCamara = new CamaraFragment();
        fragmentUbicacion = new MapaFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragment, fragmentInicio).commit();


    }

    @SuppressLint("NonConstantResourceId")
    public void cambiarVista (View view){
        transaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId())
        {

            case R.id.ivHome:

                transaction.replace(R.id.contenedorFragment,fragmentInicio).commit();
                transaction.addToBackStack(null);
                break;

            case R.id.ivCamara:

                transaction.replace(R.id.contenedorFragment,fragmentCamara).commit();
                transaction.addToBackStack(null);
                break;

            case R.id.ivPin:
                transaction.replace(R.id.contenedorFragment,fragmentUbicacion).commit();
                break;
        }
    }


    public void decodeResult(String req) {

        String modResult = "{\"data\":" + req + "}";
        JSONObject data;
        JSONArray resultsArray;
        db.deletePeliculas();
        try {
            data = new JSONObject(modResult).getJSONObject("data");
            Log.e("data", data.toString());
            if (data != null) {
                resultsArray = data.getJSONArray("results");


                for (int i = 0; i < resultsArray.length(); i++){

                    JSONObject resultsObject = resultsArray.getJSONObject(i);
                    Pelicula peliculaModel = new Pelicula();

                    peliculaModel.setId(resultsObject.getString("id"));
                    peliculaModel.setOriginal_title(resultsObject.getString("original_title"));
                    peliculaModel.setOverview(resultsObject.getString("overview"));
                    peliculaModel.setPopularity(resultsObject.getString("popularity"));
                    peliculaModel.setRelease_data(resultsObject.getString("release_date"));
                    peliculaModel.setVote_average(resultsObject.getString("vote_average"));
                    peliculaModel.setVote_count(resultsObject.getString("vote_count"));
                    peliculaModel.setPoster_path(resultsObject.getString("poster_path"));
                    peliculaModel.setBackdrop_path(resultsObject.getString("backdrop_path"));
                    peliculaModel.setOriginal_lenguaje(resultsObject.getString("original_language"));
                    db.addPelicula(peliculaModel);
                }

                Log.e("size_pelis", String.valueOf(db.getAllPeliculas().size()));
            } else {
                /*showMessage(getResources().getString(R.string.conexionservidor));*/
            }

        } catch (JSONException e) {
            Log.e("errorJson", e.getMessage());
        }
    }

    public boolean isNetworkConnected() {
        ConnectivityManager conMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();

        if (i == null || !i.isConnected() || !i.isAvailable()) {
            return false;
        }
        return true;
    }

    public void getUbication(Context context) {

        GPSLocation gps = new GPSLocation(context);
        String lat = String.valueOf(gps.getLatitude());
        String lon = String.valueOf(gps.getLongitude());



        if (lat.equals("0") || lat.equals("0.0") || lon.equals("0") || lon.equals("0.0")) {
            Log.e("UBICACION FALLIDA ====>", "Ubicacion no encontrada");
        } else {
            Log.e("UBICACION ENCONTRADA =>", "Latitud " + lat + " Longitud " + lon);


        }
    }

    public Boolean solicitarPermisoUbicacion(Context context){
        int permisoUbicacion = ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int permisoUbicacionFine = ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION);

        if (permisoUbicacion!= PackageManager.PERMISSION_GRANTED || permisoUbicacionFine != PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setMessage(context.getResources().getString(R.string.msjUbicacion));
                alert.setTitle(context.getResources().getString(R.string.msg));
                alert.setPositiveButton(context.getResources().getString(R.string.aceptar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        requestPermissions(new String[]{
                                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                        }, 10);
                    }
                });
                alert.show();

            }
            return false;
        }
        return true;
    }

}
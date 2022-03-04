package com.vian.prueba_tecnica.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.vian.prueba_tecnica.DB.DataBaseHandler;
import com.vian.prueba_tecnica.Globals.Globals;
import com.vian.prueba_tecnica.R;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CamaraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CamaraFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int CODE_FOTO = 10;
    private static final int REQUEST_PERMISSION = 20;

    Button btGuardarImagen;
    ImageView ivCamara, ivImagenGuardada;

    DataBaseHandler db;

    String path = "";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CamaraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CamaraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CamaraFragment newInstance(String param1, String param2) {
        CamaraFragment fragment = new CamaraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camara, container, false);
        ivCamara = view.findViewById(R.id.ivCamara);
        ivImagenGuardada = view.findViewById(R.id.ivFotoTomada);
        btGuardarImagen = view.findViewById(R.id.btGuardar);

        db = new DataBaseHandler(getContext());

        ivCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        btGuardarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveImage(path);
            }
        });
        return view;


    }

    private void saveImage(String pathphoto) {
        Log.e("path", pathphoto);
        if (!pathphoto.equals("")){
            db.addImagen(pathphoto);
            Toast.makeText(getContext(), getResources().getString(R.string.fotoGuardada), Toast.LENGTH_LONG).show();
            path = "";
        }

    }

    private void openGallery(){

        if (solicitarPermisoAlmacenamientoYCamara(getContext())){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent.createChooser(intent, getResources().getString(R.string.SeleccionaUnaAplicación)), CODE_FOTO);
        }


    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_FOTO){
            Uri uri = data.getData();
            String convertedPath = Globals.getRealPathFromURI(uri, getContext());
            File file = new File(convertedPath);
            if (file.exists()) {
                ivImagenGuardada.setImageURI(uri);
                path = convertedPath;
            }
        }

    }

    public Boolean solicitarPermisoAlmacenamientoYCamara(Context context){
        int permisoCamara = ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        int permisoAlmacenamiento = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permisoCamara != PackageManager.PERMISSION_GRANTED || permisoAlmacenamiento != PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setMessage(context.getResources().getString(R.string.msjCamara));
                alert.setTitle(context.getResources().getString(R.string.msg));
                alert.setPositiveButton(context.getResources().getString(R.string.aceptar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        requestPermissions(new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        }, REQUEST_PERMISSION);
                    }
                });
                alert.show();

            }
            return false;
        }
        return true;
    }

}
package com.vian.prueba_tecnica.Globals;

import android.content.Context;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.Date;
import java.util.Locale;

public class Globals {

    public static final String URL_BASE = "https://api.themoviedb.org/3/movie/";

    public static final String API_KEY = "0f558b1cee62ff5664e9851b3235af7d";

    public static final String IMG_BASE = "https://image.tmdb.org/t/p/original";

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getDate(){
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        return currentDate + currentTime;
    }

    public static String getRealPathFromURI(Uri contentUri, Context context) {
        String getpath = contentUri.getPath();
        String path = "";
        Log.e("getPath", getpath);
        String id = "";

        if (getpath.contains("content")) {
            id = getpath.split("/")[9];
        } else {

            if (getpath.contains("media/")) {
                id = getpath.split("media/")[1];
            } else {
                if (getpath.contains(":")) {
                    id = getpath.split(":")[1];
                } else {
                    path = getpath;
                }
            }
        }

        if (getpath.contains("media/") || getpath.contains(":")) {
            String[] column = {MediaStore.Images.Media.DATA};
            String sel = MediaStore.Images.Media._ID + "=?";
            Cursor cursor = context.getContentResolver().
                    query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            column, sel, new String[]{id}, null);

            int columnIndex = cursor.getColumnIndex(column[0]);
            if (cursor.moveToFirst()) {
                path = cursor.getString(columnIndex);
            } else {
                path = getpath;
            }
            cursor.close();
        } else {
            path = getpath;
        }
        return path;
    }
}

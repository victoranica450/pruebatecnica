package com.vian.prueba_tecnica.Globals;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AsyncTaskLoopJ {

    private static final String TAG = "LoopTask";

    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;
    Context context;
    LoopInterface loopjListener;
    String url;
    ProgressDialog dialog;
    String mensajeDialog;
    boolean dialogEnabled;



    public AsyncTaskLoopJ(Context context, String url, String mensaje, LoopInterface listener) {
        asyncHttpClient = new AsyncHttpClient(true, 80, 443);
        //asyncHttpClient.setConnectTimeout(1000 * 60);
        asyncHttpClient.setTimeout(1000 * 60);
        requestParams = new RequestParams();
        this.context = context;
        this.loopjListener = listener;
        this.url = url;
        this.mensajeDialog = mensaje;
        dialogEnabled = true;
    }


    public void executeLoopjCall(RequestParams parametros){

        asyncHttpClient.post(url, parametros, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.e("internet","onStart");

            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("internet","onSuccess");
                try {

                    String jsonResponse = response.toString();
                    Log.e("aqiwoe=>", jsonResponse);
                    loopjListener.tareaFinalizada(jsonResponse);
                    Log.e("internetsuccessok=>", jsonResponse);
                }catch (Exception e){
                    Log.e("aqiwoe",e.getMessage());
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                super.onFailure(statusCode, headers,  throwable,response);

                try {
                    Log.e("mensajered",throwable.getMessage());

                }catch (Exception e){
                    Log.e("internetFailure",e.getMessage());

                }

            }


            @Override
            public void onFinish() {
                //dialog.dismiss();
                Log.e("internetFinish=>","termino");
                try {
                    if(dialogEnabled){
                        if(dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }

                }catch (Exception e){
                    Log.e(TAG, "No se creo dialog: " + e);
                }
            }
        });

    }

    public void executeLoopjCallEnvio(RequestParams parametros) throws Exception {
        asyncHttpClient.post(url, parametros, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {

            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                String jsonResponse = response.toString();
                loopjListener.tareaFinalizada(jsonResponse);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                super.onFailure(statusCode, headers,  throwable,response);

                try {
                    Log.e("mensajered",throwable.getMessage());
                    Log.e("mensajered",""+statusCode);


                }catch (Exception e){

                    Log.e("dialog","no esta creado");

                }

            }

            @Override
            public void onFinish() {
                try {
                    if(dialogEnabled){
                        if(dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }
                }catch (Exception e){
                    Log.e(TAG, "No se creo dialog: " + e);
                }
            }
        });
        throw new Exception();
    }
}

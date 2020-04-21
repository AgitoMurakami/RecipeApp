package com.example.recipeapp.connectinternet;

import android.os.AsyncTask;
import android.util.Log;

import com.example.recipeapp.classes.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ConnectInternet extends AsyncTask<URL, Integer, List<Recipe>> {

    public static final String TAG = "ConnectInternetTask";

    private IConnectInternet callbackHandler = null;

    public ConnectInternet (IConnectInternet callback) {
        this.callbackHandler = callback;
    }

    @Override
    protected List<Recipe> doInBackground(URL... urls) {
        String tmpResult = "";
        try {
            tmpResult = NetworkUtils.getResponseFromHttpUrl(urls[0]);

        }catch (IOException e ){
            Log.e(TAG,e.getMessage());
        }
        Log.d("TEST",tmpResult);

        Gson tmpGSON =new GsonBuilder().create();

        Type type = new TypeToken<ArrayList<Recipe>>() {}.getType();
        List<Recipe> tmpModel = tmpGSON.fromJson(tmpResult, type);

        return tmpModel;
    }

    @Override
    protected void onPostExecute(List<Recipe> s) {
        super.onPostExecute(s);

        if(callbackHandler != null) {
            callbackHandler.callback(s);
        }
    }
}
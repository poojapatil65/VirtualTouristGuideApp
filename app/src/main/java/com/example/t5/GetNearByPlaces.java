package com.example.t5;


import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class GetNearByPlaces extends AsyncTask<Object,String,String> {
    GoogleMap mMap;
    String url;
    InputStream is;
    BufferedReader bufferedReader;
    StringBuilder stringBuilder;
    String data;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];
        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            data = downloadUrl.readUrl(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(String s) {

        try{
            JSONObject jsonObject=new JSONObject(s);
            JSONArray jsonArray=jsonObject.getJSONArray("results");

            for (int i =0;i < jsonArray.length();i++){
                JSONObject jsonObject12=jsonArray.getJSONObject(i);
                JSONObject locationobject=jsonObject12.getJSONObject("getcountry").getJSONObject("object");

                String latitude=locationobject.getString("lat");
                String longitude=locationobject.getString("lng");
                JSONObject nameobject=jsonArray.getJSONObject(i);

                String name=nameobject.getString("name");

                LatLng latLng=new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));

                MarkerOptions markerOptions=new MarkerOptions();
                markerOptions.title(name);
                markerOptions.position(latLng);

                mMap.addMarker(markerOptions);

            }
        }
        catch (JSONException e)
        {

        }
    }
}

package org.protaxiandroidapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by DIEGO on 05/03/2017.
 */
public class AddressDetail extends AsyncTask<String, Void, String> {

    ProgressDialog pd;
    Context mContext;
    Location location;
    MyLocation myLocation;

    public AddressDetail(Context context){
        this.mContext = context;
        myLocation = (MyLocation)mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(mContext);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected void onPostExecute(String jsonObject) {

        super.onPostExecute(jsonObject);

        if(jsonObject != null)
        {
            try {
                JSONObject jsonObjectAux = new JSONObject(jsonObject);
                Location locationAux = new Location("");
                locationAux.setLatitude(Double.parseDouble(jsonObjectAux.getString("lat")));
                locationAux.setLongitude(Double.parseDouble(jsonObjectAux.getString("lng")));
                myLocation.setMyLocation(locationAux);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("error", "error1");
            }

            pd.dismiss();
        }
        else{
            Toast.makeText(mContext, "Error4!Please Try Again wiht proper values", Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        try {

            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statuscode = con.getResponseCode();

            if(statuscode == HttpURLConnection.HTTP_OK)
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while(line != null)
                {
                    sb.append(line);
                    line = br.readLine();
                }

                String json = sb.toString();

                JSONObject root = new JSONObject(json);
                JSONObject resultJSONObject = root.getJSONObject("result");
                JSONObject geometryJSONObject = resultJSONObject.getJSONObject("geometry");
                JSONObject locationJSONObject = geometryJSONObject.getJSONObject("location");

                return locationJSONObject.toString();

            }
        } catch (MalformedURLException e) {
            Log.d("error", "error1");
        } catch (IOException e) {
            Log.d("error", "error2");
        } catch (JSONException e) {
            Log.d("error","error3");
        }

        return null;
    }

    interface MyLocation{
        public void setMyLocation(Location location);
    }
}

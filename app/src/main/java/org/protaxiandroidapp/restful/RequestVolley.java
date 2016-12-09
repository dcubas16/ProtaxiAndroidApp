package org.protaxiandroidapp.restful;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by DIEGO on 03/11/2016.
 */
public class RequestVolley {

    private VolleyS volley;
    protected RequestQueue fRequestQueue;
    Context context;
//    public Activity activity;

    public RequestVolley(Context context){
        this.context = context;
        volley = VolleyS.getInstance(context.getApplicationContext());
        fRequestQueue = volley.getRequestQueue();
    }

    public void get(String urlBase, String urlSpecific, Map<String, String> params){
        String parameters = "";

        for(int i = 0; i< params.size(); i++){
            if(i==0){
                parameters = "?";
            }

            parameters += params.get(i);
        }

        String url = urlBase + urlSpecific + parameters;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
        new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                onConnectionFinished();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onConnectionFailed(volleyError.toString());
            }
        });

        addToQueue(request);
    }

    public void post(String urlBase, String urlSpecific, final Map<String, String> params){

        String url = urlBase + urlSpecific;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }
        };

        addToQueue(postRequest);
    }

    public void onPreStartConnection() {
        //getActivity().setProgressBarIndeterminateVisibility(true);
    }

    public void onConnectionFinished() {
        //getActivity().setProgressBarIndeterminateVisibility(false);
    }

    public void onConnectionFailed(String error) {
        //getActivity().setProgressBarIndeterminateVisibility(false);
        Toast.makeText(this.context, error, Toast.LENGTH_SHORT).show();
    }

    public void addToQueue(Request request) {
        if (request != null) {
            request.setTag(this);
            if (fRequestQueue == null)
                fRequestQueue = volley.getRequestQueue();
            request.setRetryPolicy(new DefaultRetryPolicy(
                    6000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            onPreStartConnection();
            fRequestQueue.add(request);
        }
    }

}

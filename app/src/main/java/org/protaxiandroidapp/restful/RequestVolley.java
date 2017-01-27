package org.protaxiandroidapp.restful;

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
import org.json.JSONObject;
import java.util.Map;

/**
 * Created by DIEGO on 03/11/2016.
 */
public class RequestVolley {

    private VolleyS volley;
    protected RequestQueue fRequestQueue;
    Context context;
    private String errorString;
    private String responseString;
    private int indicatorProccess;// 0-No iniciado  -1-Iniciado -2-Terminado

    public RequestVolley(Context context){
        this.context = context;
        volley = VolleyS.getInstance(context.getApplicationContext());
        fRequestQueue = volley.getRequestQueue();
        setErrorString("");
        setResponseString("");
        setIndicatorProccess(0);
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
                        setResponseString(response);
                        Log.d("Response", response);
                        setIndicatorProccess(2);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setErrorString(error.getMessage());
                        Log.d("Error.Response", error.getMessage());
                        setIndicatorProccess(2);
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

        int aux = 0;
        while (getIndicatorProccess() != 2){
            aux += 1;
        }
    }

    public void onPreStartConnection() {
        //getActivity().setProgressBarIndeterminateVisibility(true);
        setIndicatorProccess(1);
    }

    public void onConnectionFinished() {
        //getActivity().setProgressBarIndeterminateVisibility(false);
        setIndicatorProccess(2);
    }

    public void onConnectionFailed(String error) {
        //getActivity().setProgressBarIndeterminateVisibility(false);
        Toast.makeText(this.context, error, Toast.LENGTH_SHORT).show();
        setIndicatorProccess(2);
    }

    public void addToQueue(Request request) {
        try {
            if (request != null) {
                request.setTag(this);
                if (fRequestQueue == null)
                    fRequestQueue = volley.getRequestQueue();
                request.setRetryPolicy(new DefaultRetryPolicy(
                        10000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));
                onPreStartConnection();
                fRequestQueue.add(request);
            }
        }catch (Exception e){
            Log.d("Error.Response", e.getMessage());
        }
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public int getIndicatorProccess() {
        return indicatorProccess;
    }

    public void setIndicatorProccess(int indicatorProccess) {
        this.indicatorProccess = indicatorProccess;
    }
}

package org.protaxiandroidapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;
import org.protaxiandroidapp.dto.RequestTaxiDTO;
import org.protaxiandroidapp.entities.RequestTaxi;
import org.protaxiandroidapp.restful.RequestVolley;

import java.util.HashMap;
import java.util.Map;

import layout.Constants;
import layout.General;

public class RequestTaxiConfirmationActivity extends AppCompatActivity implements View.OnClickListener{

    private RequestTaxi requestTaxi = new RequestTaxi();

    private TextView textViewOriginAddress;
    private TextView textViewOriginAddressNumber;
    private TextView textViewOriginReference;
    private TextView textViewDestination;
    private TextView textViewPaymentAndPromotion;
    private TextView textViewCarOption;
    private TextView textViewEasyShare;
    private Button btnConfirmCallTaxi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_taxi_confirmation);

        textViewOriginAddress = (TextView)findViewById(R.id.textViewOriginAddress);
        textViewOriginAddressNumber = (TextView)findViewById(R.id.textViewOriginAddressNumber);
        textViewOriginReference = (TextView)findViewById(R.id.textViewOriginReference);
        textViewDestination = (TextView)findViewById(R.id.textViewDestination);
        textViewPaymentAndPromotion = (TextView)findViewById(R.id.textViewPaymentAndPromotion);
        textViewCarOption = (TextView)findViewById(R.id.textViewCarOption);
        textViewEasyShare = (TextView)findViewById(R.id.textViewEasyShare);
        btnConfirmCallTaxi = (Button)findViewById(R.id.btnConfirmCallTaxi);

        btnConfirmCallTaxi.setOnClickListener(this);

        requestTaxi.setLatOrigin(getIntent().getDoubleExtra("latOrigin", -1));
        requestTaxi.setLngOrigin(getIntent().getDoubleExtra("lngOrigin", -1));
        requestTaxi.setOriginAddress(getIntent().getStringExtra("originAddress"));
        requestTaxi.setOriginAddressNumber(getIntent().getStringExtra("originAddressNumber"));
        requestTaxi.setOriginReference(getIntent().getStringExtra("originReference"));
        requestTaxi.setLatDestination(getIntent().getDoubleExtra("latDestination", -1));
        requestTaxi.setLngDestination(getIntent().getDoubleExtra("lngDestination", -1));
        requestTaxi.setDestinationReference(getIntent().getStringExtra("destinationReference"));
        requestTaxi.setPaymentTypeId(getIntent().getStringExtra("paymentTypeId"));
        requestTaxi.setServiceTypeId(getIntent().getStringExtra("serviceTypeId"));

        textViewOriginAddress.setText(requestTaxi.getOriginAddress());
        textViewOriginAddressNumber.setText(requestTaxi.getOriginAddressNumber());
        textViewDestination.setText(requestTaxi.getDestinationReference());

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnConfirmCallTaxi:
                if(isValidRequestTaxi()){
                    getDataFromForm();
                    requestTaxi(requestTaxi, v);
                }else{
                    Toast.makeText(this, "Faltan datos para solicitar taxi" , Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void getDataFromForm() {

    }


    private boolean isValidRequestTaxi() {
        return true;
    }


    private void requestTaxi(RequestTaxi requestTaxi, View v) {

        try {
            final View viewAux = v;
            General.showLoading(this, "", Constants.Loading);

            RequestVolley requestVolley = new RequestVolley(this);

            final Map<String, String> params = new HashMap<>();


            ObjectMapper objectMapper = new ObjectMapper();
            RequestTaxiDTO requestTaxiDTO = new RequestTaxiDTO(requestTaxi);
            String requestTaxtJSON = objectMapper.writeValueAsString(requestTaxiDTO);

            params.put("requestTaxiDTO", requestTaxtJSON);

            String url = Constants.urlBase + Constants.urlSaveRequestTaxiConfirmation;

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Response", response);
                            try {
                                JSONObject jsonObject = new JSONObject(response.toString());

                                General.dismissLoading();
                                General.showAlert(viewAux, "Solicitud de Taxi", "Taxi Solicitado");
                                finish();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                General.dismissLoading();
                                General.showAlert(viewAux, "Ocurrió un problema", "Request Taxi");
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error.Response", error.getMessage());
                            General.dismissLoading();
                            General.showAlert(viewAux, error.getMessage(), "Error.Response");
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    return params;
                }
            };

            requestVolley.addToQueue(postRequest);

        } catch (Exception e) {
            General.dismissLoading();
            General.showAlert(v, "Ocurrió un problema", "Request Taxi");
        }
    }
}

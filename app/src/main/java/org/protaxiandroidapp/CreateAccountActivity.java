package org.protaxiandroidapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.protaxiandroidapp.restful.RequestVolley;
import java.util.HashMap;
import java.util.Map;
import layout.Constants;
import layout.General;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnCreateAccount;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtName;
    private EditText txtCountry;
    private EditText txtPhoneNumber;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        btnCreateAccount = (Button)findViewById(R.id.btnCreateAccount);
        btnCreateAccount.setOnClickListener(this);

        txtName = (EditText)findViewById(R.id.txtName);
        txtCountry = (EditText)findViewById(R.id.txtCountry);
        txtPhoneNumber = (EditText)findViewById(R.id.txtPhoneNumber);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtPassword = (EditText)findViewById(R.id.txtPassword);

        txtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (txtName.getText().toString().trim().isEmpty()) {
                    txtName.setError("El nombre es obligatorio");
                }
            }
        });

        txtPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (txtPhoneNumber.getText().toString().trim().isEmpty()) {
                    txtPhoneNumber.setError("El celular es obligatorio");
                    return;
                }

                if (!Patterns.PHONE.matcher(txtPhoneNumber.getText().toString().trim()).matches()) {
                    txtPhoneNumber.setError("El celular no tiene el formato correcto");
                    return;
                }
            }
        });

        txtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (txtEmail.getText().toString().trim().isEmpty()) {
                    txtEmail.setError("El email es obligatorio");
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString().trim()).matches()) {
                    txtEmail.setError("El email no tiene el formato correcto");
                    return;
                }
            }
        });

        txtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (txtPassword.getText().toString().trim().isEmpty()) {
                    txtPassword.setError("La contraseña es obligatorio");
                }
            }
        });

    }

    private boolean validateForm() {
        boolean isValid = true;

        if (txtName.getText().toString().trim().isEmpty()) {
            txtName.setError("El nombre es obligatorio");
            isValid = false;
        }

        if (!Patterns.PHONE.matcher(txtPhoneNumber.getText().toString().trim()).matches()) {
            txtPhoneNumber.setError("El celular no tiene el formato correcto");
            isValid = false;
        }

        if (txtPhoneNumber.getText().toString().trim().isEmpty()) {
            txtPhoneNumber.setError("El celular es obligatorio");
            isValid = false;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString().trim()).matches()) {
            txtEmail.setError("El email no tiene el formato correcto");
            isValid = false;
        }

        if (txtEmail.getText().toString().trim().isEmpty()) {
            txtEmail.setError("El email es obligatorio");
            isValid = false;
        }

        if (txtPassword.getText().toString().trim().isEmpty()) {
            txtPassword.setError("La contraseña es obligatorio");
            isValid = false;
        }

        return isValid;
    }


    private void createAccount(View v) {

        try {

            final View viewAux = v;
            General.showLoading(this, "", Constants.Loading);

            RequestVolley requestVolley = new RequestVolley(this);

            final Map<String, String> params = new HashMap<>();
            params.put("name", txtName.getText().toString());
            params.put("country", txtCountry.getText().toString());
            params.put("phoneNumber", txtPhoneNumber.getText().toString());
            params.put("email", txtEmail.getText().toString());
            params.put("password", txtPassword.getText().toString());

            String url = Constants.urlBase + Constants.urlSpecificCreateAccount;

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Response", response);

                            int protaxiUserIsLogged = 1;

                            SharedPreferences.Editor editor = getSharedPreferences(Constants.ProtaxiPreferences, MODE_PRIVATE).edit();

                            editor.putString("protaxiUserName", txtName.getText().toString());
                            editor.putString("protaxiUserId", response.toString());
                            editor.putInt("protaxiUserIsLogged", protaxiUserIsLogged);
                            editor.commit();

                            General.dismissLoading();
                            General.showAlert(viewAux, "Protaxi te da la bienvenida " + txtName.getText().toString() , "Creación cuenta");
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            setErrorString(error.getMessage());
                            Log.d("Error.Response", error.getMessage());
//                            setIndicatorProccess(2);
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
            General.showAlert(v, "Ocurrió un problema", "Login");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email_sign_in_button:
                //login(v);
                break;

            case R.id.btnCreateAccount:
                if (validateForm())
                    createAccount(v);
                break;

            case R.id.txtCountry:
                break;
        }
    }
}


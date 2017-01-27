package org.protaxiandroidapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.protaxiandroidapp.restful.RequestVolley;
import java.util.HashMap;
import java.util.Map;
import layout.Constants;
import layout.General;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private Button btnCreateAccount;
    private EditText txtEmail;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.email_sign_in_button);
        btnLogin.setOnClickListener(this);
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        btnCreateAccount.setOnClickListener(this);
        txtEmail = (EditText)findViewById(R.id.email);
        txtPassword = (EditText)findViewById(R.id.password);

        btnLogin.requestFocus();

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.email_sign_in_button:
                if (validateForm())
                    login(v);
                break;

            case R.id.btnCreateAccount:
                createAccount(v);
                break;
        }
    }

    private void createAccount(View v) {
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.login_fragment, new CreateAccountFragment(), "fragment_screen");
//        ft.commit();

        Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
        startActivity(intent);
    }

    private boolean validateForm() {
        boolean isValid = true;

        if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString().trim()).matches()) {
            txtEmail.setError("El email no tiene el formato correcto");
            isValid = false;
        }

        if (txtEmail.getText().toString().trim().isEmpty()) {
            txtEmail.setError("El email es obligatorio");
            isValid = false;
        }

        if (txtPassword.getText().toString().trim().isEmpty()) {
            txtPassword.setError("La contraseña es obligatoria");
            isValid = false;
        }

        return isValid;
    }

    private void login(View v) {
        try {
            final View viewAux = v;
            General.showLoading(this, "", Constants.Loading);

            RequestVolley requestVolley = new RequestVolley(this);

            final Map<String, String> params = new HashMap<>();
            params.put("email", txtEmail.getText().toString());
            params.put("password", txtPassword.getText().toString());
            params.put("client", "");

            String url = Constants.urlBase + Constants.urlSpecificLogin;

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
//                            requestVolley.setResponseString(response);
                            Log.d("Response", response);
//                            setIndicatorProccess(2);
//                            txtEmail.setText(response.toString());
                            try {
                                JSONObject jsonObject = new JSONObject(response.toString());
                                String protaxiUserName = jsonObject.getString("name");
                                String protaxiUserId = jsonObject.getString("id");
                                int protaxiUserIsLogged = 1;

                                SharedPreferences.Editor editor = getSharedPreferences(Constants.ProtaxiPreferences, MODE_PRIVATE).edit();

                                editor.putString("protaxiUserName", protaxiUserName);
                                editor.putString("protaxiUserId", protaxiUserId);
                                editor.putInt("protaxiUserIsLogged", protaxiUserIsLogged);
                                editor.commit();

                                MainWindow.userName.setText("Bienvenido " + protaxiUserName);

                                General.dismissLoading();
                                General.showAlert(viewAux, "Bienvenido " + protaxiUserName, "Bienvenido");

                                finish();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                General.dismissLoading();
                                General.showAlert(viewAux, "Ocurrió un problema", "Login");
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
            General.showAlert(v, "Ocurrió un problema", "Login");
        }
    }
}

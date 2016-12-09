package layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.protaxiandroidapp.R;
import org.protaxiandroidapp.restful.RequestVolley;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountFragment extends android.app.Fragment implements View.OnClickListener {

    private Button btnCreateAccount;

    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtName;
    private EditText txtCountry;
    private EditText txtPhoneNumber;

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);
        rootView = view;

        btnCreateAccount = (Button) view.findViewById(R.id.btnCreateAccount);
        btnCreateAccount.setOnClickListener(this);

        txtName = (EditText) view.findViewById(R.id.txtName);
        txtCountry = (EditText) view.findViewById(R.id.txtCountry);
        txtPhoneNumber = (EditText) view.findViewById(R.id.txtPhoneNumber);
        txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        txtPassword = (EditText) view.findViewById(R.id.txtPassword);

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

        return view;
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

            RequestVolley requestVolley = new RequestVolley(this.getActivity());

            Map<String, String> params = new HashMap<>();
            params.put("name", txtName.getText().toString());
            params.put("country", txtCountry.getText().toString());
            params.put("phoneNumber", txtPhoneNumber.getText().toString());
            params.put("email", txtEmail.getText().toString());
            params.put("password", txtPassword.getText().toString());

            requestVolley.post(Constants.urlBase, Constants.urlSpecificCreateAccount, params);

        } catch (Exception e) {
            General.showAlert(v, "Ocurrió un problema", "Login");
        }
    }


}

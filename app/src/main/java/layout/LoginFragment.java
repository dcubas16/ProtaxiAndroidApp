package layout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import org.protaxiandroidapp.R;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class LoginFragment extends android.app.Fragment implements View.OnClickListener {
    Button btnLogin;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        rootView = view;
        btnLogin = (Button) view.findViewById(R.id.email_sign_in_button);
        btnLogin.setOnClickListener(this);

        return view;
    }

    public void showAlert(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Hola Mundo");
    }

    @Override
    public void onClick(View v) {
        TextView textView = (TextView) rootView.findViewById(R.id.textView2);
        textView.setText("HOLA");
        new HttpRequestTask().execute();

    }

    private class HttpRequestTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                final String url = "http://localhost:8080/protaxi/client/callTest";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                String greeting = restTemplate.getForObject(url, String.class);
                return greeting;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String greeting) {
            TextView greetingIdText = (TextView) rootView.findViewById(R.id.textView2);
            greetingIdText.setText(greeting);
        }

        /*public void irCrearNuevaCuenta(View view){
            Intent newActivity = new Intent(this.RegisterFragment.class);
        }*/

    }

}

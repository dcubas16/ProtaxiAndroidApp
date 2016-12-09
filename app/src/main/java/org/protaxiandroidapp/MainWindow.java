package org.protaxiandroidapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.protaxiandroidapp.restful.entities.Greeting;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import layout.AboutFragment;
import layout.LoginFragment;
import layout.SendFragment;

public class MainWindow extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, LocationSource, GoogleMap.OnMyLocationButtonClickListener {

    SupportMapFragment supportMapFragment;
    Firebase mRef;
    MarkerOptions markerOptions;
    Button btnRequestTaxi;
    Marker marker;
    Button btnLlamarRest;
    //TextView txtRest = (TextView)findViewById(R.id.txtRest1);
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        supportMapFragment = SupportMapFragment.newInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();
        Fragment fragment = null;

        if (supportMapFragment.isAdded())
            sFm.beginTransaction().hide(supportMapFragment).commit();


        sFm.beginTransaction().add(R.id.content_frame, supportMapFragment).commit();


//        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment);


        drawer.closeDrawer(GravityCompat.START);


        supportMapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        Firebase.setAndroidContext(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_window, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();
        Fragment fragment = null;

        if (supportMapFragment.isAdded())
            sFm.beginTransaction().hide(supportMapFragment).commit();

        if (id == R.id.nav_login) {
            fragment = new LoginFragment();

            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            Log.e("Fragment", "Login");

        } else if (id == R.id.nav_share) {
            fragment = new MainFragment();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            if (!supportMapFragment.isAdded()) {
                sFm.beginTransaction().add(R.id.content_frame, supportMapFragment).commit();
            } else {
                sFm.beginTransaction().show(supportMapFragment).commit();
            }
            Log.e("Fragment", "Share1");

        } else if (id == R.id.nav_send) {
            fragment = new SendFragment();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            Log.e("Fragment", "Send1");

        } else if (id == R.id.nav_about) {
            fragment = new AboutFragment();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            Log.e("Fragment", "About1");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMyLocationEnabled(true);

        LatLng sydney = new LatLng(-11.991039, -77.078902);
        LatLng sydney1 = new LatLng(-11.983347, -77.060316);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);


        markerOptions = new MarkerOptions().position(sydney).snippet("Toyota Corolla 2015 \n Placa: PKY-4596\n *****");
        markerOptions.title(getAddressFromLatLng(sydney));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_local_taxi_green)));

        MarkerOptions markerOptions1 = new MarkerOptions().position(sydney1).snippet("Kia Optima 2016 \n Placa: ABC-1234\n *****");
        markerOptions1.title(getAddressFromLatLng(sydney));
        markerOptions1.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_local_taxi_red)));


        marker = googleMap.addMarker(markerOptions);
        marker.setTitle("Diego Nuñez Cubas");
        googleMap.addMarker(markerOptions1).setTitle("Juan Carlos Suarez");

    }

    private String getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this);

        String address = "";
        try{
            address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0).getAddressLine(0);
        }catch (IOException e){

        }

        return address;
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();

        mRef = new Firebase("https://arched-hybrid-118216.firebaseio.com/longitud");
        final Firebase mRefLlamar = new Firebase("https://arched-hybrid-118216.firebaseio.com/llamar");
        final Firebase mRefAceptaLlamada = new Firebase("https://arched-hybrid-118216.firebaseio.com/respondeLlamada");

        btnRequestTaxi = (Button)findViewById(R.id.btnLlamarTaxi);

        btnRequestTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mRef.setValue("-77.077962");
                mRefLlamar.setValue(1);

//                Intent i = new Intent(getApplicationContext(), CreateAccountActivity.class);
//                startActivity(i);
            }
        });


        btnLlamarRest = (Button)findViewById(R.id.btnLlamarRest);
        btnLlamarRest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new HttpRequestTask().execute();
            }
        });

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                LatLng latLng = new LatLng(-11.994467, Double.parseDouble(data));
                //markerOptions.position(latLng);
                marker.setPosition(latLng);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MainWindow Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://org.protaxiandroidapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MainWindow Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://org.protaxiandroidapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Greeting> {

        public HttpRequestTask(){}

        @Override
        protected Greeting doInBackground(Void... params) {
            try {
                final String url = "http://192.168.1.33:8081/protaxi/client/callTest";

                RestTemplate rest = new RestTemplate();

                rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                String greetingRestObject = rest.getForObject(url, String.class);

                Greeting greeting = new Greeting();

                return greeting;

            } catch (Exception e) {
                Log.e("MainWindow", e.getMessage(), e);
                return null;
            }
        }

        private List<HttpMessageConverter<?>> getMessageConverters() {
            List<HttpMessageConverter<?>> converters =
                    new ArrayList<HttpMessageConverter<?>>();
            converters.add(new MappingJackson2HttpMessageConverter());
            return converters;
        }

        @Override
        protected void onPostExecute(Greeting greeting) { }
    }
}

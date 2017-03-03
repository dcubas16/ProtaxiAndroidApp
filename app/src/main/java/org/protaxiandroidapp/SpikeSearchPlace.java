package org.protaxiandroidapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class SpikeSearchPlace extends AppCompatActivity {

    protected GoogleApiClient googleApiClient;
    private static final LatLngBounds myBounds = new LatLngBounds(new LatLng(-0,0), new LatLng(0,0));
    private EditText myAvView;
    private RecyclerView myRecyclerView;
    private LinearLayoutManager myLinearLayoutManager;
    private AtAdapter mAtAdapter;
    ImageView clearText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spike_search_place);
    }

}

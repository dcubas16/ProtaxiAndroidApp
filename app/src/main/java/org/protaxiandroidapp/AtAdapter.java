package org.protaxiandroidapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Created by DIEGO on 14/02/2017.
 */
public class AtAdapter extends RecyclerView.Adapter<AtAdapter.PreditionHolder> implements Filterable{

    private ArrayList<AtPlace> myResultList;
    private GoogleApiClient myApliClient;
    private LatLngBounds myBounds;
    private AutocompleteFilter myACFilter;

    private Context myContext;
    private int layout;

    public AtAdapter(Context context, int resource, GoogleApiClient googleApiClient, LatLngBounds bounds, AutocompleteFilter filter){
        myContext = context;
        layout = resource;
        myApliClient = googleApiClient;
        myBounds = bounds;
        myACFilter = filter;
    }

    public void setMyBounds(LatLngBounds bounds){myBounds = bounds;}

    public android.widget.Filter getFilter(){
        android.widget.Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if(constraint != null){
                    myResultList = getAutoComplete(constraint);
                    if(myResultList != null){
                        results.values = myResultList;
                        results.count = myResultList.size();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results != null && results.count > 0){
                    notifyDataSetChanged();
                }else
                {

                }
            }
        };

        return filter;
    }

    private ArrayList<AtPlace> getAutoComplete(CharSequence constraint) {
        if(myApliClient.isConnected()){
            PendingResult<AutocompletePredictionBuffer> results = Places.GeoDataApi.getAutocompletePredictions(myApliClient
                    , constraint.toString(), myBounds, myACFilter);

            AutocompletePredictionBuffer autocompletePredictions = results.await(60, TimeUnit.SECONDS);

            final Status status = autocompletePredictions.getStatus();
            if(!status.isSuccess()){
                Toast.makeText(myContext, "Error contacting API" + status.toString(), Toast.LENGTH_SHORT).show();
                Log.e("", "Error getting autocomplete prediction API call " + status.toString());
                autocompletePredictions.release();
                return null;
            }

            Log.i("", "Query completed received "+ autocompletePredictions.getCount() + " predictions");

            Iterator<AutocompletePrediction> iterator = autocompletePredictions.iterator();
            ArrayList resultList = new ArrayList<>(autocompletePredictions.getCount());
            while (iterator.hasNext()){
                AutocompletePrediction prediction = iterator.next();
                resultList.add(new AtPlace(prediction.getPlaceId(), prediction.getPlaceId()));
            }

            autocompletePredictions.release();
            return resultList;
        }

        Log.e("", "Google API client is not connected for automplete query");
        return null;
    }

    public AtPlace getItem(int position){return myResultList.get(position);}

    @Override
    public PreditionHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = layoutInflater.inflate(layout, viewGroup, false);
        PreditionHolder mprePreditionHolder = new PreditionHolder(convertView);
        return mprePreditionHolder;
    }

    @Override
    public void onBindViewHolder(PreditionHolder mPreditionHolder, final int i) {
        mPreditionHolder.myPrediction.setText(myResultList.get(i).description);
    }

    @Override
    public int getItemCount() {
        if(myResultList != null){
            return myResultList.size();
        }else{
            return 0;
        }

    }

    public class PreditionHolder extends RecyclerView.ViewHolder{
        private TextView myPrediction;
        private RelativeLayout myRow;

        public PreditionHolder(View itemView){
            super(itemView);
            myPrediction = (TextView)itemView.findViewById(R.id.address);
            myRow = (RelativeLayout)itemView.findViewById(R.id.autocomplete_row);
        }
    }

    public class AtPlace {

        public CharSequence placeId;
        public CharSequence description;

        public AtPlace(String placeId, String description) {
            this.placeId = placeId;
            this.description = description;
        }

        public String toString(){return description.toString();};
    }
}

package org.senani.priceapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    SearchView searchView;
    PriceListAdapter priceListAdapter;
    Button button;
    RelativeLayout splash;
    LottieAnimationView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Values.mDatabase = FirebaseDatabase.getInstance().getReference();
        Values.items=new ArrayList<>();
        Values.cm =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);


        listView = (ListView) findViewById(R.id.list_view);
        searchView = (SearchView) findViewById(R.id.search);
        button = (Button) findViewById(R.id.button);
        splash = (RelativeLayout) findViewById(R.id.splash);
        loading = (LottieAnimationView) findViewById(R.id.loading_anim);

        priceListAdapter=new PriceListAdapter(getApplicationContext());

        listView.setAdapter(priceListAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                priceListAdapter.getFilter().filter(newText);
                return false;
            }
        });

        if(!isInternetOn()) {
            showToast();
        }

        load();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),BillActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }

    public void load(){

        Values.mDatabase.child("veg").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    try {
                        Item item = messageSnapshot.getValue(Item.class);
                        Values.items.add(item);
                    }catch (Exception e){

                    }
                }

                priceListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Values.mDatabase.child("item").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    try {
                        Item item = messageSnapshot.getValue(Item.class);
                        Values.items.add(item);
                    }catch (Exception e){

                    }
                }

                priceListAdapter.notifyDataSetChanged();
                loading.cancelAnimation();
                splash.setVisibility(View.INVISIBLE);

                searchView.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public final boolean isInternetOn() {

        try {
            // Check for network connections
            if (Values.cm.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                    Values.cm.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    Values.cm.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    Values.cm.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

                return true;

            } else if (
                    Values.cm.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                            Values.cm.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

                return false;
            }
            return true;
        }catch (Exception e){
            return true;
        }
    }

    public void showToast(){
        View layout = getLayoutInflater().inflate(R.layout.layout_toast, (ViewGroup) findViewById(R.id.toast));
        Toast toast=new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setView(layout);
        toast.show();
    }

}

package org.senani.priceapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BillActivity extends AppCompatActivity {

    ListView listView;
    ProgressDialog progressDialog;
    SearchView searchView;
    PriceListAdapterB priceListAdapter;
    public static double total=0;
    public static TextView tvTotal;
    Button btnClear;
    Button btnBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        Values.itemBs=new ArrayList<>();
        Values.billActivity=this;
        total=0;

        listView = (ListView) findViewById(R.id.list_view);
        searchView = (SearchView) findViewById(R.id.search);
        tvTotal = (TextView) findViewById(R.id.tv_total);
        btnClear = (Button) findViewById(R.id.btn_clear);
        btnBill = (Button) findViewById(R.id.btn_bill);

        priceListAdapter=new PriceListAdapterB(getApplicationContext());

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

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Values.itemBs=new ArrayList<ItemB>();
                total=0;
                tvTotal.setText("0.0");
                Toast.makeText(BillActivity.this,"Cleared the bill", Toast.LENGTH_LONG).show();
            }
        });

        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),BillActivityFinal.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

    }

    @Override
    public void onBackPressed(){
        Values.itemBs.clear();
        total=0;
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_righ);
    }
}

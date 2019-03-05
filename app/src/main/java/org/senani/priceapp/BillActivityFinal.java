package org.senani.priceapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

public class BillActivityFinal extends AppCompatActivity {

    ListView listView;
    ProgressDialog progressDialog;
    PriceListAdapterF priceListAdapter;
    public static double total;
    public static TextView tvTotal;
    Button btnBack;
    Button btnIdea;
    TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_final);

        Values.billActivityF=this;
        total=Values.billActivity.total;

        listView = (ListView) findViewById(R.id.list_view);

        tvTotal = (TextView) findViewById(R.id.tv_total);
        tvEmpty = (TextView) findViewById(R.id.empty_tv);
        btnBack = (Button) findViewById(R.id.btn_back);
        btnIdea = (Button) findViewById(R.id.btn_idea);

        if(Values.itemBs.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
        }else {
            priceListAdapter = new PriceListAdapterF(getApplicationContext());

            listView.setAdapter(priceListAdapter);
        }

        tvTotal.setText(String.valueOf(total));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(BillActivityFinal.this);

                builder.setTitle("Proposals and complaints");

                final EditText input = new EditText(BillActivityFinal.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setHint("Tell us your ideas");
                builder.setView(input);

                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            try {
                                Values.mDatabase.child("idea").push().setValue(input.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(BillActivityFinal.this, "We will look at it", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (Exception e) {
                                Toast.makeText(Values.billActivity, "Error", Toast.LENGTH_LONG).show();
                            }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });



                if(isInternetOn()) {
                    builder.show();
                }else {
                    showToast();
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_righ);
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

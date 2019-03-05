package org.senani.priceapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class PriceListAdapterF extends BaseAdapter{

    private Context mContext;
    Filter valueFilter;
    private LayoutInflater inflater;

    ArrayList<ItemB> mData;

    public PriceListAdapterF(Context mContext) {
        this.mContext = mContext;
        mData=Values.itemBs;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v;

        v = View.inflate(mContext, R.layout.item_list_bill2, null);

        TextView tvName = (TextView) v.findViewById(R.id.tv_name);
        TextView tvPrice = (TextView) v.findViewById(R.id.tv_price);
        TextView tvAmount = (TextView) v.findViewById(R.id.tv_amount);
        Button adBtn = (Button) v.findViewById(R.id.add_btn);

        final ItemB story=mData.get(position);

        tvName.setText(story.getN());
        tvPrice.setText(String.valueOf(story.getP())+"0");
        tvAmount.setText("Qty "+String.valueOf(story.getQ()));

        adBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    double amount = Double.valueOf(story.getQ());
                    Values.billActivity.total -= amount * story.getP();
                    Values.billActivity.tvTotal.setText(String.valueOf(Values.billActivity.total));
                    Values.billActivityF.tvTotal.setText(String.valueOf(Values.billActivity.total));

                    Values.itemBs.remove(position);
                    notifyDataSetChanged();

                    Toast.makeText(Values.billActivityF, story.getN() + "\nRemoved from bill", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(Values.billActivityF, "Error", Toast.LENGTH_LONG).show();
                }
                
            }
        });

        return v;
    }


}

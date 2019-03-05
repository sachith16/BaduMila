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


public class PriceListAdapterB extends BaseAdapter implements Filterable{

    private Context mContext;
    Filter valueFilter;
    private LayoutInflater inflater;

    ArrayList<Item> mData;

    public PriceListAdapterB(Context mContext) {
        this.mContext = mContext;
        mData=Values.items;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;

        v = View.inflate(mContext, R.layout.item_list_bill, null);

        TextView tvName = (TextView) v.findViewById(R.id.tv_name);
        TextView tvPrice = (TextView) v.findViewById(R.id.tv_price);
        Button adBtn = (Button) v.findViewById(R.id.add_btn);

        final Item story=mData.get(position);

        tvName.setText(story.getN());
        tvPrice.setText(String.valueOf(story.getP())+"0");

        adBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Values.billActivity);
                builder.setTitle("Quantity");

                final EditText input = new EditText(Values.billActivity);
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            double amount=Double.valueOf(input.getText().toString());
                            Values.billActivity.total += amount*story.getP();
                            Values.billActivity.tvTotal.setText(String.valueOf(Values.billActivity.total));

                            ItemB itemB= new ItemB(story.getN(),story.getP(),story.getT(),amount);

                            Values.itemBs.add(itemB);

                            Toast.makeText(Values.billActivity,story.getN()+"\nAdded to bill", Toast.LENGTH_SHORT).show();

                        }catch (Exception e){
                            Toast.makeText(Values.billActivity,"Invalid amount", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        return v;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();

                    if (constraint != null && constraint.length() > 0) {
                        ArrayList<Item> filterList = new ArrayList<Item>();

                        for (int i = 0; i < Values.items.size(); i++) {
                            if ((Values.items.get(i).getN()+" "+Values.items.get(i).getT()).contains(constraint.toString().toUpperCase())) {
                                filterList.add(Values.items.get(i));
                            }
                        }
                        results.count = filterList.size();
                        results.values = filterList;
                    } else {
                        results.count = Values.items.size();
                        results.values = Values.items;
                    }
                    return results;

                }

                @Override
                protected void publishResults(CharSequence constraint,
                                              FilterResults results) {
                    mData = (ArrayList<Item>) results.values;
                    notifyDataSetChanged();
                }
            };
        }
        return valueFilter;
    }
}

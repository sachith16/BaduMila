package org.senani.priceapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;


public class PriceListAdapter extends BaseAdapter implements Filterable{

    private Context mContext;
    Filter valueFilter;
    private LayoutInflater inflater;

    ArrayList<Item> mData;

    public PriceListAdapter(Context mContext) {
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

            v = View.inflate(mContext, R.layout.item_list, null);

            TextView tvName = (TextView) v.findViewById(R.id.tv_name);
            TextView tvPrice = (TextView) v.findViewById(R.id.tv_price);

            Item story=mData.get(position);

            tvName.setText(story.getN());
            tvPrice.setText(String.valueOf(story.getP())+"0");

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

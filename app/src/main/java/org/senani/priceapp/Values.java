package org.senani.priceapp;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by sachith on 3/5/18.
 */

public class Values {
    public static ArrayList<Item> items;
    public static ArrayList<ItemB> itemBs;

    public static DatabaseReference mDatabase;
    public static ConnectivityManager cm;

    public static BillActivity billActivity;
    public static BillActivityFinal billActivityF;
}

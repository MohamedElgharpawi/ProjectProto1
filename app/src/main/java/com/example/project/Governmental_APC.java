package com.example.project;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Governmental_APC extends AppCompatActivity implements AdapterView.OnItemClickListener  , Toolbar.OnMenuItemClickListener {
ListView govern_ListView;
ArrayList<Cities> allcities;
    Cities c1 ;
    Toolbar toolbar_profile_tour;
    ProgressDialog dialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_governmental__apc);

        set_shared();

        if(!isConnected(this)) buildDialog(this).show();
        else {
            //Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
            // setContentView(R.layout.activity_main);
        }

        govern_ListView=findViewById(R.id.govern_ListView);
    toolbar_profile_tour=findViewById(R.id.toolbar_profile_tor);
    dialog = new ProgressDialog(this);
    setSupportActionBar(toolbar_profile_tour);
    toolbar_profile_tour.setOnMenuItemClickListener(this);

        //Cities ob= new Cities(1, "aa" , "aa");
        //allcities.add(ob);
        AndroidNetworking.initialize(getApplicationContext());

    dialog.setTitle("loading..");
        dialog.setMessage("please wait..");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        allcities= new ArrayList<>();
        final int[] lenght_arr = new int[1];
        AndroidNetworking.get("http://13.52.79.70/api/public/place/governorates")
            .setPriority(Priority.MEDIUM)
            .doNotCacheResponse()
                .getResponseOnlyFromNetwork()
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    ArrayList<Cities> try_city= new ArrayList<>();
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("list test" , String.valueOf(response.length()));
                        lenght_arr[0] = response.length();
                        for(int i = 0 ; i < response.length() ; i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                int id = object.getInt("governorate_id");
                                String name = object.getString("name");
                                String img= object.getString("img");
                                c1=new Cities(id, img,name);
                                try_city.add(c1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("list test" , "error");
                            }
                            Log.e("list test" , "done");
                            allcities.add(c1);
                        }
                        allcities= try_city;
                        governmental_Adapter adapter=new governmental_Adapter(getApplicationContext(),R.layout.places_raw_design,try_city);
                        govern_ListView.setAdapter(adapter);

                        dialog.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.dismiss();
                        Log.e("list test" , "no");
                        Toast.makeText(getApplicationContext(), "something went wrong please try again", Toast.LENGTH_SHORT).show();
                    }
                });
        govern_ListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent= new Intent(this , cities_page_APC.class);
        intent.putExtra("governmental_id" , allcities.get(position).getId());
        startActivity(intent , new Bundle());
    }
    @Override
    public void onBackPressed() {


        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("EXIT").setMessage("Exit the Application").setIcon(R.drawable.egybk).setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               moveTaskToBack(true);
               finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lpg_out_menu , menu);
        return true;
    }
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if(menuItem.getItemId()== R.id.log_out){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor= preferences.edit();
            editor.remove("is_logged_t");
            editor.clear();
            editor.apply();
            editor.commit();
            Intent intent = new Intent(this , page2APC.class);
            startActivity(intent);
        }
        return true;
    }
    public void set_shared(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("is_logged_t",true);
        editor.putString("page_t" , "t");
        editor.apply();
    }
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null &&mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }


}

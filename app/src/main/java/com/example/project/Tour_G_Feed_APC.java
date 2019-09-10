package com.example.project;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Tour_G_Feed_APC extends AppCompatActivity {
    ListView G_f_list;
    ArrayList<feedback_JC> allfeeds;
    ProgressDialog dialog ;
    ArrayList<feedback_JC> arr1= new ArrayList<>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour__g__feed__apc);

        if(!isConnected(this)) buildDialog(this).show();
        else {
            // Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
            //setContentView(R.layout.activity_main);
        }
        dialog= new ProgressDialog(this);

        G_f_list=findViewById(R.id.G_f_list);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int id = preferences.getInt("tour_guide_id" ,0 );

        AndroidNetworking.get("http://13.52.79.70/api/public/tour-guide/"+id+"/reviews")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    ArrayList<feedback_JC> feed = new ArrayList<>();
                    feedback_JC feedback_jc;
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("list feed" , String.valueOf(response.length()));
                        for(int i =0 ; i < response.length() ; i++){
                            try {
                                feedback_jc=new feedback_JC(response.getJSONObject(i).getString("details"));
                                feed.add(feedback_jc);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                        //  ArrayAdapter<String> feedback= new ArrayAdapter<>(getApplicationContext() , R.layout.support_simple_spinner_dropdown_item , feed);
                        g_feedback_adaptor feedback=new g_feedback_adaptor(getApplicationContext(),R.layout.tour_g_feed_row,feed);
                        G_f_list.setAdapter(feedback);
                        arr1= feed ;
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext() , anError.getErrorDetail()+"-----"+ anError.getErrorBody() , Toast.LENGTH_LONG).show();
                        Log.e("feedback" , anError.getErrorDetail()+"-----"+ anError.getErrorBody() );
                    }
                });



//feedback_JC f1=new feedback_JC("aaaaaa");
  //      feedback_JC f2=new feedback_JC("bbbbbbbb");
       allfeeds=new ArrayList<>();

    //    allfeeds.add(f1);
      //  allfeeds.add(f2);
        g_feedback_adaptor adaptor = new g_feedback_adaptor(this,R.layout.tour_g_feed_row,allfeeds);
        G_f_list.setAdapter(adaptor);
    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext() , tourguid_profile_APC.class);
        startActivity(intent);
    }
}

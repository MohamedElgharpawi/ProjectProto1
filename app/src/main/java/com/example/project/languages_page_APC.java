package com.example.project;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class languages_page_APC extends AppCompatActivity implements View.OnClickListener {
    private CheckBox arabic;
    private CheckBox english;
    private CheckBox spanish;
    private CheckBox french;
    private CheckBox german;
    private CheckBox italian;
    private CheckBox russain;
    private CheckBox porto;
    private CheckBox turky;
    private CheckBox duthc;
    private CheckBox swedish;
    private CheckBox polish;
    private Button submit_lang;

    ArrayList<String> langs= new ArrayList<>();
    ProgressDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.languages_page);
        if(!isConnected(this)) buildDialog(this).show();
        else {
           // Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
            //setContentView(R.layout.activity_main);
        }
        dialog= new ProgressDialog(this);
        arabic=findViewById(R.id.arabic);
        english=findViewById(R.id.english);
        spanish=findViewById(R.id.spanish);
        french=findViewById(R.id.french);
        german=findViewById(R.id.german);
        italian=findViewById(R.id.italian);
        russain=findViewById(R.id.russian);
        porto=findViewById(R.id.porto);
        turky=findViewById(R.id.turky);
        duthc=findViewById(R.id.dutch);
        swedish=findViewById(R.id.swedish);
        polish=findViewById(R.id.polish);
        submit_lang= findViewById(R.id.submit_lang);
        submit_lang.setOnClickListener(this);
        arabic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(arabic.isChecked()){
                    langs.add(arabic.getText().toString());
                }else{
                    if(langs.contains(arabic.getText().toString())){
                        int index = langs.indexOf(arabic.getText().toString());
                        langs.remove(index);
                    }
                }
            }
        });
        english.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(english.isChecked()){
                    langs.add(english.getText().toString());
                }else{
                    if(langs.contains(english.getText().toString())){
                        int index = langs.indexOf(english.getText().toString());
                        langs.remove(index);
                    }
                }
            }
        });
        spanish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(spanish.isChecked()){
                    langs.add(spanish.getText().toString());
                }else{
                    if(langs.contains(spanish.getText().toString())){
                        int index = langs.indexOf(spanish.getText().toString());
                        langs.remove(index);
                    }
                }
            }
        });
        french.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(french.isChecked()){
                    langs.add(french.getText().toString());
                }else{
                    if(langs.contains(french.getText().toString())){
                        int index = langs.indexOf(french.getText().toString());
                        langs.remove(index);
                    }
                }
            }
        });
        german.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(german.isChecked()){
                    langs.add(german.getText().toString());
                }else{
                    if(langs.contains(german.getText().toString())){
                        int index = langs.indexOf(german.getText().toString());
                        langs.remove(index);
                    }
                }
            }
        });
        italian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(italian.isChecked()){
                    langs.add(italian.getText().toString());
                }else{
                    if(langs.contains(italian.getText().toString())){
                        int index = langs.indexOf(italian.getText().toString());
                        langs.remove(index);
                    }
                }
            }
        });
        russain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(russain.isChecked()){
                    langs.add(russain.getText().toString());
                }else{
                    if(langs.contains(russain.getText().toString())){
                        int index = langs.indexOf(russain.getText().toString());
                        langs.remove(index);
                    }
                }
            }
        });
        porto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(porto.isChecked()){
                    langs.add(porto.getText().toString());
                }else{
                    if(langs.contains(porto.getText().toString())){
                        int index = langs.indexOf(porto.getText().toString());
                        langs.remove(index);
                    }
                }
            }
        });
        turky.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(turky.isChecked()){
                    langs.add(turky.getText().toString());
                }else{
                    if(langs.contains(turky.getText().toString())){
                        int index = langs.indexOf(turky.getText().toString());
                        langs.remove(index);
                    }
                }
            }
        });
        duthc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(duthc.isChecked()){
                    langs.add(duthc.getText().toString());
                }else{
                    if(langs.contains(duthc.getText().toString())){
                        int index = langs.indexOf(duthc.getText().toString());
                        langs.remove(index);
                    }
                }
            }
        });
        swedish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(swedish.isChecked()){
                    langs.add(swedish.getText().toString());
                }else{
                    if(langs.contains(swedish.getText().toString())){
                        int index = langs.indexOf(swedish.getText().toString());
                        langs.remove(index);
                    }
                }
            }
        });
        polish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(polish.isChecked()){
                    langs.add(polish.getText().toString());
                }else{
                    if(langs.contains(polish.getText().toString())){
                        int index = langs.indexOf(polish.getText().toString());
                        langs.remove(index);
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        Log.e("arr" , String.valueOf(langs.size()));
        //Intent intent= new Intent(this , );
        //intent.putExtra("langs_array", langs);
        //startActivity(intent);
        if(v.getId()==R.id.submit_lang){
            if(getIntent().getIntExtra("check_which_page" , 1)==0){

                StringBuilder stringBuilder=new StringBuilder();
                Log.e("langs size" , String.valueOf(langs.size()));
                for(int i=0;i< langs.size();i++) {
                    stringBuilder.append(langs.get(i));
                    if(langs.size()==(i+1)){

                    }else{
                        stringBuilder.append(", ");
                    }
                }
                Log.e("langs" , stringBuilder.toString());


                Map<String , String> map= new HashMap<>();
                map.put("languages",stringBuilder.toString());


                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                // SharedPreferences.Editor editor= preferences.edit();
                int id = preferences.getInt("tour_guide_id",0);
                Log.e("langs id", String.valueOf(id));

                dialog.setTitle("loading..");
                dialog.setMessage("please wait..");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                //dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();
                Log.e("langs progress", "so ?");


                AndroidNetworking.post("http://13.52.79.70/api/public/tour-guide/"+String.valueOf(id)+"/language/add?languages="+stringBuilder.toString())
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                dialog.dismiss();
                                try {
                                    if(response.getInt("result") == 0){
                                        Toast.makeText(getApplicationContext() , "something went wrong , please try again" , Toast.LENGTH_LONG ).show();
                                    }else{
                                       Intent intent= new Intent(getApplicationContext() , tourguid_profile_APC.class);
                                        startActivity(intent);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });





            }
            else{
                StringBuilder stringBuilder=new StringBuilder();
                Log.e("langs size" , String.valueOf(langs.size()));
                for(int i=0;i< langs.size();i++) {
                    stringBuilder.append(langs.get(i));
                    if(langs.size()==(i+1)){

                    }else{
                        stringBuilder.append(", ");
                    }
                        }
                Log.e("langs" , stringBuilder.toString());


                Map<String , String> map= new HashMap<>();
                map.put("languages",stringBuilder.toString());


                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
               // SharedPreferences.Editor editor= preferences.edit();
               int id = preferences.getInt("tour_guide_id",0);
               Log.e("langs id", String.valueOf(id));

                dialog.setTitle("loading..");
                dialog.setMessage("please wait..");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                //dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();
                Log.e("langs progress", "so ?");


                AndroidNetworking.post("http://13.52.79.70/api/public/tour-guide/"+String.valueOf(id)+"/language/add?languages="+stringBuilder.toString())
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                dialog.dismiss();
                                try {
                                    if(response.getInt("result") == 0){
                                        Toast.makeText(getApplicationContext() , "something went wrong , please try again" , Toast.LENGTH_LONG ).show();
                                    }else{
//                                        Toast.makeText(getApplicationContext() , String.valueOf(response.getInt("result")) , Toast.LENGTH_LONG ).show();
                                        Intent intent= new Intent(getApplicationContext()  , tour_gaide_locations_APC.class);
                                        startActivity(intent);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });


                Log.e("arr2" , String.valueOf(langs.size()));
//                Intent i=new Intent(this,tour_gaide_locations_APC.class);


  //              i.putExtra("langs",langs);
    //            i.putExtra("langs_bool",true);
      //          i.putExtra("index",langs.size());

        //        startActivity(i);

            }}
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
        Intent intent;
        if(getIntent().getIntExtra("check_which_page" , 1)==0){
             intent = new Intent(this , tourguid_profile_APC.class);
            startActivity(intent);
        }else {
            intent = new Intent(this , tourguid_register.class);
            startActivity(intent);
        }

    }
}

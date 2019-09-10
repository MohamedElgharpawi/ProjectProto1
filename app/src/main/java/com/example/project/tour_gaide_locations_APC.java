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

public class tour_gaide_locations_APC extends AppCompatActivity implements View.OnClickListener {
    private CheckBox alex;
    private CheckBox aswan;
    private CheckBox asyut;
    private CheckBox behera;
    private CheckBox bnysuaf;
    private CheckBox cairo;
    private CheckBox dakahlia;
    private CheckBox damietta;
    private CheckBox faiyum;
    private CheckBox gharbia;
    private CheckBox giza;
    private CheckBox ismailia;
    private CheckBox kfr;
    private CheckBox luxor;
    private CheckBox matruh;
    private CheckBox minya;
    private CheckBox monufia;
    private CheckBox new_valley;
    private CheckBox north_sinai;
    private CheckBox port_said;
    private CheckBox qalyubia;
    private CheckBox qena;
    private CheckBox red;
    private CheckBox sharqia;
    private CheckBox sohag;
    private CheckBox south_sinnia;
    private CheckBox suez;
    private Button submit_location;

    ArrayList<String> locations= new ArrayList<>();
    ProgressDialog dialog ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tour_gaide_locations);

        if(!isConnected(this)) buildDialog(this).show();
        else {
           // Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
            //setContentView(R.layout.activity_main);
        }
        dialog = new ProgressDialog(this);
        alex = findViewById(R.id.alex);
        aswan=findViewById(R.id.aswan);
        asyut=findViewById(R.id.asyut);
        behera=findViewById(R.id.beheira);
        bnysuaf=findViewById(R.id.beni_Suef);
        cairo=findViewById(R.id.cairo);
        dakahlia=findViewById(R.id.dakahlia);
        damietta=findViewById(R.id.damietta);
        faiyum=findViewById(R.id.faiyum);
        gharbia=findViewById(R.id.gharbia);
        giza=findViewById(R.id.giza);
        ismailia=findViewById(R.id.ismailia);
        kfr=findViewById(R.id.kfr);
        luxor=findViewById(R.id.luxor);
        matruh=findViewById(R.id.matruh);
        minya=findViewById(R.id.minya);
        monufia=findViewById(R.id.monufia);
        new_valley=findViewById(R.id.newValley);
        north_sinai=findViewById(R.id.northSinai);
        port_said=findViewById(R.id.portSaid);
        qalyubia=findViewById(R.id.qalyubia);
        qena=findViewById(R.id.qena);
        red=findViewById(R.id.redSea);
        sharqia=findViewById(R.id.sharqia);
        sohag=findViewById(R.id.sohag);
        south_sinnia=findViewById(R.id.southSinai);
        suez=findViewById(R.id.suez);
        submit_location=findViewById(R.id.submit_location);
        submit_location.setOnClickListener(this);

        alex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(alex.isChecked()){
                    locations.add(alex.getText().toString());
                }else{
                    if(locations.contains(alex.getText().toString())){
                        int index = locations.indexOf(alex.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        aswan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(aswan.isChecked()){
                    locations.add(aswan.getText().toString());
                }else{
                    if(locations.contains(aswan.getText().toString())){
                        int index = locations.indexOf(aswan.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        asyut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(asyut.isChecked()){
                    locations.add(asyut.getText().toString());
                }else{
                    if(locations.contains(asyut.getText().toString())){
                        int index = locations.indexOf(asyut.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        behera.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(behera.isChecked()){
                    locations.add(behera.getText().toString());
                }else{
                    if(locations.contains(behera.getText().toString())){
                        int index = locations.indexOf(behera.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        bnysuaf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(bnysuaf.isChecked()){
                    locations.add(bnysuaf.getText().toString());
                }else{
                    if(locations.contains(bnysuaf.getText().toString())){
                        int index = locations.indexOf(bnysuaf.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        cairo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cairo.isChecked()){
                    locations.add(cairo.getText().toString());
                }else{
                    if(locations.contains(cairo.getText().toString())){
                        int index = locations.indexOf(cairo.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        dakahlia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(dakahlia.isChecked()){
                    locations.add(dakahlia.getText().toString());
                }else{
                    if(locations.contains(dakahlia.getText().toString())){
                        int index = locations.indexOf(dakahlia.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        damietta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(damietta.isChecked()){
                    locations.add(damietta.getText().toString());
                }else{
                    if(locations.contains(damietta.getText().toString())){
                        int index = locations.indexOf(damietta.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        faiyum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(faiyum.isChecked()){
                    locations.add(faiyum.getText().toString());
                }else{
                    if(locations.contains(faiyum.getText().toString())){
                        int index = locations.indexOf(faiyum.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        gharbia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(gharbia.isChecked()){
                    locations.add(gharbia.getText().toString());
                }else{
                    if(locations.contains(gharbia.getText().toString())){
                        int index = locations.indexOf(gharbia.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        giza.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(giza.isChecked()){
                    locations.add(giza.getText().toString());
                }else{
                    if(locations.contains(giza.getText().toString())){
                        int index = locations.indexOf(giza.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        ismailia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(ismailia.isChecked()){
                    locations.add(ismailia.getText().toString());
                }else{
                    if(locations.contains(ismailia.getText().toString())){
                        int index = locations.indexOf(ismailia.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        kfr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(kfr.isChecked()){
                    locations.add(kfr.getText().toString());
                }else{
                    if(locations.contains(kfr.getText().toString())){
                        int index = locations.indexOf(kfr.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        luxor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(luxor.isChecked()){
                    locations.add(luxor.getText().toString());
                }else{
                    if(locations.contains(luxor.getText().toString())){
                        int index = locations.indexOf(luxor.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        matruh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(matruh.isChecked()){
                    locations.add(matruh.getText().toString());
                }else{
                    if(locations.contains(matruh.getText().toString())){
                        int index = locations.indexOf(matruh.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        minya.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(minya.isChecked()){
                    locations.add(minya.getText().toString());
                }else{
                    if(locations.contains(minya.getText().toString())){
                        int index = locations.indexOf(minya.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        monufia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(monufia.isChecked()){
                    locations.add(monufia.getText().toString());
                }else{
                    if(locations.contains(monufia.getText().toString())){
                        int index = locations.indexOf(monufia.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        new_valley.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(new_valley.isChecked()){
                    locations.add(new_valley.getText().toString());
                }else{
                    if(locations.contains(new_valley.getText().toString())){
                        int index = locations.indexOf(new_valley.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        north_sinai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(north_sinai.isChecked()){
                    locations.add(north_sinai.getText().toString());
                }else{
                    if(locations.contains(north_sinai.getText().toString())){
                        int index = locations.indexOf(north_sinai.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        port_said.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(port_said.isChecked()){
                    locations.add(port_said.getText().toString());
                }else{
                    if(locations.contains(port_said.getText().toString())){
                        int index = locations.indexOf(port_said.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        qalyubia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(qalyubia.isChecked()){
                    locations.add(qalyubia.getText().toString());
                }else{
                    if(locations.contains(qalyubia.getText().toString())){
                        int index = locations.indexOf(qalyubia.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        qena.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(qena.isChecked()){
                    locations.add(qena.getText().toString());
                }else{
                    if(locations.contains(qena.getText().toString())){
                        int index = locations.indexOf(qena.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        red.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(red.isChecked()){
                    locations.add(red.getText().toString());
                }else{
                    if(locations.contains(red.getText().toString())){
                        int index = locations.indexOf(red.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        sharqia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(sharqia.isChecked()){
                    locations.add(sharqia.getText().toString());
                }else{
                    if(locations.contains(sharqia.getText().toString())){
                        int index = locations.indexOf(sharqia.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        sohag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(sohag.isChecked()){
                    locations.add(sohag.getText().toString());
                }else{
                    if(locations.contains(sohag.getText().toString())){
                        int index = locations.indexOf(sohag.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        south_sinnia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(south_sinnia.isChecked()){
                    locations.add(south_sinnia.getText().toString());
                }else{
                    if(locations.contains(south_sinnia.getText().toString())){
                        int index = locations.indexOf(south_sinnia.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

        suez.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(suez.isChecked()){
                    locations.add(suez.getText().toString());
                }else{
                    if(locations.contains(suez.getText().toString())){
                        int index = locations.indexOf(suez.getText().toString());
                        locations.remove(index);
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.submit_location){
            if(getIntent().getIntExtra("check_which_page_places" , 1)==0){


                StringBuilder stringBuilder=new StringBuilder();
                Log.e("langs size" , String.valueOf(locations.size()));
                for(int i=0;i< locations.size();i++) {
                    stringBuilder.append(locations.get(i));
                    if(locations.size()==(i+1)){

                    }else{
                        stringBuilder.append(", ");
                    }
                }
                Log.e("locs" , stringBuilder.toString());


                Map<String , String> map= new HashMap<>();
                map.put("governorates",stringBuilder.toString());

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                int id = preferences.getInt("tour_guide_id",0);

                dialog.setTitle("loading..");
                dialog.setMessage("please wait..");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();

                AndroidNetworking.post("http://13.52.79.70/api/public/tour-guide/"+String.valueOf(id)+"/add-governorate?governorates="+stringBuilder.toString())
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                dialog.dismiss();
                                try {
                                    if(response.getInt("result") == 0){
                                        Toast.makeText(getApplicationContext() , "something went wrong , please try again or make sure you didn't select a selected place" , Toast.LENGTH_LONG ).show();
                                    }else{
                                        Toast.makeText(getApplicationContext() , String.valueOf(response.getInt("result")) , Toast.LENGTH_LONG ).show();
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
                Log.e("langs size" , String.valueOf(locations.size()));
                for(int i=0;i< locations.size();i++) {
                    stringBuilder.append(locations.get(i));
                    if(locations.size()==(i+1)){

                    }else{
                        stringBuilder.append(", ");
                    }
                }
                Log.e("locs" , stringBuilder.toString());


                Map<String , String> map= new HashMap<>();
                map.put("governorates",stringBuilder.toString());

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                int id = preferences.getInt("tour_guide_id",0);

                Log.e("langs id", String.valueOf(id));

                dialog.setTitle("loading..");
                dialog.setMessage("please wait..");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();

                Log.e("gov_name", "so ?");

                AndroidNetworking.post("http://13.52.79.70/api/public/tour-guide/"+String.valueOf(id)+"/add-governorate?governorates="+stringBuilder.toString())
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
                                  //      Toast.makeText(getApplicationContext() , String.valueOf(response.getInt("result")) , Toast.LENGTH_LONG ).show();
                                        Intent intent= new Intent(getApplicationContext()  , tourguid_log_in_APC.class);
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




//                Log.e("arr2" , String.valueOf(locations.size()));
  //              Intent i=new Intent(this,tourguid_profile_APC.class);
    //            i.putExtra("locations",locations);
      //          i.putExtra("loc",true);
        //        i.putExtra("index",locations.size());

          //      startActivity(i);

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
        if(getIntent().getIntExtra("check_which_page_places" , 1)==0){
            intent = new Intent(this , tourguid_profile_APC.class);
            startActivity(intent);
        }else {
            intent = new Intent(this , tourguid_register.class);
            startActivity(intent);
        }

    }
}

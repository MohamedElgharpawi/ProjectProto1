package com.example.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class page2APC extends AppCompatActivity implements View.OnClickListener {
    private Button tour_btn;
    private Button tourist_btn;
    public String  chech_which_page_shared = "r";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);
        if(is_logged()==true){
            if(chech_which_page_shared.equals("t")){
                Intent intent= new Intent(this , Governmental_APC.class);
                startActivity(intent);
            }else if(chech_which_page_shared.equals("g")){
                Intent intent= new Intent(this , tourguid_profile_APC.class);
                startActivity(intent);
            }

        }

        setshared();

        if(!isConnected(this)) buildDialog(this).show();
        else {
           // Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
            //setContentView(R.layout.activity_main);
        }
        tour_btn=findViewById(R.id.tou_btn);
        tourist_btn=findViewById(R.id.tourist_btn);
        tourist_btn.setOnClickListener(this);
        tour_btn.setOnClickListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("activity" , "onrestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("activity" , "onpuase");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("activity" , "onstop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("activity" , "onresume");
        if(is_logged()==true){
            if(chech_which_page_shared.equals("t")){
                Intent intent= new Intent(this , Governmental_APC.class);
                startActivity(intent);
            }else if(chech_which_page_shared.equals("g")){
                Intent intent= new Intent(this , tourguid_profile_APC.class);
                startActivity(intent);
            }

        }

        setshared();

        if(!isConnected(this)) buildDialog(this).show();
        else {
            // Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
            //setContentView(R.layout.activity_main);
        }
        tour_btn=findViewById(R.id.tou_btn);
        tourist_btn=findViewById(R.id.tourist_btn);
        tourist_btn.setOnClickListener(this);
        tour_btn.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        Intent intent;
        if(v.getId()== R.id.tou_btn){
            intent= new Intent(this , tourguid_log_in_APC.class);
            startActivity(intent);
           // Log.e("pag2" , "tour guid");
        }else if(v.getId()== R.id.tourist_btn){
            intent= new Intent(this , tourist_log_in_APC.class);
            startActivity(intent);
            //Log.e("pag2" , "should've opened by now !");
        }
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
        moveTaskToBack(true);
        finish();
    }


    public void setshared(){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putBoolean("loged" , true);
        editor.apply();
    }

    public boolean is_logged(){
        String x = "r" ;
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        boolean checkt=preferences.getBoolean("is_logged_t",false);//t
        boolean check=preferences.getBoolean("is_logged",false);//g
        String page_check_t = preferences.getString("page_t","r");
        String page_check = preferences.getString("page","r");
        Log.e("check" , page_check_t);
        Log.e("check" , page_check);

        if(page_check_t.equals("t")){
            x= "t";
        }else if(page_check.equals("g")){
            x="g";


        }
        chech_which_page_shared= x;
        Log.e("check" , chech_which_page_shared);
        //Log.e("check" , String.valueOf(check));
        if(checkt==true){

            return true;

        }
        else if(check==true){
            return true;

        }else {return false;}



    }


}

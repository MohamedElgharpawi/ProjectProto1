package com.example.project;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Forget_pass_APC extends AppCompatActivity implements View.OnClickListener {
    EditText forget_pass_et;
    Button forget_pass_btn;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass__apc);
        if(!isConnected(this)) buildDialog(this).show();
        else {
            // Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
            //setContentView(R.layout.activity_main);
        }
    forget_pass_et=findViewById(R.id.forget_pass_et);
    forget_pass_btn=findViewById(R.id.forget_pass_btn);
    forget_pass_btn.setOnClickListener(this);
dialog=new ProgressDialog(this);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent ;
        if(getIntent().getStringExtra("flag").equals("ok")){
            intent= new Intent(getApplicationContext() , tourguid_log_in_APC.class);
            startActivity(intent);
        }else if(getIntent().getStringExtra("flag").equals("no")){
            intent= new Intent(getApplicationContext() , tourist_log_in_APC.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
       // Intent intent ;
        if (v.getId()==R.id.forget_pass_btn){
            if(getIntent().getStringExtra("flag").equals("ok")){
                dialog.setTitle("loading..");
                dialog.setMessage("please wait..");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();

                AndroidNetworking.post("http://13.52.79.70/api/public/tour-guide/forget-password?email="+forget_pass_et.getText().toString())
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getInt("sent") == 1){
                                        Toast.makeText(getApplicationContext() , "message will be sent to your mail" , Toast.LENGTH_LONG).show();
                                        Intent intent= new Intent(getApplicationContext() , tourguid_log_in_APC.class);
                                        startActivity(intent);
                                   dialog.dismiss();
                                    }else{
                                        Toast.makeText(getApplicationContext() , "please try again" , Toast.LENGTH_LONG).show();
                 dialog.dismiss();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                dialog.dismiss();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
dialog.dismiss();
                            }
                        });
            }else if(getIntent().getStringExtra("flag").equals("no")){
                Log.e("forget", "ok");
                dialog.setTitle("loading..");
                dialog.setMessage("please wait..");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();

                AndroidNetworking.post("http://13.52.79.70/api/public/tourist/forget-password?email="+forget_pass_et.getText().toString())
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getInt("sent") == 1){
                                        Toast.makeText(getApplicationContext() , "message will be sent to your mail" , Toast.LENGTH_LONG).show();
                                        Intent intent= new Intent(getApplicationContext() , tourist_log_in_APC.class);
                                        startActivity(intent);
                                        Log.e("forget", "1");
                                        dialog.dismiss();

                                    }else{
                                        Toast.makeText(getApplicationContext() , "please try again" , Toast.LENGTH_LONG).show();
                                        Log.e("forget", "0");
                                        dialog.dismiss();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e("forget", e.getMessage());
                                    dialog.dismiss();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(getApplicationContext() ,anError.getErrorDetail() , Toast.LENGTH_LONG).show();

                                dialog.dismiss();
                            }
                        });

            }
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


}

package com.example.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class feedback_bage_APC extends AppCompatActivity implements View.OnClickListener {

    private EditText feedback_edit;
    private RatingBar feedback_rate;
    private Button feedback_submit;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Slide());
        getWindow().setExitTransition(new Explode());
        }
        setContentView(R.layout.feedback_bage);
        if(!isConnected(this)) buildDialog(this).show();
        else {
            //Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
            //setContentView(R.layout.activity_main);
        }
        feedback_edit= findViewById(R.id.feedback_edit);
        feedback_submit=findViewById(R.id.feedback_edit_submit);
        feedback_rate= findViewById(R.id.feedback_rate);
        feedback_submit.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        int tourist_id = preferences.getInt("tourist_id" , 0);
        int tou_guide_id = preferences.getInt("tour_guide_id_for_tourist" , 0);
        Log.e("feedbacks" , String.valueOf(tou_guide_id));
        Log.e("feedbacks" , String.valueOf(tourist_id));
        Log.e("feedbacks" , String.valueOf(feedback_rate.getRating()));

                                                                  //public/tourist/1/guide-review/add?stars=5&details=excelent guide&guide_id=1

        AndroidNetworking.post("http://13.52.79.70/api/public/tourist/"+tourist_id+"/guide-review?stars="+(int)feedback_rate.getRating()+"&details="+feedback_edit.getText().toString()+"&guide_id="+tou_guide_id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getInt("result") == 1){
                                Intent intent= new Intent(getApplicationContext() , tour_gaide_details_page_APC.class);

                                startActivity(intent);

                            }else{
                                Toast.makeText(getApplicationContext() , "please try again" , Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext() , anError.getErrorDetail()+"-----"+ anError.getErrorBody() , Toast.LENGTH_LONG).show();
                        Log.e("feedback" , anError.getErrorDetail()+"-----"+ anError.getErrorBody() );
                    }
                });


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
        Intent intent = new Intent(this , tour_gaide_details_page_APC.class);
        startActivity(intent);
    }
}

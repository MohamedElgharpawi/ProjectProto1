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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class phone_edit_APC extends AppCompatActivity implements View.OnClickListener {
    ImageView cancle_phone_edit;
    EditText edit_phone;
    Button update_phone;
    private String phone_regex = "^((?!(#))[0-9]{11})$";
    ProgressDialog dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_edit__apc);

        if(!isConnected(this)) buildDialog(this).show();
        else {
           // Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
            //setContentView(R.layout.activity_main);
        }
        cancle_phone_edit=findViewById(R.id.cancle_phone_edit);
        edit_phone=findViewById(R.id.edit_phone);
        update_phone=findViewById(R.id.update_phone);
        cancle_phone_edit.setOnClickListener(this);
        update_phone.setOnClickListener(this);
        dialog = new ProgressDialog(this);


    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.cancle_phone_edit){

            Intent i =new Intent(this, tourguid_profile_APC.class);
            startActivity(i);

        }
        else if(v.getId()== R.id.update_phone){
            if(edit_phone.getText().toString().isEmpty()||!edit_phone.getText().toString().matches(phone_regex)){
                edit_phone.setError("Please Enter Correctly Formatted Phone!");
            }
            else{
                dialog.setTitle("loading..");
                dialog.setMessage("please wait..");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                int id = preferences.getInt("tour_guide_id",0);

                AndroidNetworking.put("http://13.52.79.70/api/public/tour-guide/"+id+"/phone?phone="+edit_phone.getText().toString())
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                dialog.dismiss();
                                try {
                                    // exists will be 0 or 1
                                    if(response.getInt("changed") == 0){
                                        Toast.makeText(getApplicationContext(),"please try again" ,Toast.LENGTH_LONG ).show();
                                    }else if(response.getInt("changed") == 1){
                                        Toast.makeText(getApplicationContext(),"phone number has been updated" ,Toast.LENGTH_LONG ).show();
                                        Intent intent1 = new Intent(getApplicationContext() , tourguid_profile_APC.class);
                                        startActivity(intent1);
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(),"something is wrong  , try again .." ,Toast.LENGTH_LONG ).show();
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(getApplicationContext() , "Server Error please try again later .." , Toast.LENGTH_LONG).show();
                                Log.e("phone" , anError.getErrorDetail()+"  "+anError.getResponse());
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this , tourguid_profile_APC.class);
        startActivity(intent);
    }
}

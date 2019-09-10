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
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class tourguid_log_in_APC extends AppCompatActivity implements View.OnClickListener {

    private EditText glog_email;
    private EditText glog_pass;
    private Button glog_submit;
    private TextView gsignup;
    private String email_regex="^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
    private String pass_regex="^.{4,20}$";
    ProgressDialog dialog ;
    TextView forget_pass_guide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourguid_log_in__apc);
        if(!isConnected(this)) buildDialog(this).show();
        else {

          //  Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
            //setContentView(R.layout.activity_main);
        }
        if (is_logged()==true){
            Intent intent=new Intent(this,tourguid_profile_APC.class);
            startActivity(intent);
        }
        dialog= new ProgressDialog(this);
        forget_pass_guide=findViewById(R.id.forget_pass_guide);
        forget_pass_guide.setOnClickListener(this);
        glog_email=findViewById(R.id.glog_email);
        glog_pass=findViewById(R.id.glog_pass);
        glog_submit=findViewById(R.id.glog_submit);
        glog_submit.setOnClickListener(this);
        gsignup = findViewById(R.id.gsignup);
        gsignup.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent ;
        if(v.getId() == R.id.glog_submit){

            if(!glog_email.getText().toString().isEmpty() && glog_email.getText().toString().trim().matches(email_regex) && !glog_pass.getText().toString().isEmpty() && glog_pass.getText().toString().trim().matches(pass_regex)){

                dialog.setTitle("loading..");
                dialog.setMessage("please wait..");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();

                Map<String , String> map= new HashMap<>();
                map.put("email" , glog_email.getText().toString());
                map.put("password" , glog_pass.getText().toString());

                AndroidNetworking.post("http://13.52.79.70/api/public/tour-guide/login?email="+glog_email.getText().toString()+"&password="+glog_pass.getText().toString())
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                dialog.dismiss();
                                try {
                                    // exists will be 0 or 1

                                    if(response.getInt("exists") == -2){
                                        Toast.makeText(getApplicationContext(),"your email is disbled" ,Toast.LENGTH_LONG ).show();
                                    }else if(response.getInt("exists") == 0){
                                        Toast.makeText(getApplicationContext() , "wrong email or password or try to register" , Toast.LENGTH_LONG).show();
                                    }else if(response.getInt("exists") == -1){
                                        Toast.makeText(getApplicationContext() , "please activate your account first" , Toast.LENGTH_LONG).show();
                                    }else{
                                        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                         SharedPreferences.Editor editor= preferences.edit();
                                        int id = response.getInt("exists");
                                        editor.remove("tour_guide_id");
                                        editor.putInt("tour_guide_id" , id);
                                        editor.apply();
                                        Intent intent1 = new Intent(getApplicationContext() , tourguid_profile_APC.class);
                                        startActivity(intent1);
                                    }

                                } catch (JSONException e) {
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"something went wrong" ,Toast.LENGTH_LONG ).show();
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(getApplicationContext() , anError.getErrorDetail()+"  "+anError.getResponse() , Toast.LENGTH_LONG).show();
                                Log.e("log in guide" , anError.getErrorDetail()+"  "+anError.getResponse());
                            }
                        });

            }
            else if(glog_email.getText().toString().isEmpty() || glog_email.getText().toString().trim().matches(email_regex)){
                glog_email.setError("please enter correct email");
                Toast.makeText(this , "wrong email" , Toast.LENGTH_SHORT).show();
            }else if(glog_pass.getText().toString().isEmpty() || !glog_pass.getText().toString().trim().matches(pass_regex)){
                glog_pass.setError("please make sure password is more than 4 characters");
                Toast.makeText(this , "wrong password" , Toast.LENGTH_SHORT).show();
            }

        }

        else if(v.getId()== R.id.gsignup){
            Intent i =new Intent(this, tourguid_register.class);
            i.putExtra("loc",false);
            startActivity(i);
        }
        else if(v.getId()==R.id.forget_pass_guide){
            Intent i =new Intent(this,Forget_pass_APC.class);
            i.putExtra("flag" , "ok");
            startActivity(i);


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
        Intent intent = new Intent(this , page2APC.class);
        startActivity(intent);
    }

    public boolean is_logged(){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        boolean check=preferences.getBoolean("is_logged",false);
        return check;



    }


}

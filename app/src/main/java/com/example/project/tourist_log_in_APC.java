package com.example.project;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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
import com.androidnetworking.interfaces.UploadProgressListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class tourist_log_in_APC extends AppCompatActivity implements View.OnClickListener {

    private EditText log_email;
    private EditText log_pass;
    private Button log_submit;
    private TextView log_sign_up;
    private String email_regex="^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
    private String pass_regex="^.{4,20}$";
    ProgressDialog dialog ;
    TextView forget_pass_tourist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourist_log_in_);
        if (is_logged()==true){

            Intent intent=new Intent(this,Governmental_APC.class);
            startActivity(intent);


        }

        forget_pass_tourist=findViewById(R.id.forget_pass_tourist);
        forget_pass_tourist.setOnClickListener(this);
        log_email=findViewById(R.id.log_email);
        log_pass=findViewById(R.id.log_pass);
        log_submit=findViewById(R.id.log_submit);
        log_sign_up=findViewById(R.id.log_sign_up);
        log_sign_up.setOnClickListener(this);
        log_submit.setOnClickListener(this);
        dialog = new ProgressDialog(this);
        if(!isConnected(this)) buildDialog(this).show();
        else {
         //   Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
            //setContentView(R.layout.activity_main);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent ;
        if(v.getId() == R.id.log_submit){
            // intent= new Intent(this , );
            //startActivity(intent);
            if(!log_email.getText().toString().isEmpty() && log_email.getText().toString().trim().matches(email_regex) && !log_pass.getText().toString().isEmpty() && log_pass.getText().toString().trim().matches(pass_regex) ){

                Map<String , String> map= new HashMap<>();
                map.put("email" , log_email.getText().toString());
                map.put("password" , log_pass.getText().toString());

                dialog.setTitle("loading..");
                dialog.setMessage("please wait..");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                //dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();
                //Log.e("langs progress", "so ?");

                AndroidNetworking.post("http://13.52.79.70/api/public/tourist/login?email="+log_email.getText().toString()+"&password="+log_pass.getText().toString())
                        .addBodyParameter(map)
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                dialog.dismiss();
                                int result ;
                                try {
                                     result = response.getInt("exists");
                                    if(result == -2){
                                        Toast.makeText(getApplicationContext(),"your email is disbled" ,Toast.LENGTH_LONG ).show();
                                    }else if(result == 0){
                                        Toast.makeText(getApplicationContext() , "wrong email or password or try to register" , Toast.LENGTH_LONG).show();
                                    }else if(result == -1){
                                        Toast.makeText(getApplicationContext() , "please activate your account first" , Toast.LENGTH_LONG).show();
                                    }else{
                                        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor editor= preferences.edit();
                                        int id = response.getInt("exists");
                                        editor.remove("tourist_id");
                                        editor.putInt("tourist_id" , id);
                                        editor.apply();
                                        Intent intent1 = new Intent(getApplicationContext() , Governmental_APC.class);
                                        startActivity(intent1);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                                           }

                            @Override
                            public void onError(ANError anError) {

                                Toast.makeText(getApplicationContext() , anError.getErrorDetail()+"  "+anError.getResponse() , Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        });

                //intent= new Intent(this , Governmental_APC.class);
                //startActivity(intent);

            }else if(log_email.getText().toString().isEmpty() || !log_email.getText().toString().trim().matches(email_regex)){
                log_email.setError("please enter correct email");
                Toast.makeText(this , "wrong email" , Toast.LENGTH_SHORT).show();
            }else if(log_pass.getText().toString().isEmpty() || !log_pass.getText().toString().trim().matches(pass_regex)){
                log_pass.setError("please make sure password is more than 4 characters");
                Toast.makeText(this , "wrong password" , Toast.LENGTH_SHORT).show();
            }

        }
         if(v.getId() == R.id.log_sign_up){
            intent= new Intent(this , tourist_register_APC.class);
            startActivity(intent);
        }
         if (v.getId()==R.id.forget_pass_tourist){
             Intent i =new Intent(this,Forget_pass_APC.class);
             i.putExtra("flag" , "no");
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
        Intent intent= new Intent(this , page2APC.class);
        startActivity(intent);
    }
    public boolean is_logged(){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        boolean check=preferences.getBoolean("is_logged_t",false);
        return check;



    }


}

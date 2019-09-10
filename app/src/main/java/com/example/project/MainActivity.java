package com.example.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

public class MainActivity extends AppCompatActivity {
    private CircularImageView logo1;
    private Animation animation;
    private Animation animation2;
    private TextView myname;
    private TextView andname;
    private TextView gharbawi_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(checkshared()==true){

            Intent intent= new Intent(getApplicationContext() , page2APC.class);
            startActivity(intent);

        }

        logo1=findViewById(R.id.logo1);
        myname=findViewById(R.id.myname);
        animation= AnimationUtils.loadAnimation(this , R.anim.mixinm);
        animation2=AnimationUtils.loadAnimation(this  , R.anim.slid_out_right);
        myname.setAnimation(animation2);
       // andname.setAnimation(animation2);
        //gharbawi_name.setAnimation(animation2);


        logo1.setAnimation(animation);
        Thread thread= new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(5500);
                    Intent intent= new Intent(getApplicationContext() , page2APC.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    Log.e("errro" , "error");
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
    public  Boolean checkshared(){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        boolean check=  preferences.getBoolean("loged" , false);
        return check;
    }

}



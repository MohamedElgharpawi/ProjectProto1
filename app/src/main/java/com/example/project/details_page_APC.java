package com.example.project;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class details_page_APC extends AppCompatActivity implements View.OnClickListener , Toolbar.OnMenuItemClickListener {
    public static final int REQUEST_CODE = 1234;
    private ImageView details_img;
    private TextView details_text;
    private Button details_submit , details_feedback;
    private RatingBar place_rate ;
    Toolbar mytoolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP ){
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Slide());
            getWindow().setExitTransition(new Explode());
        }
        setContentView(R.layout.details_page);
        if(!isConnected(this)) buildDialog(this).show();
        else {
          //  Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
            //setContentView(R.layout.activity_main);
        }

        mytoolbar=findViewById(R.id.toolbar_profile_tor2);
        setSupportActionBar(mytoolbar);
        mytoolbar.setOnMenuItemClickListener(this);
        details_img=findViewById(R.id.details_img);
        details_text=findViewById(R.id.details_text);
        place_rate = findViewById(R.id.place_rate);
        details_feedback = findViewById(R.id.details_feedback);
        details_feedback.setOnClickListener(this);
        place_rate.setNumStars(5);
        place_rate.setMax(5);

        places ob = (places) getIntent().getSerializableExtra("city_object");

        details_text.setText("name : "+ob.getName() +"\n"+"description : "+ob.getDescription()+"\n"+"restrictions : "+ob.getRestrictions() +"\n"+"open time : "+ob.getOpen_time() +"\n"+"ticket : "+ ob.getTicket()+"\n"+"governorate : "+ob.getGovernorate() +"\n"+"categories : " +ob.getCategories());
        Log.e("img", ""+ob.getPic());
        Picasso.get().load("http://13.52.79.70/storage/places-imgs/"+ob.getPic())
                .placeholder(R.drawable.alex)
                .error(R.drawable.logo)
                .into(details_img);

      //  details_img.setImageResource(getIntent().getIntExtra("test1" , 1));
        Log.e("rate1" , ob.getStars());
        if(ob.getStars().equals("null")){
            Log.e("rate1" , "no");

        }else{
            place_rate.setRating(Float.parseFloat(ob.getStars()));
        }
        details_submit=findViewById(R.id.details_submit);
        details_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.details_submit){
            AlertDialog builder= new AlertDialog.Builder(this).setIcon(R.drawable.egybk).setTitle("Choose your way").setMessage("please choose your way to get to your destnation ").
                    setPositiveButton("choose your tour guide", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //tour gaide :
                    Intent intent = new Intent(getApplicationContext() , nearby_tour_gaide_page_APC.class);
                    places ob = (places) getIntent().getSerializableExtra("city_object");
                    intent.putExtra("place_id" , ob.getId());
                    startActivity(intent);

                }
            }).setNegativeButton("get distenation route", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.LOCATION_HARDWARE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                        requestPermissions(new String[]{Manifest.permission.LOCATION_HARDWARE ,Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS } , REQUEST_CODE);

                        places ob = (places) getIntent().getSerializableExtra("city_object");

                        Uri gmmIntentUri = Uri.parse("geo:"+ob.getCoordinates()+"?q=" + Uri.encode(ob.getName()));
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(mapIntent);
                        }
                        //Uri gmmIntentUri = Uri.parse("google.streetview:cbll=29.9774614,31.1329645&cbp=0,30,0,0,-15");


//                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

  //                      mapIntent.setPackage("com.google.android.apps.maps");

               //         startActivity(mapIntent);
                    } else {

                        places ob = (places) getIntent().getSerializableExtra("city_object");

                        Uri gmmIntentUri = Uri.parse("geo:"+ob.getCoordinates()+"?q=" + Uri.encode(ob.getName()));
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(mapIntent);
                        }


                    }



                }
            }).show();

        }else if(v.getId()== R.id.details_feedback){
            places ob = (places) getIntent().getSerializableExtra("city_object");
            Intent intent = new Intent(getApplicationContext() , Places_feedback_APC.class);
            intent.putExtra("place_id_feed" , ob.getId());
            startActivity(intent);

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
        Intent intent = new Intent(this , cities_page_APC.class);
        intent.putExtra("governmental_id",getIntent().getIntExtra("governmental_id" , 0) );
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lpg_out_menu , menu);
        return true;
    }
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if(menuItem.getItemId()== R.id.log_out){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor= preferences.edit();
            editor.remove("is_logged_t");editor.clear();
            editor.apply();

            Intent intent = new Intent(this , page2APC.class);
            startActivity(intent);
        }
        return true;
    }
    public boolean isLocationServiceEnabled() {
        LocationManager lm = (LocationManager)
                this.getSystemService(Context.LOCATION_SERVICE);
        String provider = lm.getBestProvider(new Criteria(), true);

        return (!LocationManager.PASSIVE_PROVIDER.equals(provider));
    }
    public boolean isGPSEnabled(Context mContext)
    {
        LocationManager lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}

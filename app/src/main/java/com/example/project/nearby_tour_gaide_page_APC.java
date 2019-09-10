package com.example.project;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.prefs.Preferences;

public class nearby_tour_gaide_page_APC extends AppCompatActivity implements AdapterView.OnItemClickListener  , GestureDetector.OnGestureListener {
    public static final int INT = 100;
    public static final int movemint = 100;
    private ListView nearby_tour_listl ;
    ArrayList<nearby_tour_db> arr = new ArrayList<>();
    nearby_tour_db ob ;
    GestureDetector detector;
    private View img;
    private View name_t;
    ProgressDialog dialog ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nearby_tour_gaide_page);
        if(!isConnected(this)) buildDialog(this).show();
        else {
           // Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
            //setContentView(R.layout.activity_main);
        }
        //arr.add(ob = new nearby_tour_db("aa" , R.drawable.egybk, "arabic"));
       // arr.add(ob = new nearby_tour_db("aab" , R.drawable.egybk, "arabic"));
       // arr.add(ob = new nearby_tour_db("aabc" , R.drawable.egybk, "arabic"));

        nearby_tour_listl= findViewById(R.id.nearby_tour_list);
        dialog= new ProgressDialog(this);

        dialog.setTitle("loading..");
        dialog.setMessage("please wait..");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        int place_id = getIntent().getIntExtra("place_id" , 0);
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        int tourist_id = preferences.getInt("tourist_id" , 0);
        Log.e("nearby" , String.valueOf(place_id));
        Log.e("nearby" , String.valueOf(tourist_id));
    AndroidNetworking.get("http://13.52.79.70/api/public/tourist/"+tourist_id+"/find-tour-guide?place_id="+place_id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObjectList(nearby_tour_db.class, new ParsedRequestListener<ArrayList<nearby_tour_db>>() {
                    @Override
                    public void onResponse(ArrayList<nearby_tour_db> response) {
                        Log.e("nearby" ,String.valueOf(response.size()) +"   size");
                        ArrayList<nearby_tour_db> tour_guides = new ArrayList<>();
                        tour_guides = response ;
                        custom_adapter_nearby_tour custom_adapter_nearby_tour = new custom_adapter_nearby_tour(getApplicationContext() , R.layout.nearby_tour_raw_design , tour_guides);
                        nearby_tour_listl.setAdapter(custom_adapter_nearby_tour);


                        arr= tour_guides ;
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

       // custom_adapter_nearby_tour custom_adapter_nearby_tour = new custom_adapter_nearby_tour(this , R.layout.nearby_tour_raw_design , arr);
       // nearby_tour_listl.setAdapter(custom_adapter_nearby_tour);
        nearby_tour_listl.setOnItemClickListener(this);
        detector=new GestureDetector(this);

         img = (ImageView)findViewById(R.id.list_img_tour_nearby);

        // get the common element for the transition in this activity
         name_t =(TextView) findViewById(R.id.list_name_tour_nearby);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}

                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}

                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();

        Intent intent= new Intent(this  , tour_gaide_details_page_APC.class);
        intent.putExtra("tour_guide_ob"  , arr.get(position));
        intent.putExtra("place_id_nearby" , getIntent().getIntExtra("place_id" , 0));

        SharedPreferences preferences1= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences1.edit();
        editor.putInt("tour_guide_id_for_tourist" , arr.get(position).getId());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,  Pair.create(parent.findViewById(R.id.list_img_tour_nearby), "image_transition"),
                    Pair.create(parent.findViewById(R.id.list_name_tour_nearby), "name_transition"));

            startActivity(intent, options.toBundle());
            overridePendingTransition(R.anim.slide_up,  R.anim.no_animimation);

        }else{
            startActivity(intent);
        }

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent down, MotionEvent move, float velocityX, float velocityY) {
        boolean result= false ;
        float diffx= move.getX()- down.getX();
        float diffy=move.getY()-down.getY();
        if(Math.abs(diffx) > Math.abs(diffy)){
            if(Math.abs(diffx) > movemint && Math.abs(velocityX) > movemint){
                if(diffx > 0 ){
                    Toast.makeText(this , "right" , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this , "left" , Toast.LENGTH_SHORT).show();
                }
                result =true ; ;
            }

        }else if(Math.abs(diffy) > Math.abs(diffx)){
            if(Math.abs(diffy) > movemint && Math.abs(velocityY) > movemint){
                if(diffy > 0 ){
                    Toast.makeText(this , "down" , Toast.LENGTH_SHORT).show();
                }else{
    //                details_layout.layout(nearby_tour_listl.getWidth() ,nearby_tour_listl.getHeight() , nearby_tour_listl.getWidth() ,nearby_tour_listl.getHeight() );
                    Toast.makeText(this , "up" , Toast.LENGTH_SHORT).show();
                }
                result =true ; ;
            }

        }


        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
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
        Intent intent = new Intent(this , details_page_APC.class);
        startActivity(intent);
    }
}

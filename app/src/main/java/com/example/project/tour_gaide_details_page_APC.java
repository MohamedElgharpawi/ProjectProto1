package com.example.project;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class tour_gaide_details_page_APC extends AppCompatActivity implements GestureDetector.OnGestureListener , View.OnClickListener
{
    public static final int movemint = 100;
    public static final int REQUEST_CODE = 1234;
    private LinearLayout details_layout;
    private CircularImageView details_img;
    private TextView details_name;
    private TextView tour_locations_details;
    private CheckBox tour_car_details;
    private TextView tour_phone_details;
    private ListView tour_feedback_details_list;
    private Button feedback_submit;
    private TextView tour_gender_details;
    private TextView tour_age_details;

    ArrayList<feedback_JC> arr1 = new ArrayList<>();
    nearby_tour_db ob;
    GestureDetector detector;
    String phone_number ;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    int index;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Slide());
            getWindow().setExitTransition(new Explode());
        }
        //finish();
        //overridePendingTransition(R.anim.no_animimation, R.anim.slide_down);
        setContentView(R.layout.tour_gaide_details_page);

        if(!isConnected(this)) buildDialog(this).show();
        else {
           // Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
            //setContentView(R.layout.activity_main);
        }
        Toast.makeText(this , "Swip right to call " , Toast.LENGTH_LONG).show();
        index= getIntent().getIntExtra("index" , 0 );
        details_layout = findViewById(R.id.details_layout);
        details_img = findViewById(R.id.tour_img_details);
        details_name = findViewById(R.id.tour_name_details);
        tour_locations_details = findViewById(R.id.tour_locations_details);
        tour_car_details = findViewById(R.id.tour_car_details);
        tour_phone_details = findViewById(R.id.tour_phone_details);
        tour_phone_details.setOnClickListener(this);
        feedback_submit= findViewById(R.id.feedback_submit);
        feedback_submit.setOnClickListener(this);
        tour_age_details= findViewById(R.id.tour_age_details);
        tour_gender_details=findViewById(R.id.tour_gender_details);
        tour_locations_details.setOnClickListener(this);

        nearby_tour_db ob= (nearby_tour_db) getIntent().getSerializableExtra("tour_guide_ob");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("tour_guide_id_for_tourist"  , ob.getGuide_id());
        editor.apply();
        Picasso.get().load("http://13.52.79.70/api/public/uploads/"+ob.getImg())
                .placeholder(R.drawable.ic_contacts_black_24dp)
                .error(R.drawable.ic_info_outline_black_24dp)
                .into(details_img);
        details_name.setText(ob.getName());
        //tour_locations_details.setText("Click to view all the locations ...");
        if (ob.getHas_car().equals("1")){
            tour_car_details.setChecked(true);
        }
        if (ob.getSex().equals("1")){
            tour_gender_details.setText("male");
        }else{
            tour_gender_details.setText("female");
        }

        tour_phone_details.setText(ob.getPhone());
        tour_age_details.setText(ob.getDob());
phone_number =ob.getPhone();


        tour_phone_details.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });


        tour_feedback_details_list = findViewById(R.id.tour_feedback_details_list);



        //arr1.add("a");arr1.add("a");arr1.add("a");arr1.add("a");
        AndroidNetworking.get("http://13.52.79.70/api/public/tour-guide/"+ob.getGuide_id()+"/reviews")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    ArrayList<feedback_JC> feed = new ArrayList<>();
                    feedback_JC feedback_jc;
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("list feed" , String.valueOf(response.length()));
                        for(int i =0 ; i < response.length() ; i++){
                            try {
                                feedback_jc=new feedback_JC(response.getJSONObject(i).getString("details"));
                                feed.add(feedback_jc);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                      //  ArrayAdapter<String> feedback= new ArrayAdapter<>(getApplicationContext() , R.layout.support_simple_spinner_dropdown_item , feed);
                        g_feedback_adaptor feedback=new g_feedback_adaptor(getApplicationContext(),R.layout.tour_g_feed_row,feed);
                        tour_feedback_details_list.setAdapter(feedback);
                        arr1= feed ;
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext() , anError.getErrorDetail()+"-----"+ anError.getErrorBody() , Toast.LENGTH_LONG).show();
                        Log.e("feedback" , anError.getErrorDetail()+"-----"+ anError.getErrorBody() );
                    }
                });

        Log.e("list feed" , String.valueOf(arr1.size())+"-----");



        detector = new GestureDetector(this);


        Log.e("phone", phone_number);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;
        float diffx = e2.getX() - e1.getX();
        float diffy = e2.getY() - e1.getY();
        if (Math.abs(diffx) > Math.abs(diffy)) {
            if (Math.abs(diffx) > movemint && Math.abs(velocityX) > movemint) {
                if (diffx > 0) {
             //       Toast.makeText(this, "right", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "calling ..", Toast.LENGTH_SHORT).show();
                    String number = phone_number;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + number));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE} , REQUEST_CODE); //requestPermissions();
                        startActivityForResult(intent , REQUEST_CODE);
                    Log.e("test" , "granted");
                    }else{
                        startActivityForResult(intent , REQUEST_CODE);
                        Log.e("test" , "granted+else");
                    }

                }else{
                    Toast.makeText(this , "left" , Toast.LENGTH_SHORT).show();
                }
                result =true ; ;
            }

        }else if(Math.abs(diffy) > Math.abs(diffx)){
            if(Math.abs(diffy) > movemint && Math.abs(velocityY) > movemint){
                if(diffy > 0 ){
                  //  Toast.makeText(this , "down" , Toast.LENGTH_SHORT).show();
                }else{
                    //                details_layout.layout(nearby_tour_listl.getWidth() ,nearby_tour_listl.getHeight() , nearby_tour_listl.getWidth() ,nearby_tour_listl.getHeight() );
                    //Toast.makeText(this , "up" , Toast.LENGTH_SHORT).show();
                }
                result =true ; ;
            }

        }


        return result;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE :
                    Toast.makeText(this , "calling ... " , Toast.LENGTH_SHORT).show();
                    Log.e("test" , "granted +done");
                    break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        nearby_tour_db ob= (nearby_tour_db) getIntent().getSerializableExtra("tour_guide_ob");
        if(v.getId() == R.id.tour_phone_details){
            String number = "23454568678";
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + number));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE} , REQUEST_CODE);
                startActivityForResult(intent , REQUEST_CODE);
             }else{
                startActivityForResult(intent , REQUEST_CODE);
            }

        }else if (v.getId()==R.id.feedback_submit){
       Intent intent= new Intent(this , feedback_bage_APC.class);
       intent.putExtra("index_tour" , index);
       startActivity(intent);
        }else if(v.getId()==R.id.tour_locations_details){
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
            builder.setTitle("Locations").setMessage(ob.getGovernorates()).setIcon(R.drawable.egybk).setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
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
        Intent intent = new Intent(this , nearby_tour_gaide_page_APC.class);
        intent.putExtra("place_id", getIntent().getIntExtra("place_id_nearby" , 0));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        nearby_tour_db ob= (nearby_tour_db) getIntent().getSerializableExtra("tour_guide_ob");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("tour_guide_id_for_tourist"  , ob.getGuide_id());
        editor.apply();
        Picasso.get().load("http://13.52.79.70/api/public/uploads/"+ob.getImg())
                .placeholder(R.drawable.alex)
                .error(R.drawable.aswaan)
                .into(details_img);
        details_name.setText(ob.getName());
        //tour_locations_details.setText(ob.getGovernorates());
        if (ob.getHas_car().equals("1")){
            tour_car_details.setChecked(true);
        }
        if (ob.getSex().equals("1")){
            tour_gender_details.setText("male");
        }else{
            tour_gender_details.setText("female");
        }

        tour_phone_details.setText(ob.getPhone());
        tour_age_details.setText(ob.getDob());
        phone_number =ob.getPhone();


        tour_phone_details.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });


        tour_feedback_details_list = findViewById(R.id.tour_feedback_details_list);



        //arr1.add("a");arr1.add("a");arr1.add("a");arr1.add("a");
        AndroidNetworking.get("http://13.52.79.70/api/public/tour-guide/"+ob.getGuide_id()+"/reviews")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    ArrayList<feedback_JC> feed = new ArrayList<>();
                    feedback_JC feedback_jc;
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("list feed" , String.valueOf(response.length()));
                        for(int i =0 ; i < response.length() ; i++){
                            try {
                                feedback_jc=new feedback_JC(response.getJSONObject(i).getString("details"));
                                feed.add(feedback_jc);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                        //  ArrayAdapter<String> feedback= new ArrayAdapter<>(getApplicationContext() , R.layout.support_simple_spinner_dropdown_item , feed);
                        g_feedback_adaptor feedback=new g_feedback_adaptor(getApplicationContext(),R.layout.tour_g_feed_row,feed);
                        tour_feedback_details_list.setAdapter(feedback);
                        arr1= feed ;
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext() , anError.getErrorDetail()+"-----"+ anError.getErrorBody() , Toast.LENGTH_LONG).show();
                        Log.e("feedback" , anError.getErrorDetail()+"-----"+ anError.getErrorBody() );
                    }
                });

    }
}

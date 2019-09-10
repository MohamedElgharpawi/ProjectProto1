package com.example.project;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.gesture.GestureUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class tourguid_profile_APC extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener , Toolbar.OnMenuItemClickListener, GestureDetector.OnGestureListener {
    TextView g_profile_ssn, guidname, g_profile_email, g_profile_pass, g_profile_phone, g_edit_language, g_edit_places;
    Switch guidswitch;
    Toolbar toolbar_profile_tour;
    ProgressDialog dialog;
    private Button review;
    private CircularImageView guidprofile;
    private int movemint;
    int id ;
    GestureDetector detector ;
    LinearLayout lay_out_swip ;
    ScrollView scroll_geture;
    SwipeRefreshLayout refresh ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourguid_profile);
        if (!isConnected(this)) buildDialog(this).show();
        else {
            // Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
            //setContentView(R.layout.activity_main);
        }
        set_shared();


        detector= new GestureDetector(this);

        refresh=findViewById(R.id.refresh);
        refresh.setColorSchemeResources(R.color.refresh1 ,R.color.refresh2 , R.color.refresh3 );
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            //int Refreshcounter = 1; //Counting how many times user have refreshed the layout

            @Override
            public void onRefresh() {
                //Here you can update your data from internet or from local SQLite data
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                id = preferences.getInt("tour_guide_id", 0);
                dialog.setTitle("loading..");
                dialog.setMessage("please wait..");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();


                AndroidNetworking.get("http://13.52.79.70/api/public/tour-guide/" + id)
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONArray(new JSONArrayRequestListener() {
                            ArrayList<nearby_tour_db> ob= new ArrayList<>();
                            @Override
                            public void onResponse(JSONArray response) {

                                try {

                                    guidname.setText(response.getJSONObject(0).getString("name"));
                                    g_profile_ssn.setText(response.getJSONObject(0).getString("ssn"));
                                    g_profile_email.setText(response.getJSONObject(0).getString("email"));
                                    g_profile_pass.setText(response.getJSONObject(0).getString("password"));
                                    g_profile_phone.setText(response.getJSONObject(0).getString("phone"));
                                    g_edit_language.setText(response.getJSONObject(0).getString("languages"));
                                    g_edit_places.setText(response.getJSONObject(0).getString("governorates"));
                                    if (response.getJSONObject(0).getString("active").equals("1")) {
                                        guidswitch.setChecked(true);
                                    } else {
                                        guidswitch.setChecked(false);
                                    }
                                    Glide.with(getApplicationContext()).load("http://13.52.79.70/api/public/uploads/" + response.getJSONObject(0).getString("img"))
                                            .placeholder(R.drawable.ic_contacts_black_24dp)
                                            .error(R.drawable.ic_info_outline_black_24dp)
                                            .into(guidprofile);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    dialog.dismiss();
             //                       Toast.makeText(getApplicationContext(), "something went wrong ..", Toast.LENGTH_LONG).show();
                                }
                                dialog.dismiss();
                            }

                            @Override
                            public void onError(ANError anError) {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "check your internet connection ..", Toast.LENGTH_LONG).show();
                                Log.e("details", anError.getErrorBody() + "--" + anError.getErrorDetail());
                            }
                        });

                refresh.setRefreshing(false);
            }
        });

/* refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh.setRefreshing(false);
                        int min = 65;
                        int max=5 ;
                        Random random = new Random();
                        int i= random.nextInt(max-min+1)+min ;

                    }
                } , 3000);
            }
        });

       */
        scroll_geture= findViewById(R.id.scroll_geture);
        lay_out_swip= findViewById(R.id.lay_out_swip);
        dialog = new ProgressDialog(this);
        review = findViewById(R.id.review);
        guidprofile = findViewById(R.id.guidprofile);
        guidswitch = findViewById(R.id.guidswitch);
        guidswitch.setOnCheckedChangeListener(this);
        g_profile_ssn = findViewById(R.id.g_profile_ssn);
        g_profile_email = findViewById(R.id.g_profile_email);
        g_profile_pass = findViewById(R.id.g_profile_pass);
        g_profile_phone = findViewById(R.id.g_profile_phone);
        g_edit_language = findViewById(R.id.g_edit_language);
        g_edit_places = findViewById(R.id.g_edit_places);
        guidname = findViewById(R.id.guidname);
        g_profile_ssn.setOnClickListener(this);
        g_profile_email.setOnClickListener(this);
        g_profile_pass.setOnClickListener(this);
        g_profile_phone.setOnClickListener(this);
        g_edit_language.setOnClickListener(this);
        g_edit_places.setOnClickListener(this);
        toolbar_profile_tour = findViewById(R.id.toolbar_profile_tour);
        setSupportActionBar(toolbar_profile_tour);
        toolbar_profile_tour.setOnMenuItemClickListener(this);
        review.setOnClickListener(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
         id = preferences.getInt("tour_guide_id", 0);
        dialog.setTitle("loading..");
        dialog.setMessage("please wait..");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();


        AndroidNetworking.get("http://13.52.79.70/api/public/tour-guide/" + id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                   ArrayList<nearby_tour_db> ob= new ArrayList<>();
                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            guidname.setText(response.getJSONObject(0).getString("name"));
                            g_profile_ssn.setText(response.getJSONObject(0).getString("ssn"));
                            g_profile_email.setText(response.getJSONObject(0).getString("email"));
                            g_profile_pass.setText(response.getJSONObject(0).getString("password"));
                            g_profile_phone.setText(response.getJSONObject(0).getString("phone"));
                            g_edit_language.setText(response.getJSONObject(0).getString("languages"));
                            g_edit_places.setText(response.getJSONObject(0).getString("governorates"));
                            if (response.getJSONObject(0).getString("active").equals("1")) {
                                guidswitch.setChecked(true);
                            } else {
                                guidswitch.setChecked(false);
                            }
                            Picasso.get().load("http://13.52.79.70/api/public/uploads/" + response.getJSONObject(0).getString("img"))
                                    .placeholder(R.drawable.ic_contacts_black_24dp)
                                    .error(R.drawable.ic_info_outline_black_24dp)
                                    .into(guidprofile);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.dismiss();
                           // Toast.makeText(getApplicationContext(), "something went wrong , please refresh ..", Toast.LENGTH_LONG).show();
                        Log.e("errorprofile" , e.getMessage()+"--"+ e.getCause());
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "check your internet connection ..", Toast.LENGTH_LONG).show();
                        Log.e("details", anError.getErrorBody() + "--" + anError.getErrorDetail());
                    }
                });

        scroll_geture.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });
        Log.e("activity" , "oncreatr");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lpg_out_menu, menu);
        return true;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.g_profile_ssn || v.getId() == R.id.g_profile_email) {

            Toast.makeText(this, "This Field Cannot be Edited", Toast.LENGTH_LONG).show();
        } else if (v.getId() == R.id.g_profile_pass) {

            Intent i = new Intent(this, pass_edit_APC.class);
            startActivity(i);
            Toast.makeText(this, "pass", Toast.LENGTH_LONG).show();

        } else if (v.getId() == R.id.g_profile_phone) {
            Intent i = new Intent(this, phone_edit_APC.class);
            startActivity(i);
            Toast.makeText(this, "phone", Toast.LENGTH_LONG).show();
        } else if (v.getId() == R.id.g_edit_language) {
            //Intent i = new Intent(this,);
            //startActivity(i);
            Toast.makeText(this, "lang", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, languages_page_APC.class);
            intent.putExtra("check_which_page", 0);
            startActivity(intent);

        } else if (v.getId() == R.id.g_edit_places) {
            //Intent i = new Intent(this,);
            //startActivity(i);
            Intent intent = new Intent(this, tour_gaide_locations_APC.class);
            intent.putExtra("check_which_page_places", 0);
            startActivity(intent);
            Toast.makeText(this, "places", Toast.LENGTH_LONG).show();

        } else if (v.getId() == R.id.review) {

            Intent i = new Intent(this, Tour_G_Feed_APC.class);
            startActivity(i);

        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (guidswitch.isChecked()) {

            Map<String, String> map = new HashMap<>();
            map.put("state", "1");

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            int id = preferences.getInt("tour_guide_id", 0);

            Log.e("langs id", String.valueOf(id));

            dialog.setTitle("loading..");
            dialog.setMessage("please wait..");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();

            Log.e("gov_name", "so ?");

            AndroidNetworking.put("http://13.52.79.70/api/public/tour-guide/"+id+"/set-active-state?state=1")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            dialog.dismiss();
                            try {
                                if (response.getInt("changed") == 0) {
                                   // Toast.makeText(getApplicationContext(), "something went wrong , please try again", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "available", Toast.LENGTH_LONG).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                     dialog.dismiss();
                     Toast.makeText(getApplicationContext() , "check your internet coonection" , Toast.LENGTH_LONG).show();
                     if(guidswitch.isChecked()){
                         guidswitch.setChecked(false);
                     }else{
                         guidswitch.setChecked(true);
                     }

                        }
                    });


        } else {

            Map<String, String> map = new HashMap<>();
            map.put("state", "0");

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            int id = preferences.getInt("tour_guide_id", 0);

            Log.e("langs id", String.valueOf(id));

            dialog.setTitle("loading..");
            dialog.setMessage("please wait..");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();

            Log.e("gov_name", "so ?");
//http://13.52.79.70/api/public/tour-guide/"+id+"/set-active-state?state=1
            AndroidNetworking.put("http://13.52.79.70/api/public/tour-guide/"+id+"/set-active-state?state=0")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            dialog.dismiss();
                            try {
                                if (response.getInt("changed") == 0) {
                                    //Toast.makeText(getApplicationContext(), "something went wrong , please try again", Toast.LENGTH_LONG).show();
                                } else {

                                    Toast.makeText(getApplicationContext(), "unavailable", Toast.LENGTH_LONG).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(getApplicationContext() , "check your internet coonection" , Toast.LENGTH_LONG).show();
                            if(guidswitch.isChecked()){
                                guidswitch.setChecked(false);
                            }else{
                                guidswitch.setChecked(true);
                            }

                        }
                    });
        }
    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
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
        this.finish();

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

    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.log_out) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("is_logged");
            editor.clear();
            editor.apply();
            Intent intent = new Intent(this, page2APC.class);
            startActivity(intent);
        }
        return true;
    }

    public void set_shared() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("is_logged", true);
        editor.putString("page", "g");
        editor.apply();


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
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        boolean result = false;
        float diffx = e2.getX() - e1.getX();
        float diffy = e2.getY() - e1.getY();
        if (Math.abs(diffx) > Math.abs(diffy)) {
            if (Math.abs(diffx) > movemint && Math.abs(velocityX) > movemint) {
                if (diffx > 0) {
                  //  Toast.makeText(this, "updating ...", Toast.LENGTH_SHORT).show();


                } else {

                }
                result = true;

            }
        }
        return result;
    }
}
/*    if(getIntent().getBooleanExtra("changed_places1_langs",false))
        {

            StringBuilder stringBuilder=new StringBuilder();

            for(int i=0;i< getIntent().getStringArrayListExtra("changed_places_lang").size();i++) {
                stringBuilder.append( getIntent().getStringArrayListExtra("changed_places_lang").get(i));
                stringBuilder.append(" , ");

            }
            g_edit_language.setText(stringBuilder.toString());


        }


        if(getIntent().getBooleanExtra("changed_places1",false))
        {

            StringBuilder stringBuilder=new StringBuilder();

            for(int i=0;i< getIntent().getStringArrayListExtra("changed_places").size();i++) {
                stringBuilder.append( getIntent().getStringArrayListExtra("changed_places").get(i));
                stringBuilder.append(" , ");

            }
            g_edit_places.setText(stringBuilder.toString());


        }
*/

//                    Toast.makeText(this, "updating ...", Toast.LENGTH_SHORT).show();
//                  dialog.setTitle("loading..");
//                dialog.setMessage("please wait..");
//              dialog.setIndeterminate(true);
//            dialog.setCancelable(false);
//          dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        dialog.show();

//      AndroidNetworking.get("http://number1unlock.com/delete/public/tour-guide/" + id)
//            .setPriority(Priority.MEDIUM)
//          .build()
//        .getAsJSONObject(new JSONObjectRequestListener() {
//          @Override
//        public void onResponse(JSONObject response) {
//          Log.e("details", String.valueOf(response.length()));
//       try {
//          Log.e("details", response.getString("name"));
//      } catch (JSONException e) {
//        e.printStackTrace();
//  }

//try {
//  guidname.setText(response.getString("name"));
//g_profile_ssn.setText(response.getString("ssn"));
//g_profile_email.setText(response.getString("email"));
//g_profile_pass.setText(response.getString("password"));
//g_profile_phone.setText(response.getString("phone"));
//g_edit_language.setText(response.getString("languages"));
//g_edit_places.setText(response.getString("governorates"));
//if (response.getString("active").equals("1")) {
//  guidswitch.setChecked(true);
//} else {
//  guidswitch.setChecked(false);
//}
//Picasso.get().load("http://number1unlock.com/delete/public/uploads/" + response.getString("img"))
//      .placeholder(R.drawable.alex)
//    .error(R.drawable.aswaan)
//  .into(guidprofile);
//} catch (JSONException e) {
//  e.printStackTrace();
//dialog.dismiss();
//Toast.makeText(getApplicationContext(), "something went wrong ..", Toast.LENGTH_LONG).show();
// }
// dialog.dismiss();
//}

//@Override
//public void onError(ANError anError) {
//  dialog.dismiss();
//Toast.makeText(getApplicationContext(), "check your internet connection ..", Toast.LENGTH_LONG).show();
//Log.e("details", anError.getErrorBody() + "--" + anError.getErrorDetail());
//}
//});

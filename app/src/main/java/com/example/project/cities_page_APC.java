package com.example.project;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class cities_page_APC extends AppCompatActivity implements AdapterView.OnItemClickListener  , Toolbar.OnMenuItemClickListener {

    private ListView cities_list;
    places places;
    ArrayList<places> placesArrayList = new ArrayList<>();
    Toolbar toolbar_profile_tor;
   ProgressDialog dialog ;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP ){
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Slide());
            getWindow().setExitTransition(new Explode());
        }


        setContentView(R.layout.cities_page);
        cities_list=findViewById(R.id.cities_list);
toolbar_profile_tor=findViewById(R.id.toolbar_profile_tor1);
dialog=new  ProgressDialog(this);
setSupportActionBar(toolbar_profile_tor);
toolbar_profile_tor.setOnMenuItemClickListener(this);
   //     placesArrayList.add(places= new places("cairo" , R.drawable.bkg));
    //    placesArrayList.add(places= new places("alex", R.drawable.bkg));
     //   placesArrayList.add(places= new places("kfe alshiek", R.drawable.bkg));
        if(!isConnected(this)) buildDialog(this).show();
        else {
            //Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
           // setContentView(R.layout.activity_main);

        }
        dialog.setTitle("loading..");
        dialog.setMessage("please wait..");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        int governmental_id=getIntent().getIntExtra("governmental_id", 0);
        AndroidNetworking.initialize(getApplicationContext());

        AndroidNetworking.get("http://13.52.79.70/api/public/place/governorate/"+String.valueOf(governmental_id))
                .setPriority(Priority.LOW)
                .getResponseOnlyFromNetwork()
                .doNotCacheResponse()
                .setMaxAgeCacheControl(0 , TimeUnit.MILLISECONDS)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {

                    @Override
                    public void onResponse(JSONArray response) {
                        dialog.dismiss();
                        ArrayList<places> place = new ArrayList<>();


                        for(int i = 0 ; i < response.length() ; i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                int id = object.getInt("place_id");
                                String name = object.getString("name");
                                String description = object.getString("description");
                                if(description.equals("null")){
                                    description="____";
                                }
                                String coordinates = object.getString("coordinates");
                                if(coordinates.isEmpty()){
                                    coordinates="-";
                                }

                                String restrictions = object.getString("restrictions");
                                if(restrictions.equals("null")){
                                    restrictions="____";
                                }

                                String open_time = object.getString("open_time");
                                if(open_time.equals("null")){
                                    open_time="_____";
                                }

                                String ticket = object.getString("ticket");
                                if(ticket.equals("null")){
                                    ticket="____";
                                }

                                String governorate = object.getString("governorate");
                                if(governorate.equals("null")){
                                    governorate="____";
                                }

                                String categories = object.getString("categories");
                                if(categories.equals("null")){
                                    categories="____";
                                }

                                String stars = object.getString("stars");

                                String img= object.getString("img") ;
                                 //places ob = new places(id , name ,description,coordinates , restrictions ,open_time ,ticket , governorate , categories , img);
                                places ob = new places(id , name , description , coordinates , restrictions , open_time , ticket , governorate , categories, stars , img);
                                placesArrayList.add(ob);
                                place.add(ob);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        placesArrayList= place;
                        custom_adapter adapter= new custom_adapter(getApplicationContext() ,R.layout.places_raw_design ,place );
                        cities_list.setAdapter(adapter);

                    }

                    @Override
                    public void onError(ANError anError) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "something went wrong please try again", Toast.LENGTH_SHORT).show();
                    }
                });

        cities_list.setOnItemClickListener(this);


Log.e("city" , String.valueOf(placesArrayList.size())) ;

    }
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null &&mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent= new Intent(this  , details_page_APC.class);
        Log.e("detais_t" , "so ? ");
        intent.putExtra("test1" , placesArrayList.get(position).getPic());
        intent.putExtra("test" , placesArrayList.get(position).getName());
        //intent.putExtra("place_id" ,placesArrayList.get(position).getId() );
        int governmental_id=getIntent().getIntExtra("governmental_id", 0);
        intent.putExtra("governmental_id", governmental_id);
        intent.putExtra("city_object" , placesArrayList.get(position));
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP ){
            ActivityOptions options = ActivityOptions.
                    makeSceneTransitionAnimation(this,  Pair.create(parent.findViewById(R.id.list_img), "image_transition1"),
                            Pair.create(parent.findViewById(R.id.list_text), "name_transition1"));

            startActivity(intent, options.toBundle());
            overridePendingTransition(R.anim.slide_up,  R.anim.no_animimation);

        }else {
            startActivity(intent);
        }


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this , Governmental_APC.class);
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
            editor.remove("is_logged_t");
            editor.clear();
            editor.apply();

            Intent intent = new Intent(this , page2APC.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    protected void onRestart() {
        AndroidNetworking.initialize(getApplicationContext());

        super.onRestart();
    }
}

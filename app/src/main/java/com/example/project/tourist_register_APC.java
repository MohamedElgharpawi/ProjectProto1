package com.example.project;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.DateFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;

import org.json.JSONException;
import org.json.JSONObject;

//import prefs.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.security.Permission;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class tourist_register_APC extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {

    private static final int GALLERY_REQUEST_CODE =123 ;
    private static final int CAMERA_REQUEST_CODE =1234 ;
    File file ;
    private EditText T_name;
    private EditText T_email;
    private EditText T_pass;
    private EditText T_phone;
    private EditText T_bd;
    private RadioGroup gender;
    private RadioButton g_m;
    private RadioButton g_fm;
    private Spinner drop_down_lang;
    private Spinner drop_down_nationality;
    private Button submit;
    private ImageView t_photo;
    private final static int gallary_code=123;
    private final static int cam_code=1234;
    private final static String[] items1={"Arabic", "English", "French", "Spanish","German","Austrian","Italy","Mexican"};
    private final static String[] items2={"Egyptian", "American", "Canadian", "English","German","brazilian","mexican", "Italian"};
    private Date bdt ;
    private NumberPicker T_bd1;
    private NumberPicker T_bd2;
    private NumberPicker T_bd3;
    private ProgressDialog progressDialog;
    private String TAG = tourist_register_APC.class.getSimpleName();
    private String encoded;
    private String language_choosed;
    private String nationality_choosed;
    private int gender_choosed;
    private boolean testclick=true;
    private String email_regex="^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
    private String name_regex= "^[a-zA-Z\\s]*$";
    private String pass_regex="^.{4,20}$";
    private String phone_regex = "^((?!(#))[0-9]{11})$";
    ProgressDialog dialog  ;
    MultipartBody.Part imagenPerfil;
    private EditText T_bio;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!isConnected(this)) buildDialog(this).show();
        else {
        //    Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
          //  setContentView(R.layout.activity_main);
        }
        if (is_logged()==true){

            Intent intent=new Intent(this,Governmental_APC.class);
            startActivity(intent);


        }

        setContentView(R.layout.tourist_register);
        dialog = new ProgressDialog(this);
        T_bio=findViewById(R.id.T_bio);

        T_name= findViewById(R.id.T_name);
        T_email= findViewById(R.id.T_email);
        T_pass= findViewById(R.id.T_pass);
        T_phone= findViewById(R.id.T_phone);
//        T_bd= findViewById(R.id.T_bd);
        gender= findViewById(R.id.gender);
        g_m= findViewById(R.id.g_m);
        g_fm= findViewById(R.id.g_fm);
        T_bd1=findViewById(R.id.T_bd1);
        T_bd2=findViewById(R.id.T_bd2);
        T_bd3=findViewById(R.id.T_bd3);



        drop_down_lang= findViewById(R.id.drop_down_lang);
        drop_down_nationality= findViewById(R.id.drop_down_nationality);
        submit=findViewById(R.id.submit);
        t_photo=findViewById(R.id.t_photo);
        t_photo.setOnClickListener(this);
        submit.setOnClickListener(this);
        gender.setOnCheckedChangeListener(this);
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, items1);
        drop_down_lang.setAdapter(adapter);
        drop_down_lang.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter1= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, items2);
        drop_down_nationality.setAdapter(adapter1);
        drop_down_nationality.setOnItemSelectedListener(this);




        T_bd1.setMaxValue(31);
        T_bd1.setMinValue(1);
        T_bd2.setMaxValue(12);
        T_bd2.setMinValue(1);
        T_bd3.setMaxValue(2019);
        T_bd3.setMinValue(1970);
        T_bd3.setWrapSelectorWheel(true);
        T_bd2.setWrapSelectorWheel(true);
        T_bd1.setWrapSelectorWheel(true);

        Log.e("test" , "cam per try" );

        }

public  int id ;

    @Override
    public void onClick(View v) {
        Intent intent ;
        if(v.getId()== R.id.t_photo){
            // photos part:
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.egybk).setTitle("picture").setMessage("add your photo :")
                    .setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    pickFromGallery();
                }
            }).setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Log.e("testcam", "denied");
                       // getParent().requestPermissions(getParent(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},cam_code);
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} , cam_code);
                       // ActivityCompat.requestPermissions(getParent(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},cam_code);

                            Log.e("testcam" , "so ?"+" again");

                    } else {
                        try {
                            createImageFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("testcam" , e.getMessage()+" again");
                        }

                            captureFromCamera();


                    }
                }
            }).show();

        }
        else if(v.getId() == R.id.submit){
            Log.e("testd" , "clicked");
            if(!T_email.getText().toString().isEmpty() && T_email.getText().toString().trim().matches(email_regex) && !T_name.getText().toString().isEmpty() && T_name.getText().toString().trim().matches(name_regex)&& !T_pass.getText().toString().isEmpty() && T_pass.getText().toString().trim().matches(pass_regex) && !T_phone.getText().toString().isEmpty() && T_phone.getText().toString().trim().matches(phone_regex)){

                String datetest= String.valueOf(T_bd3.getValue())+"-"+String.valueOf(T_bd2.getValue())+"-"+String.valueOf(T_bd1.getValue());
                Bitmap bitmap=((BitmapDrawable) t_photo.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);

                final String Photo_encoded= Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
//            signup(T_name.getText().toString().trim() ,T_email.getText().toString().trim() , T_pass.getText().toString().trim() , T_phone.getText().toString().trim() , language_choosed , gender_choosed ,  datetest , nationality_choosed , Photo_encoded );
//                 intent=new Intent(this , Governmental_APC.class);
  //               startActivity(intent);

                Map<String , String> map= new HashMap<>();
                map.put("name",T_name.getText().toString());
                map.put("email" , T_email.getText().toString());
                map.put("password" , T_pass.getText().toString());
                map.put("phone" , T_phone.getText().toString());
                map.put("dob" , datetest);
                map.put("sex" , String.valueOf(gender_choosed));
                map.put("bio" , T_bio.getText().toString());
                map.put("language" , language_choosed);
                map.put("nationality" , nationality_choosed);
            //    map.put("img" , Photo_encoded );

                dialog.setTitle("loading..");
                dialog.setMessage("please wait..");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                //dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();
                Log.e("langs progress", "so ?");

                AndroidNetworking.post("http://13.52.79.70/api/public/tourist/add?name="+T_name.getText().toString()+"&email="+T_email.getText().toString()+"&password="+T_pass.getText().toString()+"&phone="+T_phone.getText().toString()+"&dob="+datetest+"&sex="+String.valueOf(gender_choosed)+"&bio="+T_bio.getText().toString()+"&language="+language_choosed+"&nationality="+nationality_choosed)
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                dialog.dismiss();
                                try {
                                   // Toast.makeText(getApplicationContext(),response.getInt("result")+"ok" ,Toast.LENGTH_LONG ).show();
                                    //Log.e("tour_guid_id" ,String.valueOf(response.getInt("result")) );
                                     id = response.getInt("result");
                                    if(id == 0){
                                        Toast.makeText(getApplicationContext() , "this mail already exist" , Toast.LENGTH_LONG).show();
                                    }else{

                                        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor editor= preferences.edit();
                                            editor.putInt("tourist_id" , id);
                                        editor.apply();
                                        Log.e("tourist" , "done register");
                                         //--------------------------------
                                        AndroidNetworking.post("http://13.52.79.70/api/public/tourist/"+id+"/add-photo")
                                                .setPriority(Priority.HIGH)
                                                .addBodyParameter("photo",Photo_encoded)
                                                .build()
                                                .getAsJSONObject(new JSONObjectRequestListener() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {

                                                        Toast.makeText(getApplicationContext() , "Next .." , Toast.LENGTH_LONG).show();
                                                        Intent intent1= new Intent(getApplicationContext() , tourist_log_in_APC.class);
                                                        startActivity(intent1);
                                                        Log.e("tourist_pic" , "done");
                                                        dialog.dismiss();
                                                        // do anything with response
                                                    }
                                                    @Override
                                                    public void onError(ANError error) {
                                                        dialog.dismiss();
                                                        Toast.makeText(getApplicationContext() , error.getErrorDetail() +"----"+ error.getErrorBody() , Toast.LENGTH_LONG).show();
                                                        Log.e("tourist_pic" , error.getErrorDetail() +"----"+ error.getErrorBody());
                                                        // handle error
                                                    }
                                                });

                                        //--------------------------------

                                    }

                                } catch (JSONException e) {
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"something went wrong" ,Toast.LENGTH_LONG ).show();
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(getApplicationContext(),"something went wrong" ,Toast.LENGTH_LONG ).show();
                                Toast.makeText(getApplicationContext() , anError.getErrorDetail()+"  "+anError.getResponse() , Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            }
                        });


                //---------------------

                /*
                AndroidNetworking.upload("http://13.52.79.70/api/public/tourist/1/add-photo")
                        .addMultipartFile("photo",file)
                        .setPriority(Priority.HIGH)
                        .build()
                        .setUploadProgressListener(new UploadProgressListener() {
                            @Override
                            public void onProgress(long bytesUploaded, long totalBytes) {
                                // do anything with progress
                            }
                        })
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Toast.makeText(getApplicationContext() , "pic done" , Toast.LENGTH_LONG).show();
                                Intent intent1= new Intent(getApplicationContext() , tourist_log_in_APC.class);
                                startActivity(intent1);
                                Log.e("tourist_pic" , "done");
                                dialog.dismiss();
                                // do anything with response
                            }
                            @Override
                            public void onError(ANError error) {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext() , error.getErrorDetail() +"----"+ error.getErrorBody() , Toast.LENGTH_LONG).show();
                                Log.e("tourist_pic" , error.getErrorDetail() +"----"+ error.getErrorBody() +"---"+error.getResponse());

                                // handle error
                            }
                        });*/
                //---------------------



                ANRequest request =
                        AndroidNetworking.post("http://13.52.79.70/api/public/tourist/add?name="+T_name.getText().toString()+"&email="+T_email.getText().toString()+"&password="+T_pass.getText().toString()+"&phone="+T_phone.getText().toString()+"&dob="+datetest+"&sex="+String.valueOf(gender_choosed)+"&bio="+T_bio.getText().toString()+"&language="+language_choosed+"&nationality="+nationality_choosed)
                                .addBodyParameter(map)
                                .setPriority(Priority.MEDIUM)
                                .build() ;
                ANRequest request1 =     AndroidNetworking.upload("http://13.52.79.70/api/public/tourist/13/add-photo")
                        .addMultipartFile("photo",file)
                        .setPriority(Priority.HIGH)
                        .build();

                Log.e("test pic tourist" , request.getUrl());
                Log.e("test pic tourist" , request1.getUrl());
              //  SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                //int id= preferences.getInt("tourist_id" , 1);
               // int id2= id+1;

               // dialog.setTitle("loading..");
               // dialog.setMessage("please wait..");
              //  dialog.setIndeterminate(true);
              //  dialog.setCancelable(false);
                //dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
               // dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
              //  dialog.show();








            }else if(T_name.getText().toString().isEmpty() || !T_name.getText().toString().trim().matches(name_regex)){
                T_name.setError("please enter your name correctly");
                Toast.makeText(this , "wrong name" , Toast.LENGTH_SHORT).show();
            }else if(T_email.getText().toString().isEmpty() || !T_email.getText().toString().trim().matches(email_regex)){
                T_email.setError("please enter correct email");
                Toast.makeText(this , "wrong email" , Toast.LENGTH_SHORT).show();
            }else if(T_pass.getText().toString().isEmpty() || !T_pass.getText().toString().trim().matches(pass_regex)){
                T_pass.setError("please make sure password is more than 4 characters");
                Toast.makeText(this , "wrong password" , Toast.LENGTH_SHORT).show();
            }else if(T_phone.getText().toString().isEmpty() || !T_phone.getText().toString().trim().matches(phone_regex)){
                T_phone.setError("please enter correct phone number");
                Toast.makeText(this , "wrong phone" , Toast.LENGTH_SHORT).show();
            }

      Log.e("tryrhisone" , gender_choosed +"    " + nationality_choosed +"    "+language_choosed);
      Log.e("jsontest" , "done");
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(group.getId() == R.id.gender){
            if(g_m.isChecked()){
                gender_choosed= 1;
            }else if(g_fm.isChecked()){
                gender_choosed= 0;
            }

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId() ==R.id.drop_down_lang){
            language_choosed= items1[position];
        }else if(parent.getId() ==R.id.drop_down_nationality){
            nationality_choosed=items2[position];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        language_choosed= items1[parent.getSelectedItemPosition()];
        nationality_choosed=items2[parent.getSelectedItemPosition()];
    }

    // photos methods
    private void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

    private String cameraFilePath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //This is the directory in which the file will be created. This is the default location of Camera photos
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(imageFileName,/* prefix */".jpg",/* suffix */storageDir/* directory */);
        // Save a file: path for using again
        cameraFilePath = "file://" + image.getAbsolutePath();
        Log.e("testcam" , "file   "+ cameraFilePath);
        return image;
    }
    private void captureFromCamera() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", createImageFile()));
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
            Log.e("testcam" , "open done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();


                     file = new File(selectedImage.getPath());
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                   imagenPerfil = MultipartBody.Part.createFormData("photo", file.getName(), requestFile);


                    t_photo.setImageDrawable(null);
                    t_photo.setImageURI(selectedImage);
                    Log.e("test" , "uri is : " +selectedImage);
                    break;
                case CAMERA_REQUEST_CODE:
                    Log.e("test" , "should've been opened by now");
                    t_photo.setImageDrawable(null);

                    file = new File(Uri.parse(cameraFilePath).getPath());
                    RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    imagenPerfil = MultipartBody.Part.createFormData("photo", file.getName(), requestFile1);

                    t_photo.setImageURI(Uri.parse(cameraFilePath));
                    break;
            }

    }



   /*
   * private void signup(final String username, final String email, final String password , final  String phone,final String language,final String gender,final String DoB, final String nationality,final String photo){
        // Tag used to cancel the request
        String tag_string_req = "req_signup";
        progressDialog.setMessage("Signing up...");
        progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Utils.REGISTER_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        JSONObject user = jObj.getJSONObject("user");
                        String uName = user.getString("username");
                        String email = user.getString("email");

                        // Inserting row in users table
                        userInfo.setEmail(email);
                        userInfo.setUsername(uName);
                        session.setLoggedin(true);

                        startActivity(new Intent(tourist_register_APC.this, MainActivity.class));
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        toast(errorMsg);
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    toast("Json error: " + e.getMessage());
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                toast("Unknown Error occurred");
                progressDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                params.put("phone",phone);
                params.put("language",language);
                params.put("gender",gender);
                params.put("DoB",DoB);
                params.put("nationality",nationality);
                params.put("photo", photo);
                return params;
            }

        };

        // Adding request to request queue
        AndroidLoginController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
     private void toast(String x){
        Toast.makeText(this, x, Toast.LENGTH_SHORT).show();
    }

   * */


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
        Intent intent = new Intent(this , tourist_log_in_APC.class);
        startActivity(intent);
    }

    public boolean is_logged(){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        boolean check=preferences.getBoolean("is_logged_t",false);
        return check;



    }

}

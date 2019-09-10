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
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

//import prefs.*;

public class tourguid_register extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener  {
private static final int GALLERY_REQUEST_CODE =123 ;
private static final int CAMERA_REQUEST_CODE =1234 ;
private EditText g_name;
private EditText g_email;
private EditText g_pass;
private EditText g_phone;
private EditText g_bd;
private EditText g_ssn;
private RadioGroup ggender;
private RadioButton gg_m;
private RadioButton gg_fm;
//private Spinner gdrop_down_lang;
    private EditText glanuage;
    private EditText gplaces;
   //---------
    File file ;
    MultipartBody.Part imagenPerfil;
    //--------
private EditText bio;
private Button gsubmit;
private ImageView gt_photo;
private final static int gallary_code=123;
private final static int cam_code=1234;
private final static String[] items1={"Arabic", "English", "chianes", "germany","etc"};
private final static String[] items2={"Egypt", "UAE", "Amireca", "Britsh","etc"};
private Date gbdt ;
private NumberPicker g_bd1;
private NumberPicker g_bd2;
private NumberPicker g_bd3;
private ProgressDialog progressDialog;
private String TAG = tourguid_register.class.getSimpleName();
private String encoded;
private CheckBox hascar;
private int hascarresult;
//private String glanguage_choosed;
private String gnationality_choosed;
private int ggender_choosed;

        private String email_regex="^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
        private String name_regex= "^[a-zA-Z\\s]*$";
        private String pass_regex="^.{4,20}$";
        private String phone_regex = "^((?!(#))[0-9]{11})$";
        private String ssn_regex= "^((?!(#))[0-9]{14})$";
        ProgressDialog dialog  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourguid_register);
        if(!isConnected(this)) buildDialog(this).show();
        else {
         //   Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
            //setContentView(R.layout.activity_main);
        }
dialog= new ProgressDialog(this);
        if (is_logged()==true){
            Intent intent=new Intent(this,tourguid_profile_APC.class);
            startActivity(intent);
        }
        g_name= findViewById(R.id.g_name);
        g_email= findViewById(R.id.g_email);
        g_pass= findViewById(R.id.g_pass);
        g_phone= findViewById(R.id.g_phone);
        g_ssn=findViewById(R.id.g_ssn);
//        g_bd= findViewById(R.id.g_bd);
        ggender= findViewById(R.id.ggender);
        gg_m= findViewById(R.id.gg_m);
        gg_fm= findViewById(R.id.gg_fm);
        g_bd1=findViewById(R.id.g_bd1);
        g_bd2=findViewById(R.id.g_bd2);
        g_bd3=findViewById(R.id.g_bd3);
     //   glanuage= findViewById(R.id.glanguage);
        hascar=findViewById(R.id.hascar);
//        glanuage.setOnClickListener(this);
       // gplaces=findViewById(R.id.gplaces);
       // gplaces.setOnClickListener(this);
        //gdrop_down_nationality= findViewById(R.id.gdrop_down_nationality);
        bio=findViewById(R.id.bio);
        gsubmit=findViewById(R.id.gsubmit);
        gt_photo=findViewById(R.id.gt_photo);
        gt_photo.setOnClickListener(this);
        gsubmit.setOnClickListener(this);
        ggender.setOnCheckedChangeListener(this);
        //ArrayAdapter<String> adapter1= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, items2);
        //gdrop_down_nationality.setAdapter(adapter1);
        //gdrop_down_nationality.setOnItemSelectedListener(this);
        g_bd1.setMaxValue(31);
        g_bd1.setMinValue(1);
        g_bd2.setMaxValue(12);
        g_bd2.setMinValue(1);
        g_bd3.setMaxValue(2019);
        g_bd3.setMinValue(1970);
        g_bd3.setWrapSelectorWheel(true);
        g_bd2.setWrapSelectorWheel(true);
        g_bd1.setWrapSelectorWheel(true);
        Log.e("test" , "cam per try" );
        boolean ch=true;

        if (!getIntent().getBooleanExtra("loc" , false)) {

        }else {

            StringBuilder stringBuilder=new StringBuilder();

        for(int i=0;i< getIntent().getStringArrayListExtra("locations").size();i++) {
            stringBuilder.append( getIntent().getStringArrayListExtra("locations").get(i));
            stringBuilder.append(" , ");

        }
        gplaces.setText(stringBuilder.toString());
        }

        if(getIntent().getBooleanExtra("langs_bool",false))
        {
            tour_guide_info_saver tour_guide_info_saver =(tour_guide_info_saver) getIntent().getSerializableExtra("object_lang");
            g_name.setText(tour_guide_info_saver.getName());
            g_email.setText(tour_guide_info_saver.getEmail());
            g_pass.setText(tour_guide_info_saver.getPass());
            g_phone.setText(tour_guide_info_saver.getPhone());
            g_ssn.setText(tour_guide_info_saver.getSsn());

            StringBuilder stringBuilder=new StringBuilder();

            for(int i=0;i< getIntent().getStringArrayListExtra("langs").size();i++) {
                stringBuilder.append( getIntent().getStringArrayListExtra("langs").get(i));
                stringBuilder.append(" , ");

            }
            glanuage.setText(stringBuilder.toString());


        }




    }




    @Override
public void onClick(View v) {
        final Intent intent ;
        if(v.getId()== R.id.gt_photo){
        // photos part:
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.egybk).setTitle("Photo").setMessage("choose your way :")
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
        else if(v.getId() == R.id.gsubmit){
        Log.e("testd" , "clicked");
                Log.e("testd" , "clicked");
                //  intent=new Intent(this , tourist_log_in_APC.class);
                // startActivity(intent);

                if(!g_email.getText().toString().isEmpty() && g_email.getText().toString().trim().matches(email_regex) && !g_name.getText().toString().isEmpty() && g_name.getText().toString().trim().matches(name_regex)&& !g_pass.getText().toString().isEmpty() && g_pass.getText().toString().trim().matches(pass_regex) && !g_phone.getText().toString().isEmpty() && g_phone.getText().toString().trim().matches(phone_regex)&&!g_ssn.getText().toString().trim().isEmpty()&&g_ssn.getText().toString().trim().matches(ssn_regex) && gt_photo.getResources().getResourceEntryName(R.drawable.camera).equals("camera")){
               if(hascar.isChecked()){
                   hascarresult= 1;
               }else if(!hascar.isChecked()){
                   hascarresult= 0;
               }
                        final String datetest= String.valueOf(g_bd3.getValue())+"-"+String.valueOf(g_bd2.getValue())+"-"+String.valueOf(g_bd1.getValue());
                        Bitmap bitmap=((BitmapDrawable) gt_photo.getDrawable()).getBitmap();
                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
                        final String Photo_encoded= Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
                       // String test1 = Base64.decode(Photo_encoded , Base64.DEFAULT);
//            signup(T_name.getText().toString().trim() ,T_email.getText().toString().trim() , T_pass.getText().toString().trim() , T_phone.getText().toString().trim() , language_choosed , gender_choosed ,  datetest , nationality_choosed , Photo_encoded );
                    Map<String , String> map= new HashMap<>();
                    map.put("name",g_name.getText().toString());
                    map.put("email" , g_email.getText().toString());
                    map.put("password" , g_pass.getText().toString());
                    map.put("phone" , g_phone.getText().toString());
                    map.put("dob" , datetest);
                    map.put("sex" , String.valueOf(ggender_choosed));
                    map.put("has_car" , String.valueOf(hascarresult));
                    map.put("bio" , bio.getText().toString());
                    map.put("img" , Photo_encoded );
                    map.put("ssn" , g_ssn.getText().toString() );
                    dialog.setTitle("loading..");
                    dialog.setMessage("please wait..");
                    dialog.setIndeterminate(true);
                    dialog.setCancelable(false);
                    //dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.show();
                    Log.e("langs progress", "so ?");

                    AndroidNetworking.post("http://13.52.79.70/api/public/tour-guide/add")
                            .addBodyParameter(map)
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    //dialog.dismiss();
                                    try {
                                       // Toast.makeText(getApplicationContext(),response.getInt("result")+"ok" ,Toast.LENGTH_LONG ).show();
                                        Log.e("tour_guid_id" ,String.valueOf(response.getInt("result")) );
                                        int id = response.getInt("result");
                                        if(id == 0){
                                        Toast.makeText(getApplicationContext() , "try again" , Toast.LENGTH_LONG).show();
                                        }else{
                                            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                            SharedPreferences.Editor editor= preferences.edit();
                                            editor.putInt("tour_guide_id" , id);
                                           editor.apply();

                                           Intent  intent= new Intent(getApplicationContext(), languages_page_APC.class);
                                            startActivity(intent);

                                            //-----------------------


                                            AndroidNetworking.post("http://13.52.79.70/api/public/tour-guide/"+String.valueOf(id)+"/add-photo")
                                                    .setPriority(Priority.HIGH)
                                                    .addBodyParameter("photo",Photo_encoded)
                                                    .build()
                                                    .getAsJSONObject(new JSONObjectRequestListener() {
                                                        @Override
                                                        public void onResponse(JSONObject response) {

                                                            Toast.makeText(getApplicationContext() , "Next .." , Toast.LENGTH_LONG).show();
                                                            Intent intent1= new Intent(getApplicationContext() , languages_page_APC.class);
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

                                            //-----------------------



                                        }

                                    } catch (JSONException e) {
                                        Toast.makeText(getApplicationContext(),"something is wrong" ,Toast.LENGTH_LONG ).show();
                                        e.printStackTrace();
                                    }
                                    //----------------------------

                                    //----------------------------

                                }

                                @Override
                                public void onError(ANError anError) {
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext() , anError.getErrorDetail()+"--- error ---"+anError.getResponse() , Toast.LENGTH_LONG).show();
                                }
                            });
                    SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    int id= preferences.getInt("tour_guide_id" , 1);

                  /*  AndroidNetworking.post("http://number1unlock.com/delete/public/tour-guide/"+String.valueOf(id)+"/add-photo")
                            .addBodyParameter(map)
                            .addBodyParameter("profile_photo",Photo_encoded)
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    dialog.dismiss();
                                    Intent  intent= new Intent(getApplicationContext(), languages_page_APC.class);
                                    startActivity(intent);


                                }

                                @Override
                                public void onError(ANError anError) {
                                    dialog.dismiss();

                                    Toast.makeText(getApplicationContext() , anError.getErrorDetail()+"--- error ---  "+anError.getResponse() , Toast.LENGTH_LONG).show();
                                }
                            });*/





                }else if(g_name.getText().toString().isEmpty() || !g_name.getText().toString().trim().matches(name_regex)){
                        g_name.setError("please enter your name correctly");
                        Toast.makeText(this , "wrong name" , Toast.LENGTH_SHORT).show();
                }else if(g_email.getText().toString().isEmpty() || !g_email.getText().toString().trim().matches(email_regex)){
                        g_email.setError("please enter correct email");
                        Toast.makeText(this , "wrong email" , Toast.LENGTH_SHORT).show();
                }else if(g_pass.getText().toString().isEmpty() || !g_pass.getText().toString().trim().matches(pass_regex)){
                        g_pass.setError("please make sure password is more than 4 characters");
                        Toast.makeText(this , "wrong password" , Toast.LENGTH_SHORT).show();
                }else if(g_phone.getText().toString().isEmpty() || !g_phone.getText().toString().trim().matches(phone_regex)){
                        g_phone.setError("please enter correct phone number");
                        Toast.makeText(this , "wrong phone" , Toast.LENGTH_SHORT).show();
                }else if(g_ssn.getText().toString().isEmpty()||!g_ssn.getText().toString().trim().matches(ssn_regex)){
                        g_ssn.setError("please enter correct SSN");
                        Toast.makeText(this , "wrong SSN" , Toast.LENGTH_SHORT).show();
                }else if(!gt_photo.getResources().getResourceEntryName(R.drawable.camera).equals("camera")){
                    Toast.makeText(this , "please enter your photo" , Toast.LENGTH_SHORT).show();
                }
//language removewd
                Log.e("tryrhisone" , ggender_choosed +"    " + gnationality_choosed );
                Log.e("jsontest" , "done");
        }
                }

@Override
public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(group.getId() == R.id.ggender){
        if(gg_m.isChecked()){
        ggender_choosed= 1;
        }else if(gg_fm.isChecked()){
        ggender_choosed= 0;
        }

        }
        }

@Override
public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // if(parent.getId() == R.id.gdrop_down_nationality){
        //gnationality_choosed=items2[position];
       // }
        }

@Override
public void onNothingSelected(AdapterView<?> parent) {

        gnationality_choosed=items2[parent.getSelectedItemPosition()];
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


            gt_photo.setImageDrawable(null);
            gt_photo.setImageURI(selectedImage);
            Log.e("test" , "uri is : " +selectedImage);
            break;

        case CAMERA_REQUEST_CODE:
        Log.e("test" , "should've been opened by now");
        gt_photo.setImageDrawable(null);

            file = new File(Uri.parse(cameraFilePath).getPath());
            RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            imagenPerfil = MultipartBody.Part.createFormData("photo", file.getName(), requestFile1);

            gt_photo.setImageURI(Uri.parse(cameraFilePath));
            break;
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
    public boolean is_logged(){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        boolean check=preferences.getBoolean("is_logged",false);
        return check;



    }

    @Override
    public void onBackPressed() {
    Intent i = new Intent(this,tourist_log_in_APC.class);
    startActivity(i);

    super.onBackPressed();
    }
}













/*
        else if(v.getId()== R.id.glanguage){
                Toast.makeText(getApplicationContext(),"language",Toast.LENGTH_LONG).show();

     Intent i = new Intent(this, languages_page_APC.class);
     //i.putExtra("name" , g_name.getText().toString());
         //   String datetest= String.valueOf(g_bd3.getValue())+"-"+String.valueOf(g_bd2.getValue())+"-"+String.valueOf(g_bd1.getValue());
           // Bitmap bitmap=((BitmapDrawable) gt_photo.getDrawable()).getBitmap();
           // ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
           // bitmap.compress(Bitmap.CompressFormat.JPEG,80,byteArrayOutputStream);
            //String Photo_encoded= Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);

             //tour_guide_info_saver ob = new tour_guide_info_saver(Photo_encoded , g_name.getText().toString() ,g_email.getText().toString(), g_pass.getText().toString() , g_phone.getText().toString() , g_ssn.getText().toString() ,datetest ,ggender_choosed ,hascarresult , gdrop_down_nationality.getSelectedItem().toString() );
    //i.putExtra("object1" , ob);
    //i.putExtra("check_lang" , true);

     startActivity(i);

        }
        else if(v.getId()== R.id.gplaces){
            Toast.makeText(getApplicationContext(),"places",Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, tour_gaide_locations_APC.class);

                String datetest= String.valueOf(g_bd3.getValue())+"-"+String.valueOf(g_bd2.getValue())+"-"+String.valueOf(g_bd1.getValue());
            Bitmap bitmap=((BitmapDrawable) gt_photo.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,80,byteArrayOutputStream);
            String Photo_encoded= Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);

            tour_guide_info_saver ob = new tour_guide_info_saver(Photo_encoded , g_name.getText().toString() ,g_email.getText().toString(), g_pass.getText().toString() , g_phone.getText().toString() , g_ssn.getText().toString() ,datetest ,ggender_choosed ,hascarresult , gdrop_down_nationality.getSelectedItem().toString() );

            startActivity(i);
        }
*/

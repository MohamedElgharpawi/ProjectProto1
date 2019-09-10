package com.example.project;

import android.app.ActivityOptions;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class custom_adapter_nearby_tour extends ArrayAdapter<nearby_tour_db> {

    ArrayList<nearby_tour_db> objects ;
    Context context;

    public custom_adapter_nearby_tour(Context context, int resource, ArrayList<nearby_tour_db> objects) {
        super(context, resource, objects);

        this.context=context;
        this.objects=objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.nearby_tour_raw_design , parent , false);
        nearby_tour_db ob = objects.get(position);
        ImageView img= v.findViewById(R.id.list_img_tour_nearby);
        TextView name = v.findViewById(R.id.list_name_tour_nearby);
        TextView lang = v.findViewById(R.id.list_lang_tour_nearby);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            img.setTransitionName("image_transition");
            name.setTransitionName("name_transition");
        }

        //img.setImageResource(ob.getPic());

        Picasso.get().load("http://13.52.79.70/api/public/uploads/"+ob.getImg())
                .placeholder(R.drawable.ic_contacts_black_24dp)
                .error(R.drawable.ic_info_outline_black_24dp)
                .into(img);



        name.setText(ob.getName());

        lang.setText(ob.getLangs());

        return v;
    }
}

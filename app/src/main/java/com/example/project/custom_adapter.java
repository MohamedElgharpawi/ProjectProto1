package com.example.project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class custom_adapter extends ArrayAdapter<places> {

    Context context ;
    ArrayList<places> objects ;

    public custom_adapter(Context context, int resource, ArrayList<places> objects) {
        super(context, resource, objects);
        this.context=context ;
        this.objects=objects ;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.places_raw_design , parent , false);
        places places= objects.get(position);
        ImageView list_img= v.findViewById(R.id.list_img);
        TextView list_text= v.findViewById(R.id.list_text);
        Picasso.get().load("http://13.52.79.70/storage/places-imgs/"+places.getPic())
                .placeholder(R.drawable.ic_place_black_24dp)
                .error(R.drawable.ic_info_outline_black_24dp)
                .into(list_img);
        list_text.setText(places.getName());
        return v;
    }
}

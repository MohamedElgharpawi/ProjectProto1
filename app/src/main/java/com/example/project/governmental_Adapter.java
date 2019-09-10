package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class governmental_Adapter extends ArrayAdapter<Cities> {
    Context context ;
    ArrayList<Cities> objects ;

    public governmental_Adapter(Context context, int resource, ArrayList<Cities> objects) {
        super(context, resource, objects);
        this.context=context ;
        this.objects=objects ;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.places_raw_design , parent , false);
        Cities cities= objects.get(position);
        ImageView list_img= v.findViewById(R.id.list_img);
        TextView list_text= v.findViewById(R.id.list_text);
        //list_img.setImageResource(cities.getImg());
        Picasso.get().load("http://13.52.79.70/api/public/uploads/"+cities.getImg())
                .placeholder(R.drawable.ic_place_black_24dp)
                .error(R.drawable.ic_info_outline_black_24dp)
                .into(list_img);
        list_text.setText(cities.getName());
        return v;
    }


}

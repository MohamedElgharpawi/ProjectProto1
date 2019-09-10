package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class g_feedback_adaptor  extends ArrayAdapter<feedback_JC> {
    Context context ;
    ArrayList<feedback_JC> objects ;

    public g_feedback_adaptor(Context context, int resource, ArrayList<feedback_JC> objects) {
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
        View v = inflater.inflate(R.layout.tour_g_feed_row , parent , false);
        feedback_JC feedback_jc= objects.get(position);

        TextView feed= v.findViewById(R.id.T_G_feed);
        feed.setText(feedback_jc.getFeedback());
        return v;
    }
}


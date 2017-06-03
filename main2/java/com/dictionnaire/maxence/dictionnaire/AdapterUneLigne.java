package com.dictionnaire.maxence.dictionnaire;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Maxence on 03/06/2017.
 */

public class AdapterUneLigne extends BaseAdapter {

    private Context context;
    private ArrayList<Mot> mots;

    public AdapterUneLigne(Context context, ArrayList<Mot> mots){
        this.context = context;
        this.mots = mots;
    }

    @Override
    public int getCount(){
        return mots.size();
    }

    @Override
    public Object getItem(int position){
        return mots.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View row;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = (View)inflater.inflate(android.R.layout.simple_list_item_1, null);
        }else{
            row = (View)convertView;
        }

        Mot e = mots.get(position);

        TextView v = (TextView)row.findViewById(android.R.id.text1);
        v.setText(e.getMot());
        v.setTextColor(Color.BLACK);
        return row;

    }

    public void updateResults(ArrayList<Mot> a) {
        mots = a;
        notifyDataSetChanged();
    }

}

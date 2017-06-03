package com.dictionnaire.maxence.dictionnaire;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.ArrayList;

/**
 * Created by Maxence on 25/05/2017.
 */

public class AdapterDeuxLignes extends BaseAdapter {

    private Context context;
    private ArrayList<? extends Element> elements;

    public AdapterDeuxLignes(Context context, ArrayList<? extends Element> elems){
        this.context = context;
        this.elements = elems;
    }

    @Override
    public int getCount(){
        return elements.size();
    }

    @Override
    public Object getItem(int position){
            return elements.get(position);
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
            row = (View)inflater.inflate(android.R.layout.simple_list_item_2, null);
        }else{
            row = (View)convertView;
        }

        Element e = elements.get(position);

        TextView v = (TextView)row.findViewById(android.R.id.text1);
        v.setText(e.getLibelle());
        v.setTextColor(Color.BLACK);
        v = (TextView)row.findViewById(android.R.id.text2);
        v.setTextColor(Color.BLACK);
        v.setText(e.getDescription());
        return row;

    }

    public void updateResults(ArrayList<? extends Element> a) {
        elements = a;
        notifyDataSetChanged();
    }

}

package com.mazur.alehandro.taskforattractgroup;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alehandro on 5/7/2016.
 */
public class ListArrayAdapter extends ArrayAdapter {
    private LayoutInflater inflater;
    private TextView listName, listDate;
    private ImageView listImage;
    private ArrayList<Model> listItems;
    Bitmap b;
    int heightDisplay=getContext().getResources().getDisplayMetrics().heightPixels;
    int widthDisplay =getContext().getResources().getDisplayMetrics().widthPixels;

    public ListArrayAdapter(Context context, int resource, ArrayList<Model> list) {
        super(context, resource,list);
        listItems=list;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.list_item_layout,null,true);

        listName=(TextView) convertView.findViewById(R.id.listName);
        listDate=(TextView)convertView.findViewById(R.id.listDate);

        listImage=(ImageView) convertView.findViewById(R.id.listImage);
        listImage.setImageResource(android.R.drawable.ic_lock_power_off);
        int targetHeight=getTargetHeight();
        GetImageTask getImageTask =new GetImageTask(listImage,targetHeight,targetHeight);
        getImageTask.execute(listItems.get(position).getImageUrl());

        listName.setText(listItems.get(position).getName());
        listDate.setText(DateFormat.format("dd/MM/yyyy hh:mm",listItems.get(position).getTime()));


         return convertView;
    }
        private int getTargetHeight(){
            int target=0;
            if(heightDisplay<800){target=heightDisplay/4;}
            if(heightDisplay>800&&heightDisplay<1280){target=heightDisplay/5;}
            if(heightDisplay>1270&&heightDisplay<1600){target=heightDisplay/6;}
            if(heightDisplay>1600&&heightDisplay<1920){target=heightDisplay/7;}
            if(heightDisplay>1900){target=heightDisplay/8;}
            return target;
        }


}

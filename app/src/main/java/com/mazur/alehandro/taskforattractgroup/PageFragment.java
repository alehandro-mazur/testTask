package com.mazur.alehandro.taskforattractgroup;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alehandro on 5/9/2016.
 */
public class PageFragment extends Fragment {
    private static final String POSITION ="position";
    private ArrayList<Model> pageList=MainActivity.getListModel();
    private ImageView pageImage;
    private TextView pageName,pageDate,pageDescription;


    public static PageFragment newInstance(int position){
        PageFragment pageFragment=new PageFragment();
        Bundle args=new Bundle();
        args.putInt(POSITION,position);
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_fragment_layout,container,false);
        int position=getArguments().getInt(POSITION);
        int heightDisplay=getContext().getResources().getDisplayMetrics().heightPixels;
        int widthDisplay =getContext().getResources().getDisplayMetrics().widthPixels;

        pageName=(TextView)rootView.findViewById(R.id.pageName);
        pageDate=(TextView)rootView.findViewById(R.id.pageDate);
        pageDescription=(TextView)rootView.findViewById(R.id.pageDescription);
        pageImage=(ImageView)rootView.findViewById(R.id.pageImage);

        pageName.setText(pageList.get(position).getName());
        pageDate.setText(DateFormat.format("dd/MM/yyyy hh:mm",pageList.get(position).getTime()));
        pageDescription.setText(pageList.get(position).getDescription());
        int targetHeight=(int)(heightDisplay*0.8);

        GetImageTask getPagerImageTask= new GetImageTask(pageImage,targetHeight,widthDisplay);
        getPagerImageTask.execute(pageList.get(position).getImageUrl());

        return rootView;
    }




}

package com.mazur.alehandro.taskforattractgroup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alehandro on 5/7/2016.
 */
public class MainListFragment extends ListFragment {
    private ArrayList<Model> list;
    private ListArrayAdapter listAdapter;
    IOnItemListClickedListener onItemListClickedListener;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onItemListClickedListener= (IOnItemListClickedListener) getActivity();
        list=MainActivity.getListModel();
        listAdapter = new ListArrayAdapter(getActivity(), R.layout.list_item_layout,list);
        setListAdapter(listAdapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        onItemListClickedListener.itemClicked(position);

    }
}


package com.mazur.alehandro.taskforattractgroup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Alehandro on 5/9/2016.
 */
public class ViewPagerFragment extends Fragment {
    public ViewPager pager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.view_pager_fragment_layout,container,false);
        pager =(ViewPager)rootView.findViewById(R.id.viewPagerFragment);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getContext(),getChildFragmentManager());
        pager.setAdapter(adapter);
        pager.setCurrentItem(MainActivity.getPositionForViewPage());
        return rootView;
    }
}

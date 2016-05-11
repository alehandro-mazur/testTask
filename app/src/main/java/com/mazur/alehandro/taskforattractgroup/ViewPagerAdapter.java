package com.mazur.alehandro.taskforattractgroup;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Alehandro on 5/9/2016.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    Context context;

    public ViewPagerAdapter(Context context,FragmentManager fm) {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position);
    }

    @Override
    public int getCount() {

        return MainActivity.getListModel().size();
    }
}

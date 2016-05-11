package com.mazur.alehandro.taskforattractgroup;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements IOnItemListClickedListener,
        NavigationView.OnNavigationItemSelectedListener{

    private Model model;

    private static ArrayList<Model> listModel;
    private static int positionForViewPage=0;

    private FragmentTransaction transaction;
    private MainListFragment listFragment;
    private ProgressDialog pDialog;
    private String jsonUrl="http://others.php-cd.attractgroup.com/test.json";
    private ViewPagerFragment viewPagerFragment;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;

    public static ArrayList<Model> getListModel() {
        return listModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView=(NavigationView)findViewById(R.id.nav_view);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        actionBarDrawerToggle.syncState();
        getJsonFromURL();

   }




    private void initializeView() {
        if(getResources().getDisplayMetrics().heightPixels>1700
                &&getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            listFragment=new MainListFragment();
            viewPagerFragment= new ViewPagerFragment();
            transaction=getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragmentContainer,listFragment);
            transaction.add(R.id.viewPagerFragmentContainer,viewPagerFragment);
            transaction.commit();
        }else{
            listFragment = new MainListFragment();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragmentContainer, listFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private void getJsonFromURL(){
        GetJsonTask getJsonTask=new GetJsonTask();
        getJsonTask.execute(jsonUrl);

    }
    public static int getPositionForViewPage() {
        return positionForViewPage;
    }

    @Override
    public void itemClicked(int position) {
        if(getResources().getDisplayMetrics().heightPixels>1700
                &&getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE)
        {
            viewPagerFragment.pager.setCurrentItem(position);
        }else {
            positionForViewPage = position;
            viewPagerFragment = new ViewPagerFragment();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, viewPagerFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }


    private  class GetJsonTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog=new ProgressDialog(MainActivity.this);
            pDialog.show();
            pDialog.setMessage("Loading Data...");
        }

        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            InputStream stream = null;
            String line = "";
            String jsonResponse = "";
            StringBuffer buffer = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                buffer = new StringBuffer();

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                jsonResponse = buffer.toString();
                //Log.d("TaskMain",jsonResponse);
                model = new Model();
                listModel = new ArrayList<>();
                JSONArray parentArray = new JSONArray(jsonResponse);

                for (int i = 0; i < parentArray.length(); i++) {
                    model = new Model();
                    JSONObject targetObject = parentArray.getJSONObject(i);
                    model.setItemId(Integer.parseInt(targetObject.getString("itemId")));
                    Log.d("TaskMain", "ItemId=" + model.getItemId());
                    model.setName(targetObject.getString("name"));
                    Log.d("TaskMain", "name=" + model.getName());
                    model.setImageUrl(targetObject.getString("image"));
                    Log.d("TaskMain", "ImageUrl=" + model.getImageUrl());
                    model.setDescription(targetObject.getString("description"));
                    Log.d("TaskMain", "Description=" + model.getDescription());
                    model.setTime(Long.parseLong(targetObject.getString("time")));
                    Log.d("TaskMain", "Time=" + model.getTime());

                    listModel.add(model);
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            sortCollection();
            pDialog.dismiss();
            initializeView();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }

    public void sortCollection(){
        Collections.sort(listModel, new Comparator<Model>(){
            public int compare(Model m1, Model m2) {
                return m1.getName().compareToIgnoreCase(m2.getName());
            }
        });
    }
}






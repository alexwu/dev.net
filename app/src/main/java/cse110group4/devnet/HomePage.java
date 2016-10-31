package cse110group4.devnet;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiaweihe on 10/23/16.
 */

public class HomePage extends AppCompatActivity {

    private List<Project> projects;
    private RecyclerView rv;
    private Toolbar toolbar;
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;

    public static class DevDrawer {
        public static int PROFILE = 0;
        public static int PROJECTS = 1;
        public static int FAVORITES = 2;
        public static int SETTINGS = 3;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recyclerview_activity);

        rv=(RecyclerView)findViewById(R.id.rv);
        setupToolbar();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        mPlanetTitles = new String[4];
        mPlanetTitles[0] = "Projects";
        mPlanetTitles[1] = "Profile";
        mPlanetTitles[2] = "Favorites";
        mPlanetTitles[3] = "Settings";

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(getApplicationContext(), NavDrawer.class);
                startActivity(nextActivity);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.homeFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(getApplicationContext(), MakePost.class);
                startActivity(nextActivity);
            }
        });
        initializeData();
        ArrayList<String> newPost = getIntent().getStringArrayListExtra("post");
        if (newPost != null) {

            projects.add(new Project(newPost.get(0), newPost.get(1)));
        }
        initializeAdapter();
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle(getIntent().getStringExtra("type"));
        toolbar.setNavigationIcon(R.drawable.ic_hamburger_white);
        setSupportActionBar(toolbar);
    }
    private void initializeData(){
        projects = new ArrayList<>();
        projects.add(new Project("UI for Delivery Service App", "Required Skills: Python"));
        projects.add(new Project("Image Recognition for Camera App", "Required Skills: Google API, Swift/Obj-C"));
        projects.add(new Project("App that creates apps", "Required Skills: Machine learning, Java, JSON"));
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(projects);
        rv.setAdapter(adapter);
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        Intent goTo;
        if(position == DevDrawer.PROFILE) {
            //goTo = new Intent(getApplicationContext(), )
        }
        else if(position == DevDrawer.PROJECTS) {
            //goTo = new Intent(getApplicationContext(), )
        }
        else if(position == DevDrawer.FAVORITES) {
            //goTo = new Intent(getApplicationContext(), )
        }
        else if(position == DevDrawer.SETTINGS) {
            //goTo = new Intent(getApplicationContext(), )
        }
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}

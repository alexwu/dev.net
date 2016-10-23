package cse110group4.devnet;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiaweihe on 10/23/16.
 */

public class RecyclerViewActivity extends Activity {

    private List<Project> projects;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recyclerview_activity);

        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();
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
}

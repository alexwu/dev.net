package cse110group4.devnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by jamesbombeelu on 10/30/16.
 */

public class ClientHomePage extends AppCompatActivity {

    private RecyclerView rv;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recyclerview_activity);

        rv=(RecyclerView)findViewById(R.id.rv);
            setupToolbar();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.homeFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //initializeAdapter();
    }

        private void setupToolbar() {
            toolbar = (Toolbar) findViewById(R.id.my_toolbar);
            toolbar.setTitle("Client Home");
            setSupportActionBar(toolbar);
        }
    private void initializeAdapter(ArrayList data){
        RVAdapter adapter = new RVAdapter(data);
        rv.setAdapter(adapter);
    }
}

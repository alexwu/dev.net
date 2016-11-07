package cse110group4.devnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import static cse110group4.devnet.R.id.toolbar;

public class PostPage extends AppCompatActivity {

    Intent intent;
    TextView post_content;
    Toolbar toolbar;
    String key = "recipientEmail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_page);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent request = new Intent(getApplicationContext(), RequestProject.class);
                //TODO: need to get email address instead of whatever is in content
                String recipientEmail = intent.getStringExtra("content");

                Bundle bundle = new Bundle();

                bundle.putString(key, recipientEmail);

                request.putExtras(bundle);
                startActivity(request);
            }
        });

        intent = getIntent();
        this.setTitle("Fucking Title");

        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        post_content = (TextView) findViewById(R.id.post_contents);
        post_content.setText(intent.getStringExtra("content"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setTitle("Fucking Title");

        setSupportActionBar(toolbar);
        return true;
    }
}

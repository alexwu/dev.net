package cse110group4.devnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.fitness.data.Value;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static cse110group4.devnet.R.id.toolbar;

public class PostPage extends AppCompatActivity {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ValueEventListener userPostListener;
    private User currentUser;
    private TextView post_content;
    private Toolbar toolbar;
    private Intent lastIntent;
    String key = "recipientEmail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_page);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent request = new Intent(getApplicationContext(), RequestProject.class);
                //TODO: need to get email address instead of whatever is in content
                String recipientEmail = lastIntent.getStringExtra("content");

                Bundle bundle = new Bundle();

                bundle.putString(key, recipientEmail);

                request.putExtras(bundle);
                startActivity(request);
            }
        });
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                 mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {
                    // User is signed in
                    Log.d("TEST", "onAuthStateChanged:signed_in:" + mUser.getUid());
                } else {
                    // User is signed out
                    Log.d("TEST", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        userPostListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                currentUser = dataSnapshot.child(mUser.getUid()).getValue(User.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TEST", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabase.child("users").addValueEventListener(userPostListener);

        lastIntent = getIntent();
        this.setTitle(lastIntent.getStringExtra("title"));
        post_content = (TextView) findViewById(R.id.post_contents);
        post_content.setText(lastIntent.getStringExtra("content"));
    }
    private void toggleFavorite(MenuItem item) {

        if (currentUser.isFavorite(lastIntent.getStringExtra("id"))) {
            mDatabase.child("users").child(mUser.getUid()).child("favorites").child(lastIntent.getStringExtra("id")).removeValue();
            item.setIcon(R.drawable.ic_action_not_important);
        }
        else {
            Map<String, Object> userUpdates = new HashMap<>();
            Map<String, Object> favorite = new HashMap<>();
            System.out.println(lastIntent.getStringExtra("id"));
            favorite.put(lastIntent.getStringExtra("id"), "test");
            userUpdates.put("/users/" + mUser.getUid() + "/favorites/" + lastIntent.getStringExtra("id"), favorite);

            mDatabase.updateChildren(userUpdates);
            item.setIcon(R.drawable.ic_action_important);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.post_page_toolbar, menu);
        MenuItem star = menu.findItem(R.id.action_favorite);
        if (currentUser.isFavorite(lastIntent.getStringExtra("id"))) {
            star.setIcon(R.drawable.ic_action_important);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:

                toggleFavorite(item);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}

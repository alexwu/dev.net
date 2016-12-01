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
    private ValueEventListener postsListener;
    private User currentUser;
    private Post currentPost;
    private String postId;
    private TextView post_short;
    private TextView post_deadline;
    private TextView post_payment;
    private TextView post_content;

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Intent lastIntent;
    private FloatingActionButton fab;
    private final String TAG = "PostPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_page);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        setSupportActionBar(toolbar);

        lastIntent = getIntent();

        post_short = (TextView) findViewById(R.id.post_short);
        post_payment = (TextView) findViewById(R.id.post_payment);
        post_deadline = (TextView) findViewById(R.id.post_deadline);
        post_content = (TextView) findViewById(R.id.post_contents);

        this.setTitle(lastIntent.getStringExtra("title"));
        postId = lastIntent.getStringExtra("id");

        fab = (FloatingActionButton) findViewById(R.id.fab);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                 mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + mUser.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        userPostListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Get the current users associated User object
                currentUser = dataSnapshot.child(mUser.getUid()).getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w(TAG, "getUserInfo:onCancelled", databaseError.toException());
            }
        };
        postsListener = new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                currentPost = dataSnapshot.child(postId).getValue(Post.class);

                post_short.setText(currentPost.getDescription());
                post_payment.setText("Payment: $" + currentPost.getPayment());
                post_deadline.setText("Deadline: " + currentPost.getDeadline());
                post_content.setText("Description: " + currentPost.getBody());

                //If person is a developer, go to the page to make a request.
                if (currentUser.isDeveloper()) {
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent request = new Intent(getApplicationContext(), RequestProject.class);
                            Bundle bundle = new Bundle();

                            bundle.putString("clientId", currentPost.getUserId());
                            bundle.putString("postId", currentPost.getPostId());
                            bundle.putString("suitorId", mUser.getUid());
                            bundle.putString("suitorName", currentUser.getName());
                            bundle.putString("postTitle", currentPost.getTitle());

                            request.putExtras(bundle);
                            startActivity(request);
                        }
                    });
                }
                //Otherwise client should be able to edit the post and see a fab that goes with that.
                else {
                    fab.setImageResource(R.drawable.ic_edit_white_48dp);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent editPost = new Intent(getApplicationContext(), MakePost.class);
                            Bundle postBundle = new Bundle();

                            postBundle.putString("title", currentPost.getTitle());
                            postBundle.putString("deadline", currentPost.getDeadline());
                            postBundle.putString("payment", currentPost.getPayment());
                            postBundle.putString("description", currentPost.getDescription());
                            postBundle.putString("body", currentPost.getBody());
                            postBundle.putString("id", currentPost.getPostId());

                            editPost.putExtra("post", postBundle);
                            editPost.putExtra("isNew", false);

                            startActivity(editPost);
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };

        mDatabase.child("users").addValueEventListener(userPostListener);
        mDatabase.child("posts").addListenerForSingleValueEvent(postsListener);

    }

    //This is called upon when star is clicked
    private void toggleFavorite(MenuItem item) {

        //If the person has already favorited it, it should be filled
        if (currentUser.isFavorite(lastIntent.getStringExtra("id"))) {
            mDatabase.child("users").child(mUser.getUid()).child("favorites").child(lastIntent.getStringExtra("id")).removeValue();
            item.setIcon(R.drawable.ic_action_not_important);
        }
        else {
            Map<String, Object> userUpdates = new HashMap<>();
            Map<String, Object> favorite = new HashMap<>();

            favorite.put(lastIntent.getStringExtra("id"), "test");
            userUpdates.put(lastIntent.getStringExtra("id"), favorite);

            mDatabase.child("users").child(mUser.getUid()).child("favorites").updateChildren(userUpdates);
            item.setIcon(R.drawable.ic_action_important);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.post_page_toolbar, menu);
        if(currentUser.isDeveloper()) {
            MenuItem star = menu.findItem(R.id.action_favorite);
            star.setVisible(true);
            if (currentUser.isFavorite(lastIntent.getStringExtra("id"))) {
                star.setIcon(R.drawable.ic_action_important);
            }
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                if(currentUser.isDeveloper()) {
                    toggleFavorite(item);
                }
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

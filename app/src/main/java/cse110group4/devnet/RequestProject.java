package cse110group4.devnet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RequestProject extends AppCompatActivity {
    private EditText emailTitle;
    private EditText emailRecipient;
    private String key;
    private Intent lastIntent;
    private Bundle requestInfo;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_project);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        key = "recipientEmail";

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        emailRecipient = (EditText)findViewById(R.id.email_recipient);
        //Get the bundle
        lastIntent = getIntent();
        requestInfo = getIntent().getExtras();
        //Extract the data…
        //String recipientEmail = bundle.getString(key);

        //emailRecipient.setText(recipientEmail);

        emailRecipient.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {


                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_NEXT) {
                    String inputText = textView.getText().toString();
                    // OPTIONAL: show toast for input
                    //Toast.makeText(MakePost.this, "Project: "
                    //        + inputText, Toast.LENGTH_SHORT).show();

                    //close keyboard
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    handled = true;
                }
                return handled;
            }

        });

        emailTitle = (EditText)findViewById(R.id.email_title);
        emailTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_NEXT) {
                    String inputText = textView.getText().toString();

                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    handled = true;
                }
                return handled;
            }

        });

        EditText emailBody = (EditText)findViewById(R.id.email_body);
        emailBody.setHorizontallyScrolling(false);
        emailBody.setMaxLines(6);
        emailBody.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    String inputText = textView.getText().toString();

                    //close keyboard
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    handled = true;
                }
                return handled;
            }

        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "You sent a request!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Map<String, Object> updatePost = new HashMap<>();
                Map<String, Object> updateUser = new HashMap<>();
                Map<String, Object> favoritesValues = new HashMap<>();
                favoritesValues.put(requestInfo.getString("suitorId"), mAuth.getCurrentUser().getProviderData());
                updatePost.put("/posts/" + requestInfo.getString("postId") + "/suitors", favoritesValues);
                updateUser.put("/users/" + requestInfo.getString("clientId") + "/posts/" + requestInfo.getString("postId") + "/suitors", favoritesValues);

                mDatabase.updateChildren(updatePost);
                mDatabase.updateChildren(updateUser);
                startActivity(new Intent(getApplicationContext(), HomeWithDrawer.class));
            }
        });
    }

}

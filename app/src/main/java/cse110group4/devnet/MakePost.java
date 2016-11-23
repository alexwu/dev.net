package cse110group4.devnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import android.content.Context;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MakePost extends AppCompatActivity {
    // Use to get text field values
    String[] devpost = new String[3];
    public void returnString(String stuff, int index){
        devpost[index] = stuff;
    }
    private EditText editTitle;
    private EditText editSkills;
    private EditText editDescription;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        editTitle = (EditText)findViewById(R.id.project_title);
        editTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_NEXT) {
                    String inputText = textView.getText().toString();
                    returnString(inputText, 0);
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

        editSkills = (EditText)findViewById(R.id.skills_needed);
        editSkills.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_NEXT) {
                    String inputText = textView.getText().toString();
                    returnString(inputText, 1);
                    // OPTIONAL: show toast for input
                    //Toast.makeText(MakePost.this, "Skills needed: "
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

        editDescription = (EditText)findViewById(R.id.description);
        editDescription.setHorizontallyScrolling(false);
        editDescription.setMaxLines(10);
        editDescription.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    String inputText = textView.getText().toString();
                    returnString(inputText, 2);
                    // OPTIONAL: show toast for input
                    //Toast.makeText(MakePost.this, "Description: "
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.makePostFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValidPost(editTitle.getText().toString(), editSkills.getText().toString(), editDescription.getText().toString())) {
                    String key = mDatabase.child("posts").push().getKey();
                    System.out.println("Make Post Description: " + editDescription.getText().toString());
                    Post post = new Post(editTitle.getText().toString(), editSkills.getText().toString(), editDescription.getText().toString(), mUser.getUid(), key);
                    Map<String, Object> postValues = post.toMap();
                    Map<String, Object> childUpdates = new HashMap<>();
                    Map<String, Object> userUpdates = new HashMap<>();
                    childUpdates.put("/posts/" + key, postValues);
                    userUpdates.put("/users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/posts/" + key, postValues);

                    mDatabase.updateChildren(childUpdates);
                    mDatabase.updateChildren(userUpdates);

                    startActivity(new Intent(getApplicationContext(), HomeWithDrawer.class));
                }

            }
        });
    }

    public boolean isValidPost(String pTitle, String pDescription, String pBody) {

        if (!TextUtils.isEmpty(pTitle) && !TextUtils.isEmpty(pDescription) && !TextUtils.isEmpty(pBody)) {
            return true;
        }
        return false;
    }

}

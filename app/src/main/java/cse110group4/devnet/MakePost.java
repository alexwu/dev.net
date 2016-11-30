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

    String[] devpost = new String[3];
    public void returnString(String stuff, int index){
        devpost[index] = stuff;
    }
    private Bundle postBundle;
    private EditText editTitle;
    private EditText editDeadline;
    private EditText editPayment;
    private EditText editBody;
    private EditText editDescription;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private Intent lastIntent;

    private final String TAG = "MakePost";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        lastIntent = getIntent();

        editTitle = (EditText)findViewById(R.id.project_title);
        editDeadline = (EditText) findViewById(R.id.project_deadline);
        editPayment = (EditText) findViewById(R.id.project_payment);
        editDescription = (EditText)findViewById(R.id.skills_needed);
        editBody = (EditText)findViewById(R.id.description);


        if(!lastIntent.getBooleanExtra("isNew", true)) {
            postBundle = lastIntent.getBundleExtra(("post"));

            editTitle.setText(postBundle.getString("title"), TextView.BufferType.EDITABLE);
            editDeadline.setText(postBundle.getString("deadline"), TextView.BufferType.EDITABLE);
            editPayment.setText(postBundle.getString("payment"), TextView.BufferType.EDITABLE);
            editDescription.setText(postBundle.getString("description"), TextView.BufferType.EDITABLE);
            editBody.setText(postBundle.getString("body"), TextView.BufferType.EDITABLE);

            Log.d(TAG, "is an edited post!");
        }


        editTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_NEXT) {
                    String inputText = textView.getText().toString();
                    returnString(inputText, 0);

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

        editDeadline.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_NEXT) {

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
        editPayment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_NEXT) {
                    String inputText = textView.getText().toString();
                    returnString(inputText, 1);

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
        editDescription.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_NEXT) {
                    String inputText = textView.getText().toString();
                    returnString(inputText, 0);

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
        editBody.setHorizontallyScrolling(false);
        editBody.setMaxLines(10);
        editBody.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    String inputText = textView.getText().toString();
                    returnString(inputText, 2);
                    // OPTIONAL: show toast for input
                    Toast.makeText(MakePost.this, "Description: "
                            + inputText, Toast.LENGTH_SHORT).show();

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
                if(!lastIntent.getBooleanExtra("isNew", true)) {
                    if (isValidPost(editTitle.getText().toString(), editDescription.getText().toString(), editBody.getText().toString(), editDeadline.getText().toString(), editPayment.getText().toString())) {
                        String key = lastIntent.getBundleExtra("post").getString("id");
                        System.out.println("Make Post Description: " + editDescription.getText().toString());
                        Post post = new Post(editTitle.getText().toString(), editDeadline.getText().toString(), editPayment.getText().toString(), editDescription.getText().toString(), editBody.getText().toString(), mUser.getUid(), key);
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
                else {
                    if (isValidPost(editTitle.getText().toString(), editDescription.getText().toString(), editBody.getText().toString(), editDeadline.getText().toString(), editPayment.getText().toString())) {
                        String key = mDatabase.child("posts").push().getKey();
                        System.out.println("Make Post Description: " + editDescription.getText().toString());
                        Post post = new Post(editTitle.getText().toString(), editDeadline.getText().toString(), editPayment.getText().toString(), editDescription.getText().toString(), editBody.getText().toString(), mUser.getUid(), key);
                        Map<String, Object> postValues = post.toMap();
                        Map<String, Object> childUpdates = new HashMap<>();
                        Map<String, Object> userUpdates = new HashMap<>();
                        childUpdates.put("/posts/" + key, postValues);
                        userUpdates.put("/users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/posts/" + key, postValues);

                        mDatabase.updateChildren(childUpdates);
                        mDatabase.updateChildren(userUpdates);

                        Intent listenerIntent = new Intent(getApplicationContext(), NotificationListener.class);
                        postBundle = new Bundle();
                        postBundle.putString("postId", key);
                        listenerIntent.putExtra("requestInfo", postBundle);
                        startService(listenerIntent);
                        Log.d(TAG, "Past the start Service Call");
                        startActivity(new Intent(getApplicationContext(), HomeWithDrawer.class));
                    }
                }

            }
        });
    }

    public boolean isValidPost(String pTitle, String pDescription, String pBody, String pDeadline, String pPayment) {

        if (!TextUtils.isEmpty(pTitle) && !TextUtils.isEmpty(pDescription) && !TextUtils.isEmpty(pBody) && !TextUtils.isEmpty(pDeadline) && !TextUtils.isEmpty(pPayment)) {
            return true;
        }
        return false;
    }

}

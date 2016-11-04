package cse110group4.devnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import android.content.Context;

import java.util.ArrayList;

public class MakePost extends AppCompatActivity {
    // Use to get text field values
    String[] devpost = new String[3];
    public void returnString(String stuff, int index){
        devpost[index] = stuff;
    }
    private EditText editTitle;
    private EditText editSkills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




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

        EditText editDescription = (EditText)findViewById(R.id.description);
        editDescription.setHorizontallyScrolling(false);
        editDescription.setMaxLines(6);
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(getApplicationContext(), HomeWithDrawer.class);
                ArrayList<String> post = new ArrayList<String>();
                post.add(0, editTitle.getText().toString());
                post.add(1, editSkills.getText().toString());

                nextActivity.putExtra("type", "Client Home");
                nextActivity.putExtra("post", post);
                startActivity(nextActivity);
            }
        });
    }

}

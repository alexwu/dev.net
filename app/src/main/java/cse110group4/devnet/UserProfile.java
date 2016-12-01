package cse110group4.devnet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        String s = getIntent().getStringExtra("user_name");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(s);
        stringBuilder.append("'s Profile");

        String titleString = stringBuilder.toString();
        setTitle(titleString);

        TextView t=new TextView(this);
        t=(TextView)findViewById(R.id.editText3);
        String gitEmail = getIntent().getStringExtra("user_git");
        if(gitEmail != null){
            t.setText(gitEmail);
        }
        else{
            t.setText("No GitHub set up");
        }

        String email = getIntent().getStringExtra("user_email");
        TextView emailView =new TextView(this);
        emailView=(TextView) findViewById(R.id.editText);
        emailView.setText(email);
    }
}

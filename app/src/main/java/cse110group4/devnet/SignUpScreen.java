package cse110group4.devnet;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignUpScreen extends AppCompatActivity {

    private TextView mClickableTextView;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        signUpButton = (Button) findViewById(R.id.button2);
        mClickableTextView = (TextView) findViewById(R.id.clickableText);

        SpannableString ss = new SpannableString(mClickableTextView.getText().toString());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent login = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(login);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 25, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mClickableTextView.setText(ss);
        mClickableTextView.setMovementMethod(LinkMovementMethod.getInstance());
        //mClickableTextView.setTextColor(Color.BLUE);
        mClickableTextView.setHighlightColor(Color.TRANSPARENT);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (getApplicationContext(), MakeUser.class));
            }
        });

    }
}

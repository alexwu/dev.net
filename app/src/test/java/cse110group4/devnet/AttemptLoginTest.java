package cse110group4.devnet;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.test.mock.MockContext;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.regex.Pattern;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by alexwu on 11/30/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class AttemptLoginTest {
    FirebaseAuth mockedAuth = mock(FirebaseAuth.class);
    Context mockedContext = Mockito.mock(Context.class);

    @Test
    public void attemptsSuccessfulLogin() {
        LoginScreen login = new LoginScreen();
        String testEmail = "dev@dev.net";
        String testPassword = "password";
        AutoCompleteTextView mEmailView = new AutoCompleteTextView(mockedContext);
        EditText mPasswordView = new EditText(mockedContext);

        mEmailView.setText(testEmail);
        mPasswordView.setText(testPassword);

        login.attemptLogin(mEmailView, mPasswordView);
        verify(mockedAuth).signInWithEmailAndPassword(testEmail, testPassword);
    }
}

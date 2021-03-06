package iti.mobile.touropia.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import iti.mobile.touropia.R;
import iti.mobile.touropia.Screens.Registration.RegistrationActivity;
import iti.mobile.touropia.Screens.Home.HomeActivity;
import maes.tech.intentanim.CustomIntent;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView {
    EditText userName;
    EditText password;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 0;
    SignInButton signInButton;
    FirebaseUser currentUser;
    private FirebaseAuth firebaseAuth;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String user_name;
    String user_password;
    boolean isFirst;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    LoginContract.LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenterImpl(this);
        firebaseAuth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        isFirst = sharedPreferences.getBoolean("firstTime", true);

        userName = findViewById(R.id.edtUserName);
        password = findViewById(R.id.edtPassword);


        if (!isFirst) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("userId", firebaseAuth.getCurrentUser().getUid());
            intent.putExtras(bundle);
            startActivity(intent);
            CustomIntent.customType(LoginActivity.this, "right-to-left");

        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton = findViewById(R.id.google_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("userId", firebaseAuth.getCurrentUser().getUid());
                            intent.putExtras(bundle);
                            editor = sharedPreferences.edit();
                            editor.putBoolean("firstTime", false);
                            editor.commit();

                            startActivity(intent);
                            CustomIntent.customType(LoginActivity.this, "right-to-left");

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "signInWithCredential:failure", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void signIn()  // google login
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void checklogin(View view) {


        user_name = userName.getText().toString().trim();
        user_password = password.getText().toString().trim();

        if (user_name.length() > 0 && user_password.length() > 0) {
            presenter.checkLogin(user_name, user_password, firebaseAuth);


        } else {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
        }
    }


    public void registeruser(View view) {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
        CustomIntent.customType(LoginActivity.this, "bottom-to-up");

    }


    @Override
    public void successLogin() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userId", firebaseAuth.getCurrentUser().getUid());
        intent.putExtras(bundle);
        editor = sharedPreferences.edit();
        editor.putBoolean("firstTime", false);
        editor.commit();
        startActivity(intent);
        CustomIntent.customType(LoginActivity.this, "right-to-left");


    }

    @Override
    public void failLogin() {

        Toast.makeText(this, "Please enter valid email and password", Toast.LENGTH_SHORT).show();
    }
}

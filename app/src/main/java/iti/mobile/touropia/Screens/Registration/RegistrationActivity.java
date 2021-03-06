package iti.mobile.touropia.Screens.Registration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//import iti.mobile.touropia.AddTrip.AddTrip;
//import iti.mobile.touropia.MainActivity;
import iti.mobile.touropia.Login.LoginActivity;
import iti.mobile.touropia.R;
import iti.mobile.touropia.Screens.Home.HomeActivity;
import maes.tech.intentanim.CustomIntent;

public class RegistrationActivity extends AppCompatActivity implements RegistrationContract.RegistrationnView {

    EditText edtemailregister, edtpasswordregister, edtName;
    String email, password, name;
    private FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean isFirst;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    RegistrationContract.RegistrationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_main);
        presenter = new RegistrationPresenterImpl(this);

        sharedPreferences = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        edtemailregister = (EditText) findViewById(R.id.edtemailregister);
        edtpasswordregister = (EditText) findViewById(R.id.edtpasswordregister);
        edtName = (EditText) findViewById(R.id.edtName);
    }

    public void registeruser(View view) {
        registerUser();

    }

    public void login(View view) {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        CustomIntent.customType(RegistrationActivity.this, "right-to-left");


    }

    void registerUser() {
        email = edtemailregister.getText().toString();
        password = edtpasswordregister.getText().toString();
        name = edtName.getText().toString();
        if (email.length() > 0 && password.length() > 0 && name.length() > 0) {

            presenter.checkRegistration(email, password, mAuth);
        } else {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = mAuth.getCurrentUser();
    }

    @Override
    public void successRegistration() {
        Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userId", mAuth.getCurrentUser().getUid());
        bundle.putString("userName", name);
        intent.putExtras(bundle);
        editor = sharedPreferences.edit();
        editor.putBoolean("firstTime", false);
        editor.commit();
        startActivity(intent);
        CustomIntent.customType(RegistrationActivity.this, "right-to-left");


    }

    @Override
    public void failRegistration() {

        Toast.makeText(this, "Please enter valid email and password", Toast.LENGTH_SHORT).show();

    }
}

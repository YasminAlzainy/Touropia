package iti.mobile.touropia.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//import iti.mobile.touropia.AddTrip.AddTrip;
import iti.mobile.touropia.MainActivity;
import iti.mobile.touropia.R;

public class RegistrationActivity extends AppCompatActivity {

    EditText edtemailregister, edtpasswordregister;
    String email, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_main);
        mAuth = FirebaseAuth.getInstance();
        edtemailregister = (EditText) findViewById(R.id.edtemailregister);
        edtpasswordregister = (EditText) findViewById(R.id.edtpasswordregister);
    }

    public void registeruser(View view) {
        registerUser();

    }

    void registerUser() {
        email = edtemailregister.getText().toString();
        password = edtpasswordregister.getText().toString();
        if (email.length() > 0 && password.length() > 0) {

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()) {

//                        Intent intent = new Intent(RegistrationActivity.this, AddTrip.class);
                        //                      startActivity(intent);

                        Toast.makeText(RegistrationActivity.this, "Registeration done", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(RegistrationActivity.this, "Registeration Failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = mAuth.getCurrentUser();
    }

}

package iti.mobile.touropia.Screens.Registration;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseRegistration {
    RegistrationContract.RegistrationPresenter presenter;

    FirebaseRegistration(RegistrationContract.RegistrationPresenter presenter) {
        this.presenter = presenter;
    }

    public void checkRegistration(String email, String password, FirebaseAuth firebaseAuth) {

        firebaseAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    presenter.successRegistration();
                }
                else
                {
                    presenter.failRegistration();
                }

            }
        });
    }
}

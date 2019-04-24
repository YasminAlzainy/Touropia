package iti.mobile.touropia.Login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseLogin {
    LoginContract.LoginPresenter presenter;

    FirebaseLogin(LoginContract.LoginPresenter presenter) {
        this.presenter = presenter;
    }
    public void checkLogin(String email , String password , FirebaseAuth firebaseAuth)
    {
        firebaseAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    presenter.successLogin();
                }
                else
                {
                    presenter.failLogin();
                }
            }
        });
    }

}

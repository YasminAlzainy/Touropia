//package iti.mobile.touropia.Login.Model;
//
//import android.content.Intent;
//import android.support.annotation.NonNull;
//import android.widget.Toast;
//
//import com.facebook.CallbackManager;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//import java.util.concurrent.Executor;
//
//import iti.mobile.touropia.Login.LoginActivity;
//import iti.mobile.touropia.Login.LoginContract;
//import iti.mobile.touropia.Login.LoginPresenter;
//import iti.mobile.touropia.MainActivity;
//
//public class LoginModel implements LoginContract.LoginModel {
//
//    LoginPresenter presenter;
//    FirebaseUser currentUser;
//    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//
//    public LoginModel(LoginContract.LoginPresenter loginActivity, LoginContract.LoginPresenter loginPresenter) {
//    }
//
//    public void login(String user_name, String password) {
//        if (user_name.length() > 0 && password.length() > 0) {
//            firebaseAuth.signInWithEmailAndPassword(user_name, password)
//                    // executer ??
//                    .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//
//                            presenter.successLogin();
//                        }
//
//
//                    });
//
//
//        } else {
//            presenter.emptyInput();
//
//        }
//    }
//}

package iti.mobile.touropia.Login;

import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenterImpl implements LoginContract.LoginPresenter {
    LoginContract.LoginView view;
    FirebaseLogin firebaseLogin;

    LoginPresenterImpl(LoginContract.LoginView view) {
        this.view = view;
        firebaseLogin = new FirebaseLogin(this);

    }

    @Override
    public void checkLogin(String email, String password, FirebaseAuth firebaseAuth) {

        firebaseLogin.checkLogin(email, password, firebaseAuth);
    }

    @Override
    public void successLogin() {
        view.successLogin();

    }

    @Override
    public void failLogin() {
        view.failLogin();
    }
}

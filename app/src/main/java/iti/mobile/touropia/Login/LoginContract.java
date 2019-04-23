package iti.mobile.touropia.Login;

import com.google.firebase.auth.FirebaseAuth;

public interface LoginContract {
    interface LoginPresenter {
        void checkLogin(String email, String password, FirebaseAuth firebaseAuth);

        void successLogin();

        void failLogin();

    }

    interface LoginView {

        void successLogin();

        void failLogin();


    }
}

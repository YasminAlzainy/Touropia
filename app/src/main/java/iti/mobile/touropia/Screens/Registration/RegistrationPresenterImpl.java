package iti.mobile.touropia.Screens.Registration;

import com.google.firebase.auth.FirebaseAuth;

public class RegistrationPresenterImpl implements RegistrationContract.RegistrationPresenter {
    RegistrationContract.RegistrationnView view;
    FirebaseRegistration firebaseRegistration;

    RegistrationPresenterImpl(RegistrationContract.RegistrationnView view) {
        this.view = view;
        firebaseRegistration = new FirebaseRegistration(this);

    }

    @Override
    public void checkRegistration(String email, String password, FirebaseAuth firebaseAuth) {
        firebaseRegistration.checkRegistration(email, password, firebaseAuth);
    }

    @Override
    public void successRegistration() {
        view.successRegistration();

    }

    @Override
    public void failRegistration() {

        view.failRegistration();
    }
}

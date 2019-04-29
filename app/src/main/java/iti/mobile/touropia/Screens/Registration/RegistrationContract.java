package iti.mobile.touropia.Screens.Registration;

import com.google.firebase.auth.FirebaseAuth;

public interface RegistrationContract {
    interface RegistrationPresenter {
        void checkRegistration(String email, String password, FirebaseAuth firebaseAuth);

        void successRegistration();

        void failRegistration();

    }

    interface RegistrationnView {

        void successRegistration();

        void failRegistration();


    }
}

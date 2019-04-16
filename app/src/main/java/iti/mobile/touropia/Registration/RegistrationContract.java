package iti.mobile.touropia.Registration;

public interface RegistrationContract {
    interface RegistrationView {
        void successRegistration();

        void failedRegistration();

        void emptyInput();


    }

    interface RegistrationPresenter {
        void Registration(String email, String password);

        void successRegistration();

        void failedRegistration();

        void emptyInput();


    }
}

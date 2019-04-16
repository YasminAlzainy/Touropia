//package iti.mobile.touropia.Login;
//
//import iti.mobile.touropia.Login.Model.LoginModel;
//
//public class LoginPresenter implements LoginContract.LoginPresenter {
//   LoginContract.LoginModel model;
//
//    LoginContract.LoginPresenter loginActivity;
//
//    public LoginPresenter(LoginContract.LoginPresenter loginActivity) {
//        this.loginActivity = loginActivity;
//        model = new LoginModel(loginActivity,this);
//    }
//
//    @Override
//    public void login(String email, String password) {
//
//        model.login(email,password);
//
//    }
//
//    @Override
//    public void successLogin() {
//
//    }
//
//    @Override
//    public void failedLogin() {
//
//    }
//
//    @Override
//    public void emptyInput() {
//
//
//    }
//
//}

package iti.mobile.touropia.Screens.SplashScreen;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import iti.mobile.touropia.Login.LoginActivity;
import iti.mobile.touropia.R;

public class SpalshScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);

        Thread thread = new Thread() {
            @Override
            public void run() {

                try {
                    sleep(2000);
                    Intent intent = new Intent(SpalshScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }


}

package id.odojadmin.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import id.odojadmin.R;
import id.odojadmin.helper.PreferenceHelper;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PreferenceHelper.getInstance().isAuthenticated())
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                else {
                    startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}

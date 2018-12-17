package template.solainteractive.com.androidsolatemplate.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import butterknife.BindView;
import butterknife.ButterKnife;
import template.solainteractive.com.androidsolatemplate.Constants;
import template.solainteractive.com.androidsolatemplate.MyApplication;
import template.solainteractive.com.androidsolatemplate.R;
import template.solainteractive.com.androidsolatemplate.base.BaseActivity;
import template.solainteractive.com.androidsolatemplate.view.SignIn.SigninActivity;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.img_splash)
    ImageView imgSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);


        setupSplashGif();
    }

    private void setupSplashGif() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    ImageView ivMainLogo = findViewById(R.id.img_splash);
                    GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(ivMainLogo);
                    Glide.with(SplashActivity.this).load(R.drawable.recharge_logo).into(imageViewTarget);
                    setupEntryActivity();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 250);
    }

    public void setupEntryActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                validateNextScreen();
            }
        }, Constants.SPLASH);
    }

    public void validateNextScreen() {
        System.out.println("my application validation : " + MyApplication.getInstance().getLoginStatus());
        if (MyApplication.getInstance().getLoginStatus().equals(Constants.LogInStatus.LOGIN)) {
            Intent intent = new Intent(SplashActivity.this, ShowTerminalActivity.class);
            startActivity(intent);
            finish();
        } else if (MyApplication.getInstance().getLoginStatus().isEmpty() || MyApplication.getInstance().getLoginStatus().equals(Constants.LogInStatus.NOT_LOGIN)) {
            Intent i = new Intent(SplashActivity.this, SigninActivity.class);
            startActivity(i);
            finish();
        }
    }
}

package com.suvidha.bazaaratyourdwaar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Login extends AppCompatActivity implements View.OnClickListener{

    TextView textView_signup;
    Context context = this;
    ImageView imageView_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        textView_signup = findViewById(R.id.login_tv_signup);
        imageView_back = findViewById(R.id.login_iv_back);

        textView_signup.setOnClickListener(this);
        imageView_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.login_tv_signup:
            {
                startActivity(new Intent(context,SignUp.class));
                break;
            }
            case R.id.login_iv_back:
            {
                finish();
                break;
            }
        }
    }
}
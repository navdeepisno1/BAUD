package com.suvidha.bazaaratyourdwaar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    TextView textView_login;
    Context context = this;
    ImageView imageView_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        textView_login = findViewById(R.id.signup_tv_login);
        imageView_back = findViewById(R.id.signup_iv_back);

        textView_login.setOnClickListener(this);
        imageView_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.signup_iv_back:
            {
                finish();
                break;
            }
            case R.id.signup_tv_login:
            {
                Intent intent = new Intent(context,Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            }
        }
    }
}
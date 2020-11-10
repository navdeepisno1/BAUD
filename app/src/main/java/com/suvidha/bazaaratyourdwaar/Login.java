package com.suvidha.bazaaratyourdwaar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener{

    TextView textView_signup;
    Context context = this;
    ImageView imageView_back;
    Button button_login;
    TextInputEditText textInputEditText_username,textInputEditText_password;
    TextInputLayout textInputLayout_username,textInputLayout_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if(checkPermission())
        {
            Toast.makeText(context,"Permission is already Granted",Toast.LENGTH_LONG).show();
        }
        else
        {
            requestPermission();
        }

        textView_signup = findViewById(R.id.login_tv_signup);
        imageView_back = findViewById(R.id.login_iv_back);
        button_login = findViewById(R.id.login_btn_login);
        textInputEditText_username = findViewById(R.id.login_et_username);
        textInputEditText_password = findViewById(R.id.login_et_password);
        textInputLayout_username = findViewById(R.id.login_etl_username);
        textInputLayout_password = findViewById(R.id.login_etl_password);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username,password;
                username = textInputEditText_username.getText().toString().trim();
                password = textInputEditText_password.getText().toString().trim();

                if(username.isEmpty())
                {
                    textInputLayout_username.setError("Username Required");
                    return;
                }
                if(password.isEmpty())
                {
                    textInputLayout_password.setError("Password Required");
                    return;
                }

                textInputLayout_username.setError(null);
                textInputLayout_password.setError(null);
                DatabaseReference databaseReference_users = FirebaseDatabase.getInstance().getReference("Users");
                databaseReference_users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean isUserFound = false;
                        for(DataSnapshot dataSnapshot: snapshot.getChildren())
                        {
                            String dbUsername = dataSnapshot.child("username").getValue(String.class);
                            String dbPassword = dataSnapshot.child("password").getValue(String.class);
                            Log.e("username",dbUsername);
                            Log.e("password",dbPassword);

                            if(dbUsername.equals(username) && dbPassword.equals(password))
                            {
                                Log.e("user","Found");
                                isUserFound = true;
                                break;
                            }
                            else
                            {
                                Log.e("user","Not Found");
                            }
                        }
                        if(isUserFound)
                        {
                            LinearLayout linearLayout = findViewById(R.id.login_ll);
                            Snackbar snackbar = Snackbar.make(linearLayout,"Logged In SuccessFully",Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                        else
                        {
                            LinearLayout linearLayout = findViewById(R.id.login_ll);
                            Snackbar snackbar = Snackbar.make(linearLayout,"No User Found",Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

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

    private boolean checkPermission()
    {
       return ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case 101:
            {
                if(grantResults.length>0)
                {
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(context,"Permission Granted",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(context,"Permission Not Granted",Toast.LENGTH_LONG).show();
                    }
                }
            }
        }

    }
}
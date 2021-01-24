package com.example.songchimp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AboutActivity extends AppCompatActivity {

    private TextView user_email;
    private Button login_signout;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private ImageView back_button;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_about);
        user_email = findViewById(R.id.tv_email_about);
        login_signout = findViewById(R.id.login_signout_btn_about);
        progressBar = findViewById(R.id.progressBar_about);
        back_button = findViewById(R.id.back_btn_about);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(firebaseUser == null){
            user_email.setText("Sign in to view your email");
            login_signout.setText("Sign in");
            login_signout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    Intent i = new Intent(AboutActivity.this,register_activity.class);
                    startActivity(i);
                    finish();
                }
            });
        }else {
            user_email.setText(firebaseUser.getEmail());
            login_signout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.signOut();
                    Intent i = new Intent(AboutActivity.this,register_activity.class);
                    startActivity(i);
                    finish();
                }
            });
        }
    }
}
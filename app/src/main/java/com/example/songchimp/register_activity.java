package com.example.songchimp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class register_activity extends AppCompatActivity {

    private FrameLayout framelayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        framelayout = findViewById(R.id.register_frame_layout);
        setfragment(new sign_in_fragment());


    }
    private void setfragment(Fragment fragment){
        FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(framelayout.getId(),fragment);
        fragmentTransaction.commit();
    }

}
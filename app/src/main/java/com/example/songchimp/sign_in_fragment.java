package com.example.songchimp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link sign_in_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sign_in_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private EditText email;
    private EditText password;
    private ProgressBar progressBar;
    private Button loginbtn;
    private String emailpattern =  "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private FirebaseAuth firebaseAuth;
    private String mParam1;
    private String mParam2;

    public sign_in_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sign_in_fragment.
     */

    // TODO: Rename and change types and number of parameters
    public static sign_in_fragment newInstance(String param1, String param2) {
        sign_in_fragment fragment = new sign_in_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextView donthaveanaccount;
    private FrameLayout parentframeLayout;
    private ImageButton cross_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        donthaveanaccount = view.findViewById(R.id.tv_register_login);
        parentframeLayout = getActivity().findViewById(R.id.register_frame_layout);
        cross_button = view.findViewById(R.id.btn_cross_login);
        email = view.findViewById(R.id.email_address_login);
        password = view.findViewById(R.id.password_login);
        progressBar = view.findViewById(R.id.progressBar_login);
        loginbtn = view.findViewById(R.id.btn_login);
        firebaseAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkinputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkinputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkemailandpassword();
            }
        });
        donthaveanaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setfragment(new sign_up_fragment());
            }
        });
        cross_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() ,HomeActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


    }

    private void checkemailandpassword() {
        if (email.getText().toString().matches(emailpattern)){
                    progressBar.setVisibility(View.VISIBLE);
                    loginbtn.setEnabled(false);
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Intent intent = new Intent(getActivity(),HomeActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
        }else {
            email.setError("Invalid Email");
        }
    }

    private void checkinputs() {
        if (!TextUtils.isEmpty(email.getText())){
            if (!TextUtils.isEmpty(password.getText())){
                loginbtn.setEnabled(true);
            }else {
                loginbtn.setEnabled(false);
            }
        }else {
            loginbtn.setEnabled(false);
        }
    }

    private void setfragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(parentframeLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
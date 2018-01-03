package com.hutech.doan.mangajava.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hutech.doan.mangajava.Model.Profile;
import com.hutech.doan.mangajava.R;

/**
 * Created by Thuan on 26-12-2017.
 EditText
 */

public class RegistActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference mDatabase;
    EditText edtEmail,edtPassword;
    Button btnRegist,btnLogin;

    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener  mAuthStateListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_activity);

        AnhXa();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null)
                {
                    startActivity(new Intent(RegistActivity.this,MainActivity.class));
                }
            }
        };
        //--
        btnRegist.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }
    private void DangKy()
    {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        //Check NULL
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Vui lòng nhập Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Vui lòng nhập Mật khẩu",Toast.LENGTH_SHORT).show();
            return;
        }

        //Thuc thi
        else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                               // finish();

                                Toast.makeText(RegistActivity.this, "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegistActivity.this, "Không thành công", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }



    private void AnhXa()
    {
        edtEmail = (EditText) findViewById(R.id.edt_Email);
        edtPassword = (EditText) findViewById(R.id.edt_Pass);
        btnLogin = (Button) findViewById(R.id.btn_Login);
        btnRegist = (Button) findViewById(R.id.btn_Regist);


    }
    @Override
    public void onClick(View view) {
        if(view == btnRegist)
        {
            DangKy();

        }
        if(view == btnLogin)
        {
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
}

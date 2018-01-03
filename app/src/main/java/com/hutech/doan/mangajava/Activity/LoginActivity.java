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
import com.google.firebase.auth.FirebaseUser;
import com.hutech.doan.mangajava.R;

/**
 * Created by Thuan on 11/24/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtEmail, edtPassword;
    Button btnLogin,btnRegist;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        AnhXa();

        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null)
                {
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
            }
        };
        btnRegist.setOnClickListener(this); // dang ky
        btnLogin.setOnClickListener(this); //Dang nhap
    }


    private void Dangnhap()
    {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
            //Check NULL
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Vui lòng nhập Email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Vui lòng nhập Mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            //Thuc thi ham
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //finish();
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Không thành công", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

            //Toast.makeText(LoginActivity.this,"Không thể kết nối đến máy chủ",Toast.LENGTH_SHORT).show();
    }
    private void AnhXa()
    {
        edtEmail = (EditText) findViewById(R.id.edt_Email);
        edtPassword = (EditText) findViewById(R.id.edt_Pass);
        btnLogin = (Button) findViewById(R.id.btn_Login);
        btnRegist = (Button) findViewById(R.id.btn_Regist);

    }

    public void onClick(View v)
    {
        if(v == btnLogin)
        {
            Dangnhap();
        }
        if(v == btnRegist)
        {
            startActivity(new Intent(LoginActivity.this,RegistActivity.class));
        }
    }
}

package com.hutech.doan.mangajava.Activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hutech.doan.mangajava.Fragment.ManagerFragment;
import com.hutech.doan.mangajava.Model.Manga;
import com.hutech.doan.mangajava.R;
import com.hutech.doan.mangajava.Utils.FragmentUtils;

/**
 * Created by Thuan on 12/28/2017.
 */

public class ManageStoryActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference mDatabase;
    Button btn_Save;
    FirebaseAuth mAuth;
    EditText edtDesciption,edtTitle,edtAuthor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managestory_activity);
        AnhXa();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (mAuth.getCurrentUser() == null)
        {
            startActivity(new Intent(this,LoginActivity.class));
        }
        btn_Save.setOnClickListener(this);

    }

    private void Additem() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


            String Title = edtTitle.getText().toString();
            String Author = edtAuthor.getText().toString();
            String Descr = edtDesciption.getText().toString();
            boolean status = false, like = false;
            Manga manga = new Manga(Title, Author, Descr, status, like, mAuth.getCurrentUser().getEmail());

            mDatabase.child("StoryManga").push().setValue(manga);
            Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MainActivity.class));

    }

    public void AnhXa()
    {
        edtTitle = (EditText)findViewById(R.id.edt_title);
        edtAuthor = (EditText)findViewById(R.id.edt_Author);
        btn_Save = (Button)findViewById(R.id.btn_Save);
        edtDesciption = (EditText)findViewById(R.id.edt_Description);
    }


    @Override
    public void onClick(View view)
    {
        if(view==btn_Save)
        {
            Additem();
        }
    }


}

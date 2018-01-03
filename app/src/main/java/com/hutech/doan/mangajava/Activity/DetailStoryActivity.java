package com.hutech.doan.mangajava.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hutech.doan.mangajava.Model.Manga;
import com.hutech.doan.mangajava.R;

import java.util.Date;


/**
 * Created by Thuan on 29-12-2017.
 */

public class DetailStoryActivity extends AppCompatActivity implements ValueEventListener {

    //Firebase
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    String MangaID=null;
    private Manga currManga;

    //Layout
    TextView tvTitle,tvAuthor,tvDescription;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailstory_activity);
        AnhXa();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()==null)
        {
            startActivity(new Intent(this,LoginActivity.class));
            Toast.makeText(this, "Hết phiên đăng nhập.", Toast.LENGTH_SHORT).show();
        }
        Intent intent = getIntent();
        if (intent == null) this.finish();
        MangaID = intent.getStringExtra("MangaID");
        if (MangaID == null) this.finish();
        Log.d("MangaID",String.valueOf(MangaID));
        mDatabase = FirebaseDatabase.getInstance().getReference("StoryManga").child(MangaID);
        mDatabase.addValueEventListener(this);

    }

    private void AnhXa()
    {
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvAuthor = (TextView)findViewById(R.id.tvAuthor);
        tvDescription =(TextView)findViewById(R.id.tvDescription);
    }


    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Manga manga= dataSnapshot.getValue(Manga.class);
        this.currManga = manga;
        this.currManga.setMangaID(dataSnapshot.getKey());
        tvTitle.setText(manga.getTitle());
        tvAuthor.setText(manga.getAuthor());
        tvDescription.setText(manga.getDesciption());
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}

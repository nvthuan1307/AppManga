package com.hutech.doan.mangajava.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hutech.doan.mangajava.Model.PicManga;
import com.hutech.doan.mangajava.R;

/**
 * Created by Thuan on 02-01-2018.
 */

public class DetailPicActivity extends AppCompatActivity implements ValueEventListener {

    //Firebase
    private StorageReference mStorage;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    String MangaID=null;
    private PicManga currManga;

    //Layout
    ImageView imageView;
    TextView tvTitle,tvAuthor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailpicstory_activity);
        AnhXa();
        mStorage = FirebaseStorage.getInstance().getReference("PicManga");
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
        mDatabase = FirebaseDatabase.getInstance().getReference("PicManga").child(MangaID);
        mDatabase.addValueEventListener(this);

    }

    private void AnhXa()
    {
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvAuthor = (TextView)findViewById(R.id.tvAuthor);
        imageView =(ImageView) findViewById(R.id.img_view);
    }


    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        PicManga picmanga= dataSnapshot.getValue(PicManga.class);
        this.currManga = picmanga;
        this.currManga.setIDPicManga(dataSnapshot.getKey());
        tvTitle.setText(picmanga.getTitle());
        tvAuthor.setText(picmanga.getAuthor());
        Glide.with(this).load(Uri.parse(picmanga.getUrl())).into(imageView);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}

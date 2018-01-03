package com.hutech.doan.mangajava.Activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.hutech.doan.mangajava.Model.PicManga;
import com.hutech.doan.mangajava.R;
import com.hutech.doan.mangajava.Utils.BitmapUtils;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Thuan on 12/28/2017.
 */

public class ManagePicActivity extends AppCompatActivity implements View.OnClickListener{

    private StorageReference storage;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private ImageView imageView;
    private EditText edtTitle,edtAuthor;
    private String imguri = null;
    private Button btn_Save,btn_Upload;
    private TextView tvtitle,tvauthor;
    private FirebaseUser mUser;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();


    private final int SELECT_PHOTO = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managepic_activity);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        AnhXa();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (mAuth.getCurrentUser() == null)
        {
            startActivity(new Intent(this,LoginActivity.class));
        }
        btn_Save.setOnClickListener(this);
        btn_Upload.setOnClickListener(this);

    }
    private void Additem() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        if(imguri!=null) {

            Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Vui lòng chọn hình cần up", Toast.LENGTH_SHORT).show();
        }
    }
    public void AnhXa()
    {
        edtTitle = (EditText)findViewById(R.id.edt_title);
        edtAuthor = (EditText)findViewById(R.id.edt_Author);
        btn_Save = (Button)findViewById(R.id.btn_Save);
        btn_Upload = (Button)findViewById(R.id.btn_upload);
        imageView = (ImageView)findViewById(R.id.img_viewup);
        tvauthor = (TextView)findViewById(R.id.tvAUTHOR);
        tvtitle = (TextView)findViewById(R.id.tvTITLE);
        tvtitle.setVisibility(View.INVISIBLE);
        tvauthor.setVisibility(View.INVISIBLE);

    }


    @Override
    public void onClick(View view)
    {
        if(view==btn_Save)
        {
            Additem();
        }
        if(view==btn_Upload)
        {
            Upitem();

        }
    }

    private void Upitem() {

       // storage = FirebaseStorage.getInstance().getReference("PicManga");
        String title = edtTitle.getText().toString().trim();
        String author = edtAuthor.getText().toString().trim();
        if( title.equals("")||author.equals(""))
        {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
        }

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data)
    {
        storage = FirebaseStorage.getInstance().getReference();

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_PHOTO){
            if(resultCode == RESULT_OK)
            {
                try {
                    Uri imageUri = data.getData();
                    imguri=imageUri.toString();
                    InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapUtils.inputStreamToBitmap(imageStream);
                    //selectedImage = BitmapUtils.resizeImage(selectedImage,600,315);
                    selectedImage = BitmapUtils.compressImage(selectedImage,300,300);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    //imgProfile.setImageBitmap(selectedImage);
                    //storage.child("image_profile");
                    StorageMetadata metadata = new StorageMetadata.Builder()
                            .setContentType("image/jpg")
                            .build();
                    UploadTask uploadTask = storage.child("PicManga").child(edtTitle.getText().toString().trim()+".jpg").putBytes(baos.toByteArray(),metadata);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String url = taskSnapshot.getDownloadUrl().toString();
                            DatabaseReference userChild = mDatabase.child("PicManga");
                            PicManga picManga = new PicManga(edtTitle.getText().toString(),edtAuthor.getText().toString(),url,String.valueOf(mUser.getEmail()));
                            userChild.push().setValue(picManga);
                            //Toast.makeText(ProfileActivity.this, taskSnapshot.getDownloadUrl().toString(), Toast.LENGTH_SHORT).show();
                            Glide.with(ManagePicActivity.this).load(url).into(imageView);
                        }
                    });
                    edtAuthor.setVisibility(View.INVISIBLE);
                    edtTitle.setVisibility(View.INVISIBLE);
                    tvtitle.setText(edtTitle.getText().toString()); tvtitle.setVisibility(View.VISIBLE);
                    tvauthor.setText(edtAuthor.getText().toString()); tvauthor.setVisibility(View.VISIBLE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }




}

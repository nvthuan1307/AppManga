package com.hutech.doan.mangajava.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hutech.doan.mangajava.Activity.LoginActivity;
import com.hutech.doan.mangajava.Activity.MainActivity;
import com.hutech.doan.mangajava.Model.Manga;
import com.hutech.doan.mangajava.Model.PicManga;
import com.hutech.doan.mangajava.Model.Profile;
import com.hutech.doan.mangajava.R;
import com.hutech.doan.mangajava.Utils.BitmapUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Thuan on 11/24/2017.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener {

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    StorageReference mStorage;
    private String imguri = null;
    private final int SELECT_PHOTO = 1;
    //Layout
    EditText edtPhone,edtName;
    Button btnSave;
    TextView tvEmail,tvPermis;
    ImageButton btn_image;
    ImageView img_avatar;
    private  int p =0;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment,
                container, false);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if(mAuth.getCurrentUser()==null)
        {
            startActivity(new Intent(getActivity(),LoginActivity.class));
            Toast.makeText(getActivity(), "Hết phiên đăng nhập.", Toast.LENGTH_SHORT).show();
        }

        btnSave = (Button)view.findViewById(R.id.btn_Save);
        edtName =  (EditText)view.findViewById(R.id.edt_Name);
        edtPhone= (EditText)view.findViewById(R.id.edt_Phone);
        tvEmail = (TextView)view.findViewById(R.id.tv_Email);
        tvPermis = (TextView)view.findViewById(R.id.tvPermision);
        btn_image = (ImageButton)view.findViewById(R.id.btn_image);
        img_avatar = (ImageView)view.findViewById(R.id.img_avatar);


        edtPhone.clearFocus();
        edtName.clearFocus();
        mDatabase.child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Profile profile =  dataSnapshot.getValue(Profile.class);


                edtName.setText(profile.getName());
                edtPhone.setText(profile.getPhone());
                p= profile.getPermission();
                if(p==1)
                    tvPermis.setText("Phân quyền: ADMIN");
                else tvPermis.setText("Phân quyền: Người dùng");
                String urlimg= profile.getUrl();
                if(urlimg!=null)
                Glide.with(getActivity()).load(urlimg).into(img_avatar);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tvEmail.setText(String.valueOf(mUser.getEmail()));
        btnSave.setOnClickListener(this);
        btn_image.setOnClickListener(this);
        return view;

    }
    private void LuuThongTin() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

            String FullName = edtName.getText().toString();
            String Phone = edtPhone.getText().toString();

           // Profile profile = new Profile(FullName,Phone,0);
            Profile profile = new Profile(edtName.getText().toString(),edtPhone.getText().toString(),p,imguri);
            mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(profile);
            Toast.makeText(getActivity(), "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(),MainActivity.class));


    }


    @Override
    public void onClick(View view) {
        if(view == btnSave)
        {
            LuuThongTin();
        }
        if(view == btn_image)
        {
            UpHinh();
        }
    }

    private void UpHinh() {
        /*
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);*/

        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECT_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        mStorage = FirebaseStorage.getInstance().getReference();

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri imageUri = data.getData();

                    InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapUtils.inputStreamToBitmap(imageStream);
                    //selectedImage = BitmapUtils.resizeImage(selectedImage,600,315);
                    selectedImage = BitmapUtils.compressImage(selectedImage, 300, 300);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    //imgProfile.setImageBitmap(selectedImage);
                    //storage.child("image_profile");
                    StorageMetadata metadata = new StorageMetadata.Builder()
                            .setContentType("image/jpg")
                            .build();
                    UploadTask uploadTask = mStorage.child("Users").child(mAuth.getUid().toString() + ".jpg").putBytes(baos.toByteArray(), metadata);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String url = taskSnapshot.getDownloadUrl().toString();
                            imguri = url.toString();
                            //Toast.makeText(ProfileActivity.this, taskSnapshot.getDownloadUrl().toString(), Toast.LENGTH_SHORT).show();
                            Glide.with(getActivity()).load(url).into(img_avatar);
                        }
                    });

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

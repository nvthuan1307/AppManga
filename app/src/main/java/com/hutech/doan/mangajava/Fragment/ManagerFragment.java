package com.hutech.doan.mangajava.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hutech.doan.mangajava.Activity.LoginActivity;
import com.hutech.doan.mangajava.Activity.ManagePicActivity;
import com.hutech.doan.mangajava.Activity.ManageStoryActivity;
import com.hutech.doan.mangajava.Activity.RegistActivity;
import com.hutech.doan.mangajava.Model.Profile;
import com.hutech.doan.mangajava.R;

/**
 * Created by Thuan on 11/24/2017.
 */

public class ManagerFragment extends Fragment implements View.OnClickListener{

    TextView tvFullName,tvEmail;
    Button btn_picmanga,btn_storymanga;
    FirebaseUser mUser;
    String UID=null;
    DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.manager_fragment, container, false);

        btn_picmanga = (Button)view.findViewById(R.id.btn_picmanga);
        btn_storymanga = (Button)view.findViewById(R.id.btn_storymanga);
        tvFullName = (TextView)view.findViewById(R.id.tvFullName);
        tvEmail = (TextView)view.findViewById(R.id.tvEmail);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Profile profile = dataSnapshot.getValue(Profile.class);

                tvFullName.setText(String.valueOf(profile.getName()));
                tvEmail.setText(String.valueOf(mUser.getEmail()));
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



        btn_storymanga.setOnClickListener(this);
        btn_picmanga.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == btn_picmanga)
        {
            startActivity(new Intent(getActivity(),ManagePicActivity.class));

        }
        if(view == btn_storymanga)
        {
            Intent intent = new Intent(getActivity(), ManageStoryActivity.class);
            startActivity(intent);
        }
    }
}

package com.hutech.doan.mangajava.Activity;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hutech.doan.mangajava.Fragment.AboutFragment;
import com.hutech.doan.mangajava.Fragment.ManagerFragment;
import com.hutech.doan.mangajava.Fragment.PicmangaFragment;
import com.hutech.doan.mangajava.Fragment.ProfileFragment;
import com.hutech.doan.mangajava.Fragment.StorymangaFragment;
import com.hutech.doan.mangajava.Model.Profile;
import com.hutech.doan.mangajava.R;
import com.hutech.doan.mangajava.Utils.FragmentUtils;
import com.hutech.doan.mangajava.Utils.MiscUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //-- Firebase
    //FirebaseDatabase mFirebaseDatabase;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    FirebaseAuth.AuthStateListener  mAuthStateListener;
    private ValueEventListener mPostListener;
    private  String UID;
    private int permission=0;
    //Layout
    NavigationView navigationView;
    ListView listView;
    View viewHeader;
    TextView tvName,tvEmail;
    ImageView img_avatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewHeader = navigationView.getHeaderView(0);
        tvEmail = (TextView) viewHeader.findViewById(R.id.tv_Emailhead);
        tvName = (TextView)viewHeader.findViewById(R.id.tv_Namehead);
        img_avatar = (ImageView) viewHeader.findViewById(R.id.img_avatar);



        //-- Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        // UID = mUser.getUid();
        if(mAuth.getCurrentUser() ==null)
        {
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }


        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Profile profile = dataSnapshot.getValue(Profile.class);
                tvEmail.setText(mAuth.getCurrentUser().getEmail());
                tvName.setText(profile.getName());
                permission=profile.getPermission();
                Log.d("permission", String.valueOf(permission));
                if(permission==0)
                { navigationView.getMenu().findItem(R.id.nav_MangaManager).setVisible(false);}
                else
                { navigationView.getMenu().findItem(R.id.nav_MangaManager).setVisible(true);}
                Glide.with(MainActivity.this).load(profile.getUrl()).into(img_avatar);
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

        // Kiem tra trang thai login
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                {
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
            }
        };





/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, tb, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        checkProfile();

    }


    private void checkProfile() {
        mDatabase.child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Profile user = dataSnapshot.getValue(Profile.class);
                if(user == null || user.getName() == null || user.getPhone() == null){
                    MiscUtils.showAlertDialog(MainActivity.this, "Thiếu thông tin cá nhân", "Bạn vui lòng bổ sung thông tin cá nhân đầy đủ trước khi sử dụng app", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FragmentUtils.replaceFragment(R.id.flContent,getSupportFragmentManager(), new ProfileFragment());
                        }
                    },false);
                }else{
                    FragmentUtils.replaceFragment(R.id.flContent,getSupportFragmentManager(), new AboutFragment());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            Toast.makeText(this, "Infomation", Toast.LENGTH_SHORT)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_ImageManga) {
            FragmentUtils.replaceFragment(R.id.flContent,getSupportFragmentManager(), new PicmangaFragment());
        } else if (id == R.id.nav_TextManga) {
            FragmentUtils.replaceFragment(R.id.flContent,getSupportFragmentManager(), new StorymangaFragment());
        } else if (id == R.id.nav_FavouManga) {

        } else if (id == R.id.nav_MangaManager) {
            FragmentUtils.replaceFragment(R.id.flContent,getSupportFragmentManager(), new ManagerFragment());
        } else if (id == R.id.nav_Infomation) {
            FragmentUtils.replaceFragment(R.id.flContent,getSupportFragmentManager(), new ProfileFragment());
        } else if (id == R.id.nav_About) {
            FragmentUtils.replaceFragment(R.id.flContent,getSupportFragmentManager(), new AboutFragment());
        }else if (id == R.id.nav_Out) {
            mAuth.signOut();
            finish();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

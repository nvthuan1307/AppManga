package com.hutech.doan.mangajava.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hutech.doan.mangajava.Activity.DetailPicActivity;
import com.hutech.doan.mangajava.Adapter.PicMangaAdapter;
import com.hutech.doan.mangajava.Model.PicManga;
import com.hutech.doan.mangajava.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thuan on 12/26/2017.
 */

public class PicmangaFragment extends Fragment {

    //private List<PicManga> imgList;

    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private ListView listView;
    private PicMangaAdapter picMangaAdapter = null;
    private ArrayList<PicManga> ArraypicMangas  = new ArrayList<>();


    //StoragePath=image
    //DatabasePath=image
    //request code 1234
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.picmanga_fargment,
                container, false);
        // imgList = new ArrayList<>();
        mStorage = FirebaseStorage.getInstance().getReference("PicManga");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        listView = (ListView)view.findViewById(R.id.listViewImage);
        picMangaAdapter = new PicMangaAdapter(getActivity(),R.layout.item_picmanga,ArraypicMangas);
        listView.setAdapter(picMangaAdapter);

    //Load DATA
        mDatabase.child("PicManga").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PicManga picmanga = dataSnapshot.getValue(PicManga.class);
                picmanga.setIDPicManga(dataSnapshot.getKey());

                if(picmanga==null)
                {
                    Toast.makeText(getActivity(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                picMangaAdapter.addMangaItem(picmanga);

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PicManga picmanga = ArraypicMangas.get(i);
                //Toast.makeText(getActivity(), String.valueOf(manga.getMangaID()), Toast.LENGTH_SHORT).show();
                Log.d("ID-PicManga",String.valueOf(picmanga.getIDPicManga()));
                Intent intent = new Intent(getActivity(), DetailPicActivity.class);
                intent.putExtra("MangaID",String.valueOf(picmanga.getIDPicManga()));
                startActivity(intent);

            }
        });



        return view;
    }
}

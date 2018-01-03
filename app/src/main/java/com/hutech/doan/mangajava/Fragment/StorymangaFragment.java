package com.hutech.doan.mangajava.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hutech.doan.mangajava.Activity.DetailStoryActivity;
import com.hutech.doan.mangajava.Adapter.MangaAdapter;
import com.hutech.doan.mangajava.Model.Manga;
import com.hutech.doan.mangajava.R;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thuan on 28-12-2017.
 */

public class StorymangaFragment extends Fragment {

    //Firebase
    DatabaseReference mDatabase;

    //Layout
    ListView listView;

    //Array List
    ArrayList<Manga> arrayList = new ArrayList<>();
    MangaAdapter mangaAdapter = null;
   // ArrayList<String> Key = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.storymanga_fragment,
                container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        listView = (ListView)view.findViewById(R.id.datalist);
        mangaAdapter = new MangaAdapter(getActivity(), R.layout.item_manga, arrayList);
        listView.setAdapter(mangaAdapter);
/*
        mDatabase.child("StoryManga").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String clubkey = childSnapshot.getKey();
                    Key.add(clubkey);
                    Log.d("Key", String.valueOf(clubkey));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

*/
        mDatabase.child("StoryManga").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final Manga manga = dataSnapshot.getValue(Manga.class);
                manga.setMangaID(dataSnapshot.getKey());
                if (manga == null)
                {
                    Toast.makeText(getActivity(),"Không có dữ liệu",Toast.LENGTH_SHORT).show();
                    return;
                }

                mangaAdapter.addMangaItem(manga);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Manga manga = dataSnapshot.getValue(Manga.class);
                manga.setMangaID(dataSnapshot.getKey());
                mangaAdapter.updateManga(manga);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Manga manga = dataSnapshot.getValue(Manga.class);
                mangaAdapter.deleteManga(manga);
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
                Manga manga = arrayList.get(i);
                //Toast.makeText(getActivity(), String.valueOf(manga.getMangaID()), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), DetailStoryActivity.class);
                intent.putExtra("MangaID",String.valueOf(manga.getMangaID()));
                startActivity(intent);

            }
        });

        return view;


    }
}

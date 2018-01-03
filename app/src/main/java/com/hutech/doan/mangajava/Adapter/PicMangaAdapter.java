package com.hutech.doan.mangajava.Adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hutech.doan.mangajava.Model.PicManga;
import com.hutech.doan.mangajava.R;
import com.squareup.picasso.Picasso;


import java.net.URL;
import java.util.List;

/**
 * Created by Thuan on 1/1/2018.
 */

public class PicMangaAdapter extends ArrayAdapter {

    private Context context;
    private List<PicManga> listImage;
    private int layoutRes;
    private StorageReference mStorage;

    public PicMangaAdapter(Context context, int resource, List<PicManga> objects) {
        super(context, resource, objects);
        this.context = context;
        this.listImage = objects;
        this.layoutRes = resource;
    }

    public void addMangaItem(PicManga picManga)
    {

        this.listImage.add(picManga);
        this.notifyDataSetChanged();
    }
    public boolean updateManga(PicManga newpicManga){
        if(newpicManga == null) return false;
        int indexOldOrder = listImage.indexOf(newpicManga);
        if(indexOldOrder == -1) return false;
        listImage.set(indexOldOrder,newpicManga);
        notifyDataSetChanged();
        return true;
    }
    public boolean deleteManga(PicManga picManga) {
        if(picManga == null) return false;
        boolean isDeteled = listImage.remove(picManga);
        notifyDataSetChanged();
        return isDeteled;
    }

    public class ViewHolder {
        TextView tvTitle, tvAuthor;
        ImageView imageView;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder holder = new ViewHolder();
        PicManga picManga;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutRes, parent, false);

            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvtitle);
            holder.tvAuthor = (TextView) convertView.findViewById(R.id.tvauthor);
            holder.imageView = (ImageView) convertView.findViewById(R.id.img_viewlist);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        picManga = listImage.get(position);
        holder.tvTitle.setText(picManga.getTitle());
        holder.tvAuthor.setText(picManga.getAuthor());

        //Picasso.with(context).load(picManga.getUrl()).into(holder.imageView);
        Glide.with(context).load(Uri.parse(picManga.getUrl())).into(holder.imageView);
        return convertView;
    }
}
/*
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        PicMangaAdapter.ViewHolder holder;
        PicManga picManga;
        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutRes, parent, false);

            // well set up the ViewHolder
            holder = new PicMangaAdapter.ViewHolder();
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvtitle);
            holder.tvAuthor = (TextView) convertView.findViewById(R.id.tvauthor);
            ImageView img = (ImageView)convertView.findViewById(R.id.imgView);


            Glide.with(context).load(listImage.get(position).getUrl()).into(img);

            // store the holder with the view.
            convertView.setTag(holder);

        }else{
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            holder = (PicMangaAdapter.ViewHolder) convertView.getTag();
        }

        picManga = listImage.get(position);
        holder.tvTitle.setText(picManga.getTitle());
        holder.tvAuthor.setText(picManga.getAuthor());



        return convertView;


    }*/




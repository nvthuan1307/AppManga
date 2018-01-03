package com.hutech.doan.mangajava.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hutech.doan.mangajava.Model.Manga;
import com.hutech.doan.mangajava.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Thuan on 12/28/2017.
 */

public class MangaAdapter extends ArrayAdapter<Manga>{
    private Context context;
    private List<Manga> mangas;
    private int layoutRes;

    public MangaAdapter(Context context, int resource, List<Manga> objects) {
        super(context, resource, objects);
        this.context = context;
        this.mangas = objects;
        this.layoutRes = resource;
    }

    public void addMangaItem(Manga manga)
    {

        this.mangas.add(manga);
        Collections.sort(this.mangas, new Comparator<Manga>() {
            @Override
            public int compare(Manga manga, Manga t1) {
                if(!manga.isStatus() && t1.isStatus())
                    return 1;
                else if (manga.isStatus() && !t1.isStatus())
                    return -1;
                return 0;
            }
        });
        this.notifyDataSetChanged();
    }
    public boolean updateManga(Manga newManga){
        if(newManga == null) return false;
        int indexOldOrder = mangas.indexOf(newManga);
        if(indexOldOrder == -1) return false;
        mangas.set(indexOldOrder,newManga);
        notifyDataSetChanged();
        return true;
    }
    public boolean deleteManga(Manga manga) {
        if(manga == null) return false;
        boolean isDeteled = mangas.remove(manga);
        notifyDataSetChanged();
        return isDeteled;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;
        Manga manga;
        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutRes, parent, false);

            // well set up the ViewHolder
            holder = new ViewHolder();
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);

            // store the holder with the view.
            convertView.setTag(holder);

        }else{
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            holder = (ViewHolder) convertView.getTag();
        }

        manga = mangas.get(position);
        holder.tvTitle.setText(manga.getTitle());
        holder.tvAuthor.setText(manga.getAuthor());


        String statusString;
        int statusColor;
        if(manga.isStatus()){
            statusString = "Đã đọc";
            statusColor = R.drawable.manga_status_success_background;
        }
        else{
            statusString = "Chưa đọc";
            statusColor = R.drawable.manga_status_unsuccess;
        }


        return convertView;


    }
    public class ViewHolder{
        TextView tvTitle, tvAuthor;

    }

}

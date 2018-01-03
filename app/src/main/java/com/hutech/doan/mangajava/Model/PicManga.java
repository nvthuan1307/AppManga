package com.hutech.doan.mangajava.Model;

import android.net.Uri;

/**
 * Created by Thuan on 1/1/2018.
get manga to user
 */

public class PicManga {
    private String title;
    private String author;
    private String url;
    private String IDPicManga;
    private String EmailCreate;


    public String getEmailCreate() {
        return EmailCreate;
    }

    public PicManga(String title, String author, String url, String emailCreate) {
        this.title = title;
        this.author = author;
        this.url = url;
        EmailCreate = emailCreate;
    }

    public void setEmailCreate(String emailCreate) {
        EmailCreate = emailCreate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PicManga() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



    public String getIDPicManga() {
        return IDPicManga;
    }

    public void setIDPicManga(String IDPicManga) {
        this.IDPicManga = IDPicManga;
    }
}

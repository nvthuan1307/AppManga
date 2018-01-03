package com.hutech.doan.mangajava.Model;

/**
 * Created by Thuan on 12/28/2017.
 */

public class Manga {
    public String getMangaID() {
        return MangaID;
    }

    public void setMangaID(String mangaID) {
        MangaID = mangaID;
    }

    private String MangaID;
    private String title;
    private String Author;
    private String Desciption;
    private boolean status;
    private boolean like;

    public Manga(String title, String author, String desciption, boolean status, boolean like, String emailCreate) {
        this.title = title;
        Author = author;
        Desciption = desciption;
        this.status = status;
        this.like = like;
        EmailCreate = emailCreate;
    }

    public String getEmailCreate() {
        return EmailCreate;
    }

    public void setEmailCreate(String emailCreate) {
        EmailCreate = emailCreate;
    }

    private String EmailCreate;


    public Manga(String title, String author, String desciption) {
        this.title = title;
        Author = author;
        Desciption = desciption;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getDesciption() {
        return Desciption;
    }

    public void setDesciption(String desciption) {
        Desciption = desciption;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public Manga() {

    }
}

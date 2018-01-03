package com.hutech.doan.mangajava.Model;

/**
 * Created by Thuan on 12/28/2017.
create new class
 */

public class Profile {
    public Profile(String name, String phone, int permission) {
        this.name = name;
        this.phone = phone;
        this.permission = permission;
    }

    public Profile() {
    }

    private String name;
    private String phone;
    private int permission;
    private String url;

    public Profile(String name, String phone, int permission, String url) {
        this.name = name;
        this.phone = phone;
        this.permission = permission;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }
}

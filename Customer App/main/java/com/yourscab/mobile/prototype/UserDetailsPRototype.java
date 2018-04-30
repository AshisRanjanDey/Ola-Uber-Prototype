package com.yourscab.mobile.prototype;



public class UserDetailsPRototype {
    public String name;
    public String phone;
    public String image_url;
    public String date_signin;
    public String email;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDetailsPRototype(String token,String name, String phone, String image_url, String date_signin, String email){
        this.name  =name;
        this.token = token;
        this.phone = phone;
        this.email = email;
        this.image_url=image_url;
        this.date_signin =date_signin;
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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDate_signin() {
        return date_signin;
    }

    public void setDate_signin(String date_signin) {
        this.date_signin = date_signin;
    }
}

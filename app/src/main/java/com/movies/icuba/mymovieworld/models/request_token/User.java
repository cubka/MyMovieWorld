package com.movies.icuba.mymovieworld.models.request_token;

/**
 * Created by icuba on 2/27/2018.
 */

public class User  {

    public String request_token;
    public String session_id;
    public String username;
    public String password;
    public String name;
    public Avatar avatar;
    public int id;

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequest_token() {
        return request_token;
    }

    public String getSession_id() {
        return session_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

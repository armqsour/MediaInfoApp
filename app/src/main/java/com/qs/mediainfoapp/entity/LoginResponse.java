package com.qs.mediainfoapp.entity;

public class LoginResponse {

    /**
     * msg : success
     * code : 0
     * expire : 604800
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2IiwiaWF0IjoxNjA4NjE1NDU4LCJleHAiOjE2MDkyMjAyNTh9.BdAcnWUIXxnXLe8LVOO-QSXCRIGLjzXUcOqVSyA0sydD1wggmUbYf9cfNWdslIQ7EE46xrLv7b-uKgQjCWBvcA
     */

    private String msg;
    private int code;
    private int expire;
    private String token;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

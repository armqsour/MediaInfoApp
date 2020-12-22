package com.qs.mediainfoapp.api;

public interface Callback {
    void onSuccess(String res);

    void onFailure(Exception e);
}

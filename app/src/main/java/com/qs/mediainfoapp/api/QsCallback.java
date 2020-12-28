package com.qs.mediainfoapp.api;

public interface QsCallback {
    void onSuccess(String res);

    void onFailure(Exception e);
}

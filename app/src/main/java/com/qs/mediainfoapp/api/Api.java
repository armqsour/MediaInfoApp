package com.qs.mediainfoapp.api;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Api {
        private static OkHttpClient client;
        private static String requestUrl;
        private static HashMap<String, Object> mparams;

        public static Api api = new Api();

        public Api(){

        }

        public static Api config(String url, HashMap<String, Object> params){
            client = new OkHttpClient.Builder()
                    .build();
            requestUrl = ApiConfig.BASE_URL + url;
            mparams = params;
            return api;
        }

        public void postRequest(Callback callback){
            JSONObject jsonObject = new JSONObject(mparams);
            String jsonStr = jsonObject.toString();
            RequestBody requestBodyJson =
                    RequestBody.create(MediaType.parse("application/json;charset=utf-8")
                            , jsonStr);
            //第三步创建Rquest
            Request request = new Request.Builder()
                    .url(requestUrl)
                    .addHeader("contentType", "application/json;charset=UTF-8")
                    .post(requestBodyJson)
                    .build();
            //第四步创建call回调对象
            final okhttp3.Call call = client.newCall(request);
            //第五步发起请求
            call.enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    Log.e("onFailure", e.getMessage());
                    callback.onFailure(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String result = response.body().string();
                    callback.onSuccess(result);
                }
            });
        }
}

package com.example.tacademy.miniautologinsample.request;


import com.example.tacademy.miniautologinsample.data.NetworkResult;
import com.example.tacademy.miniautologinsample.data.NetworkResultTemp;
import com.example.tacademy.miniautologinsample.manager.NetworkRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2016-08-09.
 */
public abstract class AbstractRequest<T> extends NetworkRequest<T> {

    protected HttpUrl.Builder getBaseUrlBuilder() {
        HttpUrl.Builder builder = new HttpUrl.Builder();
        builder.scheme("https");
        builder.host("mychoi-1470719870964.appspot.com");
        return builder;
    }

    @Override
    protected T parse(ResponseBody body) throws IOException {
        String text = body.string();
        Gson gson = new Gson();
        NetworkResultTemp temp = gson.fromJson(text, NetworkResultTemp.class);
        if (temp.getCode() == 1) {
            T result = gson.fromJson(text, getType());
            return result;
        } else {
            Type type = new TypeToken<NetworkResult<String>>(){}.getType();
            NetworkResult<String> result = gson.fromJson(text, type);
            throw new IOException(result.getResult());
        }
    }

    protected abstract Type getType();
}

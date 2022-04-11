package com.chan.googlebookdemo.data.network;

import retrofit2.Call;
import retrofit2.Response;

public interface ResponseHandler {
    void onSuccess(Call call, Response response, int reqCode);
    void onApiCrash(Call call, Throwable t, int reqCode);
}

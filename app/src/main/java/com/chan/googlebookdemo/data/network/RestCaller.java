package com.chan.googlebookdemo.data.network;

import java.io.File;
import java.net.UnknownHostException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestCaller {
    private int REQ_CODE = 0;
    ResponseHandler handler;

    public RestCaller(ResponseHandler context, Call caller, final int REQUEST_CODE) throws NumberFormatException {
        if (REQUEST_CODE <= 0) {
            NumberFormatException ex = new NumberFormatException();
            throw ex;
        }
        REQ_CODE = REQUEST_CODE;
        handler = context;

        ENQUE(caller);
    }

    public static MultipartBody.Part createMultipartFormData(String fieldName, File file) {
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData(fieldName, file.getName(), body);
    }

    private void ENQUE(Call call) {
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                handler.onSuccess(call, response, REQ_CODE);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if (t instanceof UnknownHostException)
                    handler.onApiCrash(call, new Throwable("Unable to access server. Please check your connection."), REQ_CODE);
                else
                    handler.onApiCrash(call, t, REQ_CODE);
            }
        });
    }
}

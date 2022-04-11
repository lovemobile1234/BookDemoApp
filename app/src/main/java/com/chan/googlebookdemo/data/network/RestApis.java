package com.chan.googlebookdemo.data.network;

import com.chan.googlebookdemo.constatnts.NetworkConstant;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApis {

    @GET(NetworkConstant.BOOK_URL)
    Call<JsonObject> get_books(
        @Query("q") String query,
        @Query("startIndex") int startIndex,
        @Query("maxResults") int maxResults
    );
}

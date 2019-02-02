package com.didiksazali.androidexpert.androidnews.network;

import com.didiksazali.androidexpert.androidnews.response.BeritaResponses;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIServices {

    @GET("tampil_berita.php")
    Call<BeritaResponses> request_show_all_berita();

}

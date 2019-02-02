package com.didiksazali.androidexpert.androidnews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.didiksazali.androidexpert.androidnews.network.APIServices;
import com.didiksazali.androidexpert.androidnews.network.InitRetrofit;
import com.didiksazali.androidexpert.androidnews.response.BeritaItem;
import com.didiksazali.androidexpert.androidnews.response.BeritaResponses;
//import com.didiksazali.androidexpert.androidnews.response.BeritaItem;
//import com.didiksazali.androidexpert.androidnews.response.BeritaResponses;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.rvListBerita);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tampilBerita();
    }

    private void tampilBerita(){
        APIServices api = InitRetrofit.getInstances();
        //siapkan request
        Call<BeritaResponses> beritaCall = api.request_show_all_berita();
        //kirim request
        beritaCall.enqueue(new Callback<BeritaResponses>() {
            @Override
            public void onResponse(Call<BeritaResponses> call, Response<BeritaResponses> response) {
                //pastikan response sukses
                if (response.isSuccessful()){
                    Log.d("response api", response.body().toString());
                    //tampung data response body ke variable
                    List<BeritaItem> data_berita = response.body().getBerita();
                    boolean status = response.body().isStatus();
                    //kalau status response nya = true
                    if (status){
                        //buat adapter untuk recycler view
                        BeritaAdapter adapter = new BeritaAdapter(MainActivity.this, data_berita);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(MainActivity.this, "Tidak ada berita untuk saat ini.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BeritaResponses> call, Throwable t) {
                    t.printStackTrace();
            }
        });
    }
}

package com.didiksazali.androidexpert.androidnews;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.didiksazali.androidexpert.androidnews.response.BeritaItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.MyViewHolder> {

    //buat global variable untuk menampung context
    Context context;
    List<BeritaItem> berita;

    public BeritaAdapter (Context context, List<BeritaItem> data_berita) {
        //inisialisasi
        this.context = context;
        this.berita = data_berita;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //layout inflater
        View view = LayoutInflater.from(context).inflate(R.layout.berita_item, parent, false);
        //hubungkan dengan MyViewHolder
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //set widget
        holder.tvJudul.setText(berita.get(position).getJudulBerita());
//        holder.tvTglTerbit.setText(berita.get(position).getTanggalPosting());
        String tglTerbit = berita.get(position).getTanggalPosting();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(tglTerbit);
            SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            String date_of_release = new_date_format.format(date);
//            tvReleaseDate.setText(date_of_release);
            holder.tvTglTerbit.setText(date_of_release);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvPenulis.setText("Oleh : " + berita.get(position).getPenulis());

        //dapatkan url gambar
        final String urlGambarBerita = "http://192.168.43.140/api/api_berita/images/" + berita.get(position).getFoto();

        //Set image ke widget dengan menggunakan library Glide karena image ya dari internet
        Glide.with(context).load(urlGambarBerita).into(holder.ivGambarBerita);

        //event ketika item list nya di klik
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("JDL_BERITA", berita.get(position).getJudulBerita());
                intent.putExtra("TGL_BERITA", berita.get(position).getTanggalPosting());
                intent.putExtra("PNS_BERITA", berita.get(position).getPenulis());
                intent.putExtra("FTO_BERITA", urlGambarBerita);
                intent.putExtra("ISI_BERITA", berita.get(position).getIsiBerita());
                //method startActivity hanya bisa dipakai di activity/fragment
                //jadi harus masuk ke context dulu
                context.startActivity(intent);
            }
        });
    }

    //menentukan jumlah item yang tampil
    @Override
    public int getItemCount() { return berita.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGambarBerita;
        TextView tvJudul, tvTglTerbit, tvPenulis;
        public MyViewHolder(View itemView) {
            super(itemView);
            ivGambarBerita = (ImageView)itemView.findViewById(R.id.ivPosterBerita);
            tvJudul = (TextView)itemView.findViewById(R.id.tvJudulBerita);
            tvTglTerbit = (TextView)itemView.findViewById(R.id.tvTglTerbit);
            tvPenulis = (TextView)itemView.findViewById(R.id.tvPenulis);
        }
    }

}

package com.example.thuctaptotnghiep.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thuctaptotnghiep.Object.GioHang;
import com.example.thuctaptotnghiep.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.GioHangViewHolder> {

    private List<GioHang> gioHangList;
    private Context mContext;

    public GioHangAdapter(List<GioHang> gioHangList, Context mContext) {
        this.gioHangList = gioHangList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false);
        return new GioHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangViewHolder holder, int position) {

        GioHang gioHang=gioHangList.get(position);
        holder.txt_title.setText(gioHang.getTitle());

        //chuyển đối số sang đơn vị tiền tệ việt nam
        Locale locale=new Locale("vi","VN");
        NumberFormat numberFormat=NumberFormat.getCurrencyInstance(locale);
        String gia=numberFormat.format(gioHang.getPrice());
        holder.txt_gia.setText(gia);
        holder.txt_sl_mua.setText(gioHang.getSoluong_mua()+"");
        Glide.with(mContext).load(gioHang.getImage()).into(holder.img);


    }

    @Override
    public int getItemCount() {
        if(gioHangList!=null){
            return gioHangList.size();
        }
        return 0;
    }

    public class GioHangViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView txt_title,txt_gia,txt_sl_mua;

        public GioHangViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.image_itemgiohang);
            txt_title=itemView.findViewById(R.id.txt_title_itemgiohang);
            txt_gia=itemView.findViewById(R.id.txt_gia_itemgiohang);
            txt_sl_mua=itemView.findViewById(R.id.txt_soluongmua_itemgiohang);
        }
    }
}

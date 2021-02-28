package com.example.thuctaptotnghiep.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.thuctaptotnghiep.Object.Product;
import com.example.thuctaptotnghiep.Object.User;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailFragment extends Fragment {

    User user;
    Product product;
    public static final  String TAG=DetailFragment.class.getName();
    ImageView img_back,img_detailproduct;
    TextView txt_deatailprice,txt_detaildescription,txt_detailtitle;
    Button btnmuahang;
    String ten;
    String pass;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_detail,container,false);

        img_back=view.findViewById(R.id.image_backDetail);
        img_detailproduct=view.findViewById(R.id.image_product_detail);
        txt_deatailprice=view.findViewById(R.id.txt_giaDetail);
        txt_detaildescription=view.findViewById(R.id.txt_detalproduct);
        txt_detailtitle=view.findViewById(R.id.txt_detailtitle);
        btnmuahang=view.findViewById(R.id.btn_muahang_detail);

        Bundle bundle=getArguments();

        //lấy tên của khách hàng
        ten=bundle.getString("ten");
        pass=bundle.getString("pass");

        if(bundle!=null){
            product= (Product) bundle.get("object_product");
            if(product!=null){
                txt_detailtitle.setText(product.getTitle());
                txt_deatailprice.setText(product.getPrice());
                txt_detaildescription.setText(product.getDescription());
                Glide.with(getContext()).load(product.getImage()).into(img_detailproduct);
            }

        }
        getUser(ten,pass);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getFragmentManager()!=null){
                    getFragmentManager().popBackStack();
                }

            }
        });

        //show dialog khi click button
        btnmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickShowBottomSheetDialogFragment();
            }
        });


        return view;
    }

    private void ClickShowBottomSheetDialogFragment() {
        MyBottomSheetFragment myBottomSheetFragment=MyBottomSheetFragment.getInstance(product,user);
        myBottomSheetFragment.show(getFragmentManager(),myBottomSheetFragment.getTag());
    }
    private void getUser(String tendangnhap,String pass) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.url_getdetailUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String name="";
                String email="";
                String password="";
                String tendangnhap="";
                String sdt="";

                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);

                        id=object.getInt("id");
                        name=object.getString("name");
                        email=object.getString("email");
                        password=object.getString("password");
                        tendangnhap=object.getString("tendangnhap");
                        sdt=object.getString("sdt");

                        user=new User(id,name,email,password,tendangnhap,sdt);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("tendangnhap",tendangnhap);
                params.put("pass",pass);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}

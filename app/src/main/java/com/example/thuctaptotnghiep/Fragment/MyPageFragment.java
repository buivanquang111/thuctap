package com.example.thuctaptotnghiep.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thuctaptotnghiep.MainActivity;
import com.example.thuctaptotnghiep.Object.Product;
import com.example.thuctaptotnghiep.Object.User;
import com.example.thuctaptotnghiep.R;
import com.example.thuctaptotnghiep.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MyPageFragment extends Fragment {

    ImageView image_setting;
    TextView textView;
    RelativeLayout linearLayout;
    User user;


    public static MyPageFragment getInstance(String ten,String pass){
        MyPageFragment myPageFragment=new MyPageFragment();
        Bundle bundle=new Bundle();
        bundle.putString("tendangnhap",ten);
        bundle.putString("pass",pass);
        myPageFragment.setArguments(bundle);
        return  myPageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mypage,container,false);


        image_setting=view.findViewById(R.id.image_setting_mypager);
        textView=view.findViewById(R.id.txt_tenthanhvien_mypager);
        linearLayout=view.findViewById(R.id.linerlayout_mypager);

        String ten= (String) getArguments().get("tendangnhap");
        String pas= (String) getArguments().get("pass");
        textView.setText(ten);

        getUser(ten,pas);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chuyenDuLieuSangDetailMyPageFragment(user);
            }
        });

        image_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chuyenDuLieuSangDetailMyPageFragment(user);
            }
        });

        return view;

    }

    private void chuyenDuLieuSangDetailMyPageFragment(User user) {
        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        DetailMyPageFragment detailMyPageFragment=new DetailMyPageFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("user_detaimypage",user);
        detailMyPageFragment.setArguments(bundle);

        transaction.replace(R.id.frame_layout,detailMyPageFragment);
        transaction.addToBackStack(detailMyPageFragment.TAG);
        transaction.commit();
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

}

package com.example.thuctaptotnghiep;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText ed_name,ed_email,ed_pass,ed_tendangnhap,ed_sdt;
    String str_name,str_email,strpassword,str_tendangnhap,str_sdt;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ed_name=findViewById(R.id.ed_name);
        ed_email=findViewById(R.id.ed_email);
        ed_pass=findViewById(R.id.ed_pass);
        ed_tendangnhap=findViewById(R.id.ed_tendangnhap);
        ed_sdt=findViewById(R.id.ed_sdt);
        img_back=findViewById(R.id.img_back);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public void Register(View view) {
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.show();

        str_name=ed_name.getText().toString().trim();
        str_email=ed_email.getText().toString().trim();
        strpassword=ed_pass.getText().toString().trim();
        str_tendangnhap=ed_tendangnhap.getText().toString().trim();
        str_sdt=ed_sdt.getText().toString().trim();
        Log.d("aaa",str_tendangnhap);
        Log.d("aaa",str_sdt);

        StringRequest request=new StringRequest(Request.Method.POST, Server.url_register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                ed_name.setText("");
                ed_email.setText("");
                ed_pass.setText("");
                ed_tendangnhap.setText("");
                ed_sdt.setText("");
                Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
//                Toast.makeText(RegisterActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                Log.d("bbb",error.getMessage().toString());
            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();
                params.put("name",str_name);
                params.put("email",str_email);
                params.put("password",strpassword);
                params.put("tendn",str_tendangnhap);
                params.put("sdt",str_sdt);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(request);
    }
}
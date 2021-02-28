package com.example.thuctaptotnghiep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thuctaptotnghiep.Fragment.MyPageFragment;
import com.example.thuctaptotnghiep.Object.User;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    TextView txt_dk;
    EditText ed_tendangnhap,ed_pass;
    String str_tendangnhap,strpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed_tendangnhap=findViewById(R.id.ed_tendangnhaplogin);
        ed_pass=findViewById(R.id.ed_passlogin);
        txt_dk=findViewById(R.id.txtdangki);

        txt_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    public void login(View view) {
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.show();


        str_tendangnhap=ed_tendangnhap.getText().toString().trim();
        strpassword=ed_pass.getText().toString().trim();

        StringRequest request=new StringRequest(Request.Method.POST, Server.url_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("login successfully")){
                    progressDialog.dismiss();

                   // User user=new User(str_email);
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("tendangnhap",str_tendangnhap);
                    intent.putExtra("pass",strpassword);
                    startActivity(intent);
                }
                else{
                    progressDialog.dismiss();
                    ed_tendangnhap.setText("");
                    ed_pass.setText("");
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
               // Toast.makeText(LoginActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
               // Log.d("bbb",error.getMessage().toString());
            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();

                params.put("tendn",str_tendangnhap);
                params.put("password",strpassword);


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(request);
    }
}
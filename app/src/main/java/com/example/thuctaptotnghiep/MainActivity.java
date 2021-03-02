package com.example.thuctaptotnghiep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.thuctaptotnghiep.Fragment.DetailFragment;
import com.example.thuctaptotnghiep.Fragment.DetailMyPageFragment;
import com.example.thuctaptotnghiep.Fragment.DetailTinTucFragment;
import com.example.thuctaptotnghiep.Fragment.HomeFragment;
import com.example.thuctaptotnghiep.Fragment.MyPageFragment;
import com.example.thuctaptotnghiep.Fragment.SearchFragment;
import com.example.thuctaptotnghiep.Fragment.ThuongHieuFragment;
import com.example.thuctaptotnghiep.Fragment.TinTucFragment;
import com.example.thuctaptotnghiep.Object.Product;
import com.example.thuctaptotnghiep.Object.ThuongHieu;
import com.example.thuctaptotnghiep.Object.TinTuc;
import com.example.thuctaptotnghiep.Object.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {

    Fragment fragment;
   // BottomNavigationView bottomNavigationView;
    ChipNavigationBar bottomNavigationView;
    String tendangnhap;
    String pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Intent intent=getIntent();
        tendangnhap=intent.getStringExtra("tendangnhap");
        pass=intent.getStringExtra("pass");
        Log.d("aaa",tendangnhap);


        loadFragment(HomeFragment.getInstance(tendangnhap,pass));

        bottomNavigationView=findViewById(R.id.bottom_nv);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch (item.getItemId()){
//                    case R.id.action_home:
//                        fragment=new HomeFragment();
//                        loadFragment(fragment);
//                        return true;
//                    case R.id.action_person:
//                        fragment=MyPageFragment.getInstance(tendangnhap,pass);
//                        loadFragment(fragment);
//                        return true;
//
//
//                }
//                return false;
//            }
//        });
        bottomNavigationView.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.action_home:
                        loadFragment(HomeFragment.getInstance(tendangnhap,pass));
                        break;
                    case R.id.action_tintuc:
                        loadFragment(new TinTucFragment());
                        break;
                    case R.id.action_person:
                        loadFragment(MyPageFragment.getInstance(tendangnhap,pass));
                        break;
                }
            }
        });

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    //chuyển màn đến fragment khi click vào sản phẩm
    public void goToDetailFragment(Product p){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        DetailFragment detailFragment=new DetailFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("object_product",p);
        bundle.putString("ten",tendangnhap);
        bundle.putString("pass",pass);
        detailFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.frame_layout,detailFragment);
        fragmentTransaction.addToBackStack(DetailFragment.TAG);
        fragmentTransaction.commit();
    }

    public void goToThuongHieuFragment(ThuongHieu t){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        ThuongHieuFragment thuongHieuFragment=new ThuongHieuFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("object_thuonghieu",t);
        thuongHieuFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.frame_layout,thuongHieuFragment);
        fragmentTransaction.addToBackStack(ThuongHieuFragment.TAG);
        fragmentTransaction.commit();
    }
    public void goToSearchFragment(){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        SearchFragment searchFragment=new SearchFragment();
        transaction.replace(R.id.frame_layout,searchFragment);
        transaction.addToBackStack(searchFragment.TAG);
        transaction.commit();
    }
    public void goToDetailTinTucFragment(TinTuc tinTuc){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        DetailTinTucFragment detailTinTucFragment=new DetailTinTucFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("object_tintuc",tinTuc);
        detailTinTucFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.frame_layout,detailTinTucFragment);
        fragmentTransaction.addToBackStack(DetailTinTucFragment.TAG);
        fragmentTransaction.commit();

    }

//    public void goToDetailMyPageFragment(){
//        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
//        DetailMyPageFragment detailMyPageFragment=new DetailMyPageFragment();
//        Bundle bundle=new Bundle();
//        bundle.putString("ten_detailmypage",tendangnhap);
//        bundle.putString("pas_detailmypage",pass);
//        detailMyPageFragment.setArguments(bundle);
//
//        transaction.replace(R.id.frame_layout,detailMyPageFragment);
//        transaction.addToBackStack(detailMyPageFragment.TAG);
//        transaction.commit();
//    }


}
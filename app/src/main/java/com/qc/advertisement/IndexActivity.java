package com.qc.advertisement;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qc.advertisement.fragment.FabricationFragment;
import com.qc.advertisement.fragment.IndexFragment;

import java.util.List;

public class IndexActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private RelativeLayout mRelativeLayout;
    private  Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_index);
        getFragmentManager().beginTransaction()
                .replace(R.id.content_index, new IndexFragment()).commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        mRelativeLayout = (RelativeLayout) findViewById(R.id.content_index);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_index) {
            getFragmentManager().beginTransaction()
                           .replace(R.id.content_index, new IndexFragment()).commit();
            toolbar.setTitle("首页");

            Toast.makeText(IndexActivity.this, "相册", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_fabric) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.content_index, new FabricationFragment()).commit();
            toolbar.setTitle("投放");

        } else if (id == R.id.nav_retro) {
            toolbar.setTitle("反馈");

        } else if (id == R.id.nav_production){
            toolbar.setTitle("作品");

        }else if (id == R.id.nav_about) {
            toolbar.setTitle("关于我们");

        } else if (id == R.id.nav_qq) {
            if(isQQClientAvailable(IndexActivity.this)){
                String url = "mqqwpa://im/chat?chat_type=wpa&uin=2148688736&version=1";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }else{
                Toast.makeText(IndexActivity.this, "您的手机暂未安装QQ客户端",Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_vx) {

        }
        /**
         *
         * <group android:checkableBehavior="single">
         <item
         android:id="@+id/nav_index"
         android:icon="@drawable/ic_menu_camera"
         android:title="首页" />
         <item
         android:id="@+id/nav_fabric"
         android:icon="@drawable/ic_menu_gallery"
         android:title="投放" />
         <item
         android:id="@+id/nav_retro"
         android:icon="@drawable/ic_menu_slideshow"
         android:title="反馈" />
         <item
         android:id="@+id/nav_about"
         android:icon="@drawable/ic_menu_manage"
         android:title="关于我们" />
         </group>

         <item android:title="客服">
         <menu>
         <item
         android:id="@+id/nav_qq"
         android:icon="@drawable/ic_menu_share"
         android:title="QQ客服" />
         <item
         android:id="@+id/nav_vx"
         android:icon="@drawable/ic_menu_send"
         android:title="微信客服" />
         </menu>
         </item>
         */


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     43.     * 判断qq是否可用
     44.     * @param context
     45.     * @return
     46.     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
                 for (int i = 0; i < pinfo.size(); i++) {
                        String pn = pinfo.get(i).packageName;
                              if (pn.equals("com.tencent.mobileqq")) {
                                       return true;
                                  }
                            }
                    }
              return false;
           }



}

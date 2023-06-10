package com.example.navbotdialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;
import com.example.navbotdialog.Fragment.FavoritoFragment;
import com.example.navbotdialog.Fragment.HomeFragment;
import com.example.navbotdialog.Fragment.NotificacionesFragment;
import com.example.navbotdialog.Fragment.PerfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    FloatingActionButton fab;
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fab = findViewById(R.id.fab);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav1_home);

        }

        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int animationHeight = 100; // Alto deseado en píxeles
            int topMargin = 20; // Margen superior en píxeles
            switch (item.getItemId()) {
                case R.id.nav_home:
                    replaceFragment(new HomeFragment());

                    // Cargar y reproducir la animación Lottie
                    LottieAnimationView animationView = new LottieAnimationView(this);
                    animationView.setAnimation(R.raw.home);
                    animationView.playAnimation();

                    // Obtener el contenedor FrameLayout del fragmento
                    FrameLayout frameLayout = findViewById(R.id.nav_home);
                    frameLayout.removeAllViews(); // Limpiar cualquier vista anterior

                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.WRAP_CONTENT,
                            animationHeight
                    );
                    layoutParams.topMargin = topMargin; // Establecer el margen superior
                    animationView.setLayoutParams(layoutParams);

                    // Agregar el LottieAnimationView al contenedor FrameLayout
                    frameLayout.addView(animationView);

                    break;
                case R.id.nav_love:
                    replaceFragment(new FavoritoFragment());
                    LottieAnimationView loveView = new LottieAnimationView(this);
                    loveView.setAnimation(R.raw.love);
                    loveView.playAnimation();
                    FrameLayout loveLayout = findViewById(R.id.nav_love);
                    loveLayout.removeAllViews();

                    ViewGroup.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(
                      FrameLayout.LayoutParams.MATCH_PARENT,
                      ViewGroup.LayoutParams.MATCH_PARENT
                    );
                    loveView.setLayoutParams(layoutParams1);
                    loveLayout.addView(loveView);
                    break;
                case R.id.nav_notificaciones:
                    replaceFragment(new NotificacionesFragment());
                    LottieAnimationView notifyView = new LottieAnimationView(this);
                    notifyView.setAnimation(R.raw.notify);
                    notifyView.playAnimation();
                    FrameLayout notifyLayout = findViewById(R.id.nav_notificaciones);
                    notifyLayout.removeAllViews();

                    ViewGroup.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(
                      FrameLayout.LayoutParams.MATCH_PARENT,
                      FrameLayout.LayoutParams.MATCH_PARENT
                    );
                    notifyView.setLayoutParams(layoutParams2);
                    notifyLayout.addView(notifyView);
                    break;
                case R.id.nav_perfil:
                    replaceFragment(new NotificacionesFragment());
                    LottieAnimationView profileView = new LottieAnimationView(this);
                    profileView.setAnimation(R.raw.userss);
                    profileView.playAnimation();
                    FrameLayout profileLayout = findViewById(R.id.nav_perfil);
                    profileLayout.removeAllViews();
                    ViewGroup.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(
                       FrameLayout.LayoutParams.MATCH_PARENT,
                       FrameLayout.LayoutParams.MATCH_PARENT
                    );
                    profileView.setLayoutParams(layoutParams3);
                    profileLayout.addView(profileView);
                    break;
            }
            return true;
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav1_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
                break;
            case R.id.nav1_favorito:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new FavoritoFragment()).commit();
                break;
            case R.id.nav1_notificaciones:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new NotificacionesFragment()).commit();
                break;
            case R.id.nav1_perfil:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new PerfilFragment()).commit();
                break;
            case R.id.nav1_logout:
                Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private  void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        LinearLayout videoLayout = dialog.findViewById(R.id.layoutVideo);
        videoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Toast.makeText(MainActivity.this,"Upload a Video is clicked",Toast.LENGTH_SHORT).show();

            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}
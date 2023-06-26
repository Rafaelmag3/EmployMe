package com.example.navbotdialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import com.example.navbotdialog.Herramientas.Calculadora.CalculadoraFragment;
import com.example.navbotdialog.Herramientas.Conversor.ConversorFragment;
import com.example.navbotdialog.Herramientas.Notas.NotasFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    BottomNavigationView bottomNavigationView;
    private CoordinatorLayout coordinatorLayout1;
    private CoordinatorLayout coordinatorLayout2;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    BottomNavigationView var_bNView_Herramientas;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtener el id de usuario
        UserSession userSession = UserSession.getInstance();
        int userId = userSession.getUserId();

        coordinatorLayout1 = findViewById(R.id.coordinatorLayout1);
        coordinatorLayout2 = findViewById(R.id.coordinatorLayout2);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        var_bNView_Herramientas = findViewById(R.id.bottomNavigationView_Herramientas);

        // Menu lateral
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Cerrar el menú lateral después de la selección
                drawerLayout.closeDrawer(GravityCompat.START);

                // Obtener el id del elemento seleccionado
                int itemId = item.getItemId();

                // Cambiar la visibilidad de los CoordinatorLayout según la selección
                if (itemId == R.id.menu_home) {
                    showCoordinatorLayout1();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
                } else if (itemId == R.id.menu_herramientas) {
                    showCoordinatorLayout2();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new CalculadoraFragment()).commit();
                } else if (itemId == R.id.salir) {
                    showExitDialog();
                }

                return true;
            }
        });

        // Mostrar el CoordinatorLayout1 por defecto al iniciar la actividad
        showCoordinatorLayout1();

        fab = findViewById(R.id.fab);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            // Mostrar el fragmento inicial al iniciar la actividad
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
        }

        //Menu de abajo Principal
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int animationHeight = 100; // Alto deseado en píxeles
                int topMargin = 20; // Margen superior en píxeles
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        replaceFragment(new HomeFragment());

                        // Cargar y reproducir la animación Lottie
                        LottieAnimationView animationView = new LottieAnimationView(getApplicationContext());
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
                        LottieAnimationView loveView = new LottieAnimationView(getApplicationContext());
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
                        LottieAnimationView notifyView = new LottieAnimationView(getApplicationContext());
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
                        replaceFragment(new PerfilFragment());
                        LottieAnimationView profileView = new LottieAnimationView(getApplicationContext());
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
            }
        });

        //Menu de abajo Secundario
        var_bNView_Herramientas.setBackground(null);
        var_bNView_Herramientas.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int animationHeight = 100; // Alto deseado en píxeles
                int topMargin = 20; // Margen superior en píxeles

                switch (item.getItemId()) {
                    case R.id.nav_calculadora:
                        replaceFragment(new CalculadoraFragment());

                        LottieAnimationView calculadoraView = new LottieAnimationView(getApplicationContext());
                        calculadoraView.setAnimation(R.raw.calculadora);
                        calculadoraView.playAnimation();

                        FrameLayout calculadoraLayout = findViewById(R.id.nav_calculadora);
                        calculadoraLayout.removeAllViews();
                        ViewGroup.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.MATCH_PARENT,
                                FrameLayout.LayoutParams.MATCH_PARENT
                        );
                        calculadoraView.setLayoutParams(layoutParams1);
                        calculadoraLayout.addView(calculadoraView);
                        break;

                    case R.id.nav_conversor:
                        replaceFragment(new ConversorFragment());
                        LottieAnimationView conversorView = new LottieAnimationView(getApplicationContext());
                        conversorView.setAnimation(R.raw.conversor);
                        conversorView.playAnimation();
                        FrameLayout conversorLayout = findViewById(R.id.nav_conversor);
                        conversorLayout.removeAllViews();

                        ViewGroup.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.MATCH_PARENT,
                                FrameLayout.LayoutParams.MATCH_PARENT
                        );
                        conversorView.setLayoutParams(layoutParams2);
                        conversorLayout.addView(conversorView);
                        break;

                    case R.id.nav_notas:
                        replaceFragment(new NotasFragment());
                        LottieAnimationView notasView = new LottieAnimationView(getApplicationContext());
                        notasView.setAnimation(R.raw.blog);
                        notasView.playAnimation();
                        FrameLayout notasLayout = findViewById(R.id.nav_notas);
                        notasLayout.removeAllViews();
                        ViewGroup.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.MATCH_PARENT,
                                FrameLayout.LayoutParams.MATCH_PARENT
                        );
                        notasView.setLayoutParams(layoutParams3);
                        notasLayout.addView(notasView);
                        break;
                }
                return true;
            }
        });

        //Boton flotante
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //Contenido del fragmento
    private  void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    //Boton flotante
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

    //Menu lateral
    private void showCoordinatorLayout1() {
        coordinatorLayout1.setVisibility(View.VISIBLE);
        coordinatorLayout2.setVisibility(View.GONE);
    }

    private void showCoordinatorLayout2() {
        coordinatorLayout1.setVisibility(View.GONE);
        coordinatorLayout2.setVisibility(View.VISIBLE);
    }

    private void showExitDialog() {
        // Aquí puedes mostrar un diálogo de confirmación o cualquier otro tipo de mensaje
        // en lugar de un Toast
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Salir")
                .setMessage("¿Estás seguro de que deseas salir?")
                .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish(); // Finalizar la actividad para salir
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }



}
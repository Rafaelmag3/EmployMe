package com.example.navbotdialog.Herramientas.Conversor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.navbotdialog.R;
import com.google.android.material.tabs.TabLayout;

public class ConversorFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    public ConversorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversor, container, false);

        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        // Crea el adaptador de fragmentos
        ConversorPagerAdapter pagerAdapter = new ConversorPagerAdapter(getChildFragmentManager());

        // Asigna el adaptador al ViewPager
        viewPager.setAdapter(pagerAdapter);

        // Conecta el ViewPager con el TabLayout
        tabLayout.setupWithViewPager(viewPager);

        // Asigna nombres a las pestañas
        tabLayout.getTabAt(0).setText("Peso");
        tabLayout.getTabAt(1).setText("Longitud");
        tabLayout.getTabAt(2).setText("Volumen");
        tabLayout.getTabAt(3).setText("Temperatura");

        return view;
    }
}
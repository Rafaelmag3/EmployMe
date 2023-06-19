package com.example.navbotdialog.Herramientas.Conversor;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.navbotdialog.Herramientas.Conversor.Fragmentos.LongitudFragment;
import com.example.navbotdialog.Herramientas.Conversor.Fragmentos.PesoFragment;
import com.example.navbotdialog.Herramientas.Conversor.Fragmentos.TemperaturaFragment;
import com.example.navbotdialog.Herramientas.Conversor.Fragmentos.VolumenFragment;

public class ConversorPagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_PAGES = 4;

    public ConversorPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // Aquí devuelves el fragmento correspondiente a cada posición
        switch (position) {
            case 0:
                return new PesoFragment();
            case 1:
                return new LongitudFragment();
            case 2:
                return new VolumenFragment();
            case 3:
                return new TemperaturaFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}


package com.example.navbotdialog.Post;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.navbotdialog.R;

public class PostFragment extends Fragment {

    EditText puesto, no_vacantes, locacion, horario_e, horario_s, salario, funciones, requisitos;
    Button btnpublicar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);

        puesto = rootView.findViewById(R.id.post_puesto_trabajo);
        no_vacantes = rootView.findViewById(R.id.post_No_vacantes);
        locacion = rootView.findViewById(R.id.post_Locacion);
        horario_e = rootView.findViewById(R.id.post_Horario);
        horario_s = rootView.findViewById(R.id.post_Horario2);
        salario = rootView.findViewById(R.id.post_Salario);
        funciones = rootView.findViewById(R.id.post_Funciones_puesto);
        requisitos = rootView.findViewById(R.id.post_Requisitos);

        btnpublicar = rootView.findViewById(R.id.btnPost);

        btnpublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return  rootView;
    }
}
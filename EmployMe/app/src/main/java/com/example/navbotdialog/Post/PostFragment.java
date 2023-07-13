package com.example.navbotdialog.Post;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.navbotdialog.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

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
                if (camposEstanLlenos()) {
                    if (horariosSonValidos()) {
                        Toast.makeText(getContext(), "Publicación OK!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Mostrar una alerta informando que los horarios no son válidos
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Horarios inválidos");
                        builder.setMessage("Por favor, ingresa horarios válidos en formato HH:mm.");
                        builder.setPositiveButton("Aceptar", null);
                        builder.show();
                    }
                } else {
                    // Mostrar una alerta informando que faltan campos
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Campos incompletos");
                    builder.setMessage("Por favor, completa todos los campos antes de publicar.");
                    builder.setPositiveButton("Aceptar", null);
                    builder.show();
                }
            }
        });

        return  rootView;
    }

    private boolean camposEstanLlenos() {
        String puestoTrabajo = puesto.getText().toString();
        String noVacantes = no_vacantes.getText().toString();
        String locacionPuesto = locacion.getText().toString();
        String horarioEntrada = horario_e.getText().toString();
        String horarioSalida = horario_s.getText().toString();
        String salarioPuesto = salario.getText().toString();
        String funcionesPuesto = funciones.getText().toString();
        String requisitosPuesto = requisitos.getText().toString();

        // Verificar si todos los campos tienen contenido
        return !puestoTrabajo.isEmpty() &&
                !noVacantes.isEmpty() &&
                !locacionPuesto.isEmpty() &&
                !horarioEntrada.isEmpty() &&
                !horarioSalida.isEmpty() &&
                !salarioPuesto.isEmpty() &&
                !funcionesPuesto.isEmpty() &&
                !requisitosPuesto.isEmpty();
    }

    private boolean horariosSonValidos() {
        String horarioEntrada = horario_e.getText().toString();
        String horarioSalida = horario_s.getText().toString();

        try {
            // Crear un objeto SimpleDateFormat con el formato de hora
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm", Locale.getDefault());
            formatoHora.setLenient(false);

            // Parsear los horarios y verificar si son válidos
            formatoHora.parse(horarioEntrada);
            formatoHora.parse(horarioSalida);

            return true; // Ambos horarios son válidos
        } catch (ParseException e) {
            return false; // Al menos uno de los horarios no es válido
        }
    }

}
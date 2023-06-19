package com.example.navbotdialog.Herramientas.Conversor.Fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.navbotdialog.R;

public class PesoFragment extends Fragment {

    Spinner entrada, salida;
    EditText txt_entrada;
    TextView txt_salida;
    Button btn_convertir;

    public PesoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootVie = inflater.inflate(R.layout.fragment_peso,container,false);

        entrada = (Spinner) rootVie.findViewById(R.id.text_OpcionesEntradap);
        salida = (Spinner) rootVie.findViewById(R.id.text_OpcionesSalidaP);
        txt_entrada = (EditText) rootVie.findViewById(R.id.text_entradaP);
        btn_convertir = (Button) rootVie.findViewById(R.id.btn_ConvertirP);
        txt_salida = (TextView) rootVie.findViewById(R.id.text_salidaP);

        String[] deConvertir = {"Toneladas (t)","Libras (lb)", "Onzas (oz)" };
        ArrayAdapter<String> adapterDeConvertir = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, deConvertir);
        entrada.setAdapter(adapterDeConvertir);

        String[] aConvertir = {"Toneladas (t)","Libras (lb)", "Onzas (oz)" };
        ArrayAdapter<String> adapterAConvertir = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, aConvertir);
        salida.setAdapter(adapterAConvertir);

        btn_convertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txt_entrada.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Ingresa un número", Toast.LENGTH_SHORT).show();
                    return;
                }

                Double operacion;
                Double numEntrada = Double.parseDouble(txt_entrada.getText().toString());

                //Toneladas
                if (entrada.getSelectedItem().toString() == "Toneladas (t)" && salida.getSelectedItem().toString() == "Libras (lb)"){
                    operacion = numEntrada * 2204.62;
                    txt_salida.setText(Double.toString(operacion)+" lb");
                }
                else if (entrada.getSelectedItem().toString() == "Toneladas (t)" && salida.getSelectedItem().toString() == "Onzas (oz)"){
                    operacion = numEntrada * 35274;
                    txt_salida.setText(Double.toString(operacion)+" oz");

                }
                else if (entrada.getSelectedItem().toString() == "Toneladas (t)" && salida.getSelectedItem().toString() == "Toneladas (t)"){
                    txt_salida.setText(Double.toString(numEntrada)+" t");

                }

                //LIBRAS
                else if (entrada.getSelectedItem().toString() == "Libras (lb)" && salida.getSelectedItem().toString() == "Toneladas (t)"){
                    operacion = numEntrada * 0.00045359;
                    txt_salida.setText(Double.toString(operacion)+"t");
                }
                else if (entrada.getSelectedItem().toString() == "Libras (lb)" && salida.getSelectedItem().toString() == "Onzas (oz)"){
                    operacion = numEntrada * 16;
                    txt_salida.setText(Double.toString(operacion)+" oz");
                }
                else if (entrada.getSelectedItem().toString() == "Libras (lb)" && salida.getSelectedItem().toString() == "Libras (lb)"){
                    txt_salida.setText(Double.toString(numEntrada)+" lb");

                }

                //ONZAS
                else if (entrada.getSelectedItem().toString() == "Onzas (oz)" && salida.getSelectedItem().toString() == "Toneladas (t)"){
                    operacion = numEntrada * 0.0000283495;
                    txt_salida.setText(Double.toString(operacion)+" t");
                }
                else if (entrada.getSelectedItem().toString() == "Onzas (oz)" && salida.getSelectedItem().toString() == "Libras (lb)"){
                    operacion = numEntrada * 0.0625;
                    txt_salida.setText(Double.toString(operacion)+" lb");
                }
                else if (entrada.getSelectedItem().toString() == "Onzas (oz)" && salida.getSelectedItem().toString() == "Onzas (oz)"){
                    txt_salida.setText(Double.toString(numEntrada)+" oz");

                }

            }
        });

        return rootVie;
    }
}
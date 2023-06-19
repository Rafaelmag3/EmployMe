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


public class VolumenFragment extends Fragment {

    Spinner entrada, salida;
    EditText txt_entrada;
    TextView txt_salida;
    Button btn_convertir;
    public VolumenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootVie = inflater.inflate(R.layout.fragment_volumen,container,false);

        entrada = (Spinner) rootVie.findViewById(R.id.text_OpcionesEntradaVolumen);
        salida = (Spinner) rootVie.findViewById(R.id.text_OpcionesSalidaV);
        txt_entrada = (EditText) rootVie.findViewById(R.id.text_entradaV);
        btn_convertir = (Button) rootVie.findViewById(R.id.btn_ConvertirV);
        txt_salida = (TextView) rootVie.findViewById(R.id.text_salidaV);

        String[] deConvertir = {"Litros (l)","Mililitros (ml)", "Centimetros Cúbicos (cc)" };
        ArrayAdapter<String> adapterDeConvertir = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, deConvertir);
        entrada.setAdapter(adapterDeConvertir);

        String[] aConvertir = {"Litros (l)","Mililitros (ml)", "Centimetros Cúbicos (cc)" };
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

                //Litros (l)
                if (entrada.getSelectedItem().toString() == "Litros (l)" && salida.getSelectedItem().toString() == "Mililitros (ml)"){
                    operacion = numEntrada * 1000;
                    txt_salida.setText(Double.toString(operacion)+" ml");
                    //Toast.makeText(getApplicationContext(),operacion.toString(),Toast.LENGTH_LONG).show();
                }
                else if (entrada.getSelectedItem().toString() == "Litros (l)" && salida.getSelectedItem().toString() == "Centimetros Cúbicos (cc)"){
                    operacion = numEntrada * 1000;
                    txt_salida.setText(Double.toString(operacion)+" cc");

                }
                else if (entrada.getSelectedItem().toString() == "Litros (l)" && salida.getSelectedItem().toString() == "Litros (l)"){
                    txt_salida.setText(Double.toString(numEntrada)+" l");

                }

                //Mililitros (ml)
                else if (entrada.getSelectedItem().toString() == "Mililitros (ml)" && salida.getSelectedItem().toString() == "Litros (l)"){
                    operacion = numEntrada * 0.001;
                    txt_salida.setText(Double.toString(operacion)+" l");
                }
                else if (entrada.getSelectedItem().toString() == "Mililitros (ml)" && salida.getSelectedItem().toString() == "Centimetros Cúbicos (cc)"){
                    operacion = numEntrada * 1;
                    txt_salida.setText(Double.toString(operacion)+" cc");
                }
                else if (entrada.getSelectedItem().toString() == "Mililitros (ml)" && salida.getSelectedItem().toString() == "Mililitros (ml)"){
                    txt_salida.setText(Double.toString(numEntrada)+" ml");

                }

                //Centimetros Cúbicos (cm^3)
                else if (entrada.getSelectedItem().toString() == "Centimetros Cúbicos (cc)" && salida.getSelectedItem().toString() == "Litros (l)"){
                    operacion = numEntrada * 0.001;
                    txt_salida.setText(Double.toString(operacion)+" l");
                }
                else if (entrada.getSelectedItem().toString() == "Centimetros Cúbicos (cc)" && salida.getSelectedItem().toString() == "Mililitros (ml)"){
                    operacion = numEntrada * 1;
                    txt_salida.setText(Double.toString(operacion)+" ml");
                }
                else if (entrada.getSelectedItem().toString() == "Centimetros Cúbicos (cc)" && salida.getSelectedItem().toString() == "Centimetros Cúbicos (cc)"){
                    txt_salida.setText(Double.toString(numEntrada)+" cc");

                }

            }
        });

        return rootVie;
    }
}
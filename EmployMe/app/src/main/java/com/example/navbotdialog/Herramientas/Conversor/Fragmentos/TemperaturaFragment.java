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

public class TemperaturaFragment extends Fragment {

    Spinner entrada, salida;
    EditText txt_entrada;
    TextView txt_salida;
    Button btn_convertir;
    public TemperaturaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootVie = inflater.inflate(R.layout.fragment_temperatura,container,false);

        // Obtener la referencia de los elementos de tu layout
        entrada = (Spinner) rootVie.findViewById(R.id.text_OpcionesEntradaT);
        salida = (Spinner) rootVie.findViewById(R.id.text_OpcionesSalidaT);
        txt_entrada = (EditText) rootVie.findViewById(R.id.text_entradaT);
        btn_convertir = (Button) rootVie.findViewById(R.id.btn_ConvertirT);
        txt_salida = (TextView) rootVie.findViewById(R.id.text_salidaT);

        // Configurar los adapters para los Spinners
        String[] deConvertir = {"Celsius (°C)","Fahrenheit (°F)", "Kelvin (K)" };
        ArrayAdapter<String> adapterDeConvertir = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, deConvertir);
        entrada.setAdapter(adapterDeConvertir);

        String[] aConvertir = {"Celsius (°C)","Fahrenheit (°F)", "Kelvin (K)" };
        ArrayAdapter<String> adapterAConvertir = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, aConvertir);
        salida.setAdapter(adapterAConvertir);

        btn_convertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_entrada.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Ingresa un número", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Lógica para manejar el evento de clic del botón
                Double operacion;
                Double numEntrada = Double.parseDouble(txt_entrada.getText().toString());

                //Celsius
                if (entrada.getSelectedItem().toString() == "Celsius (°C)" && salida.getSelectedItem().toString() == "Fahrenheit (°F)"){
                    operacion = numEntrada * 1.8 + 32;
                    txt_salida.setText(Double.toString(operacion)+" °F");
                }
                else if (entrada.getSelectedItem().toString() == "Celsius (°C)" && salida.getSelectedItem().toString() == "Kelvin (K)"){
                    operacion = numEntrada * 274.15;
                    txt_salida.setText(Double.toString(operacion)+" K");

                }
                else if (entrada.getSelectedItem().toString() == "Celsius (°C)" && salida.getSelectedItem().toString() == "Celsius (°C)"){
                    txt_salida.setText(Double.toString(numEntrada)+" °C");

                }

                //Fahrenheit (°F)
                else if (entrada.getSelectedItem().toString() == "Fahrenheit (°F)" && salida.getSelectedItem().toString() == "Celsius (°C)"){
                    operacion = (numEntrada - 32) * 5 / 9;
                    txt_salida.setText(Double.toString(operacion)+" °C");
                }
                else if (entrada.getSelectedItem().toString() == "Fahrenheit (°F)" && salida.getSelectedItem().toString() == "Kelvin (K)"){
                    operacion = (numEntrada - 32) * 5 / 9 + 273.15;
                    txt_salida.setText(Double.toString(operacion)+" °K");
                }
                else if (entrada.getSelectedItem().toString() == "Fahrenheit (°F)" && salida.getSelectedItem().toString() == "Fahrenheit (°F)"){
                    txt_salida.setText(Double.toString(numEntrada)+" °F");

                }

                //Kelvin (K)
                else if (entrada.getSelectedItem().toString() == "Kelvin (K)" && salida.getSelectedItem().toString() == "Celsius (°C)"){
                    operacion = numEntrada - 273.15;
                    txt_salida.setText(Double.toString(operacion)+" °C");
                }
                else if (entrada.getSelectedItem().toString() == "Kelvin (K)" && salida.getSelectedItem().toString() == "Fahrenheit (°F)"){
                    operacion = (numEntrada - 273.15) * 1.8 + 32;
                    txt_salida.setText(Double.toString(operacion)+" °F");
                }
                else if (entrada.getSelectedItem().toString() == "Kelvin (K)" && salida.getSelectedItem().toString() == "Kelvin (K)"){
                    txt_salida.setText(Double.toString(numEntrada)+" K");

                }

            }
        });

        // Retornar la vista inflada con las referencias asignadas
        return rootVie;
    }
}
package com.example.navbotdialog.Herramientas.Calculadora;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.navbotdialog.R;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class CalculadoraFragment extends Fragment {
    TextView resultado, procedimiento;
    Button masMenos, cero, uno, dos, tres, cuatro, cinco, seis, siete, ocho, nueve, multiplicacion,
            suma, resta, divicion, limpiar, parentesis, punto, porcentaje, borrar, resultado_final;
    boolean ex_parentesis = false ;
    String proceso_string;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculadora, container, false);

        procedimiento = view.findViewById(R.id.tv_proceso);
        resultado = view.findViewById(R.id.tv_resultado);

        cero = view.findViewById(R.id.btn_cero);
        uno = view.findViewById(R.id.btn_uno);
        dos = view.findViewById(R.id.btn_dos);
        tres = view.findViewById(R.id.btn_tres);
        cuatro = view.findViewById(R.id.btn_cuatro);
        cinco = view.findViewById(R.id.btn_cicno);
        seis = view.findViewById(R.id.btn_seis);
        siete = view.findViewById(R.id.btn_siete);
        ocho = view.findViewById(R.id.btn_ocho);
        nueve = view.findViewById(R.id.btn_nueve);
        multiplicacion = view.findViewById(R.id.btn_multiplicacion);
        suma = view.findViewById(R.id.btn_suma);
        resta = view.findViewById(R.id.btn_restar);
        divicion = view.findViewById(R.id.btn_division);
        limpiar = view.findViewById(R.id.btn_limpiar);
        parentesis = view.findViewById(R.id.btn_parentesis);
        punto = view.findViewById(R.id.btn_punto);
        porcentaje = view.findViewById(R.id.btn_porcentaje);
        masMenos = view.findViewById(R.id.btn_masMenos);
        borrar = view.findViewById(R.id.btn_borrar);
        resultado_final = view.findViewById(R.id.btn_igual);


        Mostrar_valores();

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar(view);
            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrar(view);
            }
        });

        resultado_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resultado_final(view);
            }
        });

        return view;
    }

    public void Mostrar_valores(){

        cero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceso_string = procedimiento.getText().toString();
                if (proceso_string.endsWith(")")) {
                    procedimiento.setText(proceso_string + "*0");
                } else {
                    procedimiento.setText(proceso_string + "0");
                }
            }
        });

        uno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceso_string = procedimiento.getText().toString();
                if (proceso_string.endsWith(")")) {
                    procedimiento.setText(proceso_string + "*1");
                } else {
                    procedimiento.setText(proceso_string + "1");
                }
            }
        });

        dos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceso_string = procedimiento.getText().toString();
                if (proceso_string.endsWith(")")) {
                    procedimiento.setText(proceso_string + "*2");
                } else {
                    procedimiento.setText(proceso_string + "2");
                }
            }
        });

        tres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceso_string = procedimiento.getText().toString();
                if (proceso_string.endsWith(")")) {
                    procedimiento.setText(proceso_string + "*3");
                } else {
                    procedimiento.setText(proceso_string + "3");
                }
            }
        });

        cuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceso_string = procedimiento.getText().toString();
                if (proceso_string.endsWith(")")) {
                    procedimiento.setText(proceso_string + "*4");
                } else {
                    procedimiento.setText(proceso_string + "4");
                }
            }
        });

        cinco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceso_string = procedimiento.getText().toString();
                if (proceso_string.endsWith(")")) {
                    procedimiento.setText(proceso_string + "*5");
                } else {
                    procedimiento.setText(proceso_string + "5");
                }
            }
        });

        seis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceso_string = procedimiento.getText().toString();
                if (proceso_string.endsWith(")")) {
                    procedimiento.setText(proceso_string + "*6");
                } else {
                    procedimiento.setText(proceso_string + "6");
                }
            }
        });

        siete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceso_string = procedimiento.getText().toString();
                if (proceso_string.endsWith(")")) {
                    procedimiento.setText(proceso_string + "*7");
                } else {
                    procedimiento.setText(proceso_string + "7");
                }
            }
        });

        ocho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceso_string = procedimiento.getText().toString();
                if (proceso_string.endsWith(")")) {
                    procedimiento.setText(proceso_string + "*8");
                } else {
                    procedimiento.setText(proceso_string + "8");
                }
            }
        });

        nueve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceso_string = procedimiento.getText().toString();
                if (proceso_string.endsWith(")")) {
                    procedimiento.setText(proceso_string + "*9");
                } else {
                    procedimiento.setText(proceso_string + "9");
                }
            }
        });

        multiplicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceso_string = procedimiento.getText().toString();
                if ((proceso_string.matches(".*\\d$") && !proceso_string.endsWith("*") )||proceso_string.endsWith(")") ) {
                    procedimiento.setText(proceso_string + "*");
                } else {
                    Toast.makeText(getContext(), "Formato no válido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        suma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceso_string = procedimiento.getText().toString();
                if ((proceso_string.matches(".*\\d$") && !proceso_string.endsWith("+") )||proceso_string.endsWith(")") ) {
                    procedimiento.setText(proceso_string + "+");
                } else {
                    Toast.makeText(getContext(), "Formato no válido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceso_string = procedimiento.getText().toString();
                if ((proceso_string.matches(".*\\d$") && !proceso_string.endsWith("-") )||proceso_string.endsWith(")") ) {
                    procedimiento.setText(proceso_string + "-");
                } else {
                    Toast.makeText(getContext(), "Formato no válido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        divicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceso_string = procedimiento.getText().toString();
                if ((proceso_string.matches(".*\\d$") && !proceso_string.endsWith("/") )||proceso_string.endsWith(")") ) {
                    procedimiento.setText(proceso_string + "/");
                } else {
                    Toast.makeText(getContext(), "Formato no válido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        parentesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                proceso_string = procedimiento.getText().toString();

                // Verificar si el string está vacío
                if (proceso_string.isEmpty()) {
                    procedimiento.setText("(");
                    ex_parentesis = true;
                    return;
                }

                //Contador de paremtesis
                int conteoPA = 0, conteoPC = 0;
                for (int i = 0; i < proceso_string.length(); i++) {
                    if (proceso_string.charAt(i) == '(') {
                        conteoPA++;

                    }else if (proceso_string.charAt(i) == ')') {
                        conteoPC++;
                    }
                }

                char ultimoCaracter = proceso_string.charAt(proceso_string.length()-1);

                //
                if(proceso_string.endsWith(")") && conteoPA > conteoPC){
                    procedimiento.setText(proceso_string + ")");
                    ex_parentesis = false;
                }else{
                    if (ex_parentesis){
                        if(!proceso_string.endsWith("(") ){
                            procedimiento.setText(proceso_string + ")");
                            ex_parentesis = false;
                        }else{
                            Toast.makeText(getContext(), "Formato no válido", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        if (Character.isDigit(ultimoCaracter)||proceso_string.endsWith(")")) {
                            procedimiento.setText(proceso_string + "*(");
                            ex_parentesis = true;
                        } else {
                            procedimiento.setText(proceso_string + "(");
                            ex_parentesis = true;
                        }
                    }
                }

            }
        });

        punto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceso_string = procedimiento.getText().toString();
                if (!proceso_string.endsWith(".")) {
                    procedimiento.setText(proceso_string + ".");
                }
            }
        });

        porcentaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceso_string = procedimiento.getText().toString();
                if ((proceso_string.matches(".*\\d$") && !proceso_string.endsWith("%") )||proceso_string.endsWith(")") ) {

                    procedimiento.setText(proceso_string + "%");
                } else {
                    Toast.makeText(getContext(), "Formato no válido", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public void limpiar(View view) {

        procedimiento.setText("");
        resultado.setText("");
        ex_parentesis = false ;

    }

    public void borrar(View view) {

        String borrar_string = procedimiento.getText().toString();

        if (borrar_string.length() >= 1){

            borrar_string = borrar_string.substring(0,borrar_string.length() - 1);
            procedimiento.setText(borrar_string);
        } else if (borrar_string.length() < 1) {
            procedimiento.setText("");
        }

    }

    public void Resultado_final(View view) {

        // Si el campo de texto está vacío, no hacer nada
        if (proceso_string.isEmpty()) {
            procedimiento.setText("");
            resultado.setText("");
            Toast.makeText(getContext(), "Ingrese una funcion", Toast.LENGTH_SHORT).show();
        }else{

            proceso_string = procedimiento.getText().toString();
            proceso_string = proceso_string.replaceAll("x", "*");
            proceso_string = proceso_string.replaceAll("%", "/100");

            Context context = Context.enter();
            context.setOptimizationLevel(-1);

            String resultado_final = "";

            try {
                Scriptable scriptable = context.initStandardObjects();
                resultado_final = context.evaluateString(scriptable, proceso_string, "javascript",1, null).toString();

            }catch (Exception e){
                Toast.makeText(getContext(), "Formato no valido ", Toast.LENGTH_SHORT).show();
            }
            if (resultado_final == "Infinity") {

                Toast.makeText(getContext(), "No se puede dividir entre cero", Toast.LENGTH_SHORT).show();

            }else{
                resultado.setText(resultado_final);
            }


        }
    }
}
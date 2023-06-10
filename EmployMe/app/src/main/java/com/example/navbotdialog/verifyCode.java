package com.example.navbotdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class verifyCode extends AppCompatActivity {
    private TextView forgetText;
    private EditText code;
    private String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        Bundle extras = getIntent().getExtras();


        forgetText = findViewById(R.id.forgetText);

        // Obtener el correo electrónico enviado desde el backend
        if (extras != null) {
            codigo = extras.getString("codigo");
            String email = extras.getString("email"); // Reemplaza "clave" con el nombre de la clave que usaste al enviar los datos
            // Haz algo con el valor recibido

            // Construir el texto de verificación con el correo electrónico
            String forgetTextC = "Se ha enviado un código de verificación al correo electrónico: <b>" + email + "</b>";

            // Mostrar el texto en el TextView
            forgetText.setText(Html.fromHtml(forgetTextC));
        }


        code = (EditText) findViewById(R.id.code);
        Button verify_button = (Button) findViewById(R.id.verify_button);

        verify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(code.getText().toString().equals(codigo)){
                    Toast.makeText(verifyCode.this, "Codigo de Recuperación Correcto", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(verifyCode.this, "Codigo de Recuperación incorrecto", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}




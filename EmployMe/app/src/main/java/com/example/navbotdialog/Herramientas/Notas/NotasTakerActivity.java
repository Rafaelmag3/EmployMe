package com.example.navbotdialog.Herramientas.Notas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.navbotdialog.Herramientas.Notas.Modales.Notas;
import com.example.navbotdialog.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotasTakerActivity extends AppCompatActivity {

    EditText et_notas, et_titulo;
    ImageView imagen_salvar;
    Notas notas;
    boolean iON = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas_taker);
        //Toast.makeText(this, "Entro a NotasTakerActivity", Toast.LENGTH_SHORT).show();

        imagen_salvar = (ImageView) findViewById(R.id.imagen_salvar);
        et_notas = findViewById(R.id.et_notas);
        et_titulo = findViewById(R.id.et_titulo);

        notas = new Notas();
        try {
            notas = (Notas) getIntent().getSerializableExtra("old_note");
            et_titulo.setText(notas.getTitulo());
            et_notas.setText(notas.getNotas());
            iON = true;
        }catch (Exception e){
            e.printStackTrace();
        }


        imagen_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = et_titulo.getText().toString();
                String nota = et_notas.getText().toString();

                if (nota.isEmpty()){
                    Toast.makeText(NotasTakerActivity.this, "Todos los campos son necesarios", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat format = new SimpleDateFormat("EEE,d MMM yyyy HH:mm a");
                Date fecha = new Date();

                if(!iON){
                    notas = new Notas();
                }

                notas.setTitulo(titulo);
                notas.setNotas(nota);
                notas.setFecha(format.format(fecha));

                Intent intent = new Intent();
                intent.putExtra("Nota", notas);
                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });
    }
}
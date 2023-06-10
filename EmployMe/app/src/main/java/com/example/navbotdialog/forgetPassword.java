package com.example.navbotdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class forgetPassword extends AppCompatActivity {

    private EditText emailEditText;

    private Intent intent;
private boolean banderaIsCorrect=false,banderaSendEmail=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        intent = new Intent(forgetPassword.this, verifyCode.class);

        Button sendButton = findViewById(R.id.send_button);
        emailEditText = findViewById(R.id.send_email);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();

                if (!email.isEmpty() && isValidEmail(email)) {
                    // El campo de correo electrónico no está vacío y tiene una sintaxis válida, navegar a otra ventana

                    checkUserExistence(email);
                    banderaIsCorrect=true;
                } else {
                    // El campo de correo electrónico está vacío o tiene una sintaxis inválida, mostrar un mensaje de error
                    Toast.makeText(forgetPassword.this, "Por favor, ingresa un correo electrónico válido", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        return email.matches(emailPattern);
    }

    private void checkUserExistence(String email){

        //play

        String url = APIUtils.getFullUrl("verify-email");

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            banderaSendEmail=true;
        } catch (JSONException e) {
            e.printStackTrace();
            banderaSendEmail=false;
            banderaIsCorrect=false;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String codigo = response.getString("codigo");


                            // El correo electrónico existe en la base de datos, navegar a otra ventana
                            intent.putExtra("email", emailEditText.getText().toString());
                            intent.putExtra("codigo", codigo);
                            startActivity(intent);

                            /*boolean userExists = response.getBoolean("userExists");

                            if (userExists) {

                                String codigo2 = response.getString("codigo");
                                Toast.makeText(forgetPassword.this, "Codigo Generado: " + codigo, Toast.LENGTH_SHORT).show();

                                // El correo electrónico existe en la base de datos, navegar a otra ventana
                                Intent intent2 = new Intent(forgetPassword.this, verifyCode.class);
                                intent2.putExtra("codigo", codigo);
                                startActivity(intent);
                            } else {
                                // El correo electrónico no existe en la base de datos, mostrar un mensaje de error
                                Toast.makeText(forgetPassword.this, "El correo electrónico no existe", Toast.LENGTH_SHORT).show();

                            }*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Error al realizar la solicitud, mostrar un mensaje de error
                        Toast.makeText(forgetPassword.this, "Enviando código", Toast.LENGTH_SHORT).show();
                    }
                });
        // Agregar la solicitud a la cola
        requestQueue.add(jsonObjectRequest);
    }
}
package com.example.navbotdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.Html;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.example.navbotdialog.APIUtils;

public class SignUpActivity extends AppCompatActivity {
    EditText nameUserET, emailUseET, categoryUserET, phoneUserET, passwordUserET;
    Button registerUser;
    boolean passwordVisible = false;
    ImageView buttonPassword;
    public boolean isAttemptSucceded = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Obtener referencias a los campos de texto y al botón de registro
        nameUserET = findViewById(R.id.nameUser);
        emailUseET = findViewById(R.id.emailUser);
        categoryUserET = findViewById(R.id.categoryUser);
        phoneUserET = findViewById(R.id.phoneUser);
        passwordUserET = findViewById(R.id.passwordUser);
        registerUser = findViewById(R.id.btnRegisterUser);

        buttonPassword = findViewById(R.id.passwordIcon);

        buttonPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (passwordVisible) {
                    // Si la contraseña es visible, cambia el tipo de entrada a 'textPassword'
                    passwordUserET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    buttonPassword.setImageResource(R.drawable.ico_visibility_off);
                    passwordVisible = false;
                } else {
                    // Si la contraseña está oculta, cambia el tipo de entrada a 'text'
                    passwordUserET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    buttonPassword.setImageResource(R.drawable.ico_visibility);
                    passwordVisible = true;
                }

                // Mueve el cursor al final del texto de la contraseña
                passwordUserET.setSelection(passwordUserET.getText().length());
            }
        });

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los campos de texto
                String userName = nameUserET.getText().toString();
                String userEmail = emailUseET.getText().toString();
                String userCategori = categoryUserET.getText().toString();
                String userPhone = phoneUserET.getText().toString();
                String userPassword = passwordUserET.getText().toString();

                // Validar que todos los campos estén llenos
                boolean isFormValid = true;

                if (userName.isEmpty()) {
                    nameUserET.setError("Campo obligatorio");
                    isFormValid = false;
                    isAttemptSucceded=false;
                }
                if (userEmail.isEmpty()) {
                    emailUseET.setError("Campo obligatorio");
                    isFormValid = false;
                    isAttemptSucceded=false;
                }
                if (userCategori.isEmpty()) {
                    categoryUserET.setError("Campo obligatorio");
                    isFormValid = false;
                    isAttemptSucceded=false;
                }
                if (userPhone.isEmpty()) {
                    phoneUserET.setError("Campo obligatorio");
                    isFormValid = false;
                    isAttemptSucceded=false;
                }
                if (userPassword.isEmpty()) {
                    passwordUserET.setError("Campo obligatorio");
                    isFormValid = false;
                    isAttemptSucceded=false;
                }

                if (isFormValid) {
                    registerUser(userName, userEmail, userCategori, userPhone, userPassword);
                } else {
                    Toast.makeText(getApplicationContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                }

                if(isAttemptSucceded){
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Registro fallido", Toast.LENGTH_SHORT).show();
                }

            }
        });

        TextView signUpRedirectText = findViewById(R.id.login_RedirectText);
        String text = "¿Ya tienes una cuenta? <br><b> Inicia sesión</b>";
        signUpRedirectText.setText(Html.fromHtml(text));
        signUpRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }

    private void registerUser(String userName, String userEmail, String userCategori, String userPhone, String userPassword) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(calendar.getTime());

        String url = APIUtils.getFullUrl("userCreate");

        // Construir el parámetro de la solicitud
        HashMap<String, String> params = new HashMap<>();
        params.put("nameUser", userName);
        params.put("email", userEmail);
        params.put("password", userPassword);
        params.put("phone", userPhone);
        params.put("dateRegister", currentDate);
        params.put("id_category", userCategori);


        // Solicitud POST
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // La respuesta del servidor
                        // Aquí puedes agregar el código para manejar la respuesta del servidor
                        try {
                            if (response.has("success")) {
                                boolean success = response.getBoolean("success");
                                if (success) {
                                    // Inserción exitosa
                                    Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                                    // Redireccionar a otra ventana
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish(); // Opcionalmente, puedes finalizar la actividad actual si no deseas que el usuario vuelva a ella con el botón "Atrás"
                                } else {
                                    // Fallo en la inserción
                                    String errorMessage = response.getString("message");
                                    Toast.makeText(getApplicationContext(), "Error al registrar usuario: " + errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Respuesta del servidor inválida", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Error en la solicitud
                        // Aquí puedes agregar el código para manejar el error de la solicitud
                        // Log.e("Error en la solicitud", error.getMessage());
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            String responseString = new String(error.networkResponse.data);
                            Log.e("Respuesta del servidor", responseString);
                        }
                    }
                });
        // Agregar la solicitud a la cola de solicitudes
        requestQueue.add(jsonObjectRequest);
    }

}
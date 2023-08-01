package com.example.navbotdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EditProfile extends AppCompatActivity {
    EditText nombreET, correoET, catgoriaET, passwordET, confirmPasswordET;
    Button savePerfil;
    ImageView passwordIcon, passwordIcon2;
    public boolean isAttemptSucceded = true;
    boolean passwordVisible = false;
    boolean passwordVisible2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        //Obtener el id de usuario
        UserSession userSession = UserSession.getInstance();
        int userId = userSession.getUserId();

        nombreET = findViewById(R.id.nombreET);
        correoET = findViewById(R.id.correoET);
        catgoriaET = findViewById(R.id.catgoriaET);

        savePerfil = findViewById(R.id.savePerfil);

        passwordET = findViewById(R.id.passwordET);
        confirmPasswordET = findViewById(R.id.confirmPasswordET);

        passwordIcon = findViewById(R.id.passwordIcon);

        passwordIcon2 = findViewById(R.id.passwordIcon2);

        passwordIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (passwordVisible) {
                    // Si la contraseña es visible, cambia el tipo de entrada a 'textPassword'
                    passwordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.ico_visibility_off);
                    passwordVisible = false;
                } else {
                    // Si la contraseña está oculta, cambia el tipo de entrada a 'text'
                    passwordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.ico_visibility);
                    passwordVisible = true;
                }
                // Mueve el cursor al final del texto de la contraseña
                passwordET.setSelection(passwordET.getText().length());
            }
        });

        passwordIcon2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (passwordVisible2) {
                    // Si la contraseña es visible, cambia el tipo de entrada a 'textPassword'
                    confirmPasswordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon2.setImageResource(R.drawable.ico_visibility_off);
                    passwordVisible2 = false;
                } else {
                    // Si la contraseña está oculta, cambia el tipo de entrada a 'text'
                    confirmPasswordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon2.setImageResource(R.drawable.ico_visibility);
                    passwordVisible2 = true;
                }
                // Mueve el cursor al final del texto de la contraseña
                confirmPasswordET.setSelection(confirmPasswordET.getText().length());
            }
        });


        savePerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFormValid = true;

                String userName = nombreET.getText().toString();
                String userEmail= correoET.getText().toString();
                String userCategoria = catgoriaET.getText().toString();
                String passwordUser = passwordET.getText().toString();
                String confirmPasswordUserET = confirmPasswordET.getText().toString();

                if (userName.isEmpty()) {
                    nombreET.setError("Campo obligatorio");
                    isFormValid = false;
                    isAttemptSucceded=false;
                }
                if (userEmail.isEmpty()) {
                    correoET.setError("Campo obligatorio");
                    isFormValid = false;
                    isAttemptSucceded=false;
                }
                if (userCategoria.isEmpty()) {
                    catgoriaET.setError("Campo obligatorio");
                    isFormValid = false;
                    isAttemptSucceded=false;
                }
                if (isFormValid) {
                    SaveProfile(userId, userName, userEmail, passwordUser);
                } else {
                    Toast.makeText(getApplicationContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                }
                if(isAttemptSucceded){
                    Intent intent = new Intent(EditProfile.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditProfile.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        getUserData(userId);
    }

    private void getUserData(int userId) {

        System.out.println("Id requerida: "+userId);
        // Construir la URL de la solicitud GET
        String url = APIUtils.getFullUrl("user/" + userId);

        // Realizar la solicitud GET con Volley
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Procesar la respuesta del servidor

                        try {
                            // Obtener los datos del usuario del objeto JSON response
                            String userName = response.getString("nameUser");
                            String userEmail = response.getString("email");
                            String userCategory = response.getString("categoria");
                            String[] skillsList = response.getString("skills").split(",");

                            System.out.println("Skills: " + skillsList);

                            String urlFoto = response.getString("routesPhoto");

                            // Mostrar los datos en los TextView
                            nombreET.setText(userName);
                            catgoriaET.setText(userCategory);
                            correoET.setText(userEmail);



                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Manejar la excepción JSONException aquí
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Ocurrió un error en la solicitud
                        // Registrar el error en los registros de la aplicación
                        Log.e("PerfilFragment", "Error en la solicitud HTTP: " + error.toString());
                        Toast.makeText(new AppCompatActivity(), "Error al obtener los datos del usuario", Toast.LENGTH_SHORT).show();
                    }
                });

        // Agregar la solicitud a la cola de solicitudes de Volley
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private void SaveProfile(int userId, String userName, String userEmail, String userPassword) {

        String url = APIUtils.getFullUrl("userModify/" + userId);

        // Construir el parámetro de la solicitud
        HashMap<String, String> params = new HashMap<>();
        params.put("nameUser", userName);
        params.put("password", userPassword);
        params.put("email", userEmail);
        params.put("idUser", String.valueOf(userId));

        System.out.println("Datos recibidos: "+params);

        // Solicitud POST
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(params),
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
                                    /*Intent intent = new Intent(EditProfile.this, PerfilFragment.class);
                                    startActivity(intent);*/
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
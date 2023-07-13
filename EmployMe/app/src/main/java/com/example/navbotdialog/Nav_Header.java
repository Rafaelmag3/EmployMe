package com.example.navbotdialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Nav_Header extends AppCompatActivity {

    private TextView TVnameNav,TVemailNav;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Obtener el id de usuario
        UserSession userSession = UserSession.getInstance();
        int userId = userSession.getUserId();

        TVnameNav = findViewById(R.id.nameNav);
        TVemailNav = findViewById(R.id.correoET);

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

                            // Mostrar los datos en los TextView
                            TVnameNav.setText(userName);
                            TVemailNav.setText(userEmail);


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
                        Toast.makeText(getApplicationContext(), "Error al obtener los datos del usuario", Toast.LENGTH_SHORT).show();
                    }
                });

        // Agregar la solicitud a la cola de solicitudes de Volley
        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
    }

}

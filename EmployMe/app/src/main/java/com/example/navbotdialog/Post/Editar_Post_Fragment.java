package com.example.navbotdialog.Post;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.navbotdialog.APIUtils;
import com.example.navbotdialog.MainActivity;
import com.example.navbotdialog.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Editar_Post_Fragment extends AppCompatActivity {

    private EditText puestoTrabajoEditText;
    private EditText noVacantesEditText;
    private EditText locacionEditText;
    private EditText diaPublicacionEditText;
    private EditText diaLimiteEditText;
    private EditText horarioEditText;
    private EditText horario2EditText;
    private EditText salarioEditText;
    private EditText funcionesEditText;
    private EditText requisitosEditText;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_editar);

        // Inicializar los atributos (EditText, Button, etc.) con findViewById
        puestoTrabajoEditText = findViewById(R.id.editText_puestoTrabajo);
        noVacantesEditText = findViewById(R.id.editText_noVacantes);
        locacionEditText = findViewById(R.id.editText_locacion);
        diaPublicacionEditText = findViewById(R.id.editText_diaPublicacion);
        diaLimiteEditText = findViewById(R.id.editText_diaLimite);
        horarioEditText = findViewById(R.id.editText_horario);
        horario2EditText = findViewById(R.id.editText_horario2);
        salarioEditText = findViewById(R.id.editText_salario);
        funcionesEditText = findViewById(R.id.editText_funciones);
        requisitosEditText = findViewById(R.id.editText_requisitos);
        btnGuardar = findViewById(R.id.button_guardar);

        // Configurar la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Habilitar el botón de retroceso en la Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Aplicar el nuevo estilo a la Toolbar
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));


        // Obtener el offerId de alguna manera (puede ser a través de un Intent)
        int offerId = getIntent().getIntExtra("offerId", -1);

        //Toast.makeText(this, "ID"+offerId, Toast.LENGTH_SHORT).show();

        String url = APIUtils.getFullUrl("offer/" + offerId);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject offerData = response.getJSONObject(0); // Obtener el primer objeto en la matriz

                            String id_jobOffer = offerData.getString("id_jobOffer");
                            String jobTitle = offerData.getString("jobTitle");
                            String description = offerData.getString("description");
                            String requirements = offerData.getString("requirements");
                            String publicationDate = offerData.getString("publicationDate");
                            String dueDate = offerData.getString("dueDate");
                            String salary = offerData.getString("salary");
                            String timeDeparture = offerData.getString("timeDeparture");
                            String timeEntry = offerData.getString("timeEntry");
                            String vacancy = offerData.getString("vacancy");
                            String country = offerData.getString("country");
                            String id_user = offerData.getString("id_user");
                            String nameUser = offerData.getString("nameUser");

                            String data = "id_jobOffer: " + id_jobOffer + "\n" +
                                    "jobTitle: " + jobTitle + "\n" +
                                    "description: " + description + "\n" +
                                    "requirements: " + requirements + "\n" +
                                    "publicationDate: " + publicationDate + "\n" +
                                    "dueDate: " + dueDate + "\n" +
                                    "salary: " + salary + "\n" +
                                    "timeDeparture: " + timeDeparture + "\n" +
                                    "timeEntry: " + timeEntry + "\n" +
                                    "vacancy: " + vacancy + "\n" +
                                    "country: " + country + "\n" +
                                    "id_user: " + id_user + "\n" +
                                    "nameUser: " + nameUser;

                            System.out.println("Editar: " + data);
                            //Toast.makeText(getApplicationContext(), "Editar\n" + data, Toast.LENGTH_SHORT).show();

                            // Llenar tus campos de edición con los valores obtenidos
                            puestoTrabajoEditText.setText(jobTitle);
                            noVacantesEditText.setText(vacancy);
                            locacionEditText.setText(country);
                            diaPublicacionEditText.setText(publicationDate);
                            diaLimiteEditText.setText(dueDate);
                            horarioEditText.setText(timeDeparture);
                            horario2EditText.setText(timeEntry);
                            salarioEditText.setText(salary);
                            funcionesEditText.setText(description);
                            requisitosEditText.setText(requirements);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error de la petición
                        Toast.makeText(getApplicationContext(), "Error al obtener los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Agregar la solicitud a la cola de Volley
        Volley.newRequestQueue(this).add(jsonArrayRequest);

        // Configurar el botón "Guardar"
        btnGuardar.setOnClickListener(v -> {
            // Obtener los nuevos valores de los campos
            String newJobTitle = puestoTrabajoEditText.getText().toString();
            String newdescription = funcionesEditText.getText().toString();
            String newrequirements = requisitosEditText.getText().toString();
            String newpublicationDate = diaPublicacionEditText.getText().toString();
            String newdueDate = diaLimiteEditText.getText().toString();
            String newsalary = salarioEditText.getText().toString();
            String newtimeDeparture = horarioEditText.getText().toString();
            String newtimeEntry   = horario2EditText.getText().toString();
            String newVacancy = noVacantesEditText.getText().toString();
            String newCountry = locacionEditText.getText().toString();
            boolean isFormValid = true;

            if (newJobTitle.isEmpty()){
                puestoTrabajoEditText.setError("Campo obligatorio");
                isFormValid = false;
            }
            if (newdescription.isEmpty()){
                funcionesEditText.setError("Campo obligatorio");
                isFormValid = false;
            }
            if (newrequirements.isEmpty()){
                requisitosEditText.setError("Campo obligatorio");
                isFormValid = false;
            }
            if (newpublicationDate.isEmpty()){
                diaPublicacionEditText.setError("Campo obligatorio");
                isFormValid = false;
            }
            if (newdueDate.isEmpty()){
                diaLimiteEditText.setError("Campo obligatorio");
                isFormValid = false;
            }
            if(newsalary.isEmpty()){
                salarioEditText.setError("Campo obligatorio");
                isFormValid = false;
            }
            if(newtimeDeparture.isEmpty()){
                horarioEditText.setError("Campo obligatorio");
                isFormValid = false;
            }
            if(newtimeEntry.isEmpty()){
                horario2EditText.setError("Campo obligatorio");
                isFormValid = false;
            }
            if(newVacancy.isEmpty()){
                noVacantesEditText.setError("Campo obligatorio");
                isFormValid = false;
            }
            if(newCountry.isEmpty()){
                locacionEditText.setError("Campo obligatorio");
                isFormValid = false;
            }
            if (isFormValid){

                // Construir el objeto JSON para la solicitud PUT
                JSONObject updatedData = new JSONObject();
                try {
                    updatedData.put("jobTitle", newJobTitle);
                    updatedData.put("description", newdescription);
                    updatedData.put("requirements", newrequirements);
                    updatedData.put("publicationDate", newpublicationDate);
                    updatedData.put("dueDate", newdueDate);
                    updatedData.put("salary", newsalary);
                    updatedData.put("timeDeparture", newtimeDeparture);
                    updatedData.put("timeEntry", newtimeEntry);
                    updatedData.put("vacancy", newVacancy);
                    updatedData.put("country", newCountry);

                    String data = newJobTitle + ";" + newdescription + ";" + newrequirements + ";" + newpublicationDate + ";" + newdueDate + ";" + newsalary + ";" + newVacancy + ";" + newCountry;

                    System.out.println( "Actualizar Datos:  " +data);


                    // Realizar la solicitud PUT usando Volley
                    String putUrl = APIUtils.getFullUrl("offer/" + offerId);
                    JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, putUrl, updatedData,
                            response -> {
                                // Manejar la respuesta exitosa
                                Toast.makeText(this, "Editado exitosamente", Toast.LENGTH_SHORT).show();
                                finish();
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Editar_Post_Fragment", "Error al editar el post", error);
                                }
                            });

                    // Agregar la solicitud a la cola de Volley
                    Volley.newRequestQueue(this).add(putRequest);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else {
                Toast.makeText(this, "Complete los campos", Toast.LENGTH_SHORT).show();
            }

        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Manejar el botón de retroceso aquí
            finish(); // Finalizar la actividad actual y regresar a la anterior
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

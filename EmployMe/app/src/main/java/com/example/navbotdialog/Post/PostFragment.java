package com.example.navbotdialog.Post;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.airbnb.lottie.animation.content.Content;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.navbotdialog.APIUtils;
import com.example.navbotdialog.MainActivity;
import com.example.navbotdialog.R;
import com.example.navbotdialog.UserSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PostFragment extends Fragment {
    EditText puesto, no_vacantes, locacion, horario_e, horario_s, salario, funciones, requisitos, post_DiaPublicacion, post_DiaLimite;

    Button btnpublicar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);

        //Obtener el id de usuario
        UserSession userSession = UserSession.getInstance();
        int userId = userSession.getUserId();

        puesto = rootView.findViewById(R.id.post_puesto_trabajo);
        no_vacantes = rootView.findViewById(R.id.post_No_vacantes);
        locacion = rootView.findViewById(R.id.post_Locacion);
        horario_e = rootView.findViewById(R.id.post_Horario);
        horario_s = rootView.findViewById(R.id.post_Horario2);
        salario = rootView.findViewById(R.id.post_Salario);
        funciones = rootView.findViewById(R.id.post_Funciones_puesto);
        requisitos = rootView.findViewById(R.id.post_Requisitos);

        EditText salarioEditText = rootView.findViewById(R.id.post_Salario);
        MoneyTextWatcher moneyTextWatcher = new MoneyTextWatcher(salarioEditText);
        salarioEditText.addTextChangedListener(moneyTextWatcher);



        post_DiaPublicacion = rootView.findViewById(R.id.post_DiaPublicacion);
        post_DiaLimite = rootView.findViewById(R.id.post_DiaLimite);

        btnpublicar = rootView.findViewById(R.id.btnPost);


        horario_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(getActivity(), horario_e);
            }
        });

        horario_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(getActivity(), horario_s);
            }
        });

        post_DiaPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(getActivity(), post_DiaPublicacion);
            }
        });

        post_DiaLimite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(getActivity(), post_DiaLimite);
            }
        });

        btnpublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isFormValid = true;
                String jobTitle = puesto.getText().toString();
                String description = funciones.getText().toString();
                String requirements = requisitos.getText().toString();
                String publicationDate = post_DiaPublicacion.getText().toString();
                String dueDate = post_DiaLimite.getText().toString();
                String salary = salario.getText().toString();
                String timeDeparture = horario_e.getText().toString();
                String timeEntry = horario_s.getText().toString();
                String vacancy = no_vacantes.getText().toString();
                String country = locacion.getText().toString();


                if (jobTitle.isEmpty()){
                    puesto.setError("Campo obligatorio");
                    isFormValid = false;
                }
                if (description.isEmpty()){
                    funciones.setError("Campo obligatorio");
                    isFormValid = false;
                }
                if (requirements.isEmpty()){
                    requisitos.setError("Campo obligatorio");
                    isFormValid = false;
                }
                if (publicationDate.isEmpty()){
                    post_DiaPublicacion.setError("Campo obligatorio");
                    isFormValid = false;
                }
                if (dueDate.isEmpty()){
                    post_DiaLimite.setError("Campo obligatorio");
                    isFormValid = false;
                }
                if(salary.isEmpty()){
                    salario.setError("Campo obligatorio");
                    isFormValid = false;
                }
                if(vacancy.isEmpty()){
                    no_vacantes.setError("Campo obligatorio");
                    isFormValid = false;
                }
                if(country.isEmpty()){
                    locacion.setError("Campo obligatorio");
                    isFormValid = false;
                }
                if (isFormValid){
                    createOffer(jobTitle, description, requirements, publicationDate, dueDate, salary, timeDeparture, timeEntry, vacancy, country, userId);
                    Toast.makeText(getActivity(), "Publicación exitosa", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);

                }else {
                    Toast.makeText(getActivity(), "Complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return  rootView;
    }

    //Mostar Reloj
    private void showTimePickerDialog(Context context, final EditText editText){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar selectedTime = Calendar.getInstance();
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                selectedTime.set(Calendar.MINUTE, minute);

                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
                String formattedTime = dateFormat.format(selectedTime.getTime());

                editText.setText(formattedTime);
            }
        }, hour, minute, false);

        timePickerDialog.show();
    }

    //Mostar Calendario
    private void showDatePickerDialog(Context context, final EditText postMes) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, month);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedDate = dateFormat.format(selectedDate.getTime());

                postMes.setText(formattedDate);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }


    //PETICIÓN POST
    public void createOffer(String jobTitle, String description, String requirements, String publicationDate, String dueDate, String salary, String timeDeparture, String timeEntry, String vacancy, String country, int userId) {
        String url = APIUtils.getFullUrl("offerJob");

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("jobTitle", jobTitle);
            jsonBody.put("description", description);
            jsonBody.put("requirements", requirements);
            jsonBody.put("publicationDate", publicationDate);
            jsonBody.put("dueDate", dueDate);
            jsonBody.put("salary", salary);
            jsonBody.put("timeDeparture", timeDeparture);
            jsonBody.put("timeEntry", timeEntry);
            jsonBody.put("vacancy", vacancy);
            jsonBody.put("country", country);
            jsonBody.put("id_user", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Crear la solicitud POST utilizando Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Respuesta del servidor", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Error al crear la oferta de trabajo
                        // Manejar el error si es necesario
                    }
                });
        // Agregar la solicitud a la cola de solicitudes de Volley
        Volley.newRequestQueue(requireActivity().getApplicationContext()).add(request);
    }
}
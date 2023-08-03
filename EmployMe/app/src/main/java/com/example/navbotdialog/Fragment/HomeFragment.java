package com.example.navbotdialog.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.navbotdialog.APIUtils;
import com.example.navbotdialog.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private List<String> listaPublicaciones;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycleViewPublicaciones);

        listaPublicaciones = new ArrayList<>();

        adapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_post_home, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                String data = listaPublicaciones.get(position);
                String[] splitData = data.split(";");
                TextView textView = holder.itemView.findViewById(R.id.title);
                TextView vacancy = holder.itemView.findViewById(R.id.vacancy);
                TextView country = holder.itemView.findViewById(R.id.country);
                TextView timeEntry = holder.itemView.findViewById(R.id.timeEntry);
                TextView salary = holder.itemView.findViewById(R.id.salary);
                TextView requirements = holder.itemView.findViewById(R.id.requirements);
                TextView description = holder.itemView.findViewById(R.id.description);
                TextView publicationDate = holder.itemView.findViewById(R.id.publicationDate);

                TextView nameUser = holder.itemView.findViewById(R.id.nameUser);
                TextView email = holder.itemView.findViewById(R.id.email);
                textView.setText(splitData[0]);
                vacancy.setText(splitData[1]);
                country.setText(splitData[2]);
                timeEntry.setText(splitData[3]);
                salary.setText(splitData[4]);
                requirements.setText(splitData[5]);
                description.setText(splitData[6]);
                publicationDate.setText(splitData[7]);
                nameUser.setText(splitData[8]);
                email.setText(splitData[9]);
            }

            @Override
            public int getItemCount() {
                return listaPublicaciones.size();
            }
        };


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        makeGetRequest();

        return rootView;
    }

    private void makeGetRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = APIUtils.getFullUrl("offers");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Crear una nueva lista temporal
                        List<String> tempList = new ArrayList<>();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String titulo = jsonObject.getString("jobTitle");
                                String vacancy = jsonObject.getString("vacancy");
                                String country = jsonObject.getString("country");
                                String timeEntry = jsonObject.getString("timeEntry");
                                String salary = jsonObject.getString("salary");
                                String requirements = jsonObject.getString("requirements");
                                String description = jsonObject.getString("description");
                                String publicationDate = jsonObject.getString("publicationDate");
                                String nameUser = jsonObject.getString("nameUser");
                                String email = jsonObject.getString("email");
                                String data = titulo + ";" + vacancy + ";" + country + ";" + timeEntry + ";" + salary + ";" + requirements + ";" + description + ";" + publicationDate + ";" + nameUser + ";" + email;

                                tempList.add(data);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Actualizar la lista principal en el hilo principal de la interfaz de usuario
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Limpiar la lista principal
                                listaPublicaciones.clear();
                                // Agregar todos los elementos de la lista temporal
                                listaPublicaciones.addAll(tempList);
                                // Notificar al adaptador del cambio en los datos
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Maneja el error de la petición
                        Toast.makeText(getActivity(), "Error al obtener los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

}
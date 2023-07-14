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
import android.widget.ImageButton;
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_post, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                String data = listaPublicaciones.get(position);
                TextView textView = holder.itemView.findViewById(R.id.title);
                textView.setText(data);
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
                                System.out.println("LLego algo??? " +titulo);
                                tempList.add(titulo);
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

                                // Imprimir los datos en la consola
                                for (String titulo : listaPublicaciones) {
                                    Log.d("Datos", titulo);
                                }
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
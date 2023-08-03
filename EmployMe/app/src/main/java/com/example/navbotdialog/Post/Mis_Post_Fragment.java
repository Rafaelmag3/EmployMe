package com.example.navbotdialog.Post;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.navbotdialog.APIUtils;
import com.example.navbotdialog.R;
import com.example.navbotdialog.UserSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Mis_Post_Fragment extends Fragment {

    private List<String> listaPublicaciones;

    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        UserSession userSession = UserSession.getInstance();
        int userId = userSession.getUserId();

        System.out.println("ID: " + userId);

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

                String[] splitData = data.split(";");
                TextView idPuestoTextView = holder.itemView.findViewById(R.id.id_puesto); // Add this line
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
                idPuestoTextView.setText(splitData[0]); // Add this line
                textView.setText(splitData[1]);
                vacancy.setText(splitData[2]);
                country.setText(splitData[3]);
                timeEntry.setText(splitData[4]);
                salary.setText(splitData[5]);
                requirements.setText(splitData[6]);
                description.setText(splitData[7]);
                publicationDate.setText(splitData[8]);
                nameUser.setText(splitData[9]);

                String idPuesto = idPuestoTextView.getText().toString();

                // Agrega un clic en el elemento de la lista
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Llama al método para mostrar el menú emergente
                        showPopupMenu(v, idPuesto);
                    }
                });

            }

            @Override
            public int getItemCount() {
                return listaPublicaciones.size();
            }

        };

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        makeGetRequest(userId);

        return rootView;
    }

    private void makeGetRequest(int userId) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = APIUtils.getFullUrl("offerUser/" + userId);

        System.out.println("URL: " + url);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Crear una nueva lista temporal
                        List<String> tempList = new ArrayList<>();


                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);


                                String idPublicacion = jsonObject.getString("id_jobOffer");
                                String titulo = jsonObject.getString("jobTitle");
                                String vacancy = jsonObject.getString("vacancy");
                                String country = jsonObject.getString("country");
                                String timeEntry = jsonObject.getString("timeEntry");
                                String salary = jsonObject.getString("salary");
                                String requirements = jsonObject.getString("requirements");
                                String description = jsonObject.getString("description");
                                String publicationDate = jsonObject.getString("publicationDate");
                                String nameUser = jsonObject.getString("nameUser");

                                String data = idPublicacion + ";" + titulo + ";" + vacancy + ";" + country + ";" + timeEntry + ";" + salary + ";" + requirements + ";" + description + ";" + publicationDate + ";" + nameUser;

                                System.out.println( "Titulo:  " +titulo);

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

    private void showPopupMenu(View view, String idPuesto) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);
        popupMenu.inflate(R.menu.popup_menu_post);

        // Agrega un listener para manejar los clics en los elementos del menú
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.PMP_editar:
                        // Crear un Intent y agregar el offerId como extra
                        Intent intent = new Intent(getActivity(), Editar_Post_Fragment.class);

                        intent.putExtra("offerId", Integer.parseInt(idPuesto));
                        startActivity(intent);
                        return true;
                    case R.id.PMP_eliminar:
                        deleteJobOffer(Integer.parseInt(idPuesto));
                        return true;
                    case R.id.PMP_mostrar_mas:
                        // Crear un Intent y agregar el offerId como extra
                        Intent intent2 = new Intent(getActivity(), Mostrar_Post_Activity.class);

                        intent2.putExtra("offerId", Integer.parseInt(idPuesto));
                        startActivity(intent2);
                        return true;
                    default:
                        return false;
                }
            }
        });

        // Muestra el menú emergente
        popupMenu.show();
    }

    private void deleteJobOffer(int offerId) {
        Toast.makeText(getContext(), "Vas a eliminar a "+offerId, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Eliminar publicación")
                .setMessage("¿Estás seguro de que quieres eliminar esta publicación?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        performDeleteRequest(offerId);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
    private void performDeleteRequest(int offerId) {
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        String url = APIUtils.getFullUrl("offer/" + offerId);

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // La publicación se ha eliminado exitosamente
                        Toast.makeText(requireContext(), "Publicación eliminada", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Error al eliminar la publicación
                        Toast.makeText(requireContext(), "Error al editar la publicación: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        System.load("Error al editar: "+error.getMessage());
                    }
                });

        requestQueue.add(stringRequest);
    }


}
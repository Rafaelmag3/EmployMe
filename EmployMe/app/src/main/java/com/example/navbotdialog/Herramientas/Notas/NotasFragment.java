package com.example.navbotdialog.Herramientas.Notas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.navbotdialog.Herramientas.Notas.Adapters.NotasListaAdapter;
import com.example.navbotdialog.Herramientas.Notas.BaseDatos.RoomDB;
import com.example.navbotdialog.Herramientas.Notas.Modales.Notas;
import com.example.navbotdialog.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class NotasFragment extends Fragment {

    RecyclerView recyclerView;
    NotasListaAdapter notasListaAdapter;
    List<Notas> notas = new ArrayList<>();
    RoomDB basedatos;
    FloatingActionButton fab_add;
    Notas NotaSelec;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notas, container, false);

        recyclerView = view.findViewById(R.id.Recycler_home);
        fab_add = view.findViewById(R.id.fab_add);

        basedatos = RoomDB.getInstance(getContext());
        notas = basedatos.mainDAO().getAll();

        actualizarRecycler(notas);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "Dio clic en +", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), NotasTakerActivity.class);
                startActivityForResult(intent, 101);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101){
            if(resultCode == Activity.RESULT_OK){
                Notas nueva_nota = (Notas) data.getSerializableExtra("Nota");
                basedatos.mainDAO().insertar(nueva_nota);
                notas.clear();
                notas.addAll(basedatos.mainDAO().getAll());
                notasListaAdapter.notifyDataSetChanged();
            }
        } else if (requestCode==102) {
            if(resultCode == Activity.RESULT_OK){
                Notas nueva_nota = (Notas) data.getSerializableExtra("Nota");
                basedatos.mainDAO().actualizar(nueva_nota.getID(), nueva_nota.getTitulo(), nueva_nota.getNotas());
                notas.clear();
                notas.addAll(basedatos.mainDAO().getAll());
                notasListaAdapter.notifyDataSetChanged();
            }
        }
    }

    private void actualizarRecycler(List<Notas> notas) {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notasListaAdapter = new NotasListaAdapter(getContext(), notas, click_notas);
        recyclerView.setAdapter(notasListaAdapter);
    }

    private final Click_Notas click_notas = new Click_Notas() {
        @Override
        public void onClick(Notas notas) {
            Intent intent = new Intent(getContext(), NotasTakerActivity.class);
            intent.putExtra("old_note", notas);
            startActivityForResult(intent, 102);

        }

        @Override
        public void onLongClick(Notas notas, CardView cardView) {
            NotaSelec = new Notas();
            NotaSelec = notas;
            showPopup(cardView);
        }
    };

    private void showPopup(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), cardView);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.P_anclar:
                        if(NotaSelec.isAnclar()){
                            basedatos.mainDAO().anclar(NotaSelec.getID(),false);
                            Toast.makeText(getContext(), "Tarea No Completada", Toast.LENGTH_SHORT).show();
                        }else{
                            basedatos.mainDAO().anclar(NotaSelec.getID(),true);
                            Toast.makeText(getContext(), "Tarea Completada", Toast.LENGTH_SHORT).show();
                        }
                        notas.clear();
                        notas.addAll(basedatos.mainDAO().getAll());
                        notasListaAdapter.notifyDataSetChanged();
                        return true;
                    case R.id.P_eliminar:
                        basedatos.mainDAO().Eliminar(NotaSelec);
                        notas.remove(NotaSelec);
                        notasListaAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Nota eliminada con exito", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }
}
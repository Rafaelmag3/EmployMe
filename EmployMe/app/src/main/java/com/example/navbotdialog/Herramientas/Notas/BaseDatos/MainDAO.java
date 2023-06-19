package com.example.navbotdialog.Herramientas.Notas.BaseDatos;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.navbotdialog.Herramientas.Notas.Modales.Notas;

import java.util.List;

@Dao
public interface MainDAO {

    @Insert(onConflict = REPLACE)
    void insertar(Notas notas);

    @Query("SELECT * FROM notas ORDER BY id DESC")
    List<Notas> getAll();

    @Query("UPDATE notas SET titulo = :titulo, notas = :notas WHERE ID = :id")
    void actualizar(int id, String titulo, String notas);

    @Delete
    void Eliminar(Notas notas);

    @Query("UPDATE notas SET anclar = :anclar WHERE ID = :id")
    void anclar(int id, boolean anclar);

}

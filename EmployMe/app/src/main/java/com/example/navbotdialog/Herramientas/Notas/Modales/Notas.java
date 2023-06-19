package com.example.navbotdialog.Herramientas.Notas.Modales;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notas")

public class Notas implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int ID = 0;

    @ColumnInfo(name = "Titulo")
    String titulo = "";

    @ColumnInfo(name = "Notas")
    String notas = "";

    @ColumnInfo(name = "Fecha")
    String fecha = "";

    @ColumnInfo(name = "Anclar")
    boolean anclar = false;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isAnclar() {
        return anclar;
    }

    public void setAnclar(boolean anclar) {
        this.anclar = anclar;
    }
}

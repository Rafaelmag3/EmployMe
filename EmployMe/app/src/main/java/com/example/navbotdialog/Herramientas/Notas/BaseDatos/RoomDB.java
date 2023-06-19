package com.example.navbotdialog.Herramientas.Notas.BaseDatos;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.navbotdialog.Herramientas.Notas.Modales.Notas;

@Database(entities = Notas.class, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB basedatos;
    private static String BASEDATOS_NAME = "NoteApp";

    public synchronized static RoomDB getInstance(Context context){
        if (basedatos == null){
            basedatos = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, BASEDATOS_NAME).allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build();
        }
        return basedatos;
    }

    public abstract MainDAO mainDAO();

}

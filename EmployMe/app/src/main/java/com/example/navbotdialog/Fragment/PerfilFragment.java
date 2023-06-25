package com.example.navbotdialog.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.navbotdialog.EditProfile;
import com.example.navbotdialog.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageButton settingsButton;

    public PerfilFragment() {
        // Required empty public constructor
    }

    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    private ImageView takePhoto, imgProfile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            // Inflar el diseño del fragmento
            View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);

            // Asignar el ImageButton utilizando rootView.findViewById()
            settingsButton = rootView.findViewById(R.id.settingsButton);
            settingsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), EditProfile.class);
                    startActivity(intent);
                }
            });

            //Asiganación a elementos de camara
            takePhoto = rootView.findViewById(R.id.takePhoto);
            imgProfile = rootView.findViewById(R.id.imgProfile);

            //Tocar para abrir la camara
            imgProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCamera();
                }
            });

            //Tocar para abrir la camara
            takePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCamera();
                }
            });


            // Resto del código del fragmento...
            return rootView;

    }

    private  void openCamera(){
        //Capturar Imagen
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Validar para usara el recurso
        startActivityForResult(intent, 1);

    }

    //Resultado de la actividad
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");

            // Redimensionar la imagen
            int targetWidth = getResources().getDimensionPixelSize(R.dimen.image_width);
            int targetHeight = getResources().getDimensionPixelSize(R.dimen.image_height);
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(imgBitmap, targetWidth, targetHeight, true);

            // Hacer que la imagen sea redonda
            Bitmap circularBitmap = getRoundedBitmap(resizedBitmap);

            imgProfile.setImageBitmap(circularBitmap);
        }
    }

    private Bitmap getRoundedBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int radius = Math.min(width, height) / 2;

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawCircle(width / 2, height / 2, radius, paint);

        return output;
    }

}
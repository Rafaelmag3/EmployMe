package com.example.navbotdialog.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;


import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.navbotdialog.EditProfile;
import com.example.navbotdialog.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

            return rootView;

    }

    private  void openCamera(){
        //Capturar Imagen
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {

            //Validar para usara el recurso
            File imageFile = null;

            try {
                imageFile = createImageFile();
            }catch (IOException ex){
                Log.e("Error", ex.toString());
            }

            if (imageFile != null) {
                Uri imageUri = FileProvider.getUriForFile(requireContext(),
                        "com.example.navbotdialog.fileprovider", imageFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 1);
            }

        }


    }

    //Resultado de la actividad
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap imgBitmap = BitmapFactory.decodeFile(currentPhotoPath);

            // Handle image orientation
            try {
                ExifInterface exifInterface = new ExifInterface(currentPhotoPath);
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                Matrix matrix = new Matrix();
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        matrix.postRotate(90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        matrix.postRotate(180);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        matrix.postRotate(270);
                        break;
                    default:
                        // No rotation needed
                        break;
                }
                imgBitmap = Bitmap.createBitmap(imgBitmap, 0, 0, imgBitmap.getWidth(), imgBitmap.getHeight(), matrix, true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Continue with image processing
            int targetWidth = getResources().getDimensionPixelSize(R.dimen.image_width);
            int targetHeight = getResources().getDimensionPixelSize(R.dimen.image_height);
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(imgBitmap, targetWidth, targetHeight, true);
            Bitmap circularBitmap = getRoundedBitmap(resizedBitmap);

            imgProfile.setImageBitmap(circularBitmap);
        }
    }


    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        // Obtain the Context object associated with the Fragment
        Context context = requireContext();

        // Get the directory for storing the image file
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Create the temporary image file
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save the file path for later use
        currentPhotoPath = image.getAbsolutePath();
        Log.e("Imagen: ", currentPhotoPath);
        return image;
    }



    //Ajustar propiedades de la fotografia
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
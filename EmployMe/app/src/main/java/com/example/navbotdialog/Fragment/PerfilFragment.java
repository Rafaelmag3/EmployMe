package com.example.navbotdialog.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
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


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.navbotdialog.APIUtils;
import com.example.navbotdialog.EditProfile;
import com.example.navbotdialog.R;
import com.example.navbotdialog.UserSession;
import com.example.navbotdialog.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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
    private TextView nameProfileTV, emailProfileTV, dateRegisterTV, categoryTV;

    private ListView skillsListlv;

    private Uri uri = null;

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.option_photo, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_camera:
                openCamera();
                //Toast.makeText(getActivity(), "Camara", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.opcion_galery:
                openGalery();
                //Toast.makeText(getActivity(), "Galeria", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Obtener el id de usuario
        UserSession userSession = UserSession.getInstance();
        int userId = userSession.getUserId();
        //Log.d("PerfilFragment", "User ID Fragment: " + userId);

        // Inflar el diseño del fragmento
        View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);

        //Asignar datos
        nameProfileTV = rootView.findViewById(R.id.nameProfile);
        emailProfileTV = rootView.findViewById(R.id.emailProfile);
        categoryTV = rootView.findViewById(R.id.categoryTV);
        skillsListlv = rootView.findViewById(R.id.skillsList);


        // Realizar la solicitud GET para obtener los datos del usuario
        getUserData(userId);


        // Asignar el ImageButton
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

        //Mostar menú para abrir la camara o galeria
        registerForContextMenu(takePhoto);
        takePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.showContextMenu();
                }
            });
        //Mostar menú para abrir la camara o galeria
        registerForContextMenu(imgProfile);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.showContextMenu();
            }
        });

        return rootView;
    }

    private String imagePath;

    //Abrir camara
    private void openCamera(){
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

    //Abrir galeria
    private  void openGalery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galeryARL.launch(intent);
    }

    //Mostrar imagen desde la GALERIA
    private ActivityResultLauncher<Intent> galeryARL = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        uri = data.getData();


                        try {
                            Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                            int targetWidth = getResources().getDimensionPixelSize(R.dimen.image_width);
                            int targetHeight = getResources().getDimensionPixelSize(R.dimen.image_height);
                            Bitmap resizedBitmap = Bitmap.createScaledBitmap(selectedImage, targetWidth, targetHeight, true);

                            Bitmap circularBitmap = getRoundedBitmap(resizedBitmap);
                            imgProfile.setImageBitmap(circularBitmap);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }else{
                        Toast.makeText(getActivity(), "Fotografia no cambiada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    //Resultado de la actividad "Mostar imagen de FOTOGRAFIA"
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePath = currentPhotoPath;
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap imgBitmap = BitmapFactory.decodeFile(imagePath);
            updateImage(imagePath);
            System.out.println("--> Ruta de imagen local "+imagePath);

            // Handle image orientation
            try {
                ExifInterface exifInterface = new ExifInterface(imagePath);
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
                        break;
                }
                imgBitmap = Bitmap.createBitmap(imgBitmap, 0, 0, imgBitmap.getWidth(), imgBitmap.getHeight(), matrix, true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //
            int targetWidth = getResources().getDimensionPixelSize(R.dimen.image_width);
            int targetHeight = getResources().getDimensionPixelSize(R.dimen.image_height);
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(imgBitmap, targetWidth, targetHeight, true);
            Bitmap circularBitmap = getRoundedBitmap(resizedBitmap);

            imgProfile.setImageBitmap(circularBitmap);
        }
    }

    public String currentPhotoPath;

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

        return image;
    }


    //Solicitud POST para enviar imagen
    public void updateImage(String imagePath) {
        System.out.println("--> Ruta recibida imagen Ruta: " + imagePath);

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        String fileName = getImageFileName(imagePath); // Obtener el nombre del archivo con la extensión



        String url = APIUtils.getFullUrl("upload");

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String result = new String(response.data);
                        Toast.makeText(getActivity(), "Imagen Actualizada", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error al actualizar", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, DataPart> getByteData() {
                return null;
            }
        };

        // Agregar parámetro de imagen con el nombre de archivo
        VolleyMultipartRequest.DataPart dataPart = new VolleyMultipartRequest.DataPart("file", imageBytes, "image/jpeg");
        multipartRequest.addByteDataParam(fileName, dataPart);

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(multipartRequest);
    }

    private String getImageFileName(String imagePath) {
        File file = new File(imagePath);
        return file.getName();
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

    //Peticon GET
    private void getUserData(int userId) {
        // Construir la URL de la solicitud GET
        String url = APIUtils.getFullUrl("user/" + userId);

        // Realizar la solicitud GET con Volley
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Procesar la respuesta del servidor

                        try {
                            // Obtener los datos del usuario del objeto JSON response
                            String userName = response.getString("nameUser");
                            String userEmail = response.getString("email");
                            String userCategory = response.getString("categoria");
                            String[] skillsList = response.getString("skills").split(",");

                            System.out.println("Skills: " + skillsList);

                            String urlFoto = response.getString("routesPhoto");

                            // Mostrar los datos en los TextView
                            nameProfileTV.setText(userName);
                            emailProfileTV.setText(userEmail);
                            categoryTV.setText(userCategory);

                            if(isAdded()){
                                ArrayAdapter<String> skills = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, skillsList);
                                skillsListlv.setAdapter(skills);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Manejar la excepción JSONException aquí
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Ocurrió un error en la solicitud
                        // Registrar el error en los registros de la aplicación
                        Log.e("PerfilFragment", "Error en la solicitud HTTP: " + error.toString());
                        Toast.makeText(getActivity(), "Error al obtener los datos del usuario", Toast.LENGTH_SHORT).show();
                    }
                });

        // Agregar la solicitud a la cola de solicitudes de Volley
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
    }
}
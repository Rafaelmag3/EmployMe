package com.example.navbotdialog;
import static android.content.ContentValues.TAG;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.navbotdialog.Fragment.PerfilFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton, fingerprintButtom;
    TextView forgetPassword, msgFingerprint;
    LinearLayout enviar_a_Registro;
    boolean passwordVisible = false;
    ImageView buttonPassword;
    RelativeLayout googleButton;
    FirebaseAuth auth;
    FirebaseDatabase dataFire;
    GoogleSignInClient mGoogleSignInClient;
    ProgressDialog progressDialog;


    //Variables del sensor de huella
    private Executor executor;
    private SharedPreferences sharedPreferences;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;


    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.login_email);
        passwordEditText = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        fingerprintButtom = findViewById(R.id.fingerprintButtom);
        msgFingerprint = findViewById(R.id.msgFingerprint);
        forgetPassword = findViewById(R.id.forgetPassword);
        enviar_a_Registro = findViewById(R.id.enviar_a_Registro);

        buttonPassword = findViewById(R.id.passwordIcon);

        //Inicio de sesion con google
        googleButton = findViewById(R.id.signInWhithGoogle);
        auth= FirebaseAuth.getInstance();
        dataFire= FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Cuenta en proceso");
        progressDialog.setMessage("Estamos creando su cuenta");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        //TextView singUpRedirectedText = findViewById(R.id.forgetPassword);
        String text = "Olvidaste tu contraseña";
        forgetPassword.setText(Html.fromHtml(text));

        buttonPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (passwordVisible) {
                    // Si la contraseña es visible, cambia el tipo de entrada a 'textPassword'
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    buttonPassword.setImageResource(R.drawable.ico_visibility_off);
                    passwordVisible = false;
                } else {
                    // Si la contraseña está oculta, cambia el tipo de entrada a 'text'
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    buttonPassword.setImageResource(R.drawable.ico_visibility);
                    passwordVisible = true;
                }

                // Mueve el cursor al final del texto de la contraseña
                passwordEditText.setSelection(passwordEditText.getText().length());
            }
        });

        enviar_a_Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Definir las animaciones
                ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat( enviar_a_Registro, "scaleX", 1f, 0.5f, 1f);
                scaleXAnimation.setDuration(500);

                ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat( enviar_a_Registro, "scaleY", 1f, 0.5f, 1f);
                scaleYAnimation.setDuration(500);

                // Crear un conjunto de animaciones
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(scaleXAnimation, scaleYAnimation);

                // Iniciar las animaciones
                animatorSet.start();
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Definir las animaciones
                ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat( forgetPassword, "scaleX", 1f, 0.5f, 1f);
                scaleXAnimation.setDuration(500);

                ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat( forgetPassword, "scaleY", 1f, 0.5f, 1f);
                scaleYAnimation.setDuration(500);

                // Crear un conjunto de animaciones
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(scaleXAnimation, scaleYAnimation);

                // Iniciar las animaciones
                animatorSet.start();
                Intent intent = new Intent(LoginActivity.this, forgetPassword.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validar campos vacíos
                if (email.isEmpty()) {
                    emailEditText.setError("Campo obligatorio");
                } else if (password.isEmpty()) {
                    passwordEditText.setError("Campo obligatorio");
                } else {
                    // Los campos no están vacíos, realizar el inicio de sesión
                    iniciarSesion(email, password);

                }
            }
        });


        //**SENSOR DE HUELLA*
        // Obtener una instancia de SharedPreferences
        sharedPreferences = getSharedPreferences("Token", MODE_PRIVATE);

        // Verificar si el usuario ya tiene un token almacenado (ya ha iniciado sesión previamente)
        String token = sharedPreferences.getString("user_token", null);
        if (token != null) {
            // Si el token existe, redirige al usuario a la pantalla deseada
            goToNewActivity();
        } else {
            // Mostrar el mensaje de bienvenida y opciones de autenticación biométrica
            showWelcomeMessage();
        }

        // Mostrar el resultado de la autenticación y si el usuario puede iniciar sesión
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(LoginActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                                "Error de autenticación: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "¡Autenticación exitosa!", Toast.LENGTH_SHORT).show();

                // Generar un token
                String token = generateToken();

                // Mostrar el token en la consola
                System.out.println("Token generado: " + token);

                // Guardar el token en SharedPreferences
                saveTokenToSharedPreferences(token);

                // Abrir la nueva actividad
                goToNewActivity();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Autenticación fallida",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Inicio de sesión biométrico")
                .setSubtitle("Inicia sesión usando tu credencial biométrica")
                .setNegativeButtonText("Volver")
                .build();

        // El aviso aparece cuando el usuario hace clic en "Iniciar sesión".

        fingerprintButtom.setOnClickListener(view -> {
            biometricPrompt.authenticate(promptInfo);
        });

    }

    private void showWelcomeMessage() {
        // Verificar si el usuario puede usar el sensor de huellas dactilares
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                msgFingerprint.setText("Usa tu datos biometricos para Iniciar Sesión");
                msgFingerprint.setTextColor(Color.parseColor("#000000"));
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                msgFingerprint.setText("No tienes el sensor de huellas dactilares para iniciar sesión");
                msgFingerprint.setTextColor(Color.parseColor("#EC4949"));
                fingerprintButtom.setVisibility(View.GONE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                msgFingerprint.setText("El sensor biométrico no está disponible actualmente");
                msgFingerprint.setTextColor(Color.parseColor("#EC4949"));
                fingerprintButtom.setVisibility(View.GONE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                msgFingerprint.setText("Tu dispositivo no tiene ninguna huella dactilar guardada, por favor verifica tus ajustes de seguridad");
                msgFingerprint.setTextColor(Color.parseColor("#EC4949"));
                fingerprintButtom.setVisibility(View.GONE);
                break;
        }
    }

    private String generateToken() {
        // Aquí puedes generar un token de manera segura utilizando cualquier método que prefieras.
        // Puedes usar bibliotecas de generación de tokens como JWT (JSON Web Tokens) o simplemente generar un UUID único.

        // Por ejemplo, para generar un UUID único:
        return UUID.randomUUID().toString();
    }

    private void saveTokenToSharedPreferences(String token) {
        // Editar el SharedPreferences para guardar el token
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_token", token);
        editor.apply();
    }

    private void goToNewActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Finalizar esta actividad para que no se pueda volver a ella con el botón "Atrás"
    }

    public void iniciarSesion(String email, String password) {

        String url = APIUtils.getFullUrl("login");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // La solicitud fue exitosa y se recibió una respuesta del servidor
                        // Procesar la respuesta del servidor


                        try {
                            String message = response.getString("message");
                            JSONObject userObject = response.optJSONObject("user");


                            if (userObject != null) {

                                Log.d("LoginActivity", "Response: " + response.toString());

                                //Resibir Id de usuario con la respuesta del swervidor
                                int userId = userObject.optInt("idUser", 0);

                                UserSession userSession = UserSession.getInstance();
                                userSession.setUserId(userId);


                                //Mandar id a otra interfaz para poder ser utilizada
                                Bundle bundle = new Bundle();
                                bundle.putInt("userId", userId);
                                PerfilFragment perfilFragment = new PerfilFragment();
                                perfilFragment.setArguments(bundle);

                                Log.d("LoginActivity", message);
                                Log.d("LoginActivity", "User ID: " + userId);

                                Intent idUser = new Intent(LoginActivity.this, PerfilFragment.class);
                                idUser.putExtra("userId", userId);
                                startActivity(idUser);


                                if (message.equals("Inicio de sesión exitoso")) {
                                    // La conexión fue exitosa y el usuario está autenticado
                                    // Redireccionar a MainActivity
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish(); // Opcionalmente, finalizar la actividad actual
                                } else {
                                    Toast.makeText(LoginActivity.this, "Contraseña o usuario incorrecto", Toast.LENGTH_SHORT).show();
                                    // La conexión fue exitosa, pero hubo un error en la autenticación
                                    // Puedes mostrar un mensaje de error o realizar otras acciones
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Usuario incorrecto", Toast.LENGTH_SHORT).show();
                                // El campo "user" es nulo en el objeto JSON
                                // Manejar este caso según tus necesidades
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
                        Log.e("LoginActivity", "Error en la solicitud HTTP: " + error.toString());
                        Toast.makeText(LoginActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();

                    }
                });
        // Agregar la solicitud a la cola de solicitudes de Volley
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

}

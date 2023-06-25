package com.example.navbotdialog;
import static android.content.ContentValues.TAG;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    TextView forgetPassword;
    LinearLayout enviar_a_Registro;
    boolean passwordVisible = false;
    ImageView buttonPassword;
    RelativeLayout googleButton;
    FirebaseAuth auth;
    FirebaseDatabase dataFire;
    GoogleSignInClient mGoogleSignInClient;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.login_email);
        passwordEditText = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
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


                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                //Validar campos llenos
                //iniciarSesion(email, password);
            }
        });

    }

    private void iniciarSesion(final String email, final String password) {
        String url = APIUtils.getFullUrl("login");


        // Crea una solicitud POST utilizando Volley
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Manejar la respuesta del servidor aquí
                        // Por ejemplo, verificar si el inicio de sesión fue exitoso
                        boolean loginSuccess = false; // Variable para almacenar el estado del inicio de sesión
                        try {
                            // Verificar si el inicio de sesión fue exitoso según la respuesta del servidor
                            loginSuccess = response.getBoolean("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (loginSuccess) {
                            // Redirigir a la ventana deseada
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            // Mostrar mensaje de error con un Toast
                            Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error de la solicitud aquí
                        Toast.makeText(getApplicationContext(), "Error en la solicitud", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public byte[] getBody() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("email", email);
                    jsonObject.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Registro de los datos que se envían en la consola
                Log.d("Datos de inicio de sesión", jsonObject.toString());

                return jsonObject.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        // Agrega la solicitud a la cola de solicitudes de Volley
        Volley.newRequestQueue(this).add(jsonRequest);
    }

}

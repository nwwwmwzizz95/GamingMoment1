package com.example.gamingmoment1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register_activity extends AppCompatActivity {

    //BtnAtras1
    CircleImageView mCircleImageViewBack;
    //Campo usuario1
    TextInputEditText mTextInputUsername;
    //Campo email1
    TextInputEditText mTextInputEmail;
    //Campo password1
    TextInputEditText mTextInputPassword;
    //Campo confirmarpassword1
    TextInputEditText mTextInputConfirmPassword;
    //btnRegister1
    Button mButtonRegister;
    //Firebase DB1
    FirebaseAuth mAuth;
    //userRegister1
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);

        //BtnAtras2
        mCircleImageViewBack = findViewById(R.id.circleImageBack);
        mCircleImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Campo usuario2
        mTextInputUsername = findViewById(R.id.textInputUsername);
        mTextInputUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Campo email2
        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Campo password2
        mTextInputPassword = findViewById(R.id.textInputPassword);
        mTextInputPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Campo confirmarpassword2
        mTextInputConfirmPassword = findViewById(R.id.textInputConfirmPassword);
        mTextInputConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //btnRegister2
        mButtonRegister = findViewById(R.id.btnRegister);
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(); // ->btnRegister3
            }
        });

        //Firebase DB2
        mAuth = FirebaseAuth.getInstance();

        //userRegister2
        mFirestore = FirebaseFirestore.getInstance();

    }

    //btnRegister3
    private void register() {
        String username = mTextInputUsername.getText().toString();
        String email = mTextInputEmail.getText().toString();
        String password = mTextInputPassword.getText().toString();
        String confirmPassword = mTextInputConfirmPassword.getText().toString();

        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()){ //isEmpty() pregunta si el campo está vacío
            if (isEmailValid(email)){
                if (password.equals(confirmPassword)){
                    if (password.length() >= 6){
                        createUser(email, password, username);
                    }else{
                        Toast.makeText(this, "La contraseña debe tener almenos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "El email ingresado no es válido", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Por favor, ingresa todos los campos para poder continuar.", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     Método para verificar que un email es válido
     */
    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /*
    Método para crear usuario
     */
    private void createUser(final String email, final String password, final String username){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() { //Recibir email, password
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String id = mAuth.getCurrentUser().getUid(); //Obtener id de usuario de firebase
                    Map <String, Object> map = new HashMap<>();

                    map.put("email", email);
                    map.put("username", username);
                    //map.put("passowrd", password); No se recomienda almacenar el password del usuario, ya que de ello se encarga firebase con un cifrado
                    mFirestore.collection("Users").document().set(map).addOnCompleteListener(new OnCompleteListener<Void>() { //Crear documento con id de usuario
                        @Override
                        public void onComplete(@NonNull Task<Void> task) { //Validar que la tarea es exitosa
                            if (task.isSuccessful()){
                                Toast.makeText(Register_activity.this, "El usuario se almacenó en la base de datos con éxito, ¡bienvenido a GamingMoment!", Toast.LENGTH_LONG).show();

                            }else {
                                Toast.makeText(Register_activity.this, "Error al almacenar usuario en la base de datos.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(Register_activity.this, "No se pudo registrar el usuario, vuelve a intentarlo",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
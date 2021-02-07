package com.example.gamingmoment1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    //REGISTRATE AQUÍ1
    TextView mTextViewRegister;
    //Entrada email1
    TextInputEditText mTextInputEmail;
    //Entrada password1
    TextInputEditText mTextInputPassword;
    //Btn iniciar sesion1
    Button mButtonLogin;
    //Login1
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //REGISTRATE AQUÍ2
        mTextViewRegister = findViewById(R.id.textViewRegister);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register_activity.class);
                startActivity(intent);
            }
        });

        //Entrada email2
        mTextInputEmail = findViewById(R.id.textInputEmail);

        //Entrada password
        mTextInputPassword = findViewById(R.id.textInputPassword);

        //Btn inciar sesion2
        mButtonLogin = findViewById(R.id.btnLogin);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        //Login2
        mAuth = FirebaseAuth.getInstance();

    }

    //Btn inciar sesion3
    private void login() {
        String email = mTextInputEmail.getText().toString(); //Recibir email
        String password = mTextInputPassword.getText().toString(); //Recibir password

        //Login3
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);

                }else {
                    Toast.makeText(MainActivity.this, "El email o la contraseña es erróneo.", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Imprimir email y password recibidos por consola para comprobación
        Log.d("CAMPO", "email: "+email);
        Log.d("CAMPO: ", "password: "+password);

    }



}
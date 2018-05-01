package info.androidhive.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    DatabaseReference databaseLogin;
    private EditText inputEmail;
    private EditText inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        auth = FirebaseAuth.getInstance();



        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        databaseLogin = FirebaseDatabase.getInstance().getReference("Credenciais");

        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);


        auth = FirebaseAuth.getInstance();


        btnSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                   // startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                }
            });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();




                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Insira seu endereco de email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Insira sua senha!", Toast.LENGTH_SHORT).show();
                    return;
                }



                progressBar.setVisibility(View.VISIBLE);




                //autenticação usuario
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                String id = databaseLogin.push().getKey();


                                progressBar.setVisibility(View.GONE);

                                if (!task.isSuccessful()) {

                                    email.equals("thais@gmail.co");


                                    Intent intent1 = new Intent(getApplicationContext(), Principal.class);
                                    startActivity(intent1);

                                    Toast.makeText(getApplicationContext(), "Seja bem vindo sindico", Toast.LENGTH_SHORT).show();

                                }else{

                                    if (task.isSuccessful()) {

                                        Intent intent2 = new Intent(getApplicationContext(), MenuCondominoActivity.class);
                                        startActivity(intent2);

                                        Toast.makeText(getApplicationContext(), "Seja bem vindo condomino", Toast.LENGTH_SHORT).show();
                                    }

                                }

                                databaseLogin.child(id).setValue(inputEmail.getText().toString());

                            }

                        });

            }
        });
    }
}


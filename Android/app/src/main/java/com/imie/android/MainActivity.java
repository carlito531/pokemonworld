package com.imie.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imie.android.api.PokemonWS;
import com.imie.android.api.TrainerWS;
import com.imie.android.model.Pokemon;
import com.imie.android.model.Trainer;
import com.imie.android.util.Util;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    ProgressDialog prgDialog;
    TextView pseudoTv;
    EditText pseudoEt;
    EditText emailEt;
    EditText pwdEt;
    CheckBox isNew;

    Button connectionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEt = (EditText)findViewById(R.id.etxtLogin);
        pwdEt = (EditText)findViewById(R.id.etxtPassword);
        pseudoTv = (TextView)findViewById(R.id.txtPseudo);
        pseudoEt = (EditText) findViewById(R.id.etxtPseudo);
        isNew = (CheckBox)findViewById(R.id.chkbxInscription);
        isNew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    connectionBtn.setText("Inscription");
                    pseudoTv.setVisibility(View.VISIBLE);
                    pseudoEt.setVisibility(View.VISIBLE);

                } else {
                    connectionBtn.setText("Connexion");
                    pseudoTv.setVisibility(View.INVISIBLE);
                    pseudoEt.setVisibility(View.INVISIBLE);
                }
            }
        });

        connectionBtn = (Button)findViewById(R.id.btnConnect);
        connectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(v);


            }
        });

        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);
    }


    /**
     *
     *
     * @param view
     */
    public void loginUser(View view){

        // Get values
        String pseudo = pseudoEt.getText().toString();
        String email = emailEt.getText().toString();
        String password = pwdEt.getText().toString();

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // When Email Edit View and Password Edit View have values other than Null
        if(!email.equals("") && !password.equals("")){
            // When Email entered is Valid
            if (Util.validate(email)) {

                // Get pokemon searched by calling the symfony api
                TrainerWS service = retrofit.create(TrainerWS.class);

                Call<String> item = service.getConnection(email, Util.toSha256(password), pseudo);
                item.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Response<String> response, Retrofit retrofit) {
                        if (response.code() == 200) {
                            // put user login in shared preferences

                            Toast.makeText(getApplicationContext(), "Utilisateur connecté", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, FightActivity.class);
                            startActivity(intent);

                        } else if (response.code() == 201) {
                            Toast.makeText(getApplicationContext(), "Nouvel utilisateur enregistré", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, FightActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(), "Mauvais identifiants", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Le serveur ne répond pas", Toast.LENGTH_LONG).show();
                    }
                });

            } else{
                Toast.makeText(getApplicationContext(), "Entrez un e-mail valide", Toast.LENGTH_LONG).show();
            }
        } else{
            Toast.makeText(getApplicationContext(), "Veuillez remplir tout les champs", Toast.LENGTH_LONG).show();
        }
    }
}

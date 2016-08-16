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

import com.imie.android.serviceWS.TrainerWS;
import com.imie.android.util.Util;
import com.microsoft.azure.engagement.EngagementAgent;
import com.microsoft.azure.engagement.EngagementConfiguration;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ConnectionActivity extends AppCompatActivity {

    ProgressDialog prgDialog;
    TextView pseudoTv;
    EditText pseudoEt;
    EditText emailEt;
    EditText pwdEt;
    CheckBox isNew;
    String deviceId;
    Button connectionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Azure mobile engagement registring device id
        EngagementConfiguration engagementConfiguration = new EngagementConfiguration();
        engagementConfiguration.setConnectionString("Endpoint=pokemonworld.device.mobileengagement.windows.net;SdkKey=fb7e9ac50f622caf9d0043e99ebfc671;AppId=nep000170");
        EngagementAgent.getInstance(this).init(engagementConfiguration);

        if (Util.getSharedPreferences("deviceId", getApplicationContext()) == null) {
            getDeviceId();
        }

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
     * Get device id to add to notification campaign
     */
    private void getDeviceId() {
        EngagementAgent.getInstance(this).getDeviceId(new EngagementAgent.Callback<String>()
        {
            @Override
            public void onResult(String dId)
            {
                Util.saveToSharedPreferences("deviceId", dId, getApplicationContext());
            }
        });
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
        String deviceId = Util.getSharedPreferences("deviceId", getApplicationContext());

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Util.getApiUrlBase(getApplicationContext()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // When Email Edit View and Password Edit View have values other than Null
        if(!email.equals("") && !password.equals("")){
            // When Email entered is Valid
            if (Util.validate(email)) {

                // Get connection
                TrainerWS service = retrofit.create(TrainerWS.class);

                Call<String> item = service.getConnection(email, Util.toSha256(password), pseudo, deviceId);
                item.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Response<String> response, Retrofit retrofit) {
                        if (response.code() == 200) {
                            // put user login in shared preferences
                            Util.saveToSharedPreferences("userLogin", emailEt.getText().toString(), getApplicationContext());

                            Toast.makeText(getApplicationContext(), "Utilisateur connecté", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ConnectionActivity.this, HomeActivity.class);
                            startActivity(intent);

                        } else if (response.code() == 201) {
                            // put user login in shared preferences
                            Util.saveToSharedPreferences("userLogin", emailEt.getText().toString(), getApplicationContext());

                            Toast.makeText(getApplicationContext(), "Nouvel utilisateur enregistré", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ConnectionActivity.this, HomeActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(), response.body(), Toast.LENGTH_LONG).show();
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

package itbs.sem2.mycallerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //sharedPreferences: Instance de SharedPreferences pour stocker les données.
    private SharedPreferences sharedPreferences;
    //PREF_NAME: Nom du fichier de préférences partagées.
    private static final String PREF_NAME = "myPref";


    //KEY_CONNECTED: Clé utilisée pour stocker l'état de la connexion.

    private static final String KEY_CONNECTED = "connected";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des vues et des boutons

        Button btnexit = findViewById(R.id.btnexit_auth);
        Button btnlogin = findViewById(R.id.btnlogin_auth);
        EditText emailEditText = findViewById(R.id.edemail_auth);
        EditText passwordEditText = findViewById(R.id.edpwd_auth);
        CheckBox checkBox = findViewById(R.id.checkBox);

        //Vérification de l'état de connexion
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        boolean connected = sharedPreferences.getBoolean(KEY_CONNECTED, false);
        if (connected) {
            startAccueilActivity();
        }

        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String pwd = passwordEditText.getText().toString();
                if (!email.isEmpty() && !pwd.isEmpty()) {
                    if (email.equals("isned") && pwd.equals("123")) {
                         // Vérification des informations de connexion
                        // Redirection vers l'activité d'accueil si la connexion est réussie
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(KEY_CONNECTED, checkBox.isChecked());
                        editor.apply();
                        startAccueilActivity();
                    } else {
                        Toast.makeText(MainActivity.this, "Input error", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Email or password empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void startAccueilActivity() {
        Intent i = new Intent(MainActivity.this, Accueil.class);
        startActivity(i);
        finish();
    }
}

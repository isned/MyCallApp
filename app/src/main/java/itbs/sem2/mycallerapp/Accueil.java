package itbs.sem2.mycallerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Accueil extends AppCompatActivity {
    private Button btnadd, btnlist, btnlogout;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "myPref";
    private static final String KEY_CONNECTED = "connected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        btnadd = findViewById(R.id.btn_add);
        btnlist = findViewById(R.id.btn_list);
        btnlogout = findViewById(R.id.btn_logout);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(KEY_CONNECTED, false);
                editor.apply();
                Intent i = new Intent(Accueil.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Accueil.this, AjoutProfil.class);
                startActivity(i);
            }
        });
        btnlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Accueil.this, listProfil.class);
                startActivity(i);
            }
        });
    }
}

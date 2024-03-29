package itbs.sem2.mycallerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentHostCallback;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AjoutProfil extends AppCompatActivity {
    Button btnback,btncancel,btnsave;
    EditText edename,edelastname,edenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_profil);
        btnback=findViewById(R.id.btn_back);
        btnsave=findViewById(R.id.btn_save);
        btncancel=findViewById(R.id.btn_cancel);
        edename=findViewById(R.id.ed_name);
        edelastname=findViewById(R.id.ed_lastname);
        edenumber=findViewById(R.id.ed_number);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edename.setText("");
                edelastname.setText("");
                edenumber.setText("");
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Création d'une instance de ProfileManager
                ProfileManager profileManager = new ProfileManager(AjoutProfil.this);

                // Ouverture de la base de données
                profileManager.open();

                // Récupération des données des champs de saisie
                String name = edename.getText().toString();
                String lastName = edelastname.getText().toString();
                String number = edenumber.getText().toString();

                if (name.isEmpty() || lastName.isEmpty() || number.isEmpty()) {
                    Toast.makeText(AjoutProfil.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {

                    profileManager.insert(name, lastName, number);
                    Toast.makeText(AjoutProfil.this, "Profil Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }


            }
        });

    }
}
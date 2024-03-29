package itbs.sem2.mycallerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class listProfil extends AppCompatActivity {
    //Déclaration
    Button btnback;
    EditText search;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_profil);

        btnback = findViewById(R.id.btn_back_list);
        search = findViewById(R.id.editSearch);
        rv = findViewById(R.id.rv);

        // Création d'une instance de ProfileManager
        ProfileManager profileManager = new ProfileManager(listProfil.this);

        // Ouverture de la base de données
        profileManager.open();

        // Récupération de tous les profils depuis la base de données
        ArrayList<Profil> data = profileManager.selectAll();

        // Fermeture de la base de données
        profileManager.close();

        // Création de l'adaptateur et affectation à la ListView
        //MyProfilAdapter adapter = new MyProfilAdapter(listProfil.this, data);



        //Création de l'adaptateur qui va prendre contexte et data
        MyRecyclerProfilAdapter adapter = new MyRecyclerProfilAdapter(listProfil.this,data);
       /* LinearLayoutManager layoutManager= new
                LinearLayoutManager(listProfil.this,
                LinearLayoutManager.HORIZONTAL,true);*/
        GridLayoutManager layoutManager = new GridLayoutManager(listProfil.this,1,GridLayoutManager.VERTICAL,true);
        //Configuration de la RecyclerView
        rv.setLayoutManager(layoutManager);
        //Association de l'adaptateur à la RecyclerView
        rv.setAdapter(adapter);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Ne rien faire avant la modification du texte
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Ne rien faire pendant que le texte est en train d'être modifié
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Après que le texte a été modifié, exécuter la recherche
                String searchText = s.toString().trim();

                // Si le texte de recherche est vide, afficher tous les profils
                if (searchText.isEmpty()) {
                    adapter.setFilteredData(data); // Réinitialiser les données filtrées à toutes les données
                } else {
                    // Filtrer les profils en fonction du texte de recherche
                    ArrayList<Profil> filteredData = filter(data, searchText);
                    adapter.setFilteredData(filteredData);
                }
            }
        });
    }

    // Méthode pour filtrer les profils en fonction du search text
    private ArrayList<Profil> filter(ArrayList<Profil> profiles, String searchText) {
        ArrayList<Profil> filteredList = new ArrayList<>();

        // Parcourir tous les profils
        for (Profil profile : profiles) {
            // Vérifier si le name, le lastname ou le number du profil contient le texte de recherche
            if (profile.name.toLowerCase().contains(searchText.toLowerCase()) ||
                    profile.lastanme.toLowerCase().contains(searchText.toLowerCase()) ||
                    profile.number.toLowerCase().contains(searchText.toLowerCase())) {
                // Si le profil correspond au critère de recherche, l'ajouter à la liste filtrée
                filteredList.add(profile);
            }
        }

        return filteredList;
        }

    }


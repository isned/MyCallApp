package itbs.sem2.mycallerapp;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.Manifest;
import android.widget.Toast;

public class MyRecyclerProfilAdapter extends RecyclerView.Adapter<MyRecyclerProfilAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Profil> originalData; // Original data set
    private ArrayList<Profil> filteredData; // Filtered data set
    private ProfileManager profilManager;
    private static final int REQUEST_CALL = 1;

    public MyRecyclerProfilAdapter(Context context, ArrayList<Profil> data) {
        this.context = context;
        this.originalData = data;
        this.filteredData = new ArrayList<>(data);
        this.profilManager = new ProfileManager(context);
        profilManager.open(); // Ensure database is opened
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_profil, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Profil profil = filteredData.get(position);
        holder.tvName.setText(profil.name);
        holder.tvLastName.setText(profil.lastanme);
        holder.tvNumber.setText(profil.number);
    }

    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    public void setFilteredData(ArrayList<Profil> data) {
        filteredData.clear(); // Efface toutes les données filtrées actuelles
        filteredData.addAll(data); // Ajoute les nouvelles données filtrées
        notifyDataSetChanged(); // Notifie à l'adaptateur que les données ont changé
    }

    // ViewHolder class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvLastName, tvNumber;
        ImageView imgEdit, imgDelete, imgCall;

        public MyViewHolder(@NonNull View v) {
            super(v);
            // Initialize views
            tvName = v.findViewById(R.id.tvname_profil);
            tvLastName = v.findViewById(R.id.tvlastname_profil);
            tvNumber = v.findViewById(R.id.tvnumber_profil);

            imgDelete = v.findViewById(R.id.imageViewdelete_profil);
            imgEdit = v.findViewById(R.id.imageViewedit_profil);
            imgCall = v.findViewById(R.id.imageViewcall_profil);

            // Implement edit, delete, call functionality
            imgEdit.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && position < filteredData.size()) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = LayoutInflater.from(context);

                    View dialogView = inflater.inflate(R.layout.view_dialog, null);
                    alertDialogBuilder.setView(dialogView);

                    EditText editTextName = dialogView.findViewById(R.id.editNameDialog);
                    EditText editTextLastName = dialogView.findViewById(R.id.editLastNameDialog);
                    EditText editTextNumber = dialogView.findViewById(R.id.editNumberDialog);

                    Profil profil = filteredData.get(position);
                    editTextName.setText(profil.name);
                    editTextLastName.setText(profil.lastanme);
                    editTextNumber.setText(profil.number);

                    alertDialogBuilder.setPositiveButton("Edit", (dialogInterface, i) -> {
                        String editedName = editTextName.getText().toString().trim();
                        String editedLastName = editTextLastName.getText().toString().trim();
                        String editedNumber = editTextNumber.getText().toString().trim();

                        // Vérifier que les champs ne sont pas vides avant la mise à jour
                        if (!editedName.isEmpty() && !editedLastName.isEmpty() && !editedNumber.isEmpty()) {
                            profilManager.update(editedName, editedLastName, editedNumber);
                            Profil updatedProfile = new Profil(editedName, editedLastName, editedNumber);
                            filteredData.set(position, updatedProfile);
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        }
                    });

                    alertDialogBuilder.setNegativeButton("Exit", (dialogInterface, i) -> {
                        // Ne rien faire si l'utilisateur choisit de quitter
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });

            imgDelete.setOnClickListener(view -> {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Delete");
                alert.setMessage("Confirm deletion?");
                alert.setPositiveButton("Delete", (dialogInterface, i) -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && position < filteredData.size()) {
                        Profil profile = filteredData.get(position);
                        profilManager.delete(profile.number);
                        filteredData.remove(position);
                        notifyDataSetChanged();
                    }
                });
                alert.setNegativeButton("Exit", null);
                alert.show();
            });




            imgCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && position < filteredData.size()) {
                        String phoneNumber = filteredData.get(position).number;

                        // Vérifier la permission CALL_PHONE
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            // Si la permission est accordée, effectuer l'appel
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + phoneNumber));
                            context.startActivity(callIntent);
                        } else {
                            // Si la permission n'est pas accordée, demander à l'utilisateur
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                        }
                    }
                }
            });

        }
    }



    // Close the database when adapter is no longer needed
    public void closeDatabase() {
        profilManager.close();
    }
}

package itbs.sem2.mycallerapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyProfilAdapter extends BaseAdapter {
    //constructeur
    //creer  les views
    Context con ;
    ArrayList<Profil> data ;
    MyProfilAdapter(Context con, ArrayList<Profil> data)
    {
        this.con=con;
        this.data=data;
    }
    @Override
    public int getCount() {
        //retourner le nombre vdes views a creer
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //creation d'un prototype
        //convertir code xml
        LayoutInflater inf = LayoutInflater.from(con);
        View v = inf.inflate(R.layout.view_profil,null);
        //Récuperation des sous views /HOLDERS

        TextView tvname=v.findViewById(R.id.tvname_profil);
        TextView tvlastname=v.findViewById(R.id.tvlastname_profil);
        TextView tvnumber=v.findViewById(R.id.tvnumber_profil);

        ImageView imgDelete=v.findViewById(R.id.imageViewdelete_profil);

        ImageView imgEdit=v.findViewById(R.id.imageViewedit_profil);
        ImageView imgCall=v.findViewById(R.id.imageViewcall_profil);
        ProfileManager pf = new ProfileManager(con);
        pf.open();

        //recuperation de la donnée
        Profil p = data.get(position);

        //affecter le view/HOLDER
        tvname.setText(p.name);
        tvlastname.setText(p.lastanme);
        tvnumber.setText(p.number);

        //action sur les HOLDERS

        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //numerotation
                Intent i = new Intent();
                i.setAction(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+p.number));
                con.startActivity(i);
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pour afficher une alerte dialogue de suppression
                AlertDialog.Builder alert= new AlertDialog.Builder(con);
                alert.setTitle("Suppression");
                alert.setMessage("Confirmer la suppression?");
                alert.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //suppresion
                        data.remove(position);
                        pf.delete(p.number);
                        //pour refracher la data
                        notifyDataSetChanged();

                    }
                });

                alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.setNeutralButton("Exit",null);
                alert.show();


            }
        });

        //v=linearlayout globale


        //retournerv le button
        return v;
    }
}

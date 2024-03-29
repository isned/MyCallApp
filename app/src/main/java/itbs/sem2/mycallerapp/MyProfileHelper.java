package itbs.sem2.mycallerapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyProfileHelper extends SQLiteOpenHelper {
    // Declaration of table name and field titles
    public static final String table_profil = "Profil";

    public static final String col_name = "Name";
    public static final String col_lastName = "LastName";
    public static final String col_number = "Number";

    String req = "create table " + table_profil + "("
            + col_number+ " Text primary Key ,"
            + col_name + " Text not null,"
            + col_lastName + " Text not null"

            + ")";

    public MyProfileHelper(@Nullable Context context, @Nullable String name,
                           @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(req);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_profil);
        onCreate(db);
    }
}
